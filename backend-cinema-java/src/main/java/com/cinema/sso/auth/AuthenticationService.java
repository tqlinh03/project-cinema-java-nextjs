package com.cinema.sso.auth;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cinema.sso.auth.dto.AuthenticationResponse;
import com.cinema.sso.auth.dto.RegistrationRequest;
import com.cinema.sso.email.EmailService;
import com.cinema.sso.email.EmailTemplateName;
import com.cinema.sso.modal.role.RoleRepository;
import com.cinema.sso.modal.token.TokenRepository;
import com.cinema.sso.modal.token.entities.Token;
import com.cinema.sso.modal.user.UserMapper;
import com.cinema.sso.modal.user.UserRepository;
import com.cinema.sso.modal.user.UserService;
import com.cinema.sso.modal.user.dto.UserResponse;
import com.cinema.sso.modal.user.entities.User;
import com.cinema.sso.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper  userMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public void register(RegistrationRequest request) throws MessagingException {
        var email = userRepository.findByEmail(request.getEmail());
        if (email.isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }
        var userRole = roleRepository.findByName("USER")
                // todo - better exception handling
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var user = ((User) auth.getPrincipal());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
    
        setRefeshTokenCookie(response, refreshToken);
        userService.updateUserToken(refreshToken, user.getEmail());

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    // @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Mã kích hoạt không hợp lệ"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            // sendValidationEmail(savedToken.getUser());
            throw new RuntimeException(
                    "Mã kích hoạt tài khoản đã hết hạn");
            // "Mã thông báo kích hoạt đã hết hạn. Mã thông báo mới đã được gửi đến email
            // của bạn.");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng."));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    private String generateAndSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(2))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    void sendCodeEmail(String email) throws MessagingException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng."));
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation");
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String refreshToken = null;
        final String userEmail;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh_token")) {
                refreshToken = cookie.getValue();
            }
        }
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void setRefeshTokenCookie(HttpServletResponse response, String refreshToken) {
        var cookie = new Cookie("refresh_token", refreshToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
    }

    public UserResponse fetchAccount(String accessToken) {
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
        // var user = (User) authentication.getPrincipal();
        var userName = jwtService.extractUsername(accessToken)  ;
        System.out.println("user");
        var data = userRepository.findByEmail(userName)
            .map(userMapper::toUserResponse)
            .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng."));
        return data; 
    }   

    public void logout(HttpServletResponse response) {
        var cookie = new Cookie("refresh_token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

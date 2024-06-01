package com.cinema.sso.handler;

public class ExceptionResponseWrapper {
  private ExceptionResponse data;

    public ExceptionResponseWrapper(ExceptionResponse data) {
        this.data = data;
    }

    public ExceptionResponse getData() {
        return data;
    }

    public void setData(ExceptionResponse data) {
        this.data = data;
    }
}

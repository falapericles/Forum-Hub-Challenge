package com.example.challenge.infra.exception;

public class UsuarioSemPermissaoException extends RuntimeException {
  public UsuarioSemPermissaoException() {
    super("Usuário não tem permissão para essas alterações.");
  }
}
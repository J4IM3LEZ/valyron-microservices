package com.realmofvalyron.ms_objetos.exception;

public record ErrorResponse(String timestamp, int status, String error, String message, String path) {}

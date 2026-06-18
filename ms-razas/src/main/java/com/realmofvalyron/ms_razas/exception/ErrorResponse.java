package com.realmofvalyron.ms_razas.exception;

public record ErrorResponse(String timestamp, int status, String error, String message, String path) {}

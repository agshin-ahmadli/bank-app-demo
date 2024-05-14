package com.example.bankinkapp.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private final int errorCode;

    private final String errorMessage;
}

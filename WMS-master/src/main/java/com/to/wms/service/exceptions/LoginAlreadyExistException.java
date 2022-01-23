package com.to.wms.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Login already exists in the database!")
public class LoginAlreadyExistException extends Exception {
}

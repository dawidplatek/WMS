package com.to.wms.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Address already exists in the database!")
public class AddressAlreadyExistException extends Exception {
}

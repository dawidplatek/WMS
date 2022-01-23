package com.to.wms.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Location already exists in the database!")
public class LocationAlreadyExistException extends Exception {
}

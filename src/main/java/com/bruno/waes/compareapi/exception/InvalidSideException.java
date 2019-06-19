package com.bruno.waes.compareapi.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown content from one of the sides of the diff is not valid.
 * The content is invalid when:
 * The side option is not Right or Left.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST,
        reason = "The option choosed is not a valid side.")
public class InvalidSideException extends Exception {
}
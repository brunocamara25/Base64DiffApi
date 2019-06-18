package com.bruno.waes.compareapi.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when attempts to add content from one of the sides of the diff an invalid content.
 * The content is invalid when one of the following cases is true:
 * Is null or empty; the data contains an invalid base64 string.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST,
        reason = "The option choosed is not a valid side.")
public class InvalidSideException extends Exception {
}
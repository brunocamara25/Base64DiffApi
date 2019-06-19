package com.bruno.waes.compareapi.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown the content from one of the sides of the diff is an invalid content.
 * The content is invalid when:
 * Is null or empty; the data contains an invalid base64 string.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST,
        reason = "The content is empty or contains an invalid base64.")
public class InvalidContentException extends Exception {
}
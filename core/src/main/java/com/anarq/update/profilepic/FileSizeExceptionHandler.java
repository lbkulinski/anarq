package com.anarq.update.profilepic;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * An exception handler for exceeding the maximum file size.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version March 25, 2020
 */
@ControllerAdvice
public final class FileSizeExceptionHandler {
    /**
     * Displays the result after handling the exception associated with exceeding the maximum file size.
     *
     * @return the HTML code for the result of exceeding the maximum file size
     */
    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
    public String handleException() {
        return "updateProfilePictureFileSizeLimitExceededResult";
    } //handleException
}

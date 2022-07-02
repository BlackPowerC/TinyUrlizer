package com.blackpowerc.tinyurlizer.exceptionhandling;

/**
 *
 * @author jordy jordy.fatigba@theopentrade.com
 */
public record ExceptionDetails(int httpCode, String message, String stackTrace) {
}

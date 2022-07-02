package com.blackpowerc.tinyurlizer.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Collectors;

/**
 *
 * @author jordy jordy.fatigba@theopentrade.com
 */
@RestControllerAdvice(basePackages = {"com.blackpowerc.tinyurlizer"})
public class ExceptionHandler
{
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionDetails> beanValidationExceptionMapping(ConstraintViolationException cve)
    {
        var details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                cve.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining()),
                getStackTraceAsString(cve)
        ) ;

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST) ;
    }

    private String getStackTraceAsString(Exception e)
    {
        StringWriter sw = new StringWriter() ;
        PrintWriter pw = new PrintWriter(sw) ;
        e.printStackTrace(pw) ;

        return sw.toString() ;
    }
}

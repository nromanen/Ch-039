package com.hospitalsearch.handlers;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Andrew Jasinskiy on 25.05.16
 */
@ControllerAdvice
@EnableWebMvc
class GlobalExceptionHandler {


    public static final String ERROR_VIEW = "500";
    public static final String PAGE_NOT_FOUND = "404";
    public static final String ACCESS_DENIED = "403";


/*    @ExceptionHandler({NoHandlerFoundException.class, NoSuchRequestHandlingMethodException.class,
            NullPointerException.class})*/

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundPage(Exception e, Model model, HttpStatus errorCode) {
        model.addAttribute("exception", e);
        model.addAttribute("errorCode", errorCode);
        System.out.println("4034444444444444444");
        return "/error/404";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDenied(Exception e, Model model, HttpStatus errorCode) {
        model.addAttribute("exception", e);
        model.addAttribute("errorCode", errorCode);
        System.out.println("4034444444444444444");
        return "/404";
    }

    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class,
            MissingPathVariableException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String error(Exception e, Model model, HttpStatus errorCode) {
        model.addAttribute("exception", e);
        model.addAttribute("errorCode", errorCode);
        System.out.println("40234234444444444444444");
        return "500";
    }

    @ExceptionHandler({BindException.class, HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class, MissingServletRequestParameterException.class,
            TypeMismatchException.class,})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String error400(Exception e, Model model, HttpStatus errorCode) {
        model.addAttribute("exception", e);
        model.addAttribute("errorCode", errorCode);
        System.out.println("40444444444sdfsdf44444444");
        return "404";
    }
}

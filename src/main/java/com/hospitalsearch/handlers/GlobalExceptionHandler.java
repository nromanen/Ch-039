/*
package com.hospitalsearch.handlers;

import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler({NullPointerException.class, TemplateProcessingException.class,
            TemplateInputException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleError(Exception e) {
        logger.error("Error 500 " + e);
        return "error/500";
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class,
            MissingServletRequestParameterException.class, TypeMismatchException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleError405(Exception e) {
        logger.error("Error 405 " + e);
        return "error/500";
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDenied(Exception e) {
        logger.error("Error 403 " + e);
        return "error/403";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(Exception e) {
        logger.error("Error 404 " + e);
        return "error/404";
    }
}
*/

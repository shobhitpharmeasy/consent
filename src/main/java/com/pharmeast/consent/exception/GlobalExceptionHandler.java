package com.pharmeast.consent.exception;

@org.springframework.web.bind.annotation.ControllerAdvice
public class GlobalExceptionHandler
    extends
    org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(
        org.springframework.web.bind.MethodArgumentNotValidException.class
    )
    public org.springframework.http.ResponseEntity< Object > handleMethodArgumentNotValidException(
        org.springframework.web.bind.MethodArgumentNotValidException e
    ) {

        StringBuilder strBuilder = new StringBuilder();

        e.getBindingResult().getAllErrors().forEach( ( error ) -> {
            String fieldName;
            try {
                fieldName
                    = ((org.springframework.validation.FieldError) error).getField();

            } catch ( ClassCastException ex ) {
                fieldName = error.getObjectName();
            }
            String message = error.getDefaultMessage();
            strBuilder.append( String.format( "%s: %s%n", fieldName, message ) );
        } );

        return new org.springframework.http.ResponseEntity<>(
            strBuilder.substring( 0, strBuilder.length() - 1 ),
            org.springframework.http.HttpStatus.BAD_REQUEST
        );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(
        {
            com.pharmeast.consent.exception.ServiceNotFoundException.class,
            com.pharmeast.consent.exception.EmployeeNotfoundException.class
        }
    )
    public org.springframework.http.ResponseEntity< com.pharmeast.consent.dto.ApiErrorDto > handleNotFoundExceptions(
        RuntimeException ex, jakarta.servlet.http.HttpServletRequest request ) {

        com.pharmeast.consent.dto.ApiErrorDto errorResponse
            = new com.pharmeast.consent.dto.ApiErrorDto(
            java.time.LocalDateTime.now(), request.getRequestURI(), request.getMethod(),
            org.springframework.http.HttpStatus.NOT_FOUND.value(),
            org.springframework.http.HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(), null, ex.getClass()
        );
        return org.springframework.http.ResponseEntity.status(
            org.springframework.http.HttpStatus.NOT_FOUND ).body( errorResponse );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(
        {
            com.pharmeast.consent.exception.WrongEmailException.class,
            com.pharmeast.consent.exception.MissingParameterException.class,
            com.pharmeast.consent.exception.MissingEmailException.class,
            com.pharmeast.consent.exception.MissingServiceException.class
        }
    )
    public org.springframework.http.ResponseEntity< com.pharmeast.consent.dto.ApiErrorDto > handleBadRequestExceptions(
        RuntimeException ex, jakarta.servlet.http.HttpServletRequest request ) {

        com.pharmeast.consent.dto.ApiErrorDto errorResponse
            = new com.pharmeast.consent.dto.ApiErrorDto(
            java.time.LocalDateTime.now(), request.getRequestURI(), request.getMethod(),
            org.springframework.http.HttpStatus.BAD_REQUEST.value(),
            org.springframework.http.HttpStatus.BAD_REQUEST.getReasonPhrase(),
            ex.getMessage(), null, ex.getClass()
        );
        return org.springframework.http.ResponseEntity.status(
            org.springframework.http.HttpStatus.BAD_REQUEST ).body( errorResponse );
    }

//    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
//    public org.springframework.http.ResponseEntity< com.pharmeast.consent.dto.ApiErrorDto > handleGeneralException(
//        Exception ex, jakarta.servlet.http.HttpServletRequest request ) {
//
//        com.pharmeast.consent.dto.ApiErrorDto errorResponse
//            = new com.pharmeast.consent.dto.ApiErrorDto(
//            java.time.LocalDateTime.now(), request.getRequestURI(), request.getMethod(),
//            org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR.value(),
//            org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
//            ex.getMessage(), null, ex.getClass()
//        );
//        // Log the exception details here
//        return org.springframework.http.ResponseEntity.status(
//            org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR ).body(
//            errorResponse );
//    }
}

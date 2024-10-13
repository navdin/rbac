package com.example.rbac.exception;

import com.example.rbac.exception.custom.NotAuthorizedException;
import com.example.rbac.exception.custom.RoleAlreadyExistsException;
import com.example.rbac.exception.custom.RoleNotFoundException;
import com.example.rbac.exception.custom.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityExceptionHandlers {


    @ExceptionHandler(value = {UsernameAlreadyExistsException.class})
    public ResponseEntity<Object> handleUsernameExistsException
            (UsernameAlreadyExistsException usernameAlreadyExistsException) {

        return new ResponseEntity<>(usernameAlreadyExistsException.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {RoleAlreadyExistsException.class})
    public ResponseEntity<Object> handleRoleExistsException
            (RoleAlreadyExistsException roleAlreadyExistsException) {

        return new ResponseEntity<>(roleAlreadyExistsException.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {NotAuthorizedException.class})
    public ResponseEntity<Object> handleNotAuthorizedException
            (NotAuthorizedException notAuthorizedException) {

        return new ResponseEntity<>(notAuthorizedException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class, RoleNotFoundException.class})
    public ResponseEntity<Object> handleUsernameNotFoundException
            (Exception notFoundException) {

        return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }



}

// package com.example.demo.exception;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice
// public class GlobalExceptionHandler {

//     @ExceptionHandler(ResourceNotFoundException.class)
//     public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
//         return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//     }

//     @ExceptionHandler(BadRequestException.class)
//     public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
//         return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//     }

//     @ExceptionHandler(Exception.class)
//     public ResponseEntity<String> handleGeneral(Exception ex) {
//         return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
//     }
// }

package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        e.printStackTrace(); // Add logging to see the actual error
        if (e.getMessage().contains("User not found") || e.getMessage().contains("Invalid credentials")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
}
package com.thebizio.biziosalonms.advice;

import com.thebizio.biziosalonms.dto.ResponseMessageDto;
import com.thebizio.biziosalonms.exception.AlreadyExistsException;
import com.thebizio.biziosalonms.exception.InvalidDataException;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationAdvice {

	Logger logger = LoggerFactory.getLogger(ValidationAdvice.class);

	@ExceptionHandler(AlreadyExistsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseMessageDto alreadyExistsAdvice(AlreadyExistsException exception) {
		return new ResponseMessageDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseMessageDto validationAdvice(ValidationException exception){
		return new ResponseMessageDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseMessageDto notFoundAdvice(NotFoundException exception) {
		return new ResponseMessageDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@ExceptionHandler(InvalidDataException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseMessageDto invalidDataAdvice(InvalidDataException exception) {
		return new ResponseMessageDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(ServerException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseMessageDto serverAdvice(ServerException exception) {
		return new ResponseMessageDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
	}


}

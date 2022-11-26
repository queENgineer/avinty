package com.avinty.hr.exception;

import com.avinty.hr.util.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ExceptionProcessor {
	
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	@ResponseBody
	public ResponseEntity<?> validationFailed(HttpServletRequest req, MethodArgumentNotValidException exception) {
		ErrorDetail errorDetail = processBindingResult(req, exception, exception.getBindingResult());
		return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({InvalidParameterException.class})
	@ResponseBody
	public ResponseEntity<?> validationFailed(HttpServletRequest req, InvalidParameterException exception) {
		ErrorDetail errorDetail = new ErrorDetail(exception.getErrorCode(), exception.getErrorDesc());
		return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({BindException.class})
	@ResponseBody
	public ResponseEntity<?> bindException(HttpServletRequest req, BindException exception) {
		ErrorDetail errorDetail = processBindingResult(req, exception, exception.getBindingResult());
		return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
		
	}
	
	private ErrorDetail processBindingResult(HttpServletRequest req, Exception exception,
											 BindingResult bindingResult) {
		ErrorDetail errorDetail = null;
		if (bindingResult != null && bindingResult.hasErrors()) {
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				errorDetail = new ErrorDetail(Integer.parseInt(objectError.getCode()),
					objectError.getDefaultMessage());
				break;
			}
		}
		
		log.info("[ExceptionProcessor] [processBindingResult] -- Bad request: " + (bindingResult!=null ? bindingResult.getTarget() :"" )+ ",From "
			+ req.getRemoteAddr() + ",Request: " + req.toString() + ",ValidationException: "
			+ exception.getMessage() + ",Returned Result: " + (errorDetail != null ? errorDetail.toString() : ""));
		
		return errorDetail;
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({HttpMessageNotReadableException.class, HttpRequestMethodNotSupportedException.class})
	public void badRequest(HttpServletRequest req, Exception exception) {
		Principal pr = req.getUserPrincipal();
		log.info("Bad request from remote:" + req.getRemoteAddr() + ",Principle:" + ((pr != null) ? pr.getName() : " null"), exception);
	}
	
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler({org.springframework.web.HttpMediaTypeNotSupportedException.class})
	public void unsupportedMedia(HttpServletRequest req, Exception exception) {
			log.error("Unsupported Media Type from remote:" + req.getRemoteAddr() + " ", exception);
	}
	
	
	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	@ResponseBody
	public ResponseEntity<?> handleIncorrectResultSizeDataAccessException(HttpServletRequest req,
																		 IncorrectResultSizeDataAccessException ex) {
		ErrorDetail errorDetail = new ErrorDetail(ReturnCode.INTERNAL_ERROR.getCode(), ReturnCode.INTERNAL_ERROR.name());
		log.error("handleIncorrectResultSizeDataAccessException ", ex);
		
		return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	public ResponseEntity<?> handleGeneral(HttpServletRequest req, AccessDeniedException ex) {
		ErrorDetail errorDetail = new ErrorDetail(ReturnCode.ACCESS_DENIED.getCode(), ReturnCode.ACCESS_DENIED.name());
		log.error("Access Denied ", ex);
		
		return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<?> handleGeneral(HttpServletRequest req, Exception ex) {
		ErrorDetail errorDetail = new ErrorDetail(ReturnCode.INTERNAL_ERROR.getCode(), ReturnCode.INTERNAL_ERROR.name());
		log.error("General Error ", ex);
		
		return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
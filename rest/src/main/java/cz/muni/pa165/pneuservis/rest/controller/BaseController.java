package cz.muni.pa165.pneuservis.rest.controller;

import cz.muni.pa165.pneuservis.api.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Martin Spisiak <spisiak@mail.muni.cz> on 13/12/2016.
 */
public abstract class BaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorDTO handleRuntimeException(Exception e) {
        ErrorDTO error = new ErrorDTO();
        error.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        error.setMessage("Server has encountered an unexpected problem. Service is temporarily unavailable. Please try again later.");
        logger.error(e.getMessage());
        return error;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(Exception e) {
        ErrorDTO error = new ErrorDTO();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("Server has encountered a problem. Please contact the administrator.");
        logger.error(e.getMessage());
        return error;
    }

}

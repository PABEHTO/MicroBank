package com.bank.controller.exception;

import com.bank.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
@Controller
public class GlobalExceptionHandler implements ErrorController {
    private Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotEnoughMoneyException.class)
    public String handleNotEnoughMoneyException(NotEnoughMoneyException ex) {
        return "redirect:/my-page/withdraw?notEnoughMoney=true";
    }

    @ExceptionHandler(NotEnoughMoneyTransferException.class)
    public String handleNotEnoughMoneyTransferException(NotEnoughMoneyTransferException ex) {
        return "redirect:/my-page/transfer?notEnoughMoney=true";
    }

    @ExceptionHandler(SelfTransferException.class)
    public String handleSelfTransferException(SelfTransferException ex) {
        return "redirect:/my-page/transfer?selfTransfer=true";
    }

    @ExceptionHandler(TransferUserNotFoundException.class)
    public String handleTransferUserNotFoundException(TransferUserNotFoundException ex) {
        return "redirect:/my-page/transfer?transferUserNotFound=true";
    }

    @ExceptionHandler(Exception.class)
    public String handleException() {
        return "redirect:/error";
    }

    @RequestMapping("/error")
    public String getErrorPage() {
        return "public/error/error-page";
    }

    @ExceptionHandler(ExistsUserException.class)
    public String handleExistsUserException(ExistsUserException ex) {
        return "redirect:/registration?alreadyExists=true";
    }

}

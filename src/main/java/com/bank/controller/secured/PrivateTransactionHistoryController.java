package com.bank.controller.secured;

import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.service.AccountService;
import com.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/my-page")
public class PrivateTransactionHistoryController {
    private final UserService userService;

    @Autowired
    public PrivateTransactionHistoryController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/transaction-history")
    public String getTransactionHistoryPage(
            @RequestParam(name = "show_type", required = false) String showType,
            Model model) {
        Account account = userService.getTheUser().getAccount();
        List<Transaction> transactionsIn = account.getIncomingTransactions();
        List<Transaction> transactionsOut = account.getOutTransactions();
        model.addAttribute("transactionsIn", transactionsIn);
        model.addAttribute("transactionsOut", transactionsOut);
        model.addAttribute("show_type", showType);
        return "private/transaction-history-page";
    }
}

package com.bank.controller.secured;

import com.bank.entity.Account;
import com.bank.service.TransactionService;
import com.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/my-page")
public class PrivateTransactionController {
    private final TransactionService transactionService;
    private final UserService userService;

    @Autowired
    public PrivateTransactionController(
            TransactionService transactionService,
            UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping("/deposit")
    public String getDepositPage(Model model) {
        Account account = userService.getTheUser().getAccount();
        model.addAttribute("myAccount", account);
        model.addAttribute("myName", userService.getTheUser().getUsername());
        return "private/deposit-page";
    }

    @PostMapping("/deposit")
    public String getDepositPage(
            @RequestParam(name="amount") int amount
    ) {
        Account account = userService.getTheUser().getAccount();
        transactionService.deposit(account, amount);
        return "redirect:/my-page/deposit";
    }

    @GetMapping("/transfer")
    public String getTransferPage() {
        return "private/transfer-page";
    }

    @GetMapping("withdraw")
    public String getWithdrawPage() {
        return "private/withdraw-page";
    }
}

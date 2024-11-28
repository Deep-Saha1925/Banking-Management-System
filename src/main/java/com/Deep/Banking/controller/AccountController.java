package com.Deep.Banking.controller;

import com.Deep.Banking.model.Account;
import com.Deep.Banking.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/create")
    public String showCreateAccountForm() {
        return "create-account";
    }

    @PostMapping("/create")
    public String createAccount(@ModelAttribute Account account) {
        accountService.createAccount(account);
        return "redirect:/accounts/list";
    }

    @GetMapping("/list")
    public String listAccounts(Model model) {
        model.addAttribute("accounts", accountService.getAllAccounts());
        return "account-list";
    }

    @PostMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return "redirect:/accounts/list";
    }

    @GetMapping("/deposit/{id}")
    public String depositForm(@PathVariable Long id, Model model){

        Account account = accountService.getAccountById(id);
        if(account == null){
            throw new RuntimeException("Account not found");
        }else{
            model.addAttribute("account", account);
        }
        return "depositForm";
    }

    @PostMapping("/deposit/{id}")
    public String deposit(@PathVariable Long id, HttpServletRequest request) {
        double amount = Double.parseDouble(request.getParameter("amount"));
        Account account = accountService.deposit(id, amount);
        if(account == null){
            throw new RuntimeException("Low Balance..");
        }
        return "redirect:/accounts/list";
    }

    @GetMapping("/withdraw/{id}")
    public String withDrawForm(@PathVariable Long id, Model model){
        Account account = accountService.getAccountById(id);
        if(account == null){
            throw new RuntimeException("Account not found");
        }else{
            model.addAttribute("account", account);
        }
        return "withdrawForm";
    }

    @PostMapping("/withdraw/{id}")
    public String withdraw(@PathVariable Long id, HttpServletRequest request) {
        double amount = Double.parseDouble(request.getParameter("withdrawAmount"));
        Account account = accountService.withdraw(id, amount);
        if(account == null){
            throw new RuntimeException("Low Balance..");
        }
        return "redirect:/accounts/list";
    }

    @GetMapping("/detail")
    public String accountDetail(@RequestParam Long accountNumber, Model model) {
        accountService.getAccountByNumber(accountNumber).ifPresent(account -> model.addAttribute("account", account));
        return "account-detail";
    }


    @GetMapping("/edit/{id}")
    public String showEditAccountForm(@PathVariable Long id, Model model) {
        Account account = accountService.getAccountById(id);

        if(account == null){
            throw new RuntimeException("Account not found");
        }else{
            model.addAttribute("account", account);
        }

        return "editDetails";
    }

    @PostMapping("/edit")
    public String editDetails(@ModelAttribute Account account){
        accountService.editDetails(account);
        return "redirect:/accounts/list";
    }
}

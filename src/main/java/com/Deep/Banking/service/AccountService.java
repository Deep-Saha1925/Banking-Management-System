package com.Deep.Banking.service;

import com.Deep.Banking.model.Account;
import com.Deep.Banking.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    public Optional<Account> getAccountByNumber(Long accountNumber) {
        return Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber));
    }

    public Account deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).get();
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    public Account withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id).get();
        if (account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            return accountRepository.save(account);
        }
        return null;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void editDetails(Account account) {
        accountRepository.save(account);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).get();
    }

    public void save(Account account) {
        accountRepository.save(account);
    }
}

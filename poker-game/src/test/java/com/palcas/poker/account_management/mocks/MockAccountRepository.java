package com.palcas.poker.account_management.mocks;

import com.palcas.poker.persistance.account.Account;
import com.palcas.poker.persistance.account.AccountRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MockAccountRepository implements AccountRepository {
    private List<Account> savedAccounts;

    public MockAccountRepository(List<Account> accounts) {
        savedAccounts = new ArrayList<>();
        savedAccounts.addAll(accounts);
    }

    @Override
    public Account loadAccount(String name) throws IOException {
        for (Account account : savedAccounts) {
            if (account.getName().equals(name)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public void saveAccount(Account account) throws IOException {
        boolean found = false;
        for (Account savedAccount: savedAccounts) {
            if (savedAccount.getName().equals(account.getName())) {
                savedAccount = account;
                found = true;
                break;
            }
        }
        if (!found) {
            savedAccounts.add(account);
        }
    }
}

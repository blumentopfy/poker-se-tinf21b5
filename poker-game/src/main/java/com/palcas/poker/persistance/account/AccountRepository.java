package com.palcas.poker.persistance.account;

import java.io.IOException;

public interface AccountRepository {
    Account loadAccount(String name) throws IOException;
    void saveAccount(Account account) throws IOException;
}

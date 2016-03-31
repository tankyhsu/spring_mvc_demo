package com.springmvc.service;

import com.springmvc.model.Account;

import java.util.List;

/**
 * Created by Administrator on 2016/3/31.
 */
public interface AccountService {
    Account findById(long id);

    Account findByName(String name);

    void saveAccount(Account account);

    void updateAccount(Account account);

    void deleteAccountById(long id);

    List<Account> findAllAccounts();

    void deleteAllAccounts();

    public boolean isAccountExist(Account account);
}

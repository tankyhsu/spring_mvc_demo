package com.springmvc.service;

import com.springmvc.dao.AccountDAO;
import com.springmvc.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/31.
 */
// Spring Service Bean的标识.
@Component
@Transactional
public class AccountServiceImpl implements AccountService {
    private AccountDAO accountDAO;


    public Account findById(long id) {
        return accountDAO.findOne(id);
    }

    public Account findByName(String name) {
        return accountDAO.findByUsername(name);
    }

    public void saveAccount(Account account) {
        accountDAO.save(account);
    }

    public void updateAccount(Account account) {
        accountDAO.save(account);
    }

    public void deleteAccountById(long id) {
        accountDAO.delete(id);
    }

    public List<Account> findAllAccounts() {
        return accountDAO.findAll();
    }

    public void deleteAllAccounts() {
        accountDAO.deleteAll();
    }

    public boolean isAccountExist(Account account) {
        return false;
    }

    // -----------------//
    // Setter methods //
    // -----------------//

    //@Autowired
    public void setAccountDAO(AccountDAO userDao) {
        this.accountDAO = accountDAO;
    }
}

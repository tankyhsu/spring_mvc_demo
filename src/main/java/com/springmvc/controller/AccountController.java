package com.springmvc.controller;

import com.springmvc.model.Account;
import com.springmvc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;  //Service which will do all data retrieval/manipulation work

    //-------------------Retrieve All Accounts--------------------------------------------------------

    @RequestMapping(value = "/account/", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> listAllAccounts() {
        List<Account> accounts = accountService.findAllAccounts();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<Account>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
    }

    //-------------------Retrieve Single Account--------------------------------------------------------

    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccount(@PathVariable("id") long id) {
        System.out.println("获取ID为 " + id + "的账户");
        Account account = accountService.findById(id);

        if (account == null) {
            System.out.println("账户ID：" + id + "不存在");
            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Account>(account, HttpStatus.OK);
    }

    //-------------------Create a Account--------------------------------------------------------

    @RequestMapping(value = "/account/", method = RequestMethod.POST)
    public ResponseEntity<Void> createAccount(@RequestBody Account account, UriComponentsBuilder ucBuilder) {
        System.out.println("创建账户 " + account.toString());

        Account currentAccount = accountService.findById(account.getId());
        //如果id不重复
        if (currentAccount == null) {
            if (accountService.isAccountExist(account)) {
                System.out.println("账户" + account.getUsername() + "已经存在");
                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
            accountService.saveAccount(account);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/account/{id}").buildAndExpand(account.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        }
        //冲突提示
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    //------------------- Update a Account --------------------------------------------------------

    @RequestMapping(value = "/account/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Account> updateAccount(@PathVariable("id") long id, @RequestBody Account account) {
        System.out.println("Updating Account " + id);

        Account currentAccount = accountService.findById(id);

        if (currentAccount == null) {
            System.out.println("Account with id " + id + " not found");
            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
        }
        if (accountService.isAccountExist(currentAccount)) {
            System.out.println("账户" + account.getUsername() + "已经存在");
            return new ResponseEntity<Account>(HttpStatus.CONFLICT);
        }

        currentAccount.setUsername(account.getUsername());
        currentAccount.setAddress(account.getAddress());
        currentAccount.setEmail(account.getEmail());

        accountService.updateAccount(currentAccount);
        return new ResponseEntity<Account>(currentAccount, HttpStatus.OK);
    }


    //------------------- Delete a Account --------------------------------------------------------

    @RequestMapping(value = "/account/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Account> deleteAccount(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Account with id " + id);

        Account account = accountService.findById(id);
        if (account == null) {
            System.out.println("Unable to delete. Account with id " + id + " not found");
            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
        }

        accountService.deleteAccountById(id);
        return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
    }


}

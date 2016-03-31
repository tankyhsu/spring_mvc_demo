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

/**
 * Created by Administrator on 2016/3/31.
 */

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;  //Service which will do all data retrieval/manipulation work

    //-------------------Retrieve Single Account--------------------------------------------------------

    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccount(@PathVariable("id") long id) {
        System.out.println("获取ID为 " + id + "的账户");
        Account account = null;
        try {
            account = accountService.findById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(account, HttpStatus.NOT_FOUND);
        }
        if (account == null) {
            System.out.println("账户ID：" + id + "不存在");
            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Account>(account, HttpStatus.OK);
    }

    //-------------------Create a User--------------------------------------------------------

    @RequestMapping(value = "/account/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody Account account, UriComponentsBuilder ucBuilder) {
        System.out.println("创建账户 " + account.toString());

        if (accountService.isAccountExist(account)) {
            System.out.println("账户" + account.getUsername() + "已经存在");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        accountService.saveAccount(account);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/account/{id}").buildAndExpand(account.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}

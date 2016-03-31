package com.springmvc.controller;

import com.springmvc.model.Account;
import com.springmvc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/3/31.
 */

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;  //Service which will do all data retrieval/manipulation work

    //-------------------Retrieve Single Account--------------------------------------------------------

    @RequestMapping(value = "/Account/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccount(@PathVariable("id") long id) {
        System.out.println("Fetching Account with id " + id);
        Account account = accountService.findById(id);
        if (account == null) {
            System.out.println("Account with id " + id + " not found");
            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Account>(account, HttpStatus.OK);
    }
}

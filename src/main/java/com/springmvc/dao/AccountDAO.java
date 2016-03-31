package com.springmvc.dao;

import com.springmvc.model.Account;
import com.springmvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/3/30.
 */
public interface AccountDAO extends JpaRepository<Account, Long> {

    Account findByUsername(String username);
}

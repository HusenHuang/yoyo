package com.yoyo.authority.account.service.impl;

import com.yoyo.authority.account.dao.AccountDao;
import com.yoyo.authority.account.pojo.AccountDTO;
import com.yoyo.authority.account.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 @Author:MrHuang
 @Date: 2019/9/4 17:30
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountDao accountDao;

    public AccountDTO add(AccountDTO accountDTO) {
        return accountDao.insert(accountDTO);
    }

    public AccountDTO get(Integer aid) {
        return accountDao.findById(aid);
    }

    public boolean update(AccountDTO accountDTO) {
        return accountDao.updateById(accountDTO).getModifiedCount() > 0;
    }

    public boolean delete(Integer aid) {
        return accountDao.deleteById(aid).getDeletedCount() > 0;
    }
}

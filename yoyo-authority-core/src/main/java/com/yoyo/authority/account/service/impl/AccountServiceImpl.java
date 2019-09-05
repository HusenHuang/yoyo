package com.yoyo.authority.account.service.impl;

import com.yoyo.authority.account.dao.AccountDao;
import com.yoyo.authority.account.pojo.AccountDTO;
import com.yoyo.authority.account.pojo.AccountRegisterReq;
import com.yoyo.authority.account.pojo.AccountRegisterRsp;
import com.yoyo.authority.account.service.IAccountService;
import com.yoyo.framework.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Override
    public AccountDTO add(AccountDTO accountDTO) {
        return accountDao.insert(accountDTO);
    }

    @Override
    public AccountDTO get(String aid) {
        return accountDao.findById(aid);
    }

    @Override
    public boolean update(AccountDTO accountDTO) {
        return accountDao.updateById(accountDTO).getModifiedCount() > 0;
    }

    @Override
    public boolean delete(String aid) {
        return accountDao.deleteById(aid).getDeletedCount() > 0;
    }

    @Override
    public AccountRegisterRsp register(AccountRegisterReq req) {
        AccountDTO accountDTO = new AccountDTO().setActiveState(0)
                .setName(req.getName())
                .setPassword(req.getPassword())
                .setEmail(req.getEmail())
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        AccountDTO result = accountDao.insert(accountDTO);
        return BeanUtils.copy(result, AccountRegisterRsp.class);
    }
}

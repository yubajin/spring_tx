package com.yubajin.transaction.service;

import com.yubajin.transaction.pojo.User;

import java.util.List;

public interface IUserService {

    public Integer addUser(User user) throws Exception;

    public List<User> query();

    public Integer addUser1(String userName, String password) throws Exception;

    public Integer business(User user, String userName, String password) throws Exception;
}

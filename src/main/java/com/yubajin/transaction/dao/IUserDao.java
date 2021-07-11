package com.yubajin.transaction.dao;

import com.yubajin.transaction.pojo.User;

import java.util.List;

public interface IUserDao {

    public Integer addUser(User user) throws Exception;

    public List<User> query();

    public Integer addUser1(String userName, String passwd) throws Exception;
}

package com.yubajin.transaction.service.impl;


import com.yubajin.transaction.dao.IUserDao;
import com.yubajin.transaction.dao.impl.UserDaoImpl;
import com.yubajin.transaction.pojo.User;
import com.yubajin.transaction.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements IUserService {

    //@Autowired
    private IUserDao dao = new UserDaoImpl();

    public Integer addUser(User user) throws Exception {
        return dao.addUser(user);
    }

    public List<User> query() {
        return dao.query();
    }

    public Integer addUser1(String userName, String password) throws Exception {
        return dao.addUser1(userName,password);
    }

    public Integer business(User user, String userName, String password) throws Exception{
        dao.addUser(user);
        dao.addUser1(userName,password);
        return null;
    }
}

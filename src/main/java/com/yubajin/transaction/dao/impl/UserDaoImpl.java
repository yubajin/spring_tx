package com.yubajin.transaction.dao.impl;

import com.yubajin.transaction.dao.IUserDao;
import com.yubajin.transaction.pojo.User;
import com.yubajin.transaction.utils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements IUserDao {

    @Autowired
    private JdbcTemplate template;

    private String sql;

    Connection conn = null;

    PreparedStatement ps = null;

    @Override
    public Integer addUser(User user) throws Exception {
        int count = 0;
        sql = "insert into user(name,age)values(?,?)";
        ps = DbUtils.getConnection().prepareStatement(sql);
        ps.setString(1,user.getName());
        ps.setInt(2,user.getAge());
        count = ps.executeUpdate();

        return count;
    }

    @Override
    public List<User> query() {
        sql = "select * from user";
        List<User> users = template.query(sql, new RowMapper<User>() {
            /**
             * 我们自己手动将查询出来的结果集和Java对象中的属性做映射
             * @param resultSet
             * @param i
             * @return
             */
            public User mapRow(ResultSet resultSet, int i) {
                User user = new User();
                try {
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setAge(resultSet.getInt("age"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return user;
            }
        });
        return users;
    }

    @Override
    public Integer addUser1(String userName, String password) throws Exception {
        int count = 0;
        sql = "insert into t_user(name,password)values(?,?)";
        ps = DbUtils.getConnection().prepareStatement(sql);
        ps.setString(1,userName);
        ps.setString(2,password);
        count = ps.executeUpdate();
        return count;
    }

    public void dml(User user,String userName,String password){

        try {
            conn=DbUtils.getConnection();
            // 默认是自动提交的，事务管理我们需要关闭掉自动提交
            conn.setAutoCommit(false);
            sql = "insert into users(name,age)values(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,user.getName());
            ps.setInt(2,user.getAge());
            ps.executeUpdate();
            sql = "insert into user (username,password)values(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,userName);
            ps.setString(2,password);
            ps.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {

            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

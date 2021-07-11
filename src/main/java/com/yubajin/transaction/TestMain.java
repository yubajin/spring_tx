package com.yubajin.transaction;

import com.yubajin.transaction.pojo.User;
import com.yubajin.transaction.service.IUserService;
import com.yubajin.transaction.service.impl.UserServiceImpl;
import com.yubajin.transaction.utils.DbUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class TestMain {
    public static void main(String[] args) {
        /*ApplicationContext ac = new AnnotationConfigApplicationContext(JavaConfig.class);
        IUserService bean = ac.getBean(IUserService.class);
        User user  = new User();
        user.setName("TOM");
        user.setAge(18);
        bean.addUser(user);
        //System.out.println(bean.query());
        bean.addUser1("admin111","123");*/

        // 目标对象
        final IUserService target = new UserServiceImpl();
        /***
         * JDK动态代理实现事务管理
         */
        // 动态代理方式获取代理对象
        IUserService proxy = (IUserService) Proxy.newProxyInstance(JavaConfig.class.getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Connection conn = null;
                try{
                    conn = DbUtils.getConnection();
                    conn.setAutoCommit(false); // 关闭数据库的自动提交
                    method.invoke(target,args[0],args[1],args[2]); // 目标对象的执行
                    System.out.println("提交成功...");
                    conn.commit(); // 两个sql一同执行后，手动提交事务
                }catch (Exception e){
                    System.out.println("执行失败，数据回滚");
                    conn.rollback();
                }finally {
                    conn.close();
                }

                return null;
            }
        });
        User user  = new User();
        user.setName("小鱼");
        user.setAge(18);

        try {
            proxy.business(user,"root2","11111");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.yubajin.transaction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
@ComponentScan
public class JavaConfig {


    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/gp?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

    @Bean
    @DependsOn("dataSource")
    public JdbcTemplate template(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    /*public static void main(String[] args) {
        *//*ApplicationContext ac = new AnnotationConfigApplicationContext(JavaConfig.class);
        IUserService bean = ac.getBean(IUserService.class);
        User user  = new User();
        user.setName("TOM");
        user.setAge(18);
        bean.addUser(user);
        //System.out.println(bean.query());
        bean.addUser1("admin111","123");*//*

        // 目标对象
        final IUserService target = new UserServiceImpl();
        // 获取代理对象
        IUserService proxy = (IUserService) Proxy.newProxyInstance(JavaConfig.class.getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Connection conn = null;
                try{
                    conn = DbUtils.getConnection();
                    conn.setAutoCommit(false);
                    method.invoke(target,args[0],args[1],args[2]); // 目标对象的执行
                    System.out.println("提交成功...");
                    conn.commit();
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
        user.setName("波波");
        user.setAge(18);

        try {
            proxy.business(user,"root","11111");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}

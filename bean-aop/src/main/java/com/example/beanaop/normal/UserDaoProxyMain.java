package com.example.beanaop.normal;


/**
 * 静态代理
 * */
public class UserDaoProxyMain {
    public static void main(String[] args) {
        System.getProperties();
        UserDao userDaoProxy = new UserDaoProxy(new UserDaoImpl());
        userDaoProxy.save();
    }
}

package com.example.beanaop.normal;

/**
 * 静态代理
 * */
public class UserDaoProxy implements UserDao{
    //目标类
    private UserDao daoTarget;

    //实列化带入目标类
    public UserDaoProxy(UserDao daoTarget) {
        this.daoTarget = daoTarget;
    }


    @Override
    public void save() {
        System.out.println("开启事务");
        daoTarget.save();
        System.out.println("关闭事务");
    }
}

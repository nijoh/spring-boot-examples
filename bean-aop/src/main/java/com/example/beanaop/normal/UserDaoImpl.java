package com.example.beanaop.normal;

public class UserDaoImpl implements UserDao{
    @Override
    public void save() {
        System.out.println("正在保存");
    }
}

package com.example.beanaop.jdkProxy;

public class UserServiceImpl implements UserService{
    @Override
    public int save(String name) {
        System.out.println(name+"save success");
        return 1;
    }

    @Override
    public void update(String name) {
        System.out.println(name+"update success");
    }
}

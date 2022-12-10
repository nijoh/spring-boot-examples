package com.example.beanaop.cglib;

public class TaixService {
    public boolean checkOnTaix(String name){
        System.out.println("乘客:"+name+"已上车");
        return true;
    }

    void checkDownTaix(){
        System.out.println("检测乘客已下车");
    }
}

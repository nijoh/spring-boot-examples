package com.nijo.example.web.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/parameterException")
    Map<String,String> parameterException(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<String,String> result=new HashMap<>();
        result.put("200","参数异常");
        System.out.println("执行/parameterException 返回结果:"+ JSON.toJSONString(result));
        return result;
    }

    @GetMapping("/test")
    Map<String,String> test(){
        Map<String,String> result=new HashMap<>();
        result.put("200","通许success");
        return result;
    }
}

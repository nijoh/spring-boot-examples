package com.example.beanaop.jdkProxy;

import org.apache.catalina.User;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

public class JdkProxyMain {
    public static void main(String[] args) throws IOException {
       UserService proxy= (UserService)Proxy.newProxyInstance(UserService.class.getClassLoader(),
                new Class[]{UserService.class},
                new UserServiceProxy(new UserServiceImpl()));
        proxy.save("nijo");
        proxy.update("huhu");
//
//        //生成class代理对象
        byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
                "com.sun.proxy.$Proxy0", new Class[]{UserService.class});
        FileOutputStream outputStream=new FileOutputStream("/保存的位置");
        outputStream.write(proxyClassFile);
        outputStream.flush();
        outputStream.close();

    }
}

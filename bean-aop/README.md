#SpringAop代理方式
## [静态代理](https://github.com/nijoh/spring-boot-examples/tree/master/bean-aop/src/main/java/com/example/beanaop/cglib)
## [JDK动态代理](https://github.com/nijoh/spring-boot-examples/tree/master/bean-aop/src/main/java/com/example/beanaop/jdkProxy)
> 1.UserService 目标类 -- 模拟用户接口  UserServiceImpl -- 用户接口实现类
> 2.UserServiceProxy 代类类 -- 委托调用目标方法，重写invoke方法做一些增强操作
> 3.JdkProxyMain 启动类 -- 包含手动生成Class代理类字节码
> 源码解析:[点击直达](https://blog.csdn.net/MostSnails/article/details/128227510)

## [Cglib动态代理](https://github.com/nijoh/spring-boot-examples/tree/master/bean-aop/src/main/java/com/example/beanaop/cglib)
> 1.TaixService 目标类 -- 模拟乘车
> 2.TaixProx 代理类 -- 委托调用目标类 重写intercept方法做一些增强操作
> 3.ProxyMain 启动类 -- 包含手动生成Class代理类字节码
> 源码解析:[点击直达](https://blog.csdn.net/MostSnails/article/details/128260546)
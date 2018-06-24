# 原有框架整合SpringBoot学习
# SpringBoot底层原理也是基于SpringMVC实现。[SpringMVC原理请移步](https://note.youdao.com/share/?id=6b38408f9e21444057368247833a91c7&type=note#/)
## 整合思路
- 自定义HandlerMapping
### 原有服务会以url方式注册到Map之中，而Spring的BeanNameUrlHandlerMapping也是以url->handler注册到BeanNameUrlHandlerMapping之中。自定HandlerMapping（CustomHandlerMapping）继承自AbstractDetectingUrlHandlerMapping [CustomHandlerMapping](https://github.com/lucky-xin/xin-springboot/blob/master/src/main/java/com/xin/springboot/web/servlet/handler/CustomHandlerMapping.java)。\<br>
## CustomHandlerMapping 添加`@Component`把CustomHandlerMapping当成bean注册到Spring IoC容器之中，DispatcherServlet初始化时会去IoC容器之中查找所有HandlerMapping实现类并根据HandlerMapping实现子类的优先级排序(实现PriorityOrdered，Ordered类，高优先级在前面）存入list之中，这点很重要，因为DispatcherServlet查找服务处理器时会遍历所有的HandlerMapping实现类，如果某一个HandlerMapping实现类获取到处理器了就返回该处理器，[具体实现请看](https://note.youdao.com/share/?id=6b38408f9e21444057368247833a91c7&type=note#/)。自定义HandlerMapping实现了PriorityOrdered并给及最高优先级,保证优先去自定义HandlerMapping查找处理器。
- 在IoC容器之中注册处理器（handler）
### 添加自定义监听类[XinWebListener](https://github.com/lucky-xin/xin-springboot/blob/master/src/main/java/com/xin/springboot/web/listenner/XinWebListener.java)完成handler的注册。根据Class获取自定义注解`CustomController`和`CustomService`拼装成url。如以下VideoQueryService获取两个服务url为video/query,video/realVideo。则以video/query,video/realVideo为bean名称,[使用HandlerMethod封装该方法注册到DefaultListableBeanFactory之中](https://github.com/lucky-xin/xin-springboot/blob/master/src/main/java/com/xin/springboot/web/method/BeanNameUrlRegisterHepper.java)
```java
@CustomController(value = "video")
public class VideoQueryService {

    @CustomService(value = "query", description = "摄像机查询服务", author = "lcx", since = "1.0", isLog = true)
    public void queryVideo(RequestContext context) {
        String name = StringUtil.toString(context.getParameter("name"));
        context.getResponse().setMessage("hello " + name + " v5v5v5v5v5");
    }

    @CustomService(value = "realVideo", description = "摄像机查询服务", author = "lcx", since = "1.0", isLog = true)
    public void realVideo(RequestContext context) {
        String name = StringUtil.toString(context.getParameter("name"));
        context.getResponse().putData("ggg", "fdfdfd");
        context.getResponse().putData("name", name);
    }
}

```
- 自定义HandlerAdapter
### DispatcherServlet初始化也会去IoC之中查找所有HandlerAdapter的实现类，自定义HandlerAdapter只要注册到IoC就可用，和HandlerMapping一样也是先去高优先级HandlerAdapter判断是否可以处理HandlerMapping查找handler时返回的handler如果可以则使用该HandlerAdapter。[具体实现请看](https://note.youdao.com/share/?id=6b38408f9e21444057368247833a91c7&type=note#/)自定义HandlerAdapter完成访问自定义服务相关功能，[具体实现](https://github.com/lucky-xin/xin-springboot/blob/master/src/main/java/com/xin/springboot/web/servlet/CustomHandlerAdapter.java)

## 为了把原有的老框架整合到SpringBoot之中，只需要自定义HandlerMapping和HandlerAdapter，并把handler注册到Spring IoC之中就可以了，为什么要整合呢？主要为了新框架使用了SpringBoot，为了不影响原有的框架的服务，把老框架的服务注册到Spring之中，这样原有框架能正常访问，还可以愉快的拥抱SpringBoot。。


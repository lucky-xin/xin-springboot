#原有框架整合SpringBoot学习
## 整合思路，原有服务会以url方式注册到Map之中，而Spring的BeanNameUrlHandlerMapping也是以url->handler注册到BeanNameUrlHandlerMapping之中。自定HandlerMapping[CustomHandlerMapping](https://github.com/lucky-xin/xin-springboot/blob/master/src/main/java/com/xin/springboot/web/servlet/handler/CustomHandlerMapping.java)

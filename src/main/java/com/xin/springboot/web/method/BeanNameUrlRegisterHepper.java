package com.xin.springboot.web.method;

import com.suntek.eap.log.EapServerLog;
import com.suntek.eap.pico.annotation.BeanService;
import com.suntek.eap.pico.annotation.LocalComponent;
import com.suntek.eap.pico.annotation.MessageConsumer;
import com.suntek.eap.pico.annotation.QueryService;
import com.suntek.eap.util.Dom4jUtil;
import com.xin.springboot.web.Constants;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 注册服务到ioc容器之中
 * @date 9:43 2018-05-17
 **/
public class BeanNameUrlRegisterHepper {

    public void registerUrlHandler(ServletContext servletContext) {
        try {
            String path = servletContext.getRealPath("/") + "WEB-INF/rest-servlet.xml";
            Document doc = Dom4jUtil.read(path);
            Element root = doc.getRootElement();
            for (Iterator<Element> it = root.elementIterator(); it.hasNext(); ) {
                Element comp = it.next();
                Attribute attr = comp.attribute("base-package");
                if (attr == null) {
                    continue;
                }
                doRegisterUrlHandler(attr.getText(), servletContext);
            }
        } catch (Exception e) {
            EapServerLog.log.error(e, e);
        }
    }

    public void testRegisterUrlHandler(ServletContext servletContext) {
        String packageName = "com.xin.springboot.eapservices";
        try {
            doRegisterUrlHandler(packageName, servletContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void doRegisterUrlHandler(String packageName, ServletContext servletContext)
            throws Exception {
//        EapServerLog.log.debug("LocalComponent-scan: " + packageName);
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context.getBeanFactory();
        DefaultListableBeanFactory localBeanFactory = new DefaultListableBeanFactory();
        for (Class clazz : getClasses(packageName)) {
            if (!clazz.isAnnotationPresent(LocalComponent.class)) {
                continue;
            }
            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(BeanService.class) && method.isAnnotationPresent(QueryService.class)) {
                    throw new RuntimeException("");
                }

                if (!method.isAnnotationPresent(BeanService.class) && !method.isAnnotationPresent(QueryService.class)) {
                    continue;
                }

                CustomHandlerMethodHolder handlerMethodHolder = HandlerMethodUtil.getHandlerMethod(clazz, method);
                RootBeanDefinition beanDefinition = handlerMethodHolder.getBeanDefinition();
                if (handlerMethodHolder.isRemote()) {
                    registry.registerBeanDefinition(handlerMethodHolder.getId(), beanDefinition);
                } else {
                    localBeanFactory.registerBeanDefinition(handlerMethodHolder.getId(), beanDefinition);
                }
            }
        }
        servletContext.setAttribute(Constants.LOCAL_BEAN_FACTORY_NAME, localBeanFactory);
    }

    private Iterable<Class> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }

        return classes;
    }

    private List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();

        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                if (packageName.startsWith("com.suntek.eap.msg")) {
                    continue;
                }
                Class clazz = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
                if (!clazz.isAnnotationPresent(LocalComponent.class) && !clazz.isAnnotationPresent(MessageConsumer.class)) {
                    continue;
                }
                classes.add(clazz);
            }
        }
        return classes;
    }
}

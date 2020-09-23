package com.github.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 一个创建Bean对象的工厂
 *
 * Bean：在计算机英语中，有可重用组件的含义。
 * JavaBean：用java语言编写的可重用组件。
 *      javabean >  实体类
 *
 *   它就是创建我们的service和dao对象的。
 *
 *   第一个：需要一个配置文件来配置我们的service和dao
 *           配置的内容：唯一标识=全限定类名（key=value)
 *   第二个：通过读取配置文件中配置的内容，反射创建对象
 *
 *   我的配置文件可以是xml也可以是properties
 */
public class BeanFactory {
    //定义一个properties对象
    private static Properties poprs;

    //定义一个容器来存储bean
    private static Map<String,Object> beanMap;
    static {
        try {
            //实例化对象
            poprs = new Properties();
            //获取Properties文件的流对象
            InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
            poprs.load(is);
            //实例化容器
            beanMap = new HashMap<String, Object>();
            //取出配置文件中所有的key
            Enumeration<Object> keys = poprs.keys();
            //遍历枚举
            while (keys.hasMoreElements()){
                //取出每一个key
                String key = keys.nextElement().toString();
                //根据key获取value
                String beanPath = poprs.getProperty(key);
                //反射创建对象
                Object value = null;

                try {
                    value = Class.forName(beanPath).newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //把key和value存储到容器中
                beanMap.put(key,value);
                System.out.println(beanPath);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("初始化properties失败！");
        }
    }
    public static Object getBean(String beanName){
        Object o = beanMap.get(beanName);
        System.out.println(o);
        return o;

    }

    /**
     * 根据key查询配置文件获取全限定类名
     * @param beanName
     * @return

    public static Object getBean(String beanName){
        Object bean = null;
            String beanPath = poprs.getProperty(beanName);
        try {
            System.out.println(beanPath.toString());
            bean = Class.forName(beanPath).newInstance();//每次都会调用默认构造函数创建对象
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    } */

}

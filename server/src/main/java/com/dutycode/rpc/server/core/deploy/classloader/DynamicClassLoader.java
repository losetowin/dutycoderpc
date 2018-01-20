package com.dutycode.rpc.server.core.deploy.classloader;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态类加载器
 * 动态加载指定路径下的jar包文件
 *
 * @author zhangzhonghua
 * @version 0.0.1
 * @date 2018/1/20
 */
public class DynamicClassLoader extends SecureClassLoader{

    private Logger logger = Logger.getLogger(DynamicClassLoader.class);

    public DynamicClassLoader(){}

    public DynamicClassLoader(ClassLoader loader){
        super(loader);
    }


    /**
     * class缓存
     */
    public static Map<String,Class<?>> classCache = new HashMap<String, Class<?>>();


    /**
     * jar列表,要被扫描的jar包列表
     */
    public static List<String> jarList = new ArrayList<String>();


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }


    /**
     * 动态从jar包中加载class
     * @param jarpath
     * @param className
     * @param fromCache
     * @return
     */
    public Class<?> findClass(String jarpath, String className, boolean fromCache){
        logger.info(String.format("find class from jar: jarpath=%s,className=%s,fromcache=%s", jarpath, className, fromCache);

        //检查是否从缓存中获取
        if (fromCache && classCache.containsKey(className)){
            return classCache.get(className);
        }


        //class的具体路径
        String classPath = className.replace(".","/").concat(".class");
        if (StringUtils.isNotBlank(jarpath)){
            //如果传入的jarpath为空,则从所有的jar包中扫描.
            for (String tmpjarpath : jarList){

            }
        }

        return null;//TODO
    }

}

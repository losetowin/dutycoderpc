package com.dutycode.rpc.server.core.deploy.classloader;

import com.dutycode.rpc.server.utils.FileHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 动态类加载器
 * 动态加载指定路径下的jar包文件
 *
 * @author zhangzhonghua
 * @version 0.0.1
 * @date 2018/1/20
 */
public class DynamicClassLoader extends SecureClassLoader {

    private Logger logger = Logger.getLogger(DynamicClassLoader.class);

    public DynamicClassLoader() {
    }

    public DynamicClassLoader(ClassLoader loader) {
        super(loader);
    }


    /**
     * class缓存
     */
    public static Map<String, Class<?>> classCache = new HashMap<String, Class<?>>();


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
     *
     * @param jarpath
     * @param className
     * @param fromCache
     * @return
     */
    public Class<?> findClass(String jarpath, String className, boolean fromCache) {
        logger.info(String.format("find class from jar: jarpath=%s,className=%s,fromcache=%s", jarpath, className, fromCache));

        //检查是否从缓存中获取
        if (fromCache && classCache.containsKey(className)) {
            return classCache.get(className);
        }


        //class的具体路径
        String classPath = className.replace(".", "/").concat(".class");
        byte[] clsBytes = null;
        if (StringUtils.isNotBlank(jarpath)) {
            //如果传入的jarpath为空,则从所有的jar包中扫描.
            for (String tmpjarpath : jarList) {
                clsBytes = getClassBytes(tmpjarpath, classPath);
                if (clsBytes != null) {
                    //已找到,则停止搜索
                    break;
                }
            }
        } else {
            clsBytes = getClassBytes(jarpath, classPath);
        }

        URL url = null;
        try {
            url = new URL("file", "", jarpath);
        }catch (Exception e){
            logger.error("get jar file url err", e);
        }

        return findClass(className, clsBytes, url);
    }


    /**
     * 从class字节中获取class
     * @param className 类全路径
     * @param classBytes class字节码
     * @param url jar包的URL路径
     * @return
     */
    public Class<?> findClass(String className, byte[] classBytes, URL url) {
        Class<?> cls = null;
        try {
            CodeSource codeSource = new CodeSource(url, (Certificate[]) null);
            ProtectionDomain protectionDomain = new ProtectionDomain(codeSource, null, this, null);
            cls = super.defineClass(className, classBytes, 0, classBytes.length, protectionDomain);
            resolveClass(cls);

            //添加到缓存
            classCache.put(className, cls);

        }catch (Exception e){
            logger.error("resolve class fail", e);
        }
        return cls;

    }

    /**
     * 从Jar包中读取Class文件数据
     *
     * @param jarpath   jar包路径
     * @param classPath class全路径
     * @return
     */
    public byte[] getClassBytes(String jarpath, String classPath) {

        JarFile jarfile = null;
        InputStream input = null;

        try {
            jarfile = new JarFile(jarpath);
            JarEntry jarEntity = jarfile.getJarEntry(classPath);

            if (jarEntity != null) {
                input = jarfile.getInputStream(jarEntity);
                byte[] classBytes = new byte[input.available()];
                input.read(classBytes);
                return classBytes;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (jarfile != null) {
                try {
                    jarfile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;

    }


    public void addURL(String jarUrl){
        if (StringUtils.isNotBlank(jarUrl) && !jarList.contains(jarUrl)){
            jarList.add(jarUrl);
        }
    }

    public void addURLs(List<String> urls){
        if (urls != null && urls.size() > 0){
            jarList.addAll(urls);
        }
    }

    /**
     * 添加jar包所在的文件夹路径
     * @param folders
     */
    public void addFolder(String... folders){

        for (String folder : folders){
            try {
                List<String> jarList = FileHelper.getLibPath(folder);
                addURLs(jarList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}

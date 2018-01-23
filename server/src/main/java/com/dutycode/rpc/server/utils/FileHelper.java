package com.dutycode.rpc.server.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Jar包工具类
 *
 * @author zhangzhonghua
 * @version 0.0.1
 * @date 2018/1/23
 */
public class FileHelper {

    /**
     * 获取目录下的jar包
     * 相同名称的jar包,只加载第一个，之后的不会被加载
     * 目前只加载jar包,其余形式不支持.仅判断是否是jar包的后缀名,不判断jar包本身属性.
     *
     * @param dir
     * @return
     */
    public static List<String> getLibPath(String dir) throws IOException {

        List<String> jarList = new ArrayList<String>();

        File f = new File(dir);
        //必须是目录
        if (f.exists() && f.isDirectory()) {
            File[] list = f.listFiles();
            if (list == null) {
                return null;
            }

            for (File tmpFile : list) {
                if (tmpFile.getName().endsWith(".jar")) {
                    jarList.add(tmpFile.getCanonicalPath());
                }
            }

        }

        return jarList;

    }


}
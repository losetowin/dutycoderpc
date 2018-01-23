package com.dutycode.rpc.server;

import com.dutycode.rpc.server.utils.FileHelper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author zhangzhonghua
 * @version 0.0.1
 * @date 2018/1/23
 */
public class FileUtilsTest {


    @Test
    public void testGetJarList(){

        try {
            List<String> tmp = FileHelper.getLibPath("/opt/wf/tmp/lib/");

            for (String s : tmp){
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.dutycode.rpc.server.bootstrap;

import com.dutycode.rpc.server.contract.context.Global;
import com.dutycode.rpc.server.contract.context.GlobalConstant;
import com.dutycode.rpc.server.contract.context.ServerConfig;
import com.dutycode.rpc.server.core.deploy.classloader.DynamicClassLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

/**
 * 启动器,启动主入口
 * 1.初始化配置文件
 * 2.加载log4j配置
 * 3.启动主socketserver
 * 4.注册关闭信号,平滑关闭系统
 * 5.注册心跳,增加心跳机制
 * <p>
 * <p>
 * 说明:
 * 日志目前仅有log4j,未支持其他日志组件,后续可统一出口,使用sl4j做统一出口
 *
 * @author zhangzhonghua
 * @version 0.0.1
 * @date 2018/1/20
 */
public class Main {


    private static Logger logger = null;

    /**
     * 输入参数说明:
     * -Drpc.service.name=xxx服务名称
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        //检查输入参数
        if (args == null || args.length < 1) {
            System.out.println("输入参数不正确,至少输入服务名");
            System.exit(1);
        }

        //获取所有输入参数
        HashMap<String, String> argsMap = new HashMap<String, String>();
        for (String arg : args) {
            String[] argarr = arg.split("=");
            if (argarr != null && argarr.length == 2) {
                String argname = argarr[0].replace("-D", "");
                String argval = argarr[1];

                argsMap.put(argname, argval);
            }
        }


        //获取服务名称
        String serviceName = argsMap.containsKey("rpc.service.name") ? argsMap.get("rpc.service.name") : null;

        if (StringUtils.isBlank(serviceName)) {
            System.out.println("服务名称为空,系统退出,检查输入参数");
            System.exit(1);
        }

        //服务配置文件路径，默认路径为/opt/rpc/service/deploy/{servicename}/rpc.properties
        String rpcproperties = GlobalConstant.BASE_RPC_SERVICE_PATH + serviceName + "/rpc.properties";

        //加载配置文件
        ServerConfig serverConfig = ServerConfig.getServerConfig(rpcproperties);
        //添加到全局中
        Global.getInstance().setServerConfig(serverConfig);

        //加载log4j配置文件
        loadLog4jProerties(serviceName);

        logger = Logger.getLogger(Main.class);


        //将jvm参数中的数据,写入到ServerConfig中
        Set<String> keySet = argsMap.keySet();
        for (String key : keySet) {
            serverConfig.set(key, argsMap.get(key));
        }


        //初始化类加载器,加载数据到缓存中
        DynamicClassLoader dcl = new DynamicClassLoader();
        //只加载/opt/rpc/service/deploy/xxxx/lib/和/opt/rpc/service/deploy/xxxx/下的jar包
        dcl.addFolder(GlobalConstant.BASE_RPC_SERVICE_PATH + serviceName + "/lib/",
                GlobalConstant.BASE_RPC_SERVICE_PATH + serviceName);

        //TODO 加载初始化的接口类,之后加

        //加载服务接口实现,提供代理,使用javasisit


        //TODO 加载结束的接口类,之后加


        //启动服务和接口

    }


    /**
     * 加载log4j配置文件
     * TODO  后续增加异步日志和同步日志功能.
     *
     * @param serviceName 服务名称
     */
    public static void loadLog4jProerties(String serviceName) {
        String log4jpath = GlobalConstant.BASE_RPC_SERVICE_PATH + serviceName + "/log4j.properties";

        File f = new File(log4jpath);
        if (f.exists()) {
            PropertyConfigurator.configure(log4jpath);
        } else {
            //使用默认的log4j配置文件, 默认在GlobalConstant.BASE_RPC_PATH + "/conf/log4j.properties"
            String defaultLog4jpath = GlobalConstant.RPC_BASE_PATH + "/conf/log4j.properties";
            PropertyConfigurator.configure(defaultLog4jpath);
        }
    }

}

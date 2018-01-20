package com.dutycode.rpc.server.contract.context;

/**
 * 全局配置常量数据
 * @author zhangzhonghua
 * @version 0.0.1
 * @date 2018/1/20
 */
public interface GlobalConstant {


    /**
     * RPC基础路径
     */
    final static String RPC_BASE_PATH = "/opt/rpc/";
    /**
     * RPC服务部署基础路径:服务将部署在此目录下
     */
    final static String BASE_RPC_SERVICE_PATH =  RPC_BASE_PATH + "service/deploy/";


}

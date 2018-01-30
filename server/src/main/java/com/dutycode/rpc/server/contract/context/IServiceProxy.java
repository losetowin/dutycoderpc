package com.dutycode.rpc.server.contract.context;

/**
 * 代理类接口
 * @author zhangzhonghua
 * @version 0.0.1
 * @date 2018/1/25
 */
public interface IServiceProxy {


    /**
     * 接口执行
     * @param context
     * @return
     * @throws Exception
     */
    public RpcResponse invoke(DCRpcContext context) throws Exception;


}

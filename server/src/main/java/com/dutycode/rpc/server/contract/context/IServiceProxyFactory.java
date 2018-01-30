package com.dutycode.rpc.server.contract.context;

/**
 * 接口代理的工厂类,每个服务仅有一个接口代理工厂类
 * @author zhangzhonghua
 * @version 0.0.1
 * @date 2018/1/25
 */
public interface IServiceProxyFactory {

    public IServiceProxy getProxy(String lookup) throws Exception;
}

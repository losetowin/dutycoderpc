package com.dutycode.rpc.server.core.proxy;

import com.dutycode.rpc.server.contract.context.DCRpcContext;

public interface IInvokeHandler {

	public void invoke(DCRpcContext context) throws Exception;
}

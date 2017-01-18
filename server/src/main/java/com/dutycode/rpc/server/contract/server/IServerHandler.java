package com.dutycode.rpc.server.contract.server;

import com.dutycode.rpc.server.contract.context.DCRpcContext;

public interface IServerHandler {

	public void writeResponse(DCRpcContext context);
}

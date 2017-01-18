package com.dutycode.rpc.server;

import org.junit.Test;

import com.dutycode.rpc.server.contract.context.ServerConfig;

public class ServerConfigTest {

	@Test
	public void testGetInt(){
		try {
			ServerConfig config = ServerConfig.getServerConfig("/Users/zzh/Documents/git/dutycoderpc/server/src/test/resources/rpc.properties");
			int workcount = config.getInt(ServerConfig.ASYNC_WORKER_COUNT);
			System.out.println(workcount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testGetString(){
		try {
			ServerConfig config =ServerConfig.getServerConfig("/Users/zzh/Documents/git/dutycoderpc/server/src/test/resources/rpc.properties");
			String serviceName = config.getString(ServerConfig.SERVICE_NAME);
			System.out.println(serviceName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package com.dutycode.rpc.server.contract.context;

public class Global {

	private static volatile Global global = null;

	private static final Object lock = new Object();

	private ServerConfig serverConfig = null;
	
	private Global(){};
	public static Global getInstance() {
		if (global == null) {
			synchronized (lock) {
				if (global == null) {
					global = new Global();
				}
			}
		}
		return global;
	}
	public ServerConfig getServerConfig() {
		return serverConfig;
	}
	public void setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}
	
	
}

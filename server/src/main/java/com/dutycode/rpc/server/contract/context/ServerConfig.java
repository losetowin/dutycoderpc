package com.dutycode.rpc.server.contract.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 服务端配置<br>
 *     
 *     配置文件内容<br>
 *     dutycode.rpc.tcp.listenPort tcp监听端口<br>
 *     dutycode.rpc.telnet.listenPort  telnet端口<br>
 *     dutycode.rpc.servicename	服务名称<br> 
 *     dutycode.rpc.async.worker.count 调度模型中的队列个数<br>
 *     dutycode.rpc.async.worker.subcount 调度模型中的每个队列对应的线程池大小<br>
 *     dutycode.rpc.tcp.task.timeout 调度模型中等待队列调度超时时间，超时将被抛弃<br>
 *     dutycode.rpc.tcp.workercount TCP收包线程数<br>
 *     
 * @Title：ServerConfig  
 * @author: zzh        
 * @date: 2017年1月18日 上午11:03:16      
 * @version     
 *
 */
public class ServerConfig {
	/**tcp监听端口*/
	public static final String TCP_LISTENPORT = "dutycode.rpc.tcp.listenPort";
	/**telnet端口*/
	public static final String TELNET_LISTENPORT = "dutycode.rpc.telnet.listenPort";
	/**服务名称*/
	public static final String SERVICE_NAME = "dutycode.rpc.servicename";
	/**调度模型中的队列个数*/
	public static final String ASYNC_WORKER_COUNT = "dutycode.rpc.async.worker.count";
	/**调度模型中的每个队列对应的线程池大小*/
	public static final String AYSNC_WORKER_SUBCOUNT = "dutycode.rpc.async.worker.subcount";
	/**调度模型中等待队列调度超时时间，超时将被抛弃*/
	public static final String TCP_TIMEOUT = "dutycode.rpc.tcp.task.timeout";
	/**TCP收包线程数*/
	public static final String TCP_WORKERCOUNT = "dutycode.rpc.tcp.workercount";
	/**INVOKER*/
	public static final String PROXY_INVOKER = "dutycode.rpc.proxy.invoker";
	
	
	private static Map<String,Object> propertyMap = new HashMap<String, Object>();
	
	
	
	private void set(String key , String val){
		propertyMap.put(key, val);
	}
	public int getInt(String key){
		String v = (String) (propertyMap.containsKey(key)?propertyMap.get(key):"");
		try {
			return Integer.parseInt(v);
		}catch (Exception e){
			return 0;
		}
	}
	
	public String getString(String key){
		String v = (String) (propertyMap.containsKey(key)?propertyMap.get(key):"");
		return v;
	}
	
	public boolean getBlooean(String key){
		String v = (String) (propertyMap.containsKey(key)?propertyMap.get(key):"");
		try {
			return Boolean.valueOf(v);
		}catch (Exception e){
			return false;
		}
	}
	
	
	public static ServerConfig getServerConfig(String path) throws Exception{
		ServerConfig config = new ServerConfig();
		if (StringUtils.isBlank(path)){
			throw new IllegalArgumentException("未传入path参数");
		}
		File f = new File(path);
		if (!f.exists()){
			throw new FileNotFoundException("未发现服务配置文件，请检查rpc.properties是否存在");
		}
		
		Properties p = new Properties();
		p.load(new FileInputStream(f));
		
		Set<Object> keys = p.keySet();
		
		for (Object o : keys){
			config.set(String.valueOf(o), p.getProperty(String.valueOf(o)));
		}
		
		return config;
	}
	
	
	
}

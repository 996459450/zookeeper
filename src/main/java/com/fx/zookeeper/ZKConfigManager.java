package com.fx.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ZKConfigManager {

//	private Sparkconfig sparkconfig;
	private ZkClient zkClient =  ZKUtils.getZkClient();
	
	
	public void loadConfig() {
//		new ZkCli
		zkClient.getChildren(ZKUtils.NODE_PATH);
//		zkClient.
	}
	
	public void getConfig() {
		zkClient.subscribeDataChanges("", new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String arg0) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void handleDataChange(String arg0, Object arg1) throws Exception {
				// TODO Auto-generated method stub
				
			}
		});
	}
}

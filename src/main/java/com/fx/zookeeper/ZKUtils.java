package com.fx.zookeeper;

import org.I0Itec.zkclient.ZkClient;

public class ZKUtils {

	public static String NODE_PATH="/config/spark";

	public static ZkClient getZkClient() {
		return new ZkClient("localhost:2181");
	}
}

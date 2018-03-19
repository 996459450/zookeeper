package com.fx.zkClient;

public class ZKTest {

	public static void main(String[] args) {
		String host = "192.168.77.141:2181";
		
//		ZKConf zkConf = new ZKConf();
		ZKConf zkConf = ZKConf.getInstance();
		
		zkConf.Init(host, 1000);
		
		zkConf.deleteNode("/data");
		String key = zkConf.getKey("/zk");
		System.out.println(key);
		zkConf.createNode("/data", "shdjadhjkasd".getBytes());
		String key1 = zkConf.getKey("/data");
		System.out.println(key1);
//		zkConf.deleteNode("/data");
//		zkConf.createNode("/data","".getBytes());
		zkConf.setData("/data", "sida.xml");
		String key2 = zkConf.getKey("/data");
		System.out.println(key2);
		try {
			zkConf.writeXML(key2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

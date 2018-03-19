package com.fx.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;

public class TestOne {

	public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
		String hoString = "192.168.77.141:2181";
		String path = "/zktest";
		ConnectionWatcher cw = new ConnectionWatcher();
		cw.connect(hoString);
		cw.create(path, path.getBytes());
		cw.close();
		
//		List<String> children = cw.zk.getChildren(path, true);
//		List<String> children = cw.getChilds(path);
//		for(String line : children) {
//			System.out.println("child is :"+line);
//		}
//		try {
//			cw.connect(hoString);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}

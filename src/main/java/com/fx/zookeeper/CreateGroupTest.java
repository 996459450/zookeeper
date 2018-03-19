package com.fx.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

public class CreateGroupTest {

	 private static String hosts = "192.168.1.8";
	 private static String groupName = "zoo";
	    
	 private CreateGroup createGroup = null;
 
	 /**
	  * zk初始化操作
	  * 
	  * */
//	 @Before
	 public void init() throws IOException, InterruptedException {
		 createGroup = new CreateGroup();
		 createGroup.connect(hosts);
	 }
	 
	 /**
	  * 创建分组
	  * */
	 public void testCreateGroup() throws KeeperException, InterruptedException {
		 createGroup.create(groupName);
	 }
	 
	 /**
	  * 销毁资源
	  * */
	 public void destroy() {
	        try {
	            createGroup.close();
	            createGroup = null;
	            System.gc();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

}

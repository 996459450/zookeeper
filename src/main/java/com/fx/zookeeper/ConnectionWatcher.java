package com.fx.zookeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ConnectionWatcher implements Watcher{

	private static final int SESSION_TIMEOUT = 5000;

    protected ZooKeeper zk = null;
    private CountDownLatch countDownLatch = new CountDownLatch(1);


	
	@Override
	public void process(WatchedEvent event) {

		KeeperState state = event.getState();
		
		if(state == KeeperState.SyncConnected) {
			countDownLatch.countDown();
		}
		
	}
	
	public void connect(String hosts) throws IOException {
		try {
			zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
			System.out.println("zk connection success!!!!!");
			countDownLatch.countDown();
		} catch (Exception e) {
			System.out.println("zk connected is failed!!!!");
		}
	}
	
	public List<String> getChilds(String path) {
		List<String> children  = new ArrayList<>();
		try {
			children = zk.getChildren(path, false);
			System.out.println("getchildren success!!!");
		} catch (KeeperException | InterruptedException e) {
			System.out.println("getChildren failed");
//			e.printStackTrace();
		}
		return children;
	}
	
	public void create(String path,byte[] data) throws KeeperException, InterruptedException {
		zk.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
	}
	/**
	 * 
	 * */
	public void delete(String groupName) {
		String path="/"+groupName;
		
		try {
			List<String> children = zk.getChildren(path, false);
			
			for(String child : children) {
				zk.delete(path+"/"+child, -1);
			}
			zk.delete(path, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 存储文件
	 * @param path 文件路径
	 * @param file 要存储的文件
	 * */
	public void setGroupData(String path,byte[] file) throws KeeperException, InterruptedException {
		zk.setData(path, file, -1);
	}

	/**
     * 释放资源
     * @throws InterruptedException
     */
    public void close() throws InterruptedException {
        if (null != zk) {
            try {
                zk.close();
            } catch (InterruptedException e) {
                throw e;
            }finally{
                zk = null;
                System.gc();
            }
        }
    }

}

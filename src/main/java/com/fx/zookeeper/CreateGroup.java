package com.fx.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class CreateGroup implements Watcher{

	private static final int SESSION_TIMEOUT=100;
	
	private ZooKeeper zk = null;
	//同步计数器
	private CountDownLatch countdownlatch = new CountDownLatch(1);
	
	@Override
	public void process(WatchedEvent event) {
		if(event.getState()==KeeperState.SyncConnected) {
			//计数器减一
			countdownlatch.countDown();
		}
			
	}
	
	/**
	 * 创建zk对象
	 * @param host 主机名
	 * @throws IOException 
	 * @throws InterruptedException 
	 * */
	public void connect(String host) throws IOException, InterruptedException {
		zk = new ZooKeeper(host, SESSION_TIMEOUT, this);
		countdownlatch.await();
	}
	
	/**
	 * 创建Group
	 * @param groupname 分组名称
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 * */
	public void create(String groupName) throws KeeperException, InterruptedException {
		String path = "/"+groupName;
		String createpath = zk.create(	path,null, Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		System.out.println("createPath: "+createpath);
	}
	
	/**
	 * 关闭zk
	 * @throws InterruptedException 
	 * */
	public void close() throws InterruptedException {
//		zk.close();
		if(zk != null) {
			try {
			zk.close();
			}catch (InterruptedException e) {
				throw e;
			}finally {
				zk = null;
				System.gc();
			}
		}
	}
}

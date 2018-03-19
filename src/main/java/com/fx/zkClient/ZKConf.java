package com.fx.zkClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class ZKConf {

	private Logger log = Logger.getLogger(ZKConf.class);
	private static ZKConf zkConf;
	private ZooKeeper zk;
	
	/**
	 * 单例对象，私有构造函数
	 * */
	private ZKConf() {
		
	}
	public static ZKConf getInstance() {
		if(zkConf == null) {
			zkConf = new ZKConf();
		}
		return zkConf;
	}
	
	public void Init(String host,int timeout) {
		try {
			CountDownLatch countDownLatch = new CountDownLatch(1);
			Watcher watcher = new ConnectedWatcher(countDownLatch);
			zk = new ZooKeeper(host, timeout, watcher);
			log.info("zkClient Init success!!!!");
			waitUntilConnected(zk, countDownLatch);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	static class ConnectedWatcher implements Watcher{

		private CountDownLatch countdownLatch;
		
		ConnectedWatcher(CountDownLatch countDownLatch) {
			this.countdownLatch = countDownLatch;
		}
		
		@Override
		public void process(WatchedEvent event) {
			// TODO Auto-generated method stub
			if(event.getState() == KeeperState.SyncConnected) {
				countdownLatch.countDown();
			}
		}
		
	}
	
	public void waitUntilConnected(ZooKeeper zk,CountDownLatch countDownLatch) {
		if(States.CONNECTING == zk.getState()) {
			try {
				countDownLatch.await();
			} catch (Exception e) {
				throw new IllegalStateException();
			}
		}
	}
	
	public String getKey(String path) {
		Stat stat = new Stat();
		byte[] data = null;
		try {
			data = zk.getData(path, false, stat);
			
//			log.info("get data success!!!!");
			log.warn("get data success!!!");
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
		
		return new String(data);
	}
	
	public void createNode(String path,byte[] data) {
		try {
			if(zk.exists(path, false) == null);
			zk.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//			System.out.println();
			log.info("create node is success!!!");
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteNode(String path) {
		try {
			zk.delete(path, -1);
			System.out.println("delete this node success!");
		} catch (InterruptedException | KeeperException e) {
			e.printStackTrace();
		}
	}
	
	public void setData(String path,String file) {
		BufferedReader reader = null;
		String line = "";
		String lines = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			while((line=reader.readLine()) !=null) {
					lines += line;
			}
			zk.setData(path, lines.getBytes(), -1);
			System.out.println(lines);
//			zk.create(path, lines.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (IOException | KeeperException | InterruptedException e) {
			e.printStackTrace();
		}finally {
			try {
				if(reader !=null) {
					reader.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
	}
	
	public void writeXML(String line) throws Exception {
		Document text = DocumentHelper.parseText(line);
		writer(text);
//		SAXReader reader = new SAXReader();
//		Document document = reader.read(line);
//		Element rootElement = document.getRootElement();
//		System.out.println(rootElement);
//		writer(document);
	}
	
	public void writer(Document document) throws Exception {  
		String path="";
        // 紧凑的格式  
        // OutputFormat format = OutputFormat.createCompactFormat();  
        // 排版缩进的格式  
        OutputFormat format = OutputFormat.createPrettyPrint();  
        // 设置编码  
        format.setEncoding("UTF-8");  
        // 创建XMLWriter对象,指定了写出文件及编码格式  
        // XMLWriter writer = new XMLWriter(new FileWriter(new  
        // File("src//a.xml")),format);  
//        if()
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(  
                new FileOutputStream(new File("hl.xml")), "UTF-8"), format);  
        // 写入  
        writer.write(document);  
        // 立即写入  
        writer.flush();  
        // 关闭操作  
        writer.close();  
    } 
}

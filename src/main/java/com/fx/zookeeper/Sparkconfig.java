package com.fx.zookeeper;

import java.io.Serializable;

public class Sparkconfig implements Serializable{
	private static final long serialVersionUID = 1L;

	private String host;
	
	private String port;
	
	private String user;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	
}

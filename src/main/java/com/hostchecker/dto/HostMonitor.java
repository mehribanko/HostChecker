package com.hostchecker.dto;

import java.util.Date;

public class HostMonitor {
	
	private Integer id;
	private String ip;
	private boolean isAlive;
	private Date lastAliveTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public Date getLastAliveTime() {
		return lastAliveTime;
	}
	public void setLastAliveTime(Date lastAliveTime) {
		this.lastAliveTime = lastAliveTime;
	}

}

package com.hostchecker.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class HostDto {
	
	private Integer id;
	private String name;
	private String ip;
	private Date createdAt;
	private Date updatedAt;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer integer) {
		this.id = integer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date date) {
		this.createdAt = date;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date date) {
		this.updatedAt = date;
	}

}

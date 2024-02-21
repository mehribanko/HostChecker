package com.hostchecker.service;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.hostchecker.dto.HostDto;
import com.hostchecker.dto.HostMonitor;
import com.hostchecker.dto.HostRegRequest;
import com.hostchecker.dto.HostStatus;
import com.hostchecker.dto.HostUpdateRequest;
import com.hostchecker.entity.Host;
import com.hostchecker.repo.HostRepository;
import io.jsonwebtoken.io.IOException;



@Service
public class HostService {
	
	@Autowired
	private HostRepository hostRepository;
	
	public Host registerhost(HostRegRequest request) {
		
		if(hostRepository.findByName(request.getName()).isPresent() || hostRepository.findByIp(request.getIp()).isPresent()){
			throw new DuplicateKeyException("같은 Host가 이미 있습니다.");
		}
		
		if(hostRepository.count()>= 100) {
			throw new IllegalStateException("호스트 등록은 100개로 제한 되어 있습니다");
		}
		
		Host host = new Host();
		host.setName(request.getName());
		host.setIp(request.getIp());
		host.setCreatedAt(new Date(System.currentTimeMillis()));
		
		return hostRepository.save(host);
	}
	
	public HostDto getHostById(Integer id) {
		
		Host host =  hostRepository.findById(id).orElse(null);
		
		return host != null ? mapHostToDto(host): null;
	}
	
	public List<HostDto> getAllHosts(){
		List<Host>  hosts = hostRepository.findAll();
		return hosts.stream().map(this::mapHostToDto).collect(Collectors.toList());
	}
	
	public Host updateHost(Integer id, HostUpdateRequest request) {

		Host host = hostRepository.findById(id).orElse(null);
		
		if(host != null) {
	        Optional<Host> hostWithSameName = hostRepository.findByName(request.getName());
	        if (hostWithSameName.isPresent() && !hostWithSameName.get().getId().equals(id)) {
	            throw new DuplicateKeyException("같은 이름을 가진 Host가 이미 있습니다.");
	        }

	        Optional<Host> hostWithSameIp = hostRepository.findByIp(request.getIp());
	        if (hostWithSameIp.isPresent() && !hostWithSameIp.get().getId().equals(id)) {
	            throw new DuplicateKeyException("같은 IP를 가진 Host가 이미 있습니다.");
	        }
	        
			host.setName(request.getName());
			host.setIp(request.getIp());
			host.setUpdatedAt(new Date(System.currentTimeMillis()));
			hostRepository.save(host);
		}
		return host;
	}
	
	public boolean deleteHost(Integer id) {
		if (hostRepository.existsById(id)) {
			hostRepository.deleteById(id);
	          return true;
	    } else {
	          return false;
	    }
	}
	
	private HostDto mapHostToDto(Host host) {
		HostDto hostDto = new HostDto();
		
		hostDto.setId(host.getId());
		hostDto.setName(host.getName());
		hostDto.setIp(host.getIp());
		hostDto.setCreatedAt(host.getCreatedAt());
		hostDto.setUpdatedAt(host.getUpdatedAt());
		return hostDto;
	}
	
	public HostStatus getHostStatus(Integer hostId) throws java.io.IOException {
		
		Host host = hostRepository.findById(hostId).orElse(null);
		if(host != null) {
			boolean isAlive = checkHostAlive(host.getIp());
			if(isAlive) {
				host.setLastAliveTime(new Date());
				hostRepository.save(host);
			}
			return mapHostToStatusDto(host, isAlive);
		}
		
		return null;
	}
	
	private boolean checkHostAlive(String ip) throws java.io.IOException {
		try {
			InetAddress address = InetAddress.getByName(ip);
			return address.isReachable(1000);		
	   } catch (IOException e){
		return false;
	  }
	}
	
	private HostStatus mapHostToStatusDto(Host host, boolean isAlive) {
		HostStatus hostStatus = new HostStatus();
		
		hostStatus.setHostId(host.getId());
		hostStatus.setHostname(host.getName());
		hostStatus.setIp(host.getIp());
		hostStatus.setCreatedAt(host.getCreatedAt());
		hostStatus.setUpdatedAt(host.getUpdatedAt());
		hostStatus.setAlive(isAlive);
		return hostStatus;
	}
	
	public List<HostMonitor> monitorHostStatus(Integer hostId) throws java.io.IOException, InterruptedException, ExecutionException{
		
		List<Host> hosts;
		
		if(hostId != null) {
			Host host = hostRepository.findById(hostId).orElse(null);
			hosts = (host != null) ? Collections.singletonList(host):Collections.emptyList();
			
		}else {
			hosts = hostRepository.findAll();
		}
		
		List<CompletableFuture<HostMonitor>> statasync = hosts
				.stream()
				.map(host-> CompletableFuture.supplyAsync(()-> {
					try {
						return getHostMonitor(host);
					} catch (java.io.IOException e1) {
						
						e1.printStackTrace();
					}
					return null;
				}, Executors.newCachedThreadPool()))
				.collect(Collectors.toList());
		
		CompletableFuture<Void> group= CompletableFuture.allOf(statasync.toArray(new CompletableFuture[0]));
				
			try {
				group.get(1, TimeUnit.SECONDS);				
			}catch (TimeoutException e) {
				e.printStackTrace();
			}
		
			return statasync
					.stream()		
					.map(CompletableFuture::join)
					.collect(Collectors.toList());
	}
	
	private HostMonitor getHostMonitor(Host host) throws java.io.IOException {
		boolean isAlive = checkHostAlive(host.getIp());
		return mapHostToMonitorStatus(host, isAlive);
	}
	
	private HostMonitor mapHostToMonitorStatus(Host host, boolean isAlive) {
		HostMonitor hostMonitor = new HostMonitor();
		
		hostMonitor.setId(host.getId());
		hostMonitor.setIp(host.getIp());
		hostMonitor.setAlive(isAlive);
		hostMonitor.setLastAliveTime(host.getLastAliveTime());
		return hostMonitor;
	}
}

package com.hostchecker.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hostchecker.entity.Host;

public interface HostRepository extends JpaRepository<Host, Integer> {

	Optional<Host> findByName(String name);
	
	Optional<Host> findByIp(String name);

	List<Host> findAll();


}

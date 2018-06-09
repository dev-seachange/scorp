package com.seachangesimulations.scorp.service;

import java.util.Hashtable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class RepositoryMap {

	private Hashtable<String, JpaRepository> reposByClassName = new Hashtable();
	
	public JpaRepository get(String key) {
		return reposByClassName.get(key);
	}
	
	public JpaRepository put(String key, JpaRepository value) {
		return reposByClassName.put(key, value);
	}

	
	
}

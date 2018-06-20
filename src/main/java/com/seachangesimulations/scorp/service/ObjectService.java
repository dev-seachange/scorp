package com.seachangesimulations.scorp.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seachangesimulations.scorp.domain.Actor;
import com.seachangesimulations.scorp.domain.Phase;
import com.seachangesimulations.scorp.repository.ActorPhaseAssignmentRepository;
import com.seachangesimulations.scorp.repository.ActorRepository;
import com.seachangesimulations.scorp.repository.PhaseRepository;

/**
 * This service covers the basic CRUD operations for the domain objects.
 * 
 */
@Service
public class ObjectService {

	/** A standard repository for this Domain Object. */
	@Autowired
	private ActorRepository actorRepository;

	/** A standard repository for this Domain Object. */
	@Autowired
	private PhaseRepository phaseRepository;
	
	/** */
	@Autowired
	private ActorPhaseAssignmentRepository actorPhaseAssignmentRepository;

	/** This repository gets set to the particular Domain Object repository. */
	@SuppressWarnings("rawtypes")
	private JpaRepository jpaRepositoryInUse;
	
	/** Establish this object, and create way to look up the appropriate repository. */
	public ObjectService() {
		//initializeRepoStore();
	}

	/**
	 * Creates lookup table to associate a Class with its repository.
	 TODO: removed due to intialization issues
	 */
	private void initializeRepoStore() {

		//reposByClassName.put("x", null);
		
		//repositoryMap.put("actor", actorRepository);
		//reposByClassName.put("phase", phaseRepository);
	}

	/**
	 * Deprecated.
	 * 
	 * @param className
	 * @param linkedHashMap
	 */
	public void createFromJson(String className, LinkedHashMap linkedHashMap) {

		setJpaRepositoryInUse(className);

		ObjectMapper mapper = new ObjectMapper();

		try {
			if (className.equalsIgnoreCase("actor")) {
				Actor actor = mapper.convertValue(linkedHashMap, Actor.class);
				actor.setId(null);
				this.actorRepository.save(actor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Returns the object of the class passed in by its id.
	 * 
	 * @param objectClass
	 * @param id
	 * @return
	 */
	public Object findById(String objectClass, Long id) {
		
		setJpaRepositoryInUse(objectClass);
		return this.jpaRepositoryInUse.findOne(id);
	}

	/**
	 * Returns all object of the class name passed in.
	 * 
	 * @param className
	 * @return
	 */
	public List findAll(String className) {

		setJpaRepositoryInUse(className);

		if (jpaRepositoryInUse != null) {
			return jpaRepositoryInUse.findAll();
		} else {
			return new ArrayList();
		}

	}

	/**
	 * 
	 * @param className
	 * @param id
	 * @param lhm
	 */
	@SuppressWarnings("unchecked")
	public void update(String className, Long id, LinkedHashMap lhm) {

		setJpaRepositoryInUse(className);

		ObjectMapper mapper = new ObjectMapper();
		
		try {
			if (className.equalsIgnoreCase("actor")) {
				Actor actor = mapper.convertValue(lhm, Actor.class);
				actor.setId(id);
				this.jpaRepositoryInUse.save(actor);
			} else if (className.equalsIgnoreCase("phase")) {
				Phase phase = mapper.convertValue(lhm, Phase.class);
				phase.setId(id);
				this.jpaRepositoryInUse.save(phase);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

	/**
	 * Deletes Object
	 * 
	 * @param className
	 * @param id
	 */
	public void delete(String className, Long id) {

		// Finding the object sets the repositoryInUse
		Object object = this.findById(className, id);

		if (object != null) {
			this.jpaRepositoryInUse.delete(object);
		} else {
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			System.out.println("could not find " + className + ", with id of " + id);
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

		}
	}

	/**
	 * Looks up the repository for this class.
	 * TODO: Thought I could replace this with a hashtable, but initialization had issues.
	 * 
	 * @param className
	 */
	private void setJpaRepositoryInUse(String className) {
		
		if ("actor".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = actorRepository;
		} else if ("phase".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = phaseRepository;
		} else if ("actorPhaseAssignment".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = actorPhaseAssignmentRepository;
		} else {
			jpaRepositoryInUse = null;
		}
	}
	
	
	
}
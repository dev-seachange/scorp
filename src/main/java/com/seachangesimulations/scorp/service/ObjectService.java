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
import com.seachangesimulations.scorp.domain.ActorPhaseAssignment;
import com.seachangesimulations.scorp.domain.Page;
import com.seachangesimulations.scorp.domain.PageAssignment;
import com.seachangesimulations.scorp.domain.Performance;
import com.seachangesimulations.scorp.domain.Phase;
import com.seachangesimulations.scorp.domain.RolePlay;
import com.seachangesimulations.scorp.repository.ActorPhaseAssignmentRepository;
import com.seachangesimulations.scorp.repository.ActorRepository;
import com.seachangesimulations.scorp.repository.PageAssignmentRepository;
import com.seachangesimulations.scorp.repository.PageRepository;
import com.seachangesimulations.scorp.repository.PerformanceRepository;
import com.seachangesimulations.scorp.repository.PhaseRepository;
import com.seachangesimulations.scorp.repository.RolePlayRepository;

/**
 * This service covers the basic CRUD operations for the domain objects.
 * 
 */
@Service
@Transactional
public class ObjectService {

	/** Standard repositories for Domain Objects. */
	@Autowired  // Dependency Inject this Value
	private ActorPhaseAssignmentRepository actorPhaseAssignmentRepository;
	/** A standard repository for this Domain Object. */
	@Autowired  // Dependency Inject this Value
	private ActorRepository actorRepository;
	@Autowired private PageAssignmentRepository pageAssignmentRepository;
	@Autowired private PageRepository pageRepository;
	@Autowired private PerformanceRepository performanceRepository;
	@Autowired private PhaseRepository phaseRepository;
	@Autowired private RolePlayRepository rolePlayRepository;
	
	/** This repository gets set to the particular Domain Object repository. */
	@SuppressWarnings("rawtypes")
	private JpaRepository jpaRepositoryInUse;
	
	/** Establish this object, and create way to look up the appropriate repository. */
	public ObjectService() {
		initializeRepoStore();
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
	 * 
	 * @param object
	 * @param className
	 */
	public void save(Object object, String className) {
		setJpaRepositoryInUse(className);
		this.jpaRepositoryInUse.save(object);
	}

	/**
	 * saveJson - called by create.
	 * @param className
	 * @param linkedHashMap
	 */
	public void saveJson(String className, LinkedHashMap linkedHashMap) {

		setJpaRepositoryInUse(className);

		ObjectMapper mapper = new ObjectMapper();  // JSon ObjectMapper

		try {
			// MJS 6.20.18 - Think we might get rid of this so didnt add new pojos.
			// Currently called by Create.  Note that update has a similar map and 
			// conversion based upon object type.
			if (className.equalsIgnoreCase("actor")) {
				// Convert Map to JSon, then Json to Actor Object
				Actor actor = mapper.convertValue(linkedHashMap, Actor.class);
				// this.jpaRepositoryInUse.save(actor);
				actor.setId(null);  // MJS 6.20.18 per skip
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

		ObjectMapper mapper = new ObjectMapper();  // JSon Object Mapper
		
		try {
			// saveJSon called by create likely needs similar conversion.
			if (className.equalsIgnoreCase("actorPhaseAssignment")) {
				ActorPhaseAssignment actorPhaseAssignment = mapper.convertValue(lhm, ActorPhaseAssignment.class);
				actorPhaseAssignment.setId(id);
				this.jpaRepositoryInUse.save(actorPhaseAssignment);
			} else if (className.equalsIgnoreCase("actor")) {
				Actor actor = mapper.convertValue(lhm, Actor.class);
				actor.setId(id);
				this.jpaRepositoryInUse.save(actor);
			} else if (className.equalsIgnoreCase("pageAssignment")) {
				PageAssignment pageAssignment = mapper.convertValue(lhm, PageAssignment.class);
				pageAssignment.setId(id);
				this.jpaRepositoryInUse.save(pageAssignment);
			} else if (className.equalsIgnoreCase("page")) {
				Page page = mapper.convertValue(lhm, Page.class);
				page.setId(id);
				this.jpaRepositoryInUse.save(page);
			} else if (className.equalsIgnoreCase("performance")) {
				Performance performance = mapper.convertValue(lhm, Performance.class);
				performance.setId(id);
				this.jpaRepositoryInUse.save(performance);
			} else if (className.equalsIgnoreCase("phase")) {
				Phase phase = mapper.convertValue(lhm, Phase.class);
				phase.setId(id);
				this.jpaRepositoryInUse.save(phase);
			} else if (className.equalsIgnoreCase("rolePlay")) {
				RolePlay rolePlay = mapper.convertValue(lhm, RolePlay.class);
				rolePlay.setId(id);
				this.jpaRepositoryInUse.save(rolePlay);
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
			// MJS 6.20.18 - REST web service calling this should return 404 NOT_FOUND
			// hence added a findByID check in the controller.
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			System.out.println("could not find " + className + ", with id of " + id);
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

		}
	}

	/**
	 * Looks up the repository for this class.
	 * TODO: Thought I could replace this with a hashtable, but initialization had issues.
	 * 
	 * @param className - a String such as actor or phase 
	 */
	private void setJpaRepositoryInUse(String className) {
		
		if ("actor".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = actorRepository;
		} else if ("actorPhaseAssignment".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = actorPhaseAssignmentRepository;
		} else if ("page".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = pageRepository;
		} else if ("pageAssignment".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = pageAssignmentRepository;
		} else if ("performance".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = performanceRepository;
		} else if ("phase".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = phaseRepository;
		} else if ("rolePlay".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = rolePlayRepository;
		} else {
			jpaRepositoryInUse = null;
		}
	}
	
	
	
}
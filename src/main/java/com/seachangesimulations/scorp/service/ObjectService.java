package com.seachangesimulations.scorp.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	
    @PersistenceContext
    private EntityManager entityManager;
	
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

	 * 
	 * @param object
	 * @param className
	 */
	public void save(Object object, String className) {
		setJpaRepositoryInUse(className);
		this.jpaRepositoryInUse.save(object);
	}

	/**
	 * saveJson - called by create (POST).
	 * @param className
	 * @param linkedHashMap
	 * @return - an Object including the new ID. (MJS 6.20)
	 */
	public Object saveJson(String className, LinkedHashMap linkedHashMap) {

		setJpaRepositoryInUse(className);

		ObjectMapper mapper = new ObjectMapper();  // JSon ObjectMapper

		try {
			// MJS 6.20.18 - Per Skip we need this. Claimed it didnt work with 
			// JpaRepositoryInUse instead of actorRepository
			// Currently called by Create.  Note that update has a similar map and 
			// conversion based upon object type.
			if (className.equalsIgnoreCase("actor")) {
				// Convert Map to JSon, then Json to Actor Object
				Actor actor = mapper.convertValue(linkedHashMap, Actor.class);
				// this.jpaRepositoryInUse.save(actor);
				actor.setId(null);  // MJS 6.20.18 per skip
				// return this.actorRepository.save(actor);
				return this.jpaRepositoryInUse.save(actor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	 * Update (Put)
	 * @param className
	 * @param id
	 * @param lhm
	 */
	@SuppressWarnings("unchecked") 
	// Most PUTs pass in both a path param id and an object, possibly with an id. 
	// The path param id overrides the objects id.
	public Object update(String className, Long id, LinkedHashMap lhm) {

		Object result = null;
		setJpaRepositoryInUse(className);

		ObjectMapper mapper = new ObjectMapper();  // JSon Object Mapper
		
		/* Per web, If the goal is to modify an entity, you don't need any update method. 
		You get the object from the database, modify it, and JPA saves it automatically:
		User u = repository.findOne(id);
		u.setFirstName("new first name");
		u.setLastName("new last name");
		If you have a detached entity and want to merge it, then use the save() method of CrudRepository: 
		User attachedUser = repository.save(detachedUser); */
	
		try {
			// saveJSon called by create likely needs similar conversion.
			if (className.equalsIgnoreCase("actorPhaseAssignment")) {
				ActorPhaseAssignment actorPhaseAssignment = mapper.convertValue(lhm, ActorPhaseAssignment.class);
				actorPhaseAssignment.setId(id);
				result = this.jpaRepositoryInUse.save(actorPhaseAssignment);
			} else if (className.equalsIgnoreCase("actor")) {
				Actor actor = mapper.convertValue(lhm, Actor.class);
				actor.setId(id);
				Actor newActor = (Actor) this.jpaRepositoryInUse.saveAndFlush(actor);
				// Next line shows that the save result is "attached" to the database. 
				// Hence any change to actor is reflected in the database.
				newActor.setDescription("ChangedAfterSave5");
				// But if we detach the newActor no changes at all occur . . . 
				// unless we first flush . . . 
				// this.jpaRepositoryInUse.flush();
				entityManager.detach(newActor);
				// Don't remove as it removes object from database.
				// entityManager.remove(newActor);
				newActor.setDescription("ChangedAfterDetached5");
				result = newActor;
			} else if (className.equalsIgnoreCase("pageAssignment")) {
				PageAssignment pageAssignment = mapper.convertValue(lhm, PageAssignment.class);
				pageAssignment.setId(id);
				result = this.jpaRepositoryInUse.save(pageAssignment);
			} else if (className.equalsIgnoreCase("page")) {
				Page page = mapper.convertValue(lhm, Page.class);
				page.setId(id);
				result = this.jpaRepositoryInUse.save(page);
			} else if (className.equalsIgnoreCase("performance")) {
				Performance performance = mapper.convertValue(lhm, Performance.class);
				performance.setId(id);
				result = this.jpaRepositoryInUse.save(performance);
			} else if (className.equalsIgnoreCase("phase")) {
				Phase phase = mapper.convertValue(lhm, Phase.class);
				phase.setId(id);
				result = this.jpaRepositoryInUse.save(phase);
			} else if (className.equalsIgnoreCase("rolePlay")) {
				RolePlay rolePlay = mapper.convertValue(lhm, RolePlay.class);
				rolePlay.setId(id);
				result = this.jpaRepositoryInUse.save(rolePlay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result; // added MJS 6.20.18
	}  // end update
	
	

	/**
	 * Deletes Object
	 * 
	 * @param className
	 * @param id
	 */
	public void delete(String className, Long id) {

		// Finding the object sets the repositoryInUse
		// setJpaRepositoryInUse(className);
		// MJS 6.20 - existsById wont work since jpaRepositoryInUse is not parameterized.
		// if (this.jpaRepositoryInUse.existsById(id)) {};
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
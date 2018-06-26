package com.seachangesimulations.scorp.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seachangesimulations.scorp.domain.Actor;
import com.seachangesimulations.scorp.domain.ActorPhaseAssignment;
import com.seachangesimulations.scorp.domain.BaseSCObject;
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
	private ActorRepository actorRepository;	
	@Autowired private ActorPhaseAssignmentRepository actorPhaseAssignmentRepository;
	@Autowired private PageRepository pageRepository;
	@Autowired private PageAssignmentRepository pageAssignmentRepository;
	@Autowired private PerformanceRepository performanceRepository;
	@Autowired private PhaseRepository phaseRepository;
	@Autowired private RolePlayRepository rolePlayRepository;
	
    @PersistenceContext
    private EntityManager entityManager;
	
	/** This repository gets set to the particular Domain Object repository. */
	@SuppressWarnings("rawtypes")
	private JpaRepository jpaRepositoryInUse;
	private Class domainClassInUse;      // such as Actor.class or Phase.class
	
	/** Establish this object, and create way to look up the appropriate repository. */
	public ObjectService() {
		//initializeRepoStore();
	}

	/**
	 * Creates lookup table to associate a Class with its repository.
	 * TODO: removed due to intialization issues
	 */
	private void initializeRepoStore() {
		//reposByClassName.put("x", null);		
		//repositoryMap.put("actor", actorRepository);
		//reposByClassName.put("phase", phaseRepository);
	}

	/**
	 * Creates a new object and DB entry of type className - called by create (POST).
	 * @param className
	 * @param linkedHashMap
	 * @return - an Object including the new ID. (MJS 6.20)
	 */
	public Object create(String className, LinkedHashMap linkedHashMap) {

		Object result = null;
		setClassInUseValues(className);
		ObjectMapper mapper = new ObjectMapper();  // JSon ObjectMapper

		try {
			// MJS 6.20.18 - Per Skip we need this. Claimed it didnt work with 
			// JpaRepositoryInUse instead of actorRepository
			if ((BaseSCObject.class.isAssignableFrom(domainClassInUse))) {
				// Convert Map to JSon, then Json to BaseSCObject such as Actor or Phase
				BaseSCObject scObj = (BaseSCObject) mapper.convertValue(linkedHashMap, domainClassInUse);
				scObj.setId(null);  // MJS 6.20.18 per skip
				// Must save (attach) and flush if object is detached.
				result = this.jpaRepositoryInUse.saveAndFlush(scObj);
				entityManager.detach(result); // dont allow caller to update db by changing returned value
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	} // end create
	
	/**
	 * Creates (Posts) or Updates (Puts) the className with primary key id
	 * 
	 * @param className
	 * @param id
	 * @param lhm
	 */
	@SuppressWarnings("unchecked") 
	// If id is null this will be a create (primary key must be auto-generated).
	// Most PUTs pass in both a path param id and an object, possibly with an id. 
	// The path param id overrides the objects id. 
	public Object createOrUpdateDetached(String className, Long id, LinkedHashMap lhm) {

		Object result = null;
		setClassInUseValues(className);
		ObjectMapper mapper = new ObjectMapper();  // JSon Object Mapper
		
		/* Per web, If the goal is to modify an entity, you don't need any update method. 
		You get the object from the database, modify it, and JPA saves it automatically:
		User u = repository.findOne(id);
		u.setFirstName("new first name");
		If you have a detached entity and want to merge it, then use the save() method of CrudRepository: 
		User attachedUser = repository.save(detachedUser); */
	
		try {
			// isAssignableFrom => same or superclass of - opposite of instanceof
			if ((BaseSCObject.class).isAssignableFrom(domainClassInUse)) {
				// domainClassInUse is ActorSub.class, PageSub.class or similar
				BaseSCObject scObj = (BaseSCObject) mapper.convertValue(lhm, domainClassInUse);  
				// null id => create, otherwise update
				scObj.setId(id);  // Object must be a BaseSCObject so id can be set with a long.
				// saveAndFlush - returned object is "attached" to DB
				result = this.jpaRepositoryInUse.saveAndFlush(scObj); 
				entityManager.detach(result); // prevent caller changes to newObject propogating to db
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result; // added MJS 6.20.18
	}  // end update

	/**
	 * Returns the object of the class passed in by its id.
	 * 
	 * @param className - a String representing the class (ie. Actor, Phase, etc.) to be retrieved.
	 * @param id - a long representing the primary key of the object to be retrieved.
	 * @return - the found object or null.
	 */
	public Object findById(String className, Long id) {
		
		setClassInUseValues(className);
		Object result = this.jpaRepositoryInUse.findOne(id);
		if (result != null ) {
			entityManager.detach(result); // dont propogate changes to returned object to DB.
		}
		return result;
	}

	/**
	 * Returns all objects of the class name passed in.
	 * 
	 * @param className - a String representing the class (ie. Actor, Phase, etc.) to be retrieved.
	 * @return - a List of all objects of the given className
	 */
	public List findAll(String className) {
		setClassInUseValues(className);
		if (jpaRepositoryInUse != null) {
			return jpaRepositoryInUse.findAll();
		} else {
			return new ArrayList();
		}
	} // end findAll

	/**
	 * Updates (Puts) the className with primary key id
	 * 
	 * @param className
	 * @param id
	 * @param lhm
	 */
	@SuppressWarnings("unchecked") 
	// Most PUTs pass in both a path param id and an object, possibly with an id. 
	// The path param id overrides the objects id.
	public Object update(String className, Long id, LinkedHashMap lhm) {

		Object result = null;
		setClassInUseValues(className);
		ObjectMapper mapper = new ObjectMapper();  // JSon Object Mapper
		
		/* Per web, If the goal is to modify an entity, you don't need any update method. 
		You get the object from the database, modify it, and JPA saves it automatically:
		User u = repository.findOne(id);
		u.setFirstName("new first name");
		If you have a detached entity and want to merge it, then use the save() method of CrudRepository: 
		User attachedUser = repository.save(detachedUser); */
	
		try {
			// isAssignableFrom => same or superclass of - opposite of instanceof
			if ((BaseSCObject.class).isAssignableFrom(domainClassInUse)) {
				// domainClassInUse is ActorSub.class, PageSub.class or similar
				BaseSCObject scObj = (BaseSCObject) mapper.convertValue(lhm, domainClassInUse);  
				scObj.setId(id);  // Object must be a BaseSCObject so id can be set with a long.
				// saveAndFlush - returned object is "attached" to DB
				result = this.jpaRepositoryInUse.saveAndFlush(scObj); 
				entityManager.detach(result); // prevent caller changes to newObject propogating to db
			} else if (className.equalsIgnoreCase("actor") ||
					   className.equalsIgnoreCase("page")) {
				// domainClassInUse is ActorSub.class, PageSub.class or similar
				BaseSCObject scObj = (BaseSCObject) mapper.convertValue(lhm, domainClassInUse);  
				scObj.setId(id);  // Object must be a BaseSCObject so id can be set with a long.
				// saveAndFlush - returned object is "attached" to DB
				Object obj = scObj;
				Object newObject = this.jpaRepositoryInUse.saveAndFlush(scObj); 
				entityManager.detach(newObject); // prevent caller changes to newObject propogating to db
				result = newObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result; // added MJS 6.20.18
	}  // end update
	
	

	/**
	 * Deletes the object with input id of input className from DB.
	 * 
	 * @param className
	 * @param id
	 */
	public void delete(String className, Long id) {
		// Finding the object sets the repositoryInUse
		// setClassInUseValues(className);
		// MJS 6.20 - existsById wont work since jpaRepositoryInUse is not parameterized.
		// if (this.jpaRepositoryInUse.existsById(id)) {};
		Object object = this.findById(className, id);

		if (object != null) {
			jpaRepositoryInUse.delete(object);
		} else {
			// MJS 6.20.18 - REST web service calling this should return 404 NOT_FOUND
			// hence added a findByID check in the controller.
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			System.out.println("could not find " + className + ", with id of " + id);
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		}
	} // end delete

	/**
	 * Looks up the repository for this class.
	 * TODO: Thought I could replace this with a hashtable, but initialization had issues.
	 * 
	 * @param className - a String such as actor or phase 
	 */
	private void setClassInUseValues(String className) {
		
		if ("actor".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = actorRepository;
			domainClassInUse = Actor.class;
		} else if ("actorPhaseAssignment".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = actorPhaseAssignmentRepository;
			domainClassInUse = ActorPhaseAssignment.class;
		} else if ("page".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = pageRepository;
			domainClassInUse = Page.class;
		} else if ("pageAssignment".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = pageAssignmentRepository;
			domainClassInUse = PageAssignment.class;
		} else if ("performance".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = performanceRepository;
			domainClassInUse = Performance.class;
		} else if ("phase".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = phaseRepository;
			domainClassInUse = Phase.class;
		} else if ("rolePlay".equalsIgnoreCase(className)) {
			jpaRepositoryInUse = rolePlayRepository;
			domainClassInUse = RolePlay.class;
		} else {
			jpaRepositoryInUse = null;
		}
	} // end setJpaRespositoryInUse
	
} // end ObjectService class

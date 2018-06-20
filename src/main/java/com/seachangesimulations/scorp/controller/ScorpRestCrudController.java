package com.seachangesimulations.scorp.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.seachangesimulations.scorp.service.ObjectService;

/**
 * Handles the CRUD operations for base domain objects.
 * No error checking. For example if a foreign key does 
 * not exist.
 */
@RestController
@RequestMapping("/rest")  // scorp/rest if run from Tomcat, just /rest if RunAs->SpringBootApp
public class ScorpRestCrudController {
	
	@Autowired  // Dependency Inject this value.
	protected ObjectService objectService;
	
	ScorpRestCrudController() {		
	}
	
	// ------ Methods in CRUD order ------ 
	/**
	 * Create.
	 * If object already exists ...
	 * 
	 * @param objectName - a String such as actor or phase representing the object to create.
	 * @param linkedHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{objectName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity create(@PathVariable String objectName, @RequestBody LinkedHashMap linkedHashMap) {
		this.objectService.saveJson(objectName, linkedHashMap);
		return new ResponseEntity(linkedHashMap, HttpStatus.CREATED);
	}

	/**
	 * Read All.
	 * 
	 * Get all Objects - Read All. For example rest/actor returns all actors.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{objectClass}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List> findAllObject(@PathVariable String objectClass) {
		
		List objects = this.objectService.findAll(objectClass);
		return new ResponseEntity<List>(objects, HttpStatus.OK);
	}
	
	/**
	 * Read One.
	 * 
	 * @param objectClass
	 * @param objectId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{objectClass}/{objectId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findOne(@PathVariable String objectClass, @PathVariable Long objectId) {
		Object object = this.objectService.findById(objectClass, objectId);
		// 6.20.18 MJS - return 404 Not Found if object not in database
		if (object == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(object, HttpStatus.OK);
	}
	
	/**
	 * Update
	 * 
	 * @param objectClass
	 * @param objectId
	 * @return
	 */
	@RequestMapping(value="/{objectClass}/{objectId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity update(@PathVariable String objectClass, @PathVariable Long objectId, @RequestBody LinkedHashMap linkedHashMap) {
		Object object = this.objectService.findById(objectClass, objectId);
		// 6.20.18 MJS - return 404 Not Found if object not in database
		if (object == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		this.objectService.update(objectClass, objectId, linkedHashMap);
		return new ResponseEntity(linkedHashMap, HttpStatus.OK);
	}

	/**
	 * Delete
	 * 
	 * @param objectClass
	 * @param objectId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/{objectClass}/{objectId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HttpEntity delete(@PathVariable String objectClass, @PathVariable Long objectId) {
		Object object = this.objectService.findById(objectClass, objectId);
		// 6.20.18 MJS - return 404 Not Found if object not in database
		if (object == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);  // 404
		}
		this.objectService.delete(objectClass, objectId);
		return new ResponseEntity(HttpStatus.NO_CONTENT);   // 204
	}
}

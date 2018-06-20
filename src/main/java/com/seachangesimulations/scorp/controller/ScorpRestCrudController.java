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
 *
 */
@RestController
@RequestMapping("/rest")
public class ScorpRestCrudController {
	
	@Autowired
	protected ObjectService objectService;
	
	ScorpRestCrudController(){
		
	}
	
	/**
	 * Create.
	 * 
	 * @param objectName
	 * @param linkedHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{objectName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity create(@PathVariable String objectName, @RequestBody LinkedHashMap linkedHashMap) {
		this.objectService.createFromJson(objectName, linkedHashMap);
		// TODO Return object including ID
		return new ResponseEntity(linkedHashMap, HttpStatus.CREATED);
	}

	/**
	 * Read All.
	 * 
	 * @param objectName
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
		return new ResponseEntity(object, HttpStatus.OK);
	}
	
	/* UPDATE */
	
	@RequestMapping(value="/{objectClass}/{objectId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity update(@PathVariable String objectClass, @PathVariable Long objectId, @RequestBody LinkedHashMap linkedHashMap) {

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
		this.objectService.delete(objectClass, objectId);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}

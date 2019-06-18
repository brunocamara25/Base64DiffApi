package com.bruno.waes.compareapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.bruno.waes.compareapi.entity.DataEntity;


public interface DataRepositoryInterface extends CrudRepository<DataEntity, Integer> {
	
	/**
	 * 
	 * Method will find the data by its ID.
	 *
	 * @author Bruno Camara.
	 * @param id
	 * @return
	 */
	DataEntity findById(long id);
	
	/**
	 * 
	 * Method will delete the data by its ID.
	 *
	 * @author Bruno Camara.
	 * @param id
	 * @return
	 */
	DataEntity deleteById(long id);
	
	
} 
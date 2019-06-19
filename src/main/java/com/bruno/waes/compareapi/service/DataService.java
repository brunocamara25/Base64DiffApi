package com.bruno.waes.compareapi.service;

import java.util.List;

import com.bruno.waes.compareapi.entity.DataEntity;
import com.bruno.waes.compareapi.util.SideEnum;

public interface DataService {

	/**
	 * 
	 * Method will All data.
	 *
	 * @author Bruno Camara.
	 * @param 
	 * @return List<DataEntity>
	 * @throws Exception 
	 */
	public abstract List<DataEntity> getAllData() throws Exception;
	
	/**
	 * 
	 * Method will find the document by its ID.
	 *
	 * @author Bruno Camara.
	 * @param id
	 * @return DataEntity
	 */
	public abstract DataEntity getDataById(long id) throws Exception;
		
	/**
	 * 
	 * Method will save or update the data.
	 *
	 * @author Bruno Camara.
	 * @param dataInput
	 * @param side
	 * @return String
	 * Document
	 */
	public abstract String saveOrUpdate(DataEntity dataInput, SideEnum side)throws Exception;
	
	/**
	 * 
	 * Method will delete the data by its ID.
	 *
	 * @author Bruno Camara.
	 * @param id
	 * @return
	 * Document
	 */
	 public abstract void delete(int id) throws Exception;
	 
	 /**
	 * 
	 * Method will find the difference in the data and return the positon , if exist
	 * Or alerts about the difference of size in the data
	 * 
	 * @author Bruno Camara.
	 * @param dataInput
	 * @return String
	 * Document
	 */
	 public abstract String baseDataDiff(DataEntity dataInput) throws Exception;
	
}

package com.bruno.waes.compareapi.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 *Class created to receive the object data from DiffController
 *
 * @author Bruno Camara
 */
@ToString @EqualsAndHashCode
public class ObjectDataTransfer {

	/**
	 * Simple string variable that receives the json data
	 */
	@Getter @Setter
	private String data;


}
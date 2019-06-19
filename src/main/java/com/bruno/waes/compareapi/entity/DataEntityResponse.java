package com.bruno.waes.compareapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Response VO of a data request
 * @author Bruno Camara
 */

@Entity @EqualsAndHashCode @ToString
public class DataEntityResponse {

	/**
	 * Id unique identifier to return
	 */
	@Id @Getter @Setter
	private long id;
	/**
	 * Side of data return
	 */
	@Getter @Setter
	private String side;
	/**
	 * Message of data return
	 */
	@Getter @Setter
	private String message;
		
		

}


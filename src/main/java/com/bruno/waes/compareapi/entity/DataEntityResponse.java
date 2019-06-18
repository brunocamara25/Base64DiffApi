package com.bruno.waes.compareapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Entity @EqualsAndHashCode @ToString
public class DataEntityResponse {

	@Id @Getter @Setter
	private long id;
	@Getter @Setter
	private String side;
	@Getter @Setter
	private String message;
		
		

}


package com.bruno.waes.compareapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.data.jpa.repository.JpaRepository;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Entity @EqualsAndHashCode @ToString
public class DataEntity {

	@Id @Getter @Setter
	private long id;
	@Lob
	@Column(length = 10000)
	@Getter @Setter
	private String right;
	@Lob
	@Column(length = 10000)
	@Getter @Setter
	private String left;
	
	public DataEntity() {}
	
	public DataEntity(long id, String right, String left) {
		this.id = id;
		this.right = right;
		this.left = left;
	}

	public interface DataEntityRepository
	  extends JpaRepository<DataEntity, Long> { }

}


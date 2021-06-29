package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="kutikomi")
public class kutikomi {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int kutikomiid;
	
	@Column
	private int mapid;
	
	@Column
	private String kutikomiuser;

	@Column
	private String contents;
}

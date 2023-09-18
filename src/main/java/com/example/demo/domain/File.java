package com.example.demo.domain;

import org.springframework.data.annotation.Id;

public class File {
	
	@Id
	private int id;
	private String name;
	private String note;
	
//	File(final int id, final String name, final String note){
//		if(name == null) throw new NullPointerException("ファイルを指定してください。");
//		this.id = id;
//		this.name = name;
//		this.note = note;
//	}
}
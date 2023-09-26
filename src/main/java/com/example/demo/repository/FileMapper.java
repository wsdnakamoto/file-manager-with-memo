package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.File;

@Mapper
public interface FileMapper {
	//CRUD操作のメソッド名だけを記述。
	int insert(File record);
	
	File selectByFileName(String fileName);
	
	List<File> selectAll();
	
	int deleteByFileName(String fileName);
}

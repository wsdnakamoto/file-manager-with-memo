package com.example.demo.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.config.AwsS3Properties;
import com.example.demo.domain.File;
import com.example.demo.repository.FileMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FileService {
	
	private final FileMapper mapper;
	
	private final ResourceLoader resourceLoader;
	
	private final ResourcePatternResolver resourcePatternResolver;
	
	private final AwsS3Properties awsS3Properties;
	
	
	
	public void insertFile(File file) {
		mapper.insert(file);
	}
	
	public File selectByPrimaryKey(int id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	public List<File> selectAll(){
		return mapper.selectAll();
	}
	
	public void updateFile(File file) {
		mapper.update(file);
	}
	
	public void deleteByPrimaryKey(int id) {
		mapper.deleteByPrimaryKey(id);
	}
	
	public List<Resource> fileList(String fileName){
		if(fileName == null) fileName = "";
		
		List<Resource> resourceList = null;
		try {
			Resource[] resourceArray = resourcePatternResolver.getResources("s3://"+awsS3Properties.getBucketName()+"/**/*"+fileName+"*.*");
			resourceList = Arrays.asList(resourceArray);
		}catch(IOException e) {
			log.error("S3FileListError", e);
		}
		return resourceList;
	}
}

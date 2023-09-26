package com.example.demo.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.example.demo.config.AwsS3Properties;
import com.example.demo.domain.File;
import com.example.demo.repository.FileMapper;

import io.awspring.cloud.core.io.s3.PathMatchingSimpleStorageResourcePatternResolver;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class FileService {
	
	//コンストラクタインジェクション
	private final FileMapper fileMapper;
	
	private final ResourceLoader resourceLoader;
	
	private final AwsS3Properties awsS3Properties;
	
	public FileService(FileMapper fileMapper, ResourceLoader resourceLoader, AwsS3Properties awsS3Properties) {
		this.fileMapper = fileMapper;
		this.resourceLoader = resourceLoader;
		this.awsS3Properties = awsS3Properties;
	}
	
	//セッターインジェクション
	private  ResourcePatternResolver resourcePatternResolver;
	
	@Autowired
	public void setupResolver(ApplicationContext applicationContext, AmazonS3 amazonS3) {
		this.resourcePatternResolver = new PathMatchingSimpleStorageResourcePatternResolver(amazonS3, applicationContext);
	}
	
	
	public void insertFile(File file) {
		fileMapper.insert(file);
	}
	
	public File selectByFileName(String fileName) {
		return fileMapper.selectByFileName(fileName);
	}
	
	public List<File> selectAll() {
		return fileMapper.selectAll();
	}
	
	public void deleteByFileName(String fileName) {
		fileMapper.deleteByFileName(fileName);
	}
	
	public List<Resource> fileList(String fileName) {
		if(fileName == null) fileName = "";
		List<Resource> resourceList = null;
		try {
			Resource[] resourceArray = resourcePatternResolver.getResources("s3://"+awsS3Properties.getBucketName()+"/**/*"+fileName+"*.*");
			resourceList = Arrays.asList(resourceArray);
		}catch(IOException e) {
			log.error("FileListError", e);
		}
		return resourceList;
	}
	
	public void upload(MultipartFile uploadFile) {
		Resource resource = this.resourceLoader.getResource("s3://"+awsS3Properties.getBucketName()+"/"+uploadFile.getOriginalFilename());
		WritableResource writableResource = (WritableResource)resource;
		try(OutputStream outputStream = writableResource.getOutputStream()){
			outputStream.write(uploadFile.getBytes());
		}catch(IOException e) {
			log.error("FileUploadError", e);
		}
	}
	
	//public InputStream download(String fileName) throws IOException {
	//	Resource resource = this.resourceLoader.getResource("s3://"+awsS3Properties.getBucketName()+"/"+fileName);
	//	return resource.getInputStream();
	//}
}

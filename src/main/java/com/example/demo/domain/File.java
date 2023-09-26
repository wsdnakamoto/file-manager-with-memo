package com.example.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.AwsS3Properties;

public class File {
	
	private AwsS3Properties awsS3Properties;
	
	@Id
	private String fileName;
	private String filePath;
	private String note;
	
	public void setFileName(MultipartFile uploadFile) {
		this.fileName = uploadFile.getOriginalFilename();
	}
	
	public void setFilePath(MultipartFile uploadFile) {
		this.filePath = "s3://" + awsS3Properties.getBucketName() + "/" + uploadFile.getOriginalFilename();
	}
//	File(final int id, final String name, final String note){
//		if(name == null) throw new NullPointerException("ファイルを指定してください。");
//		this.id = id;
//		this.name = name;
//		this.note = note;
//	}
}
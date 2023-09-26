package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.File;
import com.example.demo.service.FileService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FileController {
	
	private FileService fileService;
	
	@GetMapping("/list")
	public String getFileList() {
		return "fileList";
	}
	
	@GetMapping("/form")
	public String form(@ModelAttribute File file) {
		return "form";
	}
	
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("uploadFile") MultipartFile uploadFile, @ModelAttribute File file) {
		file.setFileName(uploadFile);
		file.setFilePath(uploadFile);
		fileService.insertFile(file);
		fileService.upload(uploadFile);
		return "redirect:/list";
	}
}

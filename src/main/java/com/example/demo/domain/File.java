package com.example.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.FileMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class File {
	
	private final FileMapper mapper;
	@Id
	private int id;
	private String name;
	private String note;
}
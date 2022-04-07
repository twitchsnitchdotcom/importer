package com.twitchsnitch.importer.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

	void saveFile(MultipartFile multipartFile) throws IOException;

	void deleteFile(Long id) throws Exception;

}

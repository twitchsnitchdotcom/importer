package com.twitchsnitch.importer.service;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class StorageServiceImpl implements StorageService {

	@Autowired
	AmazonS3 s3Client;
	
	
	@Value("${do.spaces.bucket}")
	private String doSpaceBucket;

	String FOLDER = "files/";

	@Override
	public void saveFile(MultipartFile multipartFile) throws IOException {
		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		String imgName = FilenameUtils.removeExtension(multipartFile.getOriginalFilename());
		String key = FOLDER + imgName + "." + extension;
		saveImageToServer(multipartFile, key);
	}

	@Override
	public void deleteFile(Long fileId) throws Exception {
			//s3Client.deleteObject(new DeleteObjectRequest(doSpaceBucket, key));
	}

	private void saveImageToServer(MultipartFile multipartFile, String key) throws IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(multipartFile.getInputStream().available());
		if (multipartFile.getContentType() != null && !"".equals(multipartFile.getContentType())) {
			metadata.setContentType(multipartFile.getContentType());
		}
		s3Client.putObject(new PutObjectRequest(doSpaceBucket, key, multipartFile.getInputStream(), metadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
	}

}

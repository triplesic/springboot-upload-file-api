package org.uploadfile.restapi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Component
@RequestMapping("/api/uploadfile")
public class UploadfileController {

	private final Logger logger = LoggerFactory.getLogger(UploadfileController.class);

	private static String uploadPath = "/Users/napasinyamwat/Desktop/";

	@SuppressWarnings("null")
	@PostMapping
	public ResponseEntity<?> uploadfile(@RequestParam("file") MultipartFile file) {

		if (file == null && file.isEmpty())
			return new ResponseEntity<>("please send a file", HttpStatus.BAD_REQUEST);

		try {

			String savedPath = saveFile(file);

			return new ResponseEntity<>(savedPath, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private String saveFile(MultipartFile file) throws IOException {
		byte[] bytes = file.getBytes();
		String fullFilePath = uploadPath + file.getOriginalFilename();
		Path path = Paths.get(fullFilePath);
		Files.write(path, bytes);
		return fullFilePath;
	}

}

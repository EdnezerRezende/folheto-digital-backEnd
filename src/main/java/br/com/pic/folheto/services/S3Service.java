package br.com.pic.folheto.services;


import br.com.pic.folheto.services.exceptions.FileException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3Service {

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	public URI uploadFile(final MultipartFile multipartFile) {
		try {
			final String fileName = multipartFile.getOriginalFilename();
			final InputStream is = multipartFile.getInputStream();
			final String contentType = multipartFile.getContentType();
			return uploadFile(is, fileName, contentType);
		} catch (IOException e) {
			throw new FileException("Erro de IO: " + e.getMessage());
		}
	}

	public URI uploadFile(final InputStream is,final String fileName,final String contentType) {
		try {
			final ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);
			s3client.putObject(bucketName, fileName, is, meta);
			return s3client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URL para URI");
		}
	}
}

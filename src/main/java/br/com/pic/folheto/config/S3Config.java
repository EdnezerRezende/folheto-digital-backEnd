package br.com.pic.folheto.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {
	
	@Value("${s3.region}")
	private String region;
	
	@Bean
	public AmazonS3 s3client() {
		final BasicAWSCredentials awsCred = new BasicAWSCredentials(System.getenv("S3_KEY"), System.getenv("S3_SECRET"));
		return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCred)).build();
	}
}

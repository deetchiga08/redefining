package com.briller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Configuration {

	@Value("${aws.accessKey}")
	private String s3bucketAccessKey;

	@Value("${aws.secretKey}")
	private String s3bucketSecreatKey;

	@Bean
	public AmazonS3 s3ClientSourceBucket() {
		BasicAWSCredentials provider = new BasicAWSCredentials(s3bucketAccessKey, s3bucketSecreatKey);
		AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(provider);

		return AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withRegion(Regions.US_WEST_2)
				.build();

	}
}

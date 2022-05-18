package com.lookatthis.flora.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean(name="accessKey")
    public String getAccessKey() {
        return accessKey;
    }
    @Bean(name="secretKey")
    public String getSecretKey() {
        return secretKey;
    }

    @Bean(name="bucket")
    public String getBucket() {
        return bucket;
    }

    @Bean(name="region")
    public Region getRegion() {
        return Region.getRegion(Regions.fromName(region));
    }

    @Bean(name="awsCredentialsProvider")
    public AWSCredentialsProvider getAWSCredentials()
    {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        return new AWSStaticCredentialsProvider(awsCredentials);
    }


}
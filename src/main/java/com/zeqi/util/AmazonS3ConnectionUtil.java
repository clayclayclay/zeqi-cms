package com.zeqi.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Component
@Lazy
public class AmazonS3ConnectionUtil
{

    @Value("${AWSAccessKeyId}")
    private String accessKeyId;

    @Value("${AWSSecretKey}")
    private String secretKey;

    @Value("${bucket}")
    private String bucketName;
    
    @Value("${bucket.avatar}")
    private String bucketAvatarName;
    
    @Value("${bucket.background}")
    private String bucketBackgroundName;
    
    @Value("${bucket.document}")
    private String bucketDocumentName;

    private static AmazonS3 s3;

    @PostConstruct
    public void init()
    {
        BasicAWSCredentials basic = new BasicAWSCredentials(accessKeyId, secretKey);
        /*
         * if necessary
         * ClientConfiguration clientConfig = new ClientConfiguration();
         * clientConfig.setProxyHost("proxy.wdf.sap.corp"); 
         * clientConfig.setProxyPort(8080);
         */
        ClientConfiguration clientConfig = new ClientConfiguration();
//        clientConfig.setProxyHost("proxy.wdf.sap.corp"); 
//        clientConfig.setProxyPort(8080);
        s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(basic))
            .withClientConfiguration(clientConfig).withRegion(Regions.AP_SOUTHEAST_1).build();
    }

    public AmazonS3 getAmazonS3Client()
    {
        return s3;
    }

    public String getBucketName()
    {
        return bucketName;
    }

	public String getBucketAvatarName() {
	
		return bucketAvatarName;
	}

	public String getBucketBackgroundName() {
	
		return bucketBackgroundName;
	}

	public String getBucketDocumentName() {
	
		return bucketDocumentName;
	}
    
    
}

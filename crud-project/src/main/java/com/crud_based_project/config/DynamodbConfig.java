package com.crud_based_project.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.crud_based_project.repositories")
public class DynamodbConfig {

    @Value("${aws.dynamodb.endpoint}")
    private String END_POINT;
    @Value("${aws.dynamodb.accessKey}")
    private String ACCESS_KEY;
    @Value("${aws.dynamodb.secretKey}")
    private String SECRET_KEY;
    @Value("${aws.dynamodb.region}")
    private String REGION;

    @Bean
    public AmazonDynamoDB amazonDynamoDB(){
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(END_POINT,REGION)
                )
                .withCredentials(awsCredentialsProvider()).build();
    }

    private AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY));
    }
}

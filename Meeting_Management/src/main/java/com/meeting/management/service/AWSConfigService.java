package com.meeting.management.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithWebIdentityRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithWebIdentityResult;

@Service
public class AWSConfigService{

	@Value("${minio.rest-url}")
	private String baseUrl;
	
	@Value("${minio.rest-port}")
	private String port;

	public BasicSessionCredentials client(String token)
	{
		System.out.println("reached");
		AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(baseUrl+":"+port,
						Regions.US_EAST_1.getName()))
				.withCredentials((AWSCredentialsProvider) new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
				.build();
		System.out.println("stsclient"+" "+stsClient);
		System.out.println("reached");
		AssumeRoleWithWebIdentityRequest assumeRequest = new AssumeRoleWithWebIdentityRequest().withDurationSeconds(3600)
				.withWebIdentityToken(token);
		System.out.println(assumeRequest);
		AssumeRoleWithWebIdentityResult assumeResult = stsClient.assumeRoleWithWebIdentity(assumeRequest);
		System.out.println("reached");
				
		BasicSessionCredentials sessionCredentials = new BasicSessionCredentials(
				assumeResult.getCredentials().getAccessKeyId(),
				assumeResult.getCredentials().getSecretAccessKey(),
				assumeResult.getCredentials().getSessionToken());
		return sessionCredentials;
	}

}

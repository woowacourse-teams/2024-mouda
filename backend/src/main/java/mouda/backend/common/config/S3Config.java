package mouda.backend.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

	@Value("${aws.region.static}")
	private String region;

	@Bean
	public AmazonS3 amazonS3() {
		return AmazonS3ClientBuilder.standard()
			.withRegion(region)
			.withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
			.build();
	}
}

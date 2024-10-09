package mouda.backend.darakbangmember.implement;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.exception.DarakbangMemberErrorMessage;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;

@Component
@RequiredArgsConstructor
public class S3Client {

	private final AmazonS3 amazonS3;

	@Value("${aws.s3.bucket}")
	private String bucket;

	@Value("${aws.s3.key-prefix}")
	private String keyPrefix;

	public String uploadFile(MultipartFile file) {
		try {
			String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
			String key = keyPrefix + fileName;

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());
			metadata.setContentType(file.getContentType());

			amazonS3.putObject(bucket, key, file.getInputStream(), metadata);
			return amazonS3.getUrl(bucket, fileName).toString();
		} catch (IOException e) {
			throw new DarakbangMemberException(HttpStatus.BAD_REQUEST, DarakbangMemberErrorMessage.INVALID_FILE);
		}
	}
}

package site.pistudio.backend.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AWSS3Service {
    private final AmazonS3 amazonS3;
    private Logger logger = LoggerFactory.getLogger(AWSS3Service.class);
    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Value(("${aws.s3.dir}"))
    private String dirName;

    public AWSS3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void deleteImage(String fileName) {
      try {
          amazonS3.deleteObject(new DeleteObjectRequest(bucketName, dirName + "/" + fileName));
          logger.info("Successfully deleted image " + dirName + "/" + fileName);
      } catch (AmazonServiceException amazonServiceException) {
          logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
          logger.info("Error Message:    " + amazonServiceException.getMessage());
          logger.info("HTTP Status Code: " + amazonServiceException.getStatusCode());
          logger.info("AWS Error Code:   " + amazonServiceException.getErrorCode());
          logger.info("Error Type:       " + amazonServiceException.getErrorType());
          logger.info("Request ID:       " + amazonServiceException.getRequestId());
          throw amazonServiceException;
      } catch (SdkClientException sdkClientException) {
          logger.info("Caught an SdkClientException: ");
          logger.info("Error Message: " + sdkClientException.getMessage());
          throw sdkClientException;
      }
    }
}

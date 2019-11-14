package co.getmehired.social.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public  class AmazonUtil {

    public static boolean uploadToAmazonS3(MultipartFile file, String path) {
        try {
        	byte[] fileBytes = file.getBytes();
            System.out.println("Uploading a new object to S3 from a file\n");
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");
            metadata.setContentLength(fileBytes.length);
            AmazonS3Client s3Client = getS3Client();
            if(!StringUtils.isEmpty(path)) {
            	s3Client.putObject(PrivateKeys.BUCKET_NAME, path, new ByteArrayInputStream(fileBytes), metadata);
            	return true;
            } else {
            	return false;
            }
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            return false;
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
            return false;
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }

    public static boolean existsInAmazonS3(String filePath) {
    	AmazonS3Client s3Client = getS3Client();
    	
        try {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(PrivateKeys.BUCKET_NAME).withPrefix(filePath);
            ObjectListing listing = s3Client.listObjects(listObjectsRequest);
            return listing != null && listing.getObjectSummaries() != null && listing.getObjectSummaries().size() > 0;
        } catch (AmazonClientException e) {
            return false;
        }
    }
    
    public static byte[] getFromAmazonS3(String filePath) throws Exception {
    	AmazonS3Client s3Client = getS3Client();
        S3Object s3Object = s3Client.getObject(PrivateKeys.BUCKET_NAME, filePath);
        InputStream objectData = s3Object.getObjectContent();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(objectData, baos);
        objectData.close();
        return baos.toByteArray();
    }

    public static void deleteFromAmazonS3(String filePath) throws Exception {
    	AmazonS3Client s3Client = getS3Client();
    	s3Client.deleteObject(PrivateKeys.BUCKET_NAME, filePath);
    }

    public static AmazonS3Client getS3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(PrivateKeys.S3_ACCESS_KEY, PrivateKeys.S3_SECRET_KEY);
        return new AmazonS3Client(credentials);
    }
}

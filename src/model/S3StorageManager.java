package model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.StorageClass;

import freemarker.template.Configuration;


/**
 * This is a class for storage of any kind of data on S3.  There is some functionality included 
 * in this class that's not used in the TravelLog application but should serve to illustrate 
 * additional capabilities of S3.
 *
 */
public class S3StorageManager {

	private Date lastUpdate;
	
	/*
	 * The s3 client class is thread safe so we only ever need one static instance.
	 * While you can have multiple instances it is better to only have one because it's
	 * a relatively heavy weight class.
	 */
	private static AmazonS3Client globalS3Client;
	private final AmazonS3Client s3Client;
	
	static {
		try {
			globalS3Client = createClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        String s3Endpoint = Configuration.getInstance().getServiceEndpoint(Configuration.S3_ENDPOINT_KEY);
//        if ( s3Endpoint != null ) {
//            globalS3Client.setEndpoint(s3Endpoint);
//        }
	}

    /**
     * Returns a new AmazonS3 client using the default endpoint and current
     * credentials.
     * @throws IOException 
     */
	public static AmazonS3Client createClient() throws IOException {
        AWSCredentials credentials = new PropertiesCredentials(
        		S3StorageManager.class
				.getResourceAsStream("../AwsCredentials.properties"));
        return new AmazonS3Client(credentials);
    }
	
    public S3StorageManager() {
        this(globalS3Client);
    }	

    /**
     * Creates a new storage manager that uses the client given.
     */
    public S3StorageManager(AmazonS3Client s3Client) {
        this.s3Client = s3Client;
    }
	
	/**
	 * The bucket map keeps track of whether a bucket exists or not.  The first time
	 * any bucket name is called, it will be checked against this map and created
	 * if not already available.
	 */
	private static Map<String,Boolean> bucketMap = new LinkedHashMap<String,Boolean>();

	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	/**
	 * Stores a given item on S3
	 * @param obj the data to be stored
	 * @param reducedRedundancy whether or not to use reduced redundancy storage
	 * @param acl a canned access control list indicating what permissions to store this object with (can be null to leave it set to default)
	 */
	public void store(StorageObject obj, boolean reducedRedundancy, CannedAccessControlList acl) {
		// Make sure the bucket exists before we try to use it
		checkForAndCreateBucket(obj.getBucketName());

		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentType(obj.getMimeType());
		omd.setContentLength(obj.getData().length);

		ByteArrayInputStream is = new ByteArrayInputStream(obj.getData());
		PutObjectRequest request = new PutObjectRequest(obj.getBucketName(), obj.getStoragePath(), is, omd);

		// Check if reduced redundancy is enabled
		if (reducedRedundancy) {
			request.setStorageClass(StorageClass.ReducedRedundancy);
		}

		s3Client.putObject(request);

		// If we have an ACL set access permissions for the the data on S3
		if (acl!=null) {
		    s3Client.setObjectAcl(obj.getBucketName(), obj.getStoragePath(), acl);
		}

	}
		
	/**
	 * This method will call out to S3 to make sure that the specified bucket
	 * exists.  If it does not exist it will create it.
	 * @param bucketName name of the bucket to be checked/created
	 */
	public void checkForAndCreateBucket(String bucketName) {
		// Make sure it's lower case to comply with Amazon S3 recommendations
		bucketName = bucketName.toLowerCase();
		if (bucketMap.get(bucketName) == null) {

			if (s3Client.doesBucketExist(bucketName)) {
				bucketMap.put(bucketName, true);
			}
			else {
				// Bucket hasn't been created yet so we create it
			    s3Client.createBucket(bucketName);
				bucketMap.put(bucketName, true);
			}
		}
	}

	public InputStream loadInputStream (StorageObject s3Store) throws IOException {
		S3Object s3 = getS3Object(s3Store);
		this.lastUpdate = s3.getObjectMetadata().getLastModified();
		return s3.getObjectContent();
	}

	/**
	 * Returns the raw S3 object from S3 service
	 * @param s3Store the s3 object to be loaded from the store
	 * @return the requested S3 object
	 */
	private S3Object getS3Object(StorageObject s3Store) {
		S3Object obj = s3Client.getObject(s3Store.getBucketName(),s3Store.getStoragePath());
		return obj;
	}


	/**
	 * Loads the raw object data from S3 storage
	 * @param s3Store the s3 object to be loaded from the store
	 * @return input stream for reading in the raw object
	 * @throws IOException
	 */
	public InputStream loadStream(StorageObject s3Store) throws IOException {
		S3Object obj = getS3Object(s3Store);
		InputStream is = obj.getObjectContent();
		return is;
	}



	/**
	 * Deletes the specified S3 object from the S3 storage service.  If a
	 * storage path is passed in that has child S3 objects, it will recursively
	 * delete the underlying objects.
	 * @param s3Store the s3 object to be deleted
	 */
	public void delete(StorageObject s3Store) {

		if (s3Store.getStoragePath() == null || s3Store.getStoragePath().equals("")) {
			System.out.println("Empty storage path passed to delete method");
			return; // We don't want to delete everything in a path
		}

		// Go through the store structure and delete child objects
		ObjectListing listing = s3Client.listObjects(s3Store.getBucketName(), s3Store.getStoragePath());
		while (true) {
			List<S3ObjectSummary> objectList = listing.getObjectSummaries();
			for (S3ObjectSummary summary:objectList) {
			    s3Client.deleteObject(s3Store.getBucketName(),summary.getKey());
			}
			if (listing.isTruncated()) {
				listing = s3Client.listNextBatchOfObjects(listing);
			}
			else {
				break;
			}
		}

	}
	
	public String getResourceUrl(String bucket, String key) {
	    return s3Client.getResourceUrl(bucket, key);
	}
}

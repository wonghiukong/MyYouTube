package model;

/**
 * This is a general purpose object used for storing data on S3.
 * The mimeType and data variables need only be set if you are
 * planning to initiate a storage call.  If you do not specify a
 * mimeType "text/html" is the default.
 */
public class StorageObject {

	private String bucketName;
	private byte [] data;
	private String storagePath;
	private String mimeType="audio/mpeg";

	public void setBucketName(String bucketName) {
		/**
		 * S3 prefers that the bucket name be lower case.  While you can
		 * create buckets with different cases, it will error out when
		 * being passed through the AWS SDK due to stricter checking.
		 */
		this.bucketName = bucketName.toLowerCase();
	}

	public String getBucketName() {
		return bucketName;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data=data;
	}

	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}

	/**
	 * Convenience method to construct the URL that points to
	 * an object stored on S3 based on the bucket name and
	 * storage path.
	 * @return the S3 URL for the object
	 */
    public String getAwsUrl() {
        return new S3StorageManager().getResourceUrl(bucketName, storagePath);
    }

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

}

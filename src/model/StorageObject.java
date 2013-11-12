package model;

import java.util.HashMap;

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
	private String mimeType="video/mp4";
	public static HashMap<String, String> extToMIMETypeHashmap;
	
//	Video Type	Extension	MIME Type
//	Flash	 .flv	 video/x-flv
//	MPEG-4	 .mp4	 video/mp4
//	iPhone Index	 .m3u8	 application/x-mpegURL
//	iPhone Segment	 .ts	 video/MP2T
//	3GP Mobile	 .3gp	 video/3gpp
//	QuickTime	 .mov	 video/quicktime
//	A/V Interleave	 .avi	 video/x-msvideo
//	Windows Media	 .wmv	 video/x-ms-wmv
	static {
		extToMIMETypeHashmap = new HashMap<String, String>();
		extToMIMETypeHashmap.put("flv", "video/x-flv") ;
		extToMIMETypeHashmap.put("mp4", "video/mp4") ;
		extToMIMETypeHashmap.put("m3u8", "application/x-mpegURL") ;
		extToMIMETypeHashmap.put("ts", "video/MP2T") ;
		extToMIMETypeHashmap.put("3gp", "video/3gpp") ;
		extToMIMETypeHashmap.put("mov", "video/quicktime") ;
		extToMIMETypeHashmap.put("avi", "video/x-msvideo") ;
		extToMIMETypeHashmap.put("wmv", "video/x-ms-wmv") ;
	}
	
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

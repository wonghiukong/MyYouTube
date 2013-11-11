package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.services.s3.model.CannedAccessControlList;

import model.Helper;
import model.S3StorageManager;
import model.StorageObject;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	/*
	 * INSERT INTO Movie (title, ext, upload_date, total_rating, rating_count)
	 *	VALUES ('trust you', 'rmvb', '2013-11-10 08:30:00', 100, 25);
	 */
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String movie_id = request.getParameter("movie_id");
		//String ext = request.getParameter("ext");
		//Date upload_date = request.getParameter("date");
		//int total_rating = Integer.parseInt(request.getParameter("total_rating"));
		//int rating_count = Integer.parseInt(request.getParameter("rating_count"));
		
		
		String title = request.getParameter("movie_file");
		System.out.println(title);
		String dataString = getRequestBodyString(request);
		System.out.println(dataString.charAt(0));
		byte[] data = dataString.getBytes();
		Date upload_date = new Date();
		String sqlStmt = "INSERT INTO Movie (title, upload_date)" 
					   + "\n" + "VALUES (" + title + ", " + upload_date + "')";
		S3StorageManager S3sm = new S3StorageManager();
		StorageObject sObj = new StorageObject();
		sObj.setBucketName(Helper.s3BucketName);
		sObj.setData(data);
		sObj.setStoragePath(title);
		S3sm.store(sObj, true, CannedAccessControlList.PublicRead);
		response.sendRedirect("index.jsp");
	}
	
	private String getRequestBodyString(HttpServletRequest request) throws IOException {
		String body = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try {
	        InputStream inputStream = request.getInputStream();
	        if (inputStream != null) {
	        	System.out.println("check point");
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	                System.out.println(stringBuilder.toString());
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    }

	    body = stringBuilder.toString();
	    return body;
	}
}

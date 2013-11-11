package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.DBManager;
import model.Helper;
import model.Movie;
import model.S3StorageManager;
import model.StorageObject;

import com.amazonaws.services.s3.model.CannedAccessControlList;

//http://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet
/**
 * Servlet implementation class UploadServlet
 */
@MultipartConfig(location="./", fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*1024*5, maxRequestSize=1024*1024*1024*5)
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
		
		Movie movie = getMovie(request);
		movie.setMovieId(insertMovieInfoToDB(movie));
		putMovieDataIntoS3(movie);
		response.sendRedirect("index.jsp");
	}
	
	private void putMovieDataIntoS3(Movie movie) {
		S3StorageManager S3sm = new S3StorageManager();
		StorageObject sObj = new StorageObject();
		sObj.setBucketName(Helper.s3BucketName);
		sObj.setData(movie.getData());
		sObj.setStoragePath(String.valueOf(movie.getMovieId()));
		S3sm.store(sObj, false, CannedAccessControlList.PublicRead);
	}
	
	private long insertMovieInfoToDB(Movie movie) {
		/*String sqlStmt = "INSERT INTO Movie (title) " 
				   + "VALUES ('" + title + "')";*/
		long id = DBManager.insert(movie);
		return id;
	}
	
	private Movie getMovie(HttpServletRequest request) throws IllegalStateException, IOException, ServletException {
		Part filePart = request.getPart("movie_file"); // Retrieves <input type="file" name="file">
	    String title = getFilename(filePart);
	    System.out.println(title);
	    InputStream filecontent = filePart.getInputStream();		
		String dataString = convertStreamToString(filecontent);
		byte[] data = dataString.getBytes();
		System.out.println(dataString.length());
		//Date upload_date = new Date();
		Movie movie = new Movie();
		movie.setTitle(title);
		movie.setData(data);
		return movie;
	}
	
	private static String getFilename(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}
		
	private String convertStreamToString(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = null;
		StringBuilder stringBuilder = new StringBuilder();
		try {
	        if (inputStream != null) {
	        	System.out.println("check point");
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
//	                System.out.println(stringBuilder.toString());
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
	    System.out.println("check point2");
//	    System.out.println(stringBuilder.toString());
	    String str = stringBuilder.toString();
	    return str;
	}
}

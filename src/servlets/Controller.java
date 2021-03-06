package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Helper;
import model.Movie;
import model.S3StorageManager;
import model.StorageObject;

import com.amazonaws.services.s3.model.CannedAccessControlList;

/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Hello world!");
		S3StorageManager s3SM = new S3StorageManager();
		
		StorageObject sObj = new StorageObject();
		byte[] data = new byte[10];
		data[0] = 9;
		sObj.setBucketName("testS3SM");
		sObj.setData(data);
		sObj.setStoragePath("001");
		s3SM.store(sObj, true, CannedAccessControlList.PublicRead);
		//s3SM.delete(sObj);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// new com.mysql.jdbc.Driver();
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// conn =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase?user=testuser&password=testpassword");			
			conn = DriverManager.getConnection(Helper.connectionUrl, Helper.connectionUser,
					Helper.connectionPassword);
			/*stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Movie");
			while (rs.next()) {
				String movie_id = rs.getString("movie_id");
				String title = rs.getString("title");
				Date upload_date = rs.getDate("upload_date");
				int total_rating = rs.getInt("total_rating");
				int rating_count = rs.getInt("rating_count");
				System.out.println("ID: " + movie_id + ", title: " + title
						+ ", Upload_date: " + upload_date + ", Total_rating: " + total_rating + ", Rating_count: " + rating_count);
			}*/
			ArrayList<Movie> movies = Helper.getSortedMovieList();
			for (Movie movie : movies) {
				System.out.println(movie.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
       
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

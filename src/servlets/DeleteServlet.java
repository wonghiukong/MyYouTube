package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DBManager;
import model.Helper;
import model.S3StorageManager;
import model.StorageObject;

/**
 * Servlet implementation class DeleteServlet
 */
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long movie_id = Long.parseLong(request.getParameter("movie_id"));
		//System.out.println("movie_id: " + movie_id);
		DBManager.delete(movie_id);
		deleteMovieDataInS3(movie_id);
		response.sendRedirect("index.jsp");
	}
	
	private void deleteMovieDataInS3(long movie_id) {
		S3StorageManager S3sm = new S3StorageManager();
		StorageObject sObj = new StorageObject();
		sObj.setBucketName(Helper.s3BucketName);
		sObj.setStoragePath(String.valueOf(movie_id));
		S3sm.delete(sObj);
	}
}

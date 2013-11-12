package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.util.ResultSetUtil;

public class DBManager {
	public DBManager() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static ResultSet query(String sqlStmt) {
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
			stmt = (Statement) conn.createStatement();
			rs = stmt.executeQuery(sqlStmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/*try {
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
			}*/
		}
		return rs;
	}
	
	public static void updateRating(long movieId, int rate)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		String sqlUpdate = "UPDATE Movie "
			    + "SET total_rating = total_rating + ? , rating_count = rating_count + 1 WHERE movie_id=?";
		try {
			// new com.mysql.jdbc.Driver();
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(Helper.connectionUrl, Helper.connectionUser,
					Helper.connectionPassword);
			stmt = (PreparedStatement) conn.prepareStatement(sqlUpdate, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, rate);
			stmt.setLong(2, movieId);
	        

	        stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
	
	public static long insert(Movie movie) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String SQL_INSERT = "INSERT INTO Movie"
			    + " (title, ext, upload_date, total_rating, rating_count) VALUES (?, ?, ?, ?, ?)";
		ResultSet rs = null;
		ResultSet generatedKeys = null;
		long id = 0;
		try {
			// new com.mysql.jdbc.Driver();
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// conn =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase?user=testuser&password=testpassword");			
			conn = DriverManager.getConnection(Helper.connectionUrl, Helper.connectionUser,
					Helper.connectionPassword);
			stmt = (PreparedStatement) conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, movie.getTitle());
			stmt.setString(2, movie.getExt());
			stmt.setDate(3, (Date) movie.getUploadDate());
			stmt.setInt(4, movie.getTotalRating());
			stmt.setInt(5, movie.getRatingCount());
	        // ...

	        int affectedRows = stmt.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Creating movie failed, no rows affected.");
	        }
			
			generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next())
				id =  generatedKeys.getLong(1);
			else
				throw new Exception("Shit just got real");
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
		return id;
	}
	
	public static void delete(long movie_id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String SQL_DELETE = "DELETE FROM Movie WHERE movie_id = ?";
		ResultSet rs = null;
		try {
			// new com.mysql.jdbc.Driver();
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// conn =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase?user=testuser&password=testpassword");			
			conn = DriverManager.getConnection(Helper.connectionUrl, Helper.connectionUser,
					Helper.connectionPassword);
			stmt = (PreparedStatement) conn.prepareStatement(SQL_DELETE);
			stmt.setLong(1, movie_id);
	        // ...
			stmt.executeUpdate();
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
}

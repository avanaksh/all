package com.iasri.javaee.bookstore;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class FileUploadDBServlet
 */
@WebServlet(name = "uploadServlet", urlPatterns = { "/uploadServlet" })
@MultipartConfig(maxFileSize=16177215)
public class FileUploadDBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String dbURL="jdbc:sqlserver://localhost:1433;databaseName=pgs_ivri;authenticationScheme=NTLM;integratedSecurity=false;encrypt=false;";
    private String dbUsename="sunone";
    private String dbPassword="dean2head$";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadDBServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		
		InputStream inputStream=null;
		Part filePart =request.getPart("photo");
		if (filePart != null) {
			System.out.println(filePart.getName());
			System.out.println(filePart.getSize());
			System.out.println(filePart.getContentType());
			
			inputStream=filePart.getInputStream();			
		}
		Connection conn=null;
		String message=null;
		try{
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			conn=DriverManager.getConnection(dbURL, dbUsename, dbPassword);
			
			String query="insert into contacts(first_name,last_name,photo,entrydate) values(?,?,?,?)";
			PreparedStatement stmt=conn.prepareStatement(query);
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			
			if(inputStream!=null){
				stmt.setBlob(3, inputStream);
				stmt.setString(4, "2022-04-19");
			}
			int row=stmt.executeUpdate();
			if(row>0){
				 message="File uploaded and saved into database";
			}
		}catch(SQLException e){
			message = "ERROR: " + e.getMessage();
            e.printStackTrace();
		}finally{
			 if (conn != null) {
	                // closes the database connection
	                try {
	                    conn.close();
	                } catch (SQLException ex) {
	                    ex.printStackTrace();
	                }
	            }
		}
		
		// sets the message in request scope
        request.setAttribute("Message", message);
         
        // forwards to the message page
        getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
		
		
	}

}

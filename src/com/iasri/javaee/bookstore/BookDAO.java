package com.iasri.javaee.bookstore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
     private String jdbcURL;
     private String jdbcUsername;
     private String jdbcPassword;
     private Connection conn;
     
	public BookDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		super();
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}
     
     protected void connect() throws SQLException, ClassNotFoundException {
    	 if(conn==null || conn.isClosed()){
    		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    		 conn=DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    	 }
     }
     
     protected void disconnect() throws SQLException{
    	 if(conn!=null||!conn.isClosed()){
    		 conn.close();
    	 }
     }
     
     protected boolean insertBook(Book book) throws ClassNotFoundException, SQLException{
    	 String query="insert into Book(title,author,price)values(?,?,?)";
    	 connect();
    	 
    	 PreparedStatement stmt=conn.prepareStatement(query);
    	 
    	 stmt.setString(1, book.title);
    	 stmt.setString(2, book.author);
    	 stmt.setFloat(3, book.price);
    	 
    	 boolean rowInserted = stmt.executeUpdate()>0;
    	 stmt.close();
    	 disconnect();
    	 return rowInserted;
     }
     
     protected List<Book> listAllBooks() throws SQLException, ClassNotFoundException{
    	 List<Book> listBook=new ArrayList<Book>();
    	 
    	 String query="select * from book";
    	 connect();
    	 
    	 Statement stmt=conn.createStatement();
    	 ResultSet rs=stmt.executeQuery(query);
    	 //rs.next();
    	 if(rs.next()) {
             //System.out.println("Obtained Resultset object is not empty its contents are:");
             rs.next();
             while(rs.next()) {
    		 int id = rs.getInt("book_id");
             String title = rs.getString("title");
             String author = rs.getString("author");
             float price = rs.getFloat("price");
             //System.out.println("Id"+String.valueOf(id));
    		 Book book=new Book(id,title,author,price);
    		 listBook.add(book);
    	 }
    	 }else{
    		 System.out.println("Obtained ResultSet object is empty");
    	 }
    	 //rs.close();
    	 stmt.close();
    	 disconnect();
    	 
    	 return listBook;
     }
     
     protected  boolean deleteBook(Book book) throws SQLException, ClassNotFoundException{
    	 String query = "DELETE FROM book where book_id = ?";
         
         connect();
          
         PreparedStatement statement = conn.prepareStatement(query);
         statement.setInt(1, book.getId());
          
         boolean rowDeleted = statement.executeUpdate() > 0;
         statement.close();
         disconnect();
         return rowDeleted;     
     }
     
     public boolean updateBook(Book book) throws SQLException, ClassNotFoundException {
         String query = "UPDATE book SET title = ?, author = ?, price = ?";
         query += " WHERE book_id = ?";
         connect();
          
         PreparedStatement statement = conn.prepareStatement(query);
         statement.setString(1, book.getTitle());
         statement.setString(2, book.getAuthor());
         statement.setFloat(3, book.getPrice());
         statement.setInt(4, book.getId());
          
         boolean rowUpdated = statement.executeUpdate() > 0;
         statement.close();
         disconnect();
         return rowUpdated;     
     }
     
     public Book getBook(int id) throws SQLException, ClassNotFoundException {
         Book book = null;
         String query = "SELECT * FROM book WHERE book_id = ?";
          
         connect();
          
         PreparedStatement statement = conn.prepareStatement(query);
         statement.setInt(1, id);
          
         ResultSet resultSet = statement.executeQuery();
          
         if (resultSet.next()) {
             String title = resultSet.getString("title");
             String author = resultSet.getString("author");
             float price = resultSet.getFloat("price");
              
             book = new Book(id, title, author, price);
         }
         
         System.out.println(" Book "+book.id+"-"+book.title+"-"+book.author+"-"+String.valueOf(book.price));
          
         resultSet.close();
         statement.close();
         disconnect();
         return book;
     }
}

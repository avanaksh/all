package com.iasri.javaee.bookstore;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet(name = "ControllerServlet", urlPatterns = { "/ControllerServlet" })
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO;
	
	public void init(){
		String jdbcURL="jdbc:sqlserver://localhost:1433;databaseName=pgs_ivri;authenticationScheme=NTLM;integratedSecurity=false;encrypt=false;";//"jdbc:sqlserver://localhost:1433;databaseName=Bookstore;encrypt=false;";//getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername="sunone";//getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword="dean2head$";//getServletContext().getInitParameter("jdbcPassword");
		bookDAO = new BookDAO(jdbcURL, jdbcUsername, jdbcPassword);
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		String action=request.getServletPath();		
		System.out.println("Action called : " + action);
		
		try {
            switch (action) {
            case "/new":
            	System.out.println("inside /new");
                showNewForm(request, response);
                break;
            case "/insert":
            	System.out.println("inside /insert");
                insertBook(request, response);
                break;
            case "/delete":
            	System.out.println("inside /delete");
                deleteBook(request, response);
                break;
            case "/edit":
            	System.out.println("inside /edit");
                showEditForm(request, response);
                break;
            case "/update":
            	System.out.println("inside /update");
                updateBook(request, response);
                break;
          
            default:
            	System.out.println("inside listBook");
                listBook(request, response);
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 private void listBook(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException, ClassNotFoundException {
	        List<Book> listBook = bookDAO.listAllBooks();
	        request.setAttribute("listBook", listBook);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");
	        dispatcher.forward(request, response);
	    }
	 
	    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
	        dispatcher.forward(request, response);
	    }
	 
	    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, ServletException, IOException, ClassNotFoundException {
	        int id = Integer.parseInt(request.getParameter("id"));
	        System.out.println("ID :: " + id);
	        Book existingBook = bookDAO.getBook(id);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
	        request.setAttribute("book", existingBook);
	        dispatcher.forward(request, response);
	 
	    }
	 
	    private void insertBook(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ClassNotFoundException {
	        String title = request.getParameter("title");
	        String author = request.getParameter("author");
	        float price = Float.parseFloat(request.getParameter("price"));
	 
	        Book newBook = new Book(title, author, price);
	        bookDAO.insertBook(newBook);
	        response.sendRedirect("list");
	    }
	 
	    private void updateBook(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ClassNotFoundException {
	        int id = Integer.parseInt(request.getParameter("id"));
	        String title = request.getParameter("title");
	        String author = request.getParameter("author");
	        float price = Float.parseFloat(request.getParameter("price"));
	 
	        Book book = new Book(id, title, author, price);
	        bookDAO.updateBook(book);
	        response.sendRedirect("list");
	    }
	    
	   
	    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ClassNotFoundException {
	        int id = Integer.parseInt(request.getParameter("id"));
	 
	        Book book = new Book(id);
	        bookDAO.deleteBook(book);
	        response.sendRedirect("list");
	 
	    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

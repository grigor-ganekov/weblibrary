package weblibrary.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weblibrary.dao.*;
import weblibrary.dto.*;

@WebServlet("/add")
public class AddBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BookDAO dao;

	public void init() throws ServletException {
		dao = (BookDAO) getServletContext().getAttribute("dao");
		if (dao == null)
			throw new UnavailableException("Couldn�t get database.");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		request.setCharacterEncoding("UTF-8");
		String headline = request.getParameter("headline");
		String author = request.getParameter("author");
		String publisher = request.getParameter("publisher");
		String yearOfPublishing = request.getParameter("yearOfPublishing");
		String ISBN = request.getParameter("ISBN");
		Book theBook = new Book(headline, author, publisher, yearOfPublishing, ISBN);
		dao.addBook(theBook);
		response.sendRedirect("book?id=" + theBook.getId());

	}
}

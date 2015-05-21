package weblibrary.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weblibrary.dao.BookDAO;
import weblibrary.dao.SearchResult;

@WebServlet("/search")
public class SearchBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BookDAO dao;
	public void init() throws ServletException {
		dao = (BookDAO) getServletContext().getAttribute("dao");
		if (dao == null)
			throw new UnavailableException("Couldn’t get database.");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		SearchResult result = null;
		String searchField = request.getParameter("search");
		String type = request.getParameter("type");

		if (searchField == null) {
			searchField = "";
		}

		if (type == null) {
			result = dao.getAllBooks(1);
			result.setMethod("All books");
		} else if (type.equals("headline")) {
			result = dao.searchBookByHeadline(searchField, 1);
			result.setMethod("headline");
		} else if (type.equals("author")) {
			result = dao.searchBookByAuthor(searchField, 1);
			result.setMethod("author");
		} else {
			result = dao.getAllBooks(1);
			result.setMethod("All books");
		}

		request.setAttribute("result", result);
		RequestDispatcher dispatcher = request.getRequestDispatcher("books.jsp");
		dispatcher.forward(request, response);

	}

}

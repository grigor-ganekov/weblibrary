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
import weblibrary.dto.Book;

@WebServlet("/book")
public class SingleBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BookDAO dao;

	public void init() throws ServletException {
		dao = (BookDAO) getServletContext().getAttribute("dao");
		if (dao == null)
			throw new UnavailableException("Couldn’t get database.");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rawId = request.getParameter("id");
		Integer bookId = null;
		Book theBook = null;
		RequestDispatcher dispatcher = request.getRequestDispatcher("missing.html");
		try {
			bookId = Integer.parseInt(rawId);
		} catch (Exception e) {
			System.out.println("Bad input");
			dispatcher.forward(request, response);
		}
		try {
			theBook = dao.getBookById(bookId);
		} catch (Exception e) {
			dispatcher.forward(request, response);
		}
		if (theBook == null) {
			dispatcher.forward(request, response);
		}

		request.setAttribute("book", theBook);
		dispatcher = request.getRequestDispatcher("book.jsp");
		dispatcher.forward(request, response);
	}

}

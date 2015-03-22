package weblibrary.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weblibrary.dao.BookDAO;
import weblibrary.dto.Book;

@WebServlet("/delete")
public class DeleteBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		BookDAO dao = (BookDAO) getServletContext().getAttribute("dao");
		String rawId = request.getParameter("id");
		Integer bookId = null;
		RequestDispatcher dispatcher = request.getRequestDispatcher("missing.html");
		Book theBook = null;
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
		// delete the book
		dao.deleteBook(theBook);

		// dispatcher = request.getRequestDispatcher("single?id=" +
		// theBook.getId());
		// dispatcher.forward(request, response);
		response.sendRedirect("book?id=" + theBook.getId());

	}

}

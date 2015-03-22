package weblibrary.dao;

import java.sql.*;
import java.util.ArrayList;
import weblibrary.driver.*;
import weblibrary.dto.*;

/**
 * @author Grigor Ganekov
 *
 */
public class BookDAO {

	private static final int RESULTS_PER_PAGE = 15;

	private Connection myConn;

	public BookDAO(String DBUrl, String user, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection(DBUrl, user, password);
		} catch (Exception e) {
			System.out.println("Exception in DAOUtils.showColumns()");
			e.printStackTrace();
		}
	}

	public BookDAO(Connection myConn) {
		this.myConn = myConn;
	}

	public Connection getConn() {
		return myConn;
	}

	public SearchResult getAllBooks(int page) {
		SearchResult result = new SearchResult();
		result.setCurrentPage(page);
		result.setSearchField("");
		ArrayList<Book> list = new ArrayList<Book>();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myStmt = myConn.prepareStatement("SELECT * FROM library.get_all ORDER BY headline LIMIT ? OFFSET ?");
			myStmt.setInt(1, RESULTS_PER_PAGE);
			myStmt.setInt(2, (page - 1) * RESULTS_PER_PAGE);
			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				Book tempBook = convertRowToBook(myRs);
				list.add(tempBook);
			}
			result.setResultList(list);
			DAOUtils.close(null, myStmt, myRs);

			int QuerryPagesCount = 0;
			myStmt = myConn.prepareStatement("SELECT COUNT(*) AS pages FROM library.get_all");
			myRs = myStmt.executeQuery();
			int allRows = 0;
			while (myRs.next()) {
				allRows = myRs.getInt("pages");
			}
			if (allRows % RESULTS_PER_PAGE == 0) {
				QuerryPagesCount = allRows / RESULTS_PER_PAGE;
			} else {
				QuerryPagesCount = allRows / RESULTS_PER_PAGE + 1;
			}
			result.setPages(QuerryPagesCount);
		} catch (SQLException sqle) {
			System.out.println("Exception in BookDAO.getAllBooks()");
			sqle.printStackTrace();
		} finally {
			DAOUtils.close(null, myStmt, myRs);
		}
		return result;
	}

	public SearchResult searchBookByHeadline(String headline, int page) {
		SearchResult result = new SearchResult();
		result.setCurrentPage(page);
		result.setSearchField(headline);
		ArrayList<Book> list = new ArrayList<Book>();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myStmt = myConn
					.prepareStatement("SELECT * FROM get_all WHERE headline LIKE ? ORDER BY headline LIMIT ? OFFSET ?");
			myStmt.setString(1, "%" + headline + "%");
			myStmt.setInt(2, RESULTS_PER_PAGE);
			myStmt.setInt(3, (page - 1) * RESULTS_PER_PAGE);
			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				Book tempBook = convertRowToBook(myRs);
				list.add(tempBook);
			}
			result.setResultList(list);
			DAOUtils.close(null, myStmt, myRs);

			int QuerryPagesCount = 0;
			myStmt = myConn.prepareStatement("SELECT COUNT(*) AS pages FROM library.get_all WHERE headline LIKE ? ");
			myStmt.setString(1, "%" + headline + "%");
			myRs = myStmt.executeQuery();
			int allRows = 0;
			while (myRs.next()) {
				allRows = myRs.getInt("pages");
			}
			if (allRows % RESULTS_PER_PAGE == 0) {
				QuerryPagesCount = allRows / RESULTS_PER_PAGE;
			} else {
				QuerryPagesCount = allRows / RESULTS_PER_PAGE + 1;
			}
			result.setPages(QuerryPagesCount);

		} catch (SQLException sqle) {
			System.out.println("Exception in BookDAO.searchBookByHeadline()");
			sqle.printStackTrace();
		} finally {
			DAOUtils.close(null, myStmt, myRs);
		}
		return result;
	}

	public SearchResult searchBookByAuthor(String author, int page) {
		SearchResult result = new SearchResult();
		result.setCurrentPage(page);
		result.setSearchField(author);
		ArrayList<Book> list = new ArrayList<Book>();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myStmt = myConn
					.prepareStatement("select * from get_all where author_name like ?  order by author_name LIMIT ? OFFSET ?");
			myStmt.setString(1, "%" + author + "%");
			myStmt.setInt(2, RESULTS_PER_PAGE);
			myStmt.setInt(3, (page - 1) * RESULTS_PER_PAGE);
			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				Book tempBook = convertRowToBook(myRs);
				list.add(tempBook);
			}
			result.setResultList(list);
			DAOUtils.close(null, myStmt, myRs);

			int QuerryPagesCount = 0;
			myStmt = myConn.prepareStatement("SELECT COUNT(*) AS pages FROM library.get_all WHERE author_name like ? ");
			myStmt.setString(1, "%" + author + "%");
			myRs = myStmt.executeQuery();
			int allRows = 0;
			while (myRs.next()) {
				allRows = myRs.getInt("pages");
			}
			if (allRows % RESULTS_PER_PAGE == 0) {
				QuerryPagesCount = allRows / RESULTS_PER_PAGE;
			} else {
				QuerryPagesCount = allRows / RESULTS_PER_PAGE + 1;
			}
			result.setPages(QuerryPagesCount);

		} catch (SQLException sqle) {
			System.out.println("Exception in BookDAO.searchBookByAuthor()");
			sqle.printStackTrace();
		} finally {
			DAOUtils.close(null, myStmt, myRs);
		}
		return result;
	}

	private Book convertRowToBook(ResultSet myRs) {
		Book temp = null;
		try {
			int id = myRs.getInt("book_id");
			String headline = myRs.getString("headline");
			String author = myRs.getString("author_name");
			String publisher = myRs.getString("publisher_name");
			String yearOfPublishing = myRs.getString("year_of_publishing");
			String ISBN = myRs.getString("ISBN");

			temp = new Book(headline, author, publisher, yearOfPublishing, ISBN);
			temp.setId(id);
		} catch (SQLException sqle) {
			System.out.println("Exception in BookDAO.convertRowToBook()");
			sqle.printStackTrace();
		}
		return temp;
	}

	private void validateString(String theString) {
		if (theString == null || theString.isEmpty() || theString.trim().length() == 0) {
			throw new IllegalArgumentException();
		}

	}

	public int addAuthorIfMissing(String name) throws SQLException {
		// gets author id, if author is missing adds the author and returns the
		// new author id
		try {
			validateString(name);
		} catch (IllegalArgumentException iae) {
			throw new IllegalArgumentException("Book must have author first and last name");
		}
		String[] lineSort = name.split(" ");
		CallableStatement myStmt = null;
		PreparedStatement insertStmt = null;
		try {
			myStmt = myConn.prepareCall("{call get_author_id(?,?,?)}");
			myStmt.setString(1, lineSort[0]);
			myStmt.setString(2, lineSort[lineSort.length - 1]);
			myStmt.registerOutParameter(3, Types.INTEGER);
			myStmt.executeQuery();
			int authorId = myStmt.getInt(3);
			if (authorId != 0) {
				return authorId;
			} else {
				insertStmt = myConn.prepareStatement("INSERT INTO author (`first_name`, `last_name`) VALUES (?, ?)");
				insertStmt.setString(1, lineSort[0]);
				insertStmt.setString(2, lineSort[lineSort.length - 1]);
				insertStmt.executeUpdate();
				return addAuthorIfMissing(name);
			}
		} finally {
			DAOUtils.close(null, myStmt, null);
			DAOUtils.close(null, insertStmt, null);
		}

	}

	public int addPublisherIfMissing(String name) throws SQLException {
		// gets publisher id, if publisher is missing adds the publisher and
		// returns the new publisher id
		try {
			validateString(name);
		} catch (IllegalArgumentException iae) {
			throw new IllegalArgumentException("Book must have publisher name");
		}

		CallableStatement myStmt = null;
		PreparedStatement insertStmt = null;
		try {
			myStmt = myConn.prepareCall("{call get_publisher_id(?,?)}");
			myStmt.setString(1, name);
			myStmt.registerOutParameter(2, Types.INTEGER);
			myStmt.executeQuery();
			int publisherId = myStmt.getInt(2);
			if (publisherId != 0) {
				return publisherId;
			} else {
				insertStmt = myConn.prepareStatement("INSERT INTO publisher (`name`) VALUES (?)");
				insertStmt.setString(1, name);
				insertStmt.executeUpdate();
				return addPublisherIfMissing(name);
			}
		} finally {
			DAOUtils.close(null, myStmt, null);
			DAOUtils.close(null, insertStmt, null);
		}

	}

	public void addBook(Book theBook) {

		try {
			validateString(theBook.getHeadline());
			validateString(theBook.getISBN());
		} catch (IllegalArgumentException iae) {
			throw new IllegalArgumentException("Book must have headline, ISBN, author and publisher");
		}

		PreparedStatement myStmt = null;
		ResultSet generatedKeys = null;
		try {
			myStmt = myConn.prepareStatement(
					"INSERT INTO `library`.`book` (`headline`, `year_of_publishing`, `ISBN`) VALUES ( ? , ? , ? )",
					Statement.RETURN_GENERATED_KEYS);
			// set parameters
			myStmt.setString(1, theBook.getHeadline());
			myStmt.setString(2, theBook.getYearOfPublishing());
			myStmt.setString(3, theBook.getISBN());
			int authorId = addAuthorIfMissing(theBook.getAuthor());
			int publisherId = addPublisherIfMissing(theBook.getPublisher());
			myStmt.executeUpdate();

			// get and set the generated book ID
			generatedKeys = myStmt.getGeneratedKeys();
			int bookId = 0;
			if (generatedKeys.next()) {
				bookId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Error generating key for book");
			}
			theBook.setId(bookId);
			DAOUtils.close(null, myStmt, null);
			// set link between book and author
			myStmt = myConn.prepareStatement("INSERT INTO book_author (`book_id`,`author_id`) VALUES (?,?);");
			myStmt.setInt(1, bookId);
			myStmt.setInt(2, authorId);
			myStmt.executeUpdate();
			DAOUtils.close(null, myStmt, null);
			// set link between book and publisher
			myStmt = myConn.prepareStatement("INSERT INTO book_publisher (`book_id`,`publisher_id`) VALUES (?,?);");
			myStmt.setInt(1, bookId);
			myStmt.setInt(2, publisherId);
			myStmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("Exception in BookDAO.addBook()");
			sqle.printStackTrace();
		} finally {
			DAOUtils.close(null, myStmt, generatedKeys);
		}
	}

	public void deleteBookById(int id) {
		PreparedStatement myStmt = null;
		try {
			// first delete the links
			myStmt = myConn.prepareStatement("DELETE FROM book_author WHERE `book_id`= ?");
			myStmt.setInt(1, id);
			myStmt.executeUpdate();
			DAOUtils.close(null, myStmt, null);

			myStmt = myConn.prepareStatement("DELETE FROM book_publisher WHERE `book_id`= ?");
			myStmt.setInt(1, id);
			myStmt.executeUpdate();
			DAOUtils.close(null, myStmt, null);

			// then the book itself
			myStmt = myConn.prepareStatement("DELETE FROM book WHERE `book_id`= ?");
			myStmt.setInt(1, id);
			myStmt.executeUpdate();

		} catch (SQLException sqle) {
			System.out.println("Exception in BookDAO.deleteBookById()");
			sqle.printStackTrace();
		} finally {
			DAOUtils.close(null, myStmt, null);
		}
	}

	public void deleteBook(Book theBook) {

		deleteBookById(theBook.getId());
	}

	public Book getBookById(int id) {
		Book theBook = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myStmt = myConn.prepareStatement("SELECT * FROM library.get_all WHERE book_id = ?");
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				theBook = convertRowToBook(myRs);
			}
		} catch (SQLException sqle) {
			System.out.println("Exception in BookDAO.getBookById()");
			sqle.printStackTrace();
		} finally {
			DAOUtils.close(null, myStmt, myRs);
		}
		return theBook;
	}

}

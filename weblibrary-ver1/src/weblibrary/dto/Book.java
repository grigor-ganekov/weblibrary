package weblibrary.dto;

public class Book {

	private int id; // if the book isn't added to the database or id is
					// explicitly set this is 0
	private String headline;
	private String author;
	private String publisher;
	private String yearOfPublishing;
	private String ISBN;

	public Book(String headline, String author, String publisher, String yearOfPublishing, String ISBN) {
		this.headline = headline;
		this.author = author;
		this.publisher = publisher;
		this.yearOfPublishing = yearOfPublishing;
		this.ISBN = ISBN;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getYearOfPublishing() {
		return yearOfPublishing;
	}

	public void setYearOfPublishing(String yearOfPublishing) {
		this.yearOfPublishing = yearOfPublishing;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	@Override
	public String toString() {
		return "Book ID: " + id + " Headline: " + headline + ", Author: " + author + ", Publisher: " + publisher
				+ ", Year of publishing: " + yearOfPublishing + ", ISBN: " + ISBN;
	}

}

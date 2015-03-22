package weblibrary.dao;

import java.util.ArrayList;
import weblibrary.dto.Book;

public class SearchResult {

	private ArrayList<Book> resultList;
	private int pages;
	private int currentPage;
	private String searchField;
	private String method;

	public ArrayList<Book> getResultList() {
		return resultList;
	}

	public void setResultList(ArrayList<Book> resultList) {
		this.resultList = resultList;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}

<%@page import="org.eclipse.jdt.internal.compiler.ast.ForeachStatement"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="weblibrary.dao.*"%>
<%@ page import="weblibrary.dto.Book"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Library</title>
<link rel="shortcut icon" href="../favicon.ico">
<link rel="stylesheet" type="text/css" href="css/normalize.css" />
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/component.css" />
<title>Library</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<!--[if lte IE 8]><script src="css/ie/html5shiv.js"></script><![endif]-->
<script src="js/jquery.min.js"></script>
<script src="js/jquery.dropotron.min.js"></script>
<script src="js/skel.min.js"></script>
<script src="js/skel-layers.min.js"></script>
<script src="js/init.js"></script>
<noscript>
	<link rel="shortcut icon" href="../favicon.ico">
	<link rel="stylesheet" type="text/css" href="css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="css/demo.css" />
	<link rel="stylesheet" type="text/css" href="css/component.css" />
	<link rel="stylesheet" href="css/skel.css" />
	<link rel="stylesheet" href="css/style.css" />
	<link rel="stylesheet" href="css/style-desktop.css" />

</noscript>
<!--[if lte IE 8]><link rel="stylesheet" href="css/ie/v8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="css/ie/v9.css" /><![endif]-->
</head>
</head>
<%!private BookDAO dao;

	public void init() throws ServletException {
		dao = (BookDAO) getServletContext().getAttribute("dao");
		if (dao == null)
			throw new UnavailableException("Couldn’t get database.");
	}%>

<%
	//this page search result
		SearchResult result = (SearchResult) request.getAttribute("result");
		if (result == null){
		result = dao.getAllBooks(1);
		result.setMethod("All books");
		}
%>
<body class="left-sidebar">
	<!-- Header Wrapper -->
	<div id="header-wrapper">
		<div class="container">

			<!-- Header -->
			<header id="header">
			<div class="inner">

				<!-- Logo -->
				<h1>
					<a href="index.html" id="logo">Библиотека</a>
				</h1>

				<!-- Nav -->
				<nav id="nav">
				<ul>
					<li><a href="index.html">Начало</a></li>
					<li class="current_page_item"><a href="books.jsp">Книги</a></li>
					<li><a href="add.html">+добави</a>
					<li>
				</ul>
				</nav>

			</div>
			</header>

		</div>
	</div>

	<!-- Main Wrapper -->
	<div id="main-wrapper">
		<div class="wrapper style2">
			<div class="inner">
				<div class="container">

					<!-- Content -->

					<article>
					<form action="search">
						<input type="text" name="search"
							value="<%=result.getSearchField()%>" style="width: 50%" /> <select
							name="type" style="width: 15%">
							<%
								if (result.getMethod().equals("author")){
							%>
							<option value="headline">заглавие</option>
							<option value="author" selected="selected">автор</option>
							<%
								}else{
							%>
							<option value="headline" selected="selected">заглавие</option>
							<option value="author">автор</option>
							<%
								}
							%>
						</select> <input type="submit" value="Търси" />
					</form>

					<%
						if (result.getPages()>1){
					%>
					<form id="searchf" action="goto">
						<input type="hidden" name="search"
							value="<%=result.getSearchField()%>" /> <input type="hidden"
							name="type" value="<%=result.getMethod()%>" />
						<script>
							function myFunction() {
								var x = document.getElementsByName("page").value;
								document.getElementById("searchf").submit();
							}
						</script>
						<select name="page" style="width: 30%" onchange="myFunction()">
							<%
								for (int i = 1; i <= result.getPages(); i++) {
							%>

							<%
								if (i == result.getCurrentPage()) {
							%>
							<option value="<%=i%>" selected="selected"><%=result.getSearchField() + " page "%><%=i%></option>
							<%
								} else {
							%>
							<option value="<%=i%>"><%=result.getSearchField() + " page "%><%=i%></option>
							<%
								}
							%>
							<%
								}
							%>
						</select>
					</form>
					<%
						}
					%> <br />
					<table>
						<thead>
							<tr>
								<th>Headline</th>
								<th>Author</th>
								<th>Publisher</th>
								<th>Year of publishing</th>
							</tr>
						</thead>
						<tbody>
							<%
								for (Book aBook : result.getResultList()) {
							%>

							<tr>
								<td><a href="book?id=<%=aBook.getId()%>"
									style="text-decoration: none"><%=aBook.getHeadline()%></a></td>
								<td><a href="book?id=<%=aBook.getId()%>"
									style="text-decoration: none"><%=aBook.getAuthor()%></a></td>
								<td><a href="book?id=<%=aBook.getId()%>"
									style="text-decoration: none"><%=aBook.getPublisher()%></a></td>
								<td><a href="book?id=<%=aBook.getId()%>"
									style="text-decoration: none"><%=aBook.getYearOfPublishing()%></a></td>
							</tr>

							<%
								}
							%>
						</tbody>
					</table>
					</article>

				</div>
			</div>
		</div>

	</div>

	<!-- Footer Wrapper -->
	<div id="footer-wrapper">
		<footer id="footer" class="container">
		<div class="row">
			<div class="3u">



				<!-- Links -->


			</div>
			<div class="6u">



				<!-- Contact -->
				<section>
				<h2>Get in touch</h2>
				<div>
					<div class="row">
						<div class="6u">
							<dl class="contact">
								<dt>Twitter</dt>
								<dd>
									<a href="#">@untitled-corp</a>
								</dd>
								<dt>Facebook</dt>
								<dd>
									<a href="#">facebook.com/untitled</a>
								</dd>
								<dt>WWW</dt>
								<dd>
									<a href="#">untitled.tld</a>
								</dd>
								<dt>Email</dt>
								<dd>
									<a href="#">user@untitled.tld</a>
								</dd>
							</dl>
						</div>
						<div class="6u">
							<dl class="contact">
								<dt>Address</dt>
								<dd>
									1234 Fictional Rd<br /> Nashville, TN 00000-0000<br /> USA
								</dd>
								<dt>Phone</dt>
								<dd>(000) 000-0000</dd>
							</dl>
						</div>
					</div>
				</div>
				</section>

			</div>
		</div>
		<div class="row">
			<div class="12u">
				<div id="copyright">
					<ul class="menu">
						<li>&copy; Grigor Ganekov. All rights reserved</li>
						<li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
					</ul>
				</div>
			</div>
		</div>
		</footer>
	</div>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="weblibrary.dao.*"%>
<%@ page import="weblibrary.dto.Book"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Library</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<script src="js/jquery.min.js"></script>
		<script src="js/jquery.dropotron.min.js"></script>
		<script src="js/skel.min.js"></script>
		<script src="js/skel-layers.min.js"></script>
		<script src="js/init.js"></script>
		<noscript>
			<link rel="stylesheet" href="css/skel.css" />
			<link rel="stylesheet" href="css/style.css" />
			<link rel="stylesheet" href="css/style-desktop.css" />
		</noscript>
		<!--[if lte IE 8]><link rel="stylesheet" href="css/ie/v8.css" /><![endif]-->
		<!--[if lte IE 9]><link rel="stylesheet" href="css/ie/v9.css" /><![endif]-->
	</head>

<body class="left-sidebar">
		<% BookDAO dao = (BookDAO) getServletContext().getAttribute("dao"); %>
		<%//this page book
			Book theBook = null;
			theBook = (Book) request.getAttribute("book");
			RequestDispatcher dispatcher = request.getRequestDispatcher("missing.html");
			if(theBook == null){
				dispatcher.forward(request, response);
				}
			
			%>
		<!-- Header Wrapper -->
			<div id="header-wrapper">
				<div class="container">
						
					<!-- Header -->
						<header id="header">
							<div class="inner">
							
								<!-- Logo -->
									<h1><a href="index.html" id="logo">Библиотека</a></h1>
								
								<!-- Nav -->
									<nav id="nav">
										<ul>
											<li><a href="index.html">Начало</a></li>
											<li><a href="books.jsp">Книги</a></li>
											<li><a href="add.html">+добави</a><li>
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
							<div class="row">
								<div class="4u">
									<div id="sidebar">

										<!-- Sidebar -->
									
											<section>
												<header>
													<h2><%=theBook.getHeadline() %></h2>
												</header>
                                                
												<span class="image featured"><img src="images/pic09.jpg" alt="unrellevent" /></span>
												
											</section>
								
									</div>
								</div>
								<div class="8u important(collapse)">
									<div id="content">

										<!-- Content -->
									
											<article>
                                               <form action="search">
                                              <input type="text" name="search" style="width: 50%" />
                                                      <select name="type" style="width: 15%" >
			                                          <option value="headline">headline</option>
			                                          <option value="author">author</option>
		                                                </select>
                                               <input type="submit" value="Търси" />
                                                </form>
                                                <br />
												<br /><p>Headline: <%=theBook.getHeadline() %></p>
                                                <br /><p>Author: <%=theBook.getAuthor() %></p>
                                                <br /><p>Publisher: <%=theBook.getPublisher() %></p>
												<br /><p>Year of publishing: <%=theBook.getYearOfPublishing() %></p>
                                                <br /><p>ISBN: <%=theBook.getISBN() %></p>
												
												<form method="post" action="delete">
                                                <input style="background-color: #e31818" type="submit" value="Изтрий книгата">
                                                <input type="hidden" name="id" value="<%=theBook.getId()%>">
                                                </form>

											</article>
									</div>
								</div>
							</div>
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
													<dd><a href="#">@untitled-corp</a></dd>
													<dt>Facebook</dt>
													<dd><a href="#">facebook.com/untitled</a></dd>
													<dt>WWW</dt>
													<dd><a href="#">untitled.tld</a></dd>
													<dt>Email</dt>
													<dd><a href="#">user@untitled.tld</a></dd>
												</dl>
											</div>
											<div class="6u">
												<dl class="contact">
													<dt>Address</dt>
													<dd>
														1234 Fictional Rd<br />
														Nashville, TN 00000-0000<br />
														USA
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
									<li>&copy; Grigor Ganekov. All rights reserved</li><li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
								</ul>
							</div>
						</div>
					</div>
				</footer>
			</div>

	</body>
</html>
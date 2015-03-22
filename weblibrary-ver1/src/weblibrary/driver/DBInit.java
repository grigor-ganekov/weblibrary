package weblibrary.driver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import weblibrary.dao.BookDAO;

@WebListener
public class DBInit implements ServletContextListener {

	private BookDAO DefaultDao = null;

	public void contextDestroyed(ServletContextEvent arg0) {
		if (DefaultDao != null) {
			DAOUtils.close(this.DefaultDao.getConn(), null, null);
		}
	}

	public void contextInitialized(ServletContextEvent sce) {
		try {
			ServletContext sc = sce.getServletContext();
			String defaultUser = sc.getInitParameter("user");
			String defaultPass = sc.getInitParameter("password");
			String defaultDBUrl = sc.getInitParameter("dburl");

			this.DefaultDao = new BookDAO(defaultDBUrl, defaultUser, defaultPass);
			sc.setAttribute("dao", this.DefaultDao);
			System.out.println("dao created and added to context");
		} catch (Exception exc) {
			System.out.println("Exception in DBInit.contextInitialized()");
			exc.printStackTrace();
		}
	}

}

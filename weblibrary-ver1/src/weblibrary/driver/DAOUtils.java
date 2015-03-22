package weblibrary.driver;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Grigor Ganekov
 *
 */
public class DAOUtils {

	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}

			if (stmt != null) {
				stmt.close();
			}

			if (conn != null) {
				conn.close();
			}

		} catch (SQLException sqle) {
			System.out.println("Exception in DAOUtils.close()");
			sqle.printStackTrace();
		}
	}

	public static void basicInfo(Connection connection) {
		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			System.out.println("Product name: " + databaseMetaData.getDatabaseProductName());
			System.out.println("Product version: " + databaseMetaData.getDatabaseProductVersion());
			System.out.println();

			System.out.println("JDBC Driver name: " + databaseMetaData.getDriverName());
			System.out.println("JDBC Driver version: " + databaseMetaData.getDriverVersion());
		} catch (SQLException sqle) {
			System.out.println("Exception in DAOUtils.basicInfo()");
			sqle.printStackTrace();
		}
	}

	public static void showTables(Connection connection) {
		ResultSet myRs = null;
		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			System.out.println("List of Tables");
			System.out.println("--------------");

			myRs = databaseMetaData.getTables(null, null, null, null);
			while (myRs.next()) {
				System.out.println(myRs.getString("TABLE_NAME"));
			}
		} catch (NullPointerException npe) {
			System.out.println("Null pointer exception in DAOUtils.showTables()");
			System.out.println("First establish connection!");
		} catch (SQLException sqle) {
			System.out.println("Exception in DAOUtils.showTables()");
			sqle.printStackTrace();
		} finally {
			close(null, null, myRs);
		}
	}

	public static void showColumns(Connection connection, String tableName) {
		ResultSet myRs = null;
		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			myRs = databaseMetaData.getColumns(null, null, tableName, null);
			System.out.println("List of Columns");
			System.out.println("--------------");
			while (myRs.next()) {
				System.out.println(myRs.getString("COLUMN_NAME"));
			}
		} catch (NullPointerException npe) {
			System.out.println("Null pointer exception in DAOUtils.showColumns()");
			System.out.println("First establish connection!");
		} catch (SQLException sqle) {
			System.out.println("Exception in DAOUtils.showColumns()");
			sqle.printStackTrace();
		} finally {
			close(null, null, myRs);
		}
	}

}

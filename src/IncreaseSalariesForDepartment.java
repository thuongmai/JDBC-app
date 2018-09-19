import java.sql.*;

//Test calling stored procedure with IN parameters

public class IncreaseSalariesForDepartment {
	public static void main(String[] args) throws Exception {
		Connection myConn = null;
		CallableStatement mySt = null;

		String url = "jdbc:mysql://localhost:3306/demo?useSSL=false";
		String user = "student";
		String password = "student";

		String theDepartment = "Engineering";
		int theIncreaseAmount = 10000;

		try {
			// Get a connection to database
			myConn = DriverManager.getConnection(url, user, password);

			// Show salaries before
			System.out.println("Salaries BEFORE\n");
			showSalaries(myConn, theDepartment);

			// Prepare the stored procedure call
			mySt = myConn.prepareCall("{call increase_salaries_for_department(?, ?)}");

			// Set the parameters
			mySt.setString(1, theDepartment);
			mySt.setDouble(2, theIncreaseAmount);

			// Call stored procedure
			System.out.println("\nCalling stored procedure");
			mySt.execute();
			System.out.println("Finished calling stored procedure");

			// Show salary AFTER
			System.out.println("\nSalaries AFTER");
			showSalaries(myConn, theDepartment);

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (mySt != null)
				mySt.close();
			if (myConn != null)
				myConn.close();
		}
	}

	private static void showSalaries(Connection myConn, String theDepartment) throws SQLException {
		PreparedStatement mySt = myConn.prepareStatement("SELECT * FROM employees WHERE department = ?");
		mySt.setString(1, theDepartment);
		ResultSet myRs = mySt.executeQuery();
		while (myRs.next())
			System.out.println(
					myRs.getString("first_name") + "\t" + myRs.getString("last_name") + "\t" + myRs.getInt("salary"));
		// throw new SQLException("Test Show Salaries exception");
	}
}

import java.sql.*;

public class JDBC_Ex1 {
	Connection con;

	public JDBC_Ex1() {
		makeConnection();
	}

	public Connection makeConnection() {
		String url = "jdbc:mysql://localhost:3306/book_db";
		String id = "root";
		String passwd = "Anjuyh7410@";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, id, passwd);
			System.out.println("DB ���� �Ϸ�");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC ����̹� �ε� ����");
		} catch (SQLException e) {
			System.out.println("DB ���� ����");
			System.out.println("SQLEXception" + e.getMessage());
			System.out.println("SQLState" + e.getSQLState());
			System.out.println("VendorError" + e.getErrorCode());
		}
		return con;
	}

	public static void main(String[] args) {
		new JDBC_Ex1();
	}

}

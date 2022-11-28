import java.sql.*;

public class JDBC_Ex2 {
	Connection conn;
	Statement stmt = null;
	ResultSet srs;

	public JDBC_Ex2() {
		makeConnection();
		disConnection();
	}

	public Connection makeConnection() {
		String url = "jdbc:mysql://localhost:3306/book_db?serverTimezone=Asia/Seoul";
		String id = "root";
		String passwd = "Anjuyh7410@";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, passwd);
			System.out.println("DB 연결 완료");
			stmt = conn.createStatement();

			srs = stmt.executeQuery("select * from student"); 								// bring data
			printData(srs, "name", "id", "dept");

			srs = stmt.executeQuery("select name, id, dept from student where name='안준현'");
			printData(srs, "name", "id", "dept");

		} catch (ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로드 에러");

		} catch (SQLException e) {
			System.out.println("DB 연결 오류");
			System.out.println("SQLEXception" + e.getMessage());
			System.out.println("SQLState" + e.getSQLState());
			System.out.println("VendorError" + e.getErrorCode());

		}
		return conn;
	}

	private static void printData(ResultSet srs, String col1, String col2, String col3) throws SQLException {
		while (srs.next()) {
			if (!col1.equals(""))
				System.out.print(srs.getString("name"));
			if (!col2.equals(""))
				System.out.print("\t|\t" + srs.getString("id"));
			if (!col3.equals(""))
				System.out.println("\t|\t" + srs.getString("dept"));
			else
				System.out.println();
		}
	}
	
	public void disConnection() {
		try{
		srs.close();
		stmt.close();
		conn.close();
		}catch(SQLException e){System.out.println(e.getMessage());}
		}


	public static void main(String[] args) {
		new JDBC_Ex2();
	}

}

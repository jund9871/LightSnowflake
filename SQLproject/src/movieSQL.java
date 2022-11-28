import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;
import javax.swing.table.*;

class movieModel { // 영화 멤버
	String movieName; // 영화이름
	String director; // 감독
	String releaseDate; // 개봉일
	String country; // 국가
	String age; // 영상물 등급(나이 제한)
	String genre; // 장르

	public movieModel(String movieName, String director, String releseDate, String country, String age, String genre) {
		this.movieName = movieName;
		this.director = director;
		this.releaseDate = releseDate;
		this.country = country;
		this.age = age;
		this.genre = genre;
	}
}

class movie extends JFrame {
	String countrys[] = { "한국", "미국", "중국", "일본", "영국", "프랑스" }; // 멤버에 들어갈 값들
	String label[] = { "영화명", "감독", "개봉일자", "제작 국가", "영상물 등급", "장르선택" };
	String ages[] = { "ALL", "12세", "15세", "19세" };
	String buttonName[] = { "찾기", "선택", "추가", "수정", "삭제", "초기화" };
	String genre[] = { "드라마", "액션", "코미디", "사극", "로맨스" };
	JRadioButton age[] = new JRadioButton[4]; // 연령 등급 선택 버튼
	JLabel information[] = new JLabel[6]; // 영화 정보 분류 레이블
	JTextField tf[] = new JTextField[3]; // 영화명, 감독, 개봉일 작성용 텍스트 필드
	JButton btn[] = new JButton[6]; // 영화 조작 버튼
	JRadioButton[] genreCheck = new JRadioButton[5]; // 장르 선택 버튼
	JComboBox<String> country; // 제작사 국가 선택
	ButtonGroup ageGroup = new ButtonGroup(); // 연령 등급 버튼 그룹
	ButtonGroup genreGroup = new ButtonGroup(); // 장르 버튼 그룹
	JPanel movieInfo = new JPanel(); // 영화 정보 패널
	JPanel ageLimit = new JPanel(); // 연령 선택 패널
	JPanel genreSelect = new JPanel(); // 장르 선택 패널
	JPanel btnPanel = new JPanel(); // 조작 버튼 패널
	JLabel pL = new JLabel(); // 이미지 출력을 위한 레이블
	ImageIcon icon = new ImageIcon("images/null.png"); // 초기 빈 이미지
	GridLayout grid = new GridLayout(3, 2, 0, 1); // 영화 정보 정렬 레이아웃
	DefaultTableModel model = new DefaultTableModel(label, 0); // 테이블 기본 모델
	JTable movieTable = new JTable(model); // 영화 목록 출력

	public movie() {
		setTitle("Movie"); // 프레임 기본 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1350, 620);
		newComponent();
		setComponent();
		addComponent();
		imageSet();
		setVisible(true);
	}

	void newComponent() {
		country = new JComboBox<String>(countrys); // 제작사 국가 콤보박스에 배열 넣기

		for (int i = 0; i < information.length; i++) { // 값 넣어주기
			information[i] = new JLabel(label[i]);
			information[i].setHorizontalAlignment(SwingConstants.CENTER);
			btn[i] = new JButton(buttonName[i]);
			btnPanel.add(btn[i]);
			if (i < 3) {
				movieInfo.add(information[i]);
				movieInfo.add(tf[i] = new JTextField(10));
			}
			if (i < 4) {
				age[i] = new JRadioButton(ages[i]);
				ageLimit.add(age[i]);
				ageGroup.add(age[i]);
			}
			if (i < 5) {
				genreCheck[i] = new JRadioButton(genre[i]);
				genreGroup.add(genreCheck[i]);
				genreSelect.add(genreCheck[i]);
			}
			if (i == 3) {
				movieInfo.add(information[i]);
				movieInfo.add(country);
			} else if (i == 4) {
				movieInfo.add(information[i]);
				movieInfo.add(ageLimit);
			} else if (i == 5) {
				movieInfo.add(information[i]);
			}
		}
	}

	void setComponent() {
		movieInfo.setLayout(grid); // 프레임 세팅
		genreSelect.setBorder(new LineBorder(Color.GRAY));
		ageLimit.setBorder(new LineBorder(Color.GRAY));
		age[0].setSelected(true);
		genreCheck[0].setSelected(true);
		tf[2].setBackground(Color.YELLOW);
	}

	void addComponent() {
		add(movieInfo, BorderLayout.NORTH); // 화면 넣기
		movieInfo.add(genreSelect);
		add(new JScrollPane(movieTable));
		add(pL, BorderLayout.EAST);
		add(btnPanel, BorderLayout.SOUTH);
	}

	void imageSet() {
		Image image = icon.getImage(); //이미지 받아오기
		Image changeImage = image.getScaledInstance(300, 424, Image.SCALE_SMOOTH);	// 이미지 크기 조절
		ImageIcon changeIcon = new ImageIcon(changeImage);	// 조절한 이미지 사용
		pL.setIcon(changeIcon);
	}
}

public class movieSQL {
	movie v; // 필요 클래스 선언
	movieModel m;

	int ageSelected; // 연령 등급 선택 번호
	int genreSelected;	// 장르 선택 번호

	Statement stmt = null; // SQL조작을 위한 선언
	Connection con = null;
	ResultSet rs = null;

	public movieSQL() {
		v = new movie();
		Setting();	// SQL로 영화 목록을 불러와 세팅
		setButtons();	// 초기 버튼 세팅
		for (int i = 0; i < 6; i++) { // 버튼에 리스너 붙여주기
			v.btn[i].addActionListener(new movieHandler());
		}
		v.tf[2].addActionListener(new movieHandler());	// 텍스트 필드에 리스너 붙이기
		v.movieTable.addMouseListener(new JTableClick());	// 테이블에 클릭 리스너 붙이기
	}

	class movieHandler implements ActionListener { // 액션 조작 추가
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == v.tf[2]) {	// 개봉일을 이용한 영화 선택
				if (v.btn[1].isEnabled()) {	// 이미 선택되었을 때 다시 선택 불가
					selectMovie();	// 영화 선택 메소드 호출
					setImage();	// 이미지 출력 메소드 호출
				}
			}
			if (e.getSource() == v.btn[0]) {	// 찾기 버튼
				searchMovie();	// 영화 찾기 메소드 호출
			}else if (e.getSource() == v.btn[1]) {	// 선택 버튼
				selectMovie();	// 영화 선택 메소드 호출
				setImage();		// 이미지 출력 메소드 호출
			} else if (e.getSource() == v.btn[2]) {	// 추가 버튼
				addMovie(); // 영화 추가 메소드 호출
				Setting();	// 영화 목록 갱신
			} else if (e.getSource() == v.btn[3]) {	// 수정 버튼
				updateMovie();	// 영화 수정 메소드 호출
				Setting();	// 영화 목록 갱신
			} else if (e.getSource() == v.btn[4]) {	// 삭제 버튼
				deleteMovie();	// 영화 삭제 메소드 호출
				Setting();	//영화 목록 갱신
			} else if (e.getSource() == v.btn[5]) {	// 초기화 버튼
				Setting();	//영화 갱신 목록 호출
				clearTextFields(0);	// 모든 텍스트 에리어 비우기
				setButtons();	// 버튼 초기상태로 되돌리기
				v.country.setSelectedIndex(0);		// 초기 선택값으로 되돌리기
				v.age[0].setSelected(true);			//
				v.genreCheck[0].setSelected(true);	//
				v.icon = new ImageIcon("images/null.png");	// 빈 이미지 출력
				v.imageSet();	//이미지 크기 조절
			}
		}
	}

	class JTableClick extends JFrame implements MouseListener {	// 마우스 리스너 추가
		public void mouseClicked(MouseEvent e) {	// 클릭 시
			int row = v.movieTable.getSelectedRow();	// 선택한 행 번호 받기
			makeConnection();
			String sql = "";
			sql = "SELECT * FROM movie WHERE releaseDate='" + v.movieTable.getModel().getValueAt(row, 2) + "'";	// 선택된 행의 PK값을 가져와
			try {																								// 조건으로 넣기
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					m = new movieModel(rs.getString("movieName"), rs.getString("director"), rs.getString("releaseDate"),
							rs.getString("country"), rs.getString("age"), rs.getString("genre"));
					modelToView();
					v.btn[0].setEnabled(false);	// 각 버튼 활성화 설정
					v.btn[1].setEnabled(false);
					v.btn[2].setEnabled(false);
					v.btn[3].setEnabled(true);
					v.btn[4].setEnabled(true);
					v.btn[5].setEnabled(true);
				}
			} catch (SQLException sqle) {
				System.out.println("is Not Exist");
			}
			setImage();	// 이미지 변경
			disConnection();
		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}
	}

	void viewToModel() { // 추가할 영화 세부사항 모델로 옮기기
		if (v.age[0].isSelected()) {
			ageSelected = 0;
		} else if (v.age[1].isSelected()) {
			ageSelected = 1;
		} else if (v.age[2].isSelected()) {
			ageSelected = 2;
		} else if (v.age[3].isSelected()) {
			ageSelected = 3;
		}
		if (v.genreCheck[0].isSelected()) {
			genreSelected = 0;
		} else if (v.genreCheck[1].isSelected()) {
			genreSelected = 1;
		} else if (v.genreCheck[2].isSelected()) {
			genreSelected = 2;
		} else if (v.genreCheck[3].isSelected()) {
			genreSelected = 3;
		} else if (v.genreCheck[4].isSelected()) {
			genreSelected = 4;
		}
		m = new movieModel(v.tf[0].getText(), v.tf[1].getText(), (v.tf[2].getText()),
				v.country.getSelectedItem().toString(), v.age[ageSelected].getText(),
				v.genreCheck[genreSelected].getText());
	}

	void modelToView() { // 선택 시 영화 세부사항 표시
		v.tf[0].setText(m.movieName);
		v.tf[1].setText(m.director);
		v.tf[2].setText(m.releaseDate);
		if (m.country.equals(v.countrys[0]))
			v.country.setSelectedIndex(0);
		else if (m.country.equals(v.countrys[1]))
			v.country.setSelectedIndex(1);
		else if (m.country.equals(v.countrys[2]))
			v.country.setSelectedIndex(2);
		else if (m.country.equals(v.countrys[3]))
			v.country.setSelectedIndex(3);
		else if (m.country.equals(v.countrys[4]))
			v.country.setSelectedIndex(4);
		else if (m.country.equals(v.countrys[5]))
			v.country.setSelectedIndex(5);
		if (m.age.equals(v.ages[0])) {
			v.age[0].setSelected(true);
		} else if (m.age.equals(v.ages[1])) {
			v.age[1].setSelected(true);
		} else if (m.age.equals(v.ages[2])) {
			v.age[2].setSelected(true);
		} else if (m.age.equals(v.ages[3])) {
			v.age[3].setSelected(true);
		}
		if (m.genre.equals(v.genre[0])) {
			v.genreCheck[0].setSelected(true);
		} else if (m.genre.equals(v.genre[1])) {
			v.genreCheck[1].setSelected(true);
		} else if (m.genre.equals(v.genre[2])) {
			v.genreCheck[2].setSelected(true);
		} else if (m.genre.equals(v.genre[3])) {
			v.genreCheck[3].setSelected(true);
		} else if (m.genre.equals(v.genre[4])) {
			v.genreCheck[4].setSelected(true);
		}
	}

	void Setting() { // 초기 화면 세팅

		makeConnection();
		String sql = "";
		sql = "SELECT * FROM movie";
		try {
			rs = stmt.executeQuery(sql);
			v.model.setNumRows(0);	// 테이블 초기화
			while (rs.next()) {
				v.model.addRow(	// 테이블 영화 목록 불러오기
						new Object[] { rs.getString("movieName"), rs.getString("director"), rs.getString("releaseDate"),
								rs.getString("country"), rs.getString("age"), rs.getString("genre") });
			}
			tableSet();	// 테이블 기본 설정
		} catch (SQLException sqle) {
			System.out.println("getData: SQL Error");
		}
		disConnection();
	}

	public Connection makeConnection() { // SQL 접속
		String url = "jdbc:mysql://localhost/movie_db?serverTimezone=Asia/Seoul";
		String id = "root";
		String password = "Anjuyh7410@";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이브 적재 성공");
			con = DriverManager.getConnection(url, id, password);
			stmt = con.createStatement();
			System.out.println("데이터베이스 연결 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없습니다");
			e.getStackTrace();
		} catch (SQLException e) {
			System.out.println("연결에 실패하였습니다");
			e.getStackTrace();
		}
		return con;
	}

	public void searchMovie() { // 적은 칸에 따른 영화 검색
		makeConnection();
		String sql = "";
		if (v.tf[0].getText().equals("") && v.tf[1].getText().equals("") && v.tf[2].getText().equals(""))	// 모두 빈칸일 때 
			sql = "SELECT * FROM movie";
		else if (v.tf[1].getText().equals("") && v.tf[2].getText().equals(""))	// 영화 제목으로 검색
			sql = "SELECT * FROM movie WHERE movieName LIKE'%" + v.tf[0].getText() + "%'";
		else if (v.tf[0].getText().equals("") && v.tf[2].getText().equals(""))	// 영화 감독으로 검색
			sql = "SELECT * FROM movie WHERE director LIKE'%" + v.tf[1].getText() + "%'";
		else if (v.tf[0].getText().equals("") && v.tf[1].getText().equals(""))	// 개봉일로 검색
			sql = "SELECT * FROM movie WHERE releaseDate LIKE'%" + v.tf[2].getText() + "%'";
		else if (v.tf[0].getText().equals(""))	// 감독과 개봉일로 검색
			sql = "SELECT * FROM movie WHERE releaseDate LIKE'%" + v.tf[2].getText() + "%'" + "AND director LIKE'%"
					+ v.tf[1].getText() + "%'";
		else if (v.tf[1].getText().equals(""))	// 영화 제목과 개봉일로 검색
			sql = "SELECT * FROM movie WHERE releaseDate LIKE'%" + v.tf[2].getText() + "%'" + "AND movieName LIKE'%"
					+ v.tf[0].getText() + "%'";
		else if (v.tf[2].getText().equals(""))	// 열화 제목과 감독
			sql = "SELECT * FROM movie WHERE movieName LIKE'%" + v.tf[0].getText() + "%'" + "AND director LIKE'%"
					+ v.tf[1].getText() + "%'";
		
		try {
			rs = stmt.executeQuery(sql);
			v.model.setNumRows(0);
			while (rs.next()) {
				v.model.addRow(
						new Object[] { rs.getString("movieName"), rs.getString("director"), rs.getString("releaseDate"),
								rs.getString("country"), rs.getString("age"), rs.getString("genre") });
			}
		} catch (SQLException sqle) {
			System.out.println("getData: SQL Error");
		}
		disConnection();
	}

	public void selectMovie() { // 개봉일을 기준으로 영화 선택
		makeConnection();
		String sql = "";
		sql = "SELECT * FROM movie WHERE releaseDate='" + v.tf[2].getText() + "'";	// 개봉일(PK)을 통해 영화 선택
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				m = new movieModel(rs.getString("movieName"), rs.getString("director"), rs.getString("releaseDate"),
						rs.getString("country"), rs.getString("age"), rs.getString("genre"));
				modelToView();
				v.btn[0].setEnabled(false);	// 버튼 세팅
				v.btn[1].setEnabled(false);
				v.btn[2].setEnabled(false);
				v.btn[3].setEnabled(true);
				v.btn[4].setEnabled(true);
				v.btn[5].setEnabled(true);
			} else {
				v.btn[0].setEnabled(true);
				v.btn[1].setEnabled(true);
				v.btn[2].setEnabled(true);
				v.btn[3].setEnabled(false);
				v.btn[4].setEnabled(false);
				v.btn[5].setEnabled(true);
			}
		} catch (SQLException sqle) {
			System.out.println("is Not Exist");
		}
		disConnection();
	}

	public void addMovie() { // 적은 세부사항으로 영화 목록에 추가
		if(v.tf[2].getText().equals(""))
			v.tf[2].setText("개봉일을 넣어주세요");
		else {
		makeConnection();
		viewToModel();
		try {
			String s = "";
			s = "INSERT INTO movie (movieName,director,releaseDate,country,age,genre) values ";
			s += "('" + m.movieName + "','" + m.director + "','" + m.releaseDate + "','" + m.country + "','" + m.age
					+ "','" + m.genre + "')";
			stmt.executeUpdate(s);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		disConnection();
		clearTextFields(0);
		}
	}

	public void updateMovie() { // 변경된 세부사항 적용
		viewToModel();
		makeConnection();
		try {
			String s = "";
			s = "UPDATE movie SET movieName='" + m.movieName + "',director='" + m.director + "',releaseDate='"
					+ m.releaseDate + "',country='" + m.country + "',age='" + m.age + "',genre='" + m.genre
					+ "' WHERE releaseDate='" + m.releaseDate + "'";	// 개봉일을 PK로 찾기
			stmt.executeUpdate(s);
		} catch (SQLException sqle) {
			System.out.println("isExist: SQL Error");
		}
		disConnection();
		clearTextFields(0);
	}

	public void deleteMovie() { // 선택된 영화 삭제
		viewToModel();
		int isDelete = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까 ?");	//다이얼로그로 삭제시 확인
		if (isDelete == 0) {
			makeConnection();
			String sql = "";
			sql = "DELETE FROM movie where releaseDate='" + m.releaseDate + "'";
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException sqle) {
				System.out.println("isExist: DELETE SQL Error");
			}
			disConnection();
		}
		clearTextFields(0);
	}

	void tableSet() { // 영화 목록 정리
		DefaultTableCellRenderer ca = new DefaultTableCellRenderer();	// 테이블 기본 설정 만들기
		ca.setHorizontalAlignment(SwingConstants.CENTER);	// 가운데 정렬
		v.movieTable.setPreferredScrollableViewportSize(new Dimension(750, 200));
		v.movieTable.setAutoCreateRowSorter(true);	// 자동 정렬
		for (int i = 0; i < v.label.length; i++) {
			v.movieTable.getColumnModel().getColumn(i).setCellRenderer(ca);
		}
	}

	public void disConnection() { // SQL연결 종료
		try {
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void setImage() {
		v.icon = new ImageIcon("images/" + m.movieName + ".jpg");	// 이미지 바꾸기
		if (v.icon.getIconHeight() < 200) {
			v.icon = new ImageIcon("images/null.png");	//빈 이미지
		}
		v.imageSet();	// 이미지 크기 변경
	}

	public void setButtons() { // 초기 버튼 설정
		v.btn[0].setEnabled(true);
		v.btn[1].setEnabled(true);
		v.btn[2].setEnabled(true);
		v.btn[3].setEnabled(false);
		v.btn[4].setEnabled(false);
		v.btn[5].setEnabled(true);
	}

	public void clearTextFields(int n) { // 모든 텍스트 필드 초기화
		for (int i = n; i < v.tf.length; i++) {
			v.tf[i].setText("");
		}
	}

	public static void main(String[] args) {
		new movieSQL();
	}
}

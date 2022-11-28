import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;
import javax.swing.table.*;

class movieModel { // ��ȭ ���
	String movieName; // ��ȭ�̸�
	String director; // ����
	String releaseDate; // ������
	String country; // ����
	String age; // ���� ���(���� ����)
	String genre; // �帣

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
	String countrys[] = { "�ѱ�", "�̱�", "�߱�", "�Ϻ�", "����", "������" }; // ����� �� ����
	String label[] = { "��ȭ��", "����", "��������", "���� ����", "���� ���", "�帣����" };
	String ages[] = { "ALL", "12��", "15��", "19��" };
	String buttonName[] = { "ã��", "����", "�߰�", "����", "����", "�ʱ�ȭ" };
	String genre[] = { "���", "�׼�", "�ڹ̵�", "���", "�θǽ�" };
	JRadioButton age[] = new JRadioButton[4]; // ���� ��� ���� ��ư
	JLabel information[] = new JLabel[6]; // ��ȭ ���� �з� ���̺�
	JTextField tf[] = new JTextField[3]; // ��ȭ��, ����, ������ �ۼ��� �ؽ�Ʈ �ʵ�
	JButton btn[] = new JButton[6]; // ��ȭ ���� ��ư
	JRadioButton[] genreCheck = new JRadioButton[5]; // �帣 ���� ��ư
	JComboBox<String> country; // ���ۻ� ���� ����
	ButtonGroup ageGroup = new ButtonGroup(); // ���� ��� ��ư �׷�
	ButtonGroup genreGroup = new ButtonGroup(); // �帣 ��ư �׷�
	JPanel movieInfo = new JPanel(); // ��ȭ ���� �г�
	JPanel ageLimit = new JPanel(); // ���� ���� �г�
	JPanel genreSelect = new JPanel(); // �帣 ���� �г�
	JPanel btnPanel = new JPanel(); // ���� ��ư �г�
	JLabel pL = new JLabel(); // �̹��� ����� ���� ���̺�
	ImageIcon icon = new ImageIcon("images/null.png"); // �ʱ� �� �̹���
	GridLayout grid = new GridLayout(3, 2, 0, 1); // ��ȭ ���� ���� ���̾ƿ�
	DefaultTableModel model = new DefaultTableModel(label, 0); // ���̺� �⺻ ��
	JTable movieTable = new JTable(model); // ��ȭ ��� ���

	public movie() {
		setTitle("Movie"); // ������ �⺻ ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1350, 620);
		newComponent();
		setComponent();
		addComponent();
		imageSet();
		setVisible(true);
	}

	void newComponent() {
		country = new JComboBox<String>(countrys); // ���ۻ� ���� �޺��ڽ��� �迭 �ֱ�

		for (int i = 0; i < information.length; i++) { // �� �־��ֱ�
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
		movieInfo.setLayout(grid); // ������ ����
		genreSelect.setBorder(new LineBorder(Color.GRAY));
		ageLimit.setBorder(new LineBorder(Color.GRAY));
		age[0].setSelected(true);
		genreCheck[0].setSelected(true);
		tf[2].setBackground(Color.YELLOW);
	}

	void addComponent() {
		add(movieInfo, BorderLayout.NORTH); // ȭ�� �ֱ�
		movieInfo.add(genreSelect);
		add(new JScrollPane(movieTable));
		add(pL, BorderLayout.EAST);
		add(btnPanel, BorderLayout.SOUTH);
	}

	void imageSet() {
		Image image = icon.getImage(); //�̹��� �޾ƿ���
		Image changeImage = image.getScaledInstance(300, 424, Image.SCALE_SMOOTH);	// �̹��� ũ�� ����
		ImageIcon changeIcon = new ImageIcon(changeImage);	// ������ �̹��� ���
		pL.setIcon(changeIcon);
	}
}

public class movieSQL {
	movie v; // �ʿ� Ŭ���� ����
	movieModel m;

	int ageSelected; // ���� ��� ���� ��ȣ
	int genreSelected;	// �帣 ���� ��ȣ

	Statement stmt = null; // SQL������ ���� ����
	Connection con = null;
	ResultSet rs = null;

	public movieSQL() {
		v = new movie();
		Setting();	// SQL�� ��ȭ ����� �ҷ��� ����
		setButtons();	// �ʱ� ��ư ����
		for (int i = 0; i < 6; i++) { // ��ư�� ������ �ٿ��ֱ�
			v.btn[i].addActionListener(new movieHandler());
		}
		v.tf[2].addActionListener(new movieHandler());	// �ؽ�Ʈ �ʵ忡 ������ ���̱�
		v.movieTable.addMouseListener(new JTableClick());	// ���̺� Ŭ�� ������ ���̱�
	}

	class movieHandler implements ActionListener { // �׼� ���� �߰�
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == v.tf[2]) {	// �������� �̿��� ��ȭ ����
				if (v.btn[1].isEnabled()) {	// �̹� ���õǾ��� �� �ٽ� ���� �Ұ�
					selectMovie();	// ��ȭ ���� �޼ҵ� ȣ��
					setImage();	// �̹��� ��� �޼ҵ� ȣ��
				}
			}
			if (e.getSource() == v.btn[0]) {	// ã�� ��ư
				searchMovie();	// ��ȭ ã�� �޼ҵ� ȣ��
			}else if (e.getSource() == v.btn[1]) {	// ���� ��ư
				selectMovie();	// ��ȭ ���� �޼ҵ� ȣ��
				setImage();		// �̹��� ��� �޼ҵ� ȣ��
			} else if (e.getSource() == v.btn[2]) {	// �߰� ��ư
				addMovie(); // ��ȭ �߰� �޼ҵ� ȣ��
				Setting();	// ��ȭ ��� ����
			} else if (e.getSource() == v.btn[3]) {	// ���� ��ư
				updateMovie();	// ��ȭ ���� �޼ҵ� ȣ��
				Setting();	// ��ȭ ��� ����
			} else if (e.getSource() == v.btn[4]) {	// ���� ��ư
				deleteMovie();	// ��ȭ ���� �޼ҵ� ȣ��
				Setting();	//��ȭ ��� ����
			} else if (e.getSource() == v.btn[5]) {	// �ʱ�ȭ ��ư
				Setting();	//��ȭ ���� ��� ȣ��
				clearTextFields(0);	// ��� �ؽ�Ʈ ������ ����
				setButtons();	// ��ư �ʱ���·� �ǵ�����
				v.country.setSelectedIndex(0);		// �ʱ� ���ð����� �ǵ�����
				v.age[0].setSelected(true);			//
				v.genreCheck[0].setSelected(true);	//
				v.icon = new ImageIcon("images/null.png");	// �� �̹��� ���
				v.imageSet();	//�̹��� ũ�� ����
			}
		}
	}

	class JTableClick extends JFrame implements MouseListener {	// ���콺 ������ �߰�
		public void mouseClicked(MouseEvent e) {	// Ŭ�� ��
			int row = v.movieTable.getSelectedRow();	// ������ �� ��ȣ �ޱ�
			makeConnection();
			String sql = "";
			sql = "SELECT * FROM movie WHERE releaseDate='" + v.movieTable.getModel().getValueAt(row, 2) + "'";	// ���õ� ���� PK���� ������
			try {																								// �������� �ֱ�
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					m = new movieModel(rs.getString("movieName"), rs.getString("director"), rs.getString("releaseDate"),
							rs.getString("country"), rs.getString("age"), rs.getString("genre"));
					modelToView();
					v.btn[0].setEnabled(false);	// �� ��ư Ȱ��ȭ ����
					v.btn[1].setEnabled(false);
					v.btn[2].setEnabled(false);
					v.btn[3].setEnabled(true);
					v.btn[4].setEnabled(true);
					v.btn[5].setEnabled(true);
				}
			} catch (SQLException sqle) {
				System.out.println("is Not Exist");
			}
			setImage();	// �̹��� ����
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

	void viewToModel() { // �߰��� ��ȭ ���λ��� �𵨷� �ű��
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

	void modelToView() { // ���� �� ��ȭ ���λ��� ǥ��
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

	void Setting() { // �ʱ� ȭ�� ����

		makeConnection();
		String sql = "";
		sql = "SELECT * FROM movie";
		try {
			rs = stmt.executeQuery(sql);
			v.model.setNumRows(0);	// ���̺� �ʱ�ȭ
			while (rs.next()) {
				v.model.addRow(	// ���̺� ��ȭ ��� �ҷ�����
						new Object[] { rs.getString("movieName"), rs.getString("director"), rs.getString("releaseDate"),
								rs.getString("country"), rs.getString("age"), rs.getString("genre") });
			}
			tableSet();	// ���̺� �⺻ ����
		} catch (SQLException sqle) {
			System.out.println("getData: SQL Error");
		}
		disConnection();
	}

	public Connection makeConnection() { // SQL ����
		String url = "jdbc:mysql://localhost/movie_db?serverTimezone=Asia/Seoul";
		String id = "root";
		String password = "Anjuyh7410@";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("����̺� ���� ����");
			con = DriverManager.getConnection(url, id, password);
			stmt = con.createStatement();
			System.out.println("�����ͺ��̽� ���� ����");
		} catch (ClassNotFoundException e) {
			System.out.println("����̹��� ã�� �� �����ϴ�");
			e.getStackTrace();
		} catch (SQLException e) {
			System.out.println("���ῡ �����Ͽ����ϴ�");
			e.getStackTrace();
		}
		return con;
	}

	public void searchMovie() { // ���� ĭ�� ���� ��ȭ �˻�
		makeConnection();
		String sql = "";
		if (v.tf[0].getText().equals("") && v.tf[1].getText().equals("") && v.tf[2].getText().equals(""))	// ��� ��ĭ�� �� 
			sql = "SELECT * FROM movie";
		else if (v.tf[1].getText().equals("") && v.tf[2].getText().equals(""))	// ��ȭ �������� �˻�
			sql = "SELECT * FROM movie WHERE movieName LIKE'%" + v.tf[0].getText() + "%'";
		else if (v.tf[0].getText().equals("") && v.tf[2].getText().equals(""))	// ��ȭ �������� �˻�
			sql = "SELECT * FROM movie WHERE director LIKE'%" + v.tf[1].getText() + "%'";
		else if (v.tf[0].getText().equals("") && v.tf[1].getText().equals(""))	// �����Ϸ� �˻�
			sql = "SELECT * FROM movie WHERE releaseDate LIKE'%" + v.tf[2].getText() + "%'";
		else if (v.tf[0].getText().equals(""))	// ������ �����Ϸ� �˻�
			sql = "SELECT * FROM movie WHERE releaseDate LIKE'%" + v.tf[2].getText() + "%'" + "AND director LIKE'%"
					+ v.tf[1].getText() + "%'";
		else if (v.tf[1].getText().equals(""))	// ��ȭ ����� �����Ϸ� �˻�
			sql = "SELECT * FROM movie WHERE releaseDate LIKE'%" + v.tf[2].getText() + "%'" + "AND movieName LIKE'%"
					+ v.tf[0].getText() + "%'";
		else if (v.tf[2].getText().equals(""))	// ��ȭ ����� ����
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

	public void selectMovie() { // �������� �������� ��ȭ ����
		makeConnection();
		String sql = "";
		sql = "SELECT * FROM movie WHERE releaseDate='" + v.tf[2].getText() + "'";	// ������(PK)�� ���� ��ȭ ����
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				m = new movieModel(rs.getString("movieName"), rs.getString("director"), rs.getString("releaseDate"),
						rs.getString("country"), rs.getString("age"), rs.getString("genre"));
				modelToView();
				v.btn[0].setEnabled(false);	// ��ư ����
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

	public void addMovie() { // ���� ���λ������� ��ȭ ��Ͽ� �߰�
		if(v.tf[2].getText().equals(""))
			v.tf[2].setText("�������� �־��ּ���");
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

	public void updateMovie() { // ����� ���λ��� ����
		viewToModel();
		makeConnection();
		try {
			String s = "";
			s = "UPDATE movie SET movieName='" + m.movieName + "',director='" + m.director + "',releaseDate='"
					+ m.releaseDate + "',country='" + m.country + "',age='" + m.age + "',genre='" + m.genre
					+ "' WHERE releaseDate='" + m.releaseDate + "'";	// �������� PK�� ã��
			stmt.executeUpdate(s);
		} catch (SQLException sqle) {
			System.out.println("isExist: SQL Error");
		}
		disConnection();
		clearTextFields(0);
	}

	public void deleteMovie() { // ���õ� ��ȭ ����
		viewToModel();
		int isDelete = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ� ?");	//���̾�α׷� ������ Ȯ��
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

	void tableSet() { // ��ȭ ��� ����
		DefaultTableCellRenderer ca = new DefaultTableCellRenderer();	// ���̺� �⺻ ���� �����
		ca.setHorizontalAlignment(SwingConstants.CENTER);	// ��� ����
		v.movieTable.setPreferredScrollableViewportSize(new Dimension(750, 200));
		v.movieTable.setAutoCreateRowSorter(true);	// �ڵ� ����
		for (int i = 0; i < v.label.length; i++) {
			v.movieTable.getColumnModel().getColumn(i).setCellRenderer(ca);
		}
	}

	public void disConnection() { // SQL���� ����
		try {
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void setImage() {
		v.icon = new ImageIcon("images/" + m.movieName + ".jpg");	// �̹��� �ٲٱ�
		if (v.icon.getIconHeight() < 200) {
			v.icon = new ImageIcon("images/null.png");	//�� �̹���
		}
		v.imageSet();	// �̹��� ũ�� ����
	}

	public void setButtons() { // �ʱ� ��ư ����
		v.btn[0].setEnabled(true);
		v.btn[1].setEnabled(true);
		v.btn[2].setEnabled(true);
		v.btn[3].setEnabled(false);
		v.btn[4].setEnabled(false);
		v.btn[5].setEnabled(true);
	}

	public void clearTextFields(int n) { // ��� �ؽ�Ʈ �ʵ� �ʱ�ȭ
		for (int i = n; i < v.tf.length; i++) {
			v.tf[i].setText("");
		}
	}

	public static void main(String[] args) {
		new movieSQL();
	}
}

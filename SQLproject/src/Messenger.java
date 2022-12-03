import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.sql.*;
import java.net.*;
import java.io.*;

enum Info {
	JOIN, EXIT, SEND
}

class InfoDTO implements Serializable{
	private String nickName;
	private String message;
	private Info command;
	
	public String getNickName(){
		return nickName;
	}
	public Info getCommand(){
		return command;
	}
	public String getMessage(){
		return message;
	}
	public void setNickName(String nickName){
		this.nickName= nickName;
	}
	public void setCommand(Info command){
		this.command= command;
	}
	public void setMessage(String message){
		this.message= message; 
	}
}

class MessengerRoomModel{
	String RID;		//Room_ID
	String room_name;
	int room_num;
	String host_id;
	MessengerRoomModel(String RID, String room_name, int room_num, String host_id) {
		this.RID = RID;
		this.room_name = room_name;
		this.room_num = room_num;
		this.host_id = host_id;
	}
}

class MessengerChattingModel{
	int count_num;
	String chat_log;
	String write_time;
	String RID;		//참조용 RID
	String UID;		//참조용 UID
	MessengerChattingModel(int count_num, String chat_log, String write_time, String RID, String UID) {
		this.count_num = count_num;
		this.chat_log = chat_log;
		this.write_time = write_time;
		this.RID = RID;
		this.UID = UID;
	}
}

class MessengerCustomerModel {
	String customer_id;
	String password;
	String nickname;
	String UID;
	MessengerCustomerModel(String customer_id, String password, String nickname, String UID) {
		this.customer_id = customer_id;
		this.password = password;
		this.nickname = nickname;
		this.UID = UID;
	}
}

class MessengerPersonalRoomModel {
	String RID;
	String FID;
	String UID;
	MessengerPersonalRoomModel(String RID, String FID, String UID) {
		this.RID = RID;
		this.FID = FID;
		this.UID = UID;
	}
}

class MessengerFriendModel{
	String UID;
	String FID;
	MessengerFriendModel(String UID, String FID) {
		this.UID = UID;
		this.FID = FID;
	}
}

class MessengerLogin extends JFrame{
	JLabel info[] = new JLabel[2];
	JTextField tf[] = new JTextField[2];
	JButton btn[] = new JButton[3];
	JPanel input = new JPanel();	//info레이블과 텍스트 필드 2개가 들어간 패널
	JPanel login = new JPanel();	//input패널과 login버튼이 들어간 패널
	JPanel otherBtn = new JPanel();	//회원가입 버튼과 비밀번호 찾기 버튼이 들어간 패널
	GridLayout grid = new GridLayout(2,2,5,5);
	
	public MessengerLogin() {
		setTitle("로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(340,140);
		setResizable(false);
		newComponent();
		setComponent();
		addComponent();
		setVisible(true);
	}
	void newComponent() {
		info[0] = new JLabel("아이디");
		info[1] = new JLabel("비밀번호");
		tf[0] = new JTextField(10);
		tf[1] = new JTextField(10);
		btn[0] = new JButton("로그인");
		btn[1] = new JButton("회원가입");
		btn[2] = new JButton("비밀번호 찾기");		//원래는 본인인증 필요 여기선 X
	}
	void setComponent() {
		input.setLayout(grid);
		input.setBackground(Color.LIGHT_GRAY);
		login.setBackground(Color.LIGHT_GRAY);
	}
	void addComponent() {
		for(int i = 0; i<2; i++) {
			info[i].setHorizontalAlignment(SwingConstants.CENTER);
			input.add(info[i]);
			input.add(tf[i]);
			otherBtn.add(btn[i+1]);
		}
		login.add(input);
		login.add(btn[0]);
		add(login,BorderLayout.NORTH);
		add(otherBtn);
	}
}

class MessengerSignUp extends JFrame{
	JLabel info[] = new JLabel[4];
	JTextField tf[] = new JTextField[4];
	JButton confirm = new JButton("중복확인");
	JButton signUp = new JButton("등록");
	JPanel input = new JPanel();
	JPanel id = new JPanel();
	JPanel password = new JPanel();
	
	public MessengerSignUp() {
		setTitle("회원가입");
		setSize(360,200);
		setResizable(false);
		newComponent();
		setComponent();
		addComponent();
		setVisible(true);
	}
	void newComponent() {
		info[0] = new JLabel("아이디");
		info[1] = new JLabel("비밀번호");
		info[2] = new JLabel("비밀번호 재확인");
		info[3] = new JLabel("닉네임");
		tf[0] = new JTextField(10);
		tf[1] = new JTextField(10);
		tf[2] = new JTextField(10);
		tf[3] = new JTextField(10);
	}
	void setComponent() {
		for(int i = 0; i<info.length; i++) {
			info[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
		tf[1].setEditable(false);
		tf[2].setEditable(false);
		tf[3].setEditable(false);
		signUp.setEnabled(false);
		input.setLayout(new GridLayout(1,2,5,5));
		password.setLayout(new GridLayout(3,2,5,5));
	}
	void addComponent() {
		input.add(info[0]);
		input.add(tf[0]);
		id.add(input);
		id.add(confirm);
		add(id,BorderLayout.NORTH);
		for(int i = 1; i<4; i++) {
			password.add(info[i]);
			password.add(tf[i]);
		}
		add(password);
		add(signUp,BorderLayout.SOUTH);
	}
}

class MessengerPassword extends JFrame{
	JLabel info[] = new JLabel[3];
	JTextField tf[] = new JTextField[3];
	JButton confirm = new JButton("아이디 확인");
	JButton changePW = new JButton("비밀번호 변경");
	JPanel input = new JPanel();
	JPanel id = new JPanel();
	JPanel password = new JPanel();
	
	public MessengerPassword() {
		setTitle("비밀번호 찾기(변경)");
		setSize(360,160);
		setResizable(false);
		newComponent();
		setComponent();
		addComponent();
		setVisible(true);
	}
	void newComponent() {
		info[0] = new JLabel("아이디");
		info[1] = new JLabel("비밀번호");
		info[2] = new JLabel("비밀번호 재확인");
		tf[0] = new JTextField(10);
		tf[1] = new JTextField(10);
		tf[2] = new JTextField(10);
	}
	void setComponent() {
		for(int i = 0; i<info.length; i++) {
			info[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
		tf[1].setEditable(false);
		tf[2].setEditable(false);
		changePW.setEnabled(false);
		input.setLayout(new GridLayout(1,2,5,5));
		password.setLayout(new GridLayout(2,2,5,5));
	}
	void addComponent() {
		input.add(info[0]);
		input.add(tf[0]);
		id.add(input);
		id.add(confirm);
		add(id,BorderLayout.NORTH);
		for(int i = 1; i<3; i++) {
			password.add(info[i]);
			password.add(tf[i]);
		}
		add(password);
		add(changePW,BorderLayout.SOUTH);
	}
}

class MessengerChat extends JFrame{
	String roomList[] = {"채팅방 이름","RID"};
	JMenuBar mb = new JMenuBar();	//메뉴
	JMenu settingMenu = new JMenu("설정");
	JMenuItem item[] = new JMenuItem[4];
	
	JTabbedPane pane = new JTabbedPane();	//탭
	
	JLabel roomName = new JLabel("채팅방 이름");
	JLabel chatID = new JLabel("RID");	//채팅창 아이디로 선택 (즐겨찾기 추가필요)
	JTextField inputRoomName = new JTextField(10);
	JTextField inputRID = new JTextField(10);
	JButton enter = new JButton("등록");
	JPanel chatSelect = new JPanel();
	
	JButton deleteRoom = new JButton("삭제");
	DefaultTableModel personalTM = new DefaultTableModel(roomList, 0);
	JPanel personalChatRoom = new JPanel();	//채팅방 즐겨찾기 테이블로 만들기
	JTable personalRooms = new JTable(personalTM);
	
	DefaultTableModel openTM = new DefaultTableModel(roomList, 0);
	JPanel openChatRoom = new JPanel();	//오픈채팅방 목록 테이블로 만들기
	JTable openRooms = new JTable(openTM);
	
	JPanel nowChatRoom = new JPanel();	//현재 들어가진 채팅방 표시
	JLabel inviteLabel = new JLabel("UID :");
	JTextField inviteTF = new JTextField(10);
	JButton invite = new JButton("초대");
	JButton kick = new JButton("추방");
	JPanel invitePanel = new JPanel();
	JTextArea ta = new JTextArea();
	JTextField tf = new JTextField(10);
	
	String account[] = {"customer_id","UID","nickname"};
	DefaultTableModel accountTM = new DefaultTableModel(account,0);
	JPanel adminPanel = new JPanel();
	JTable accountTable = new JTable(accountTM);
	JButton deleteAccount = new JButton("삭제");
	
	JLabel noneRoom = new JLabel("열린 채팅방이 없습니다.");
	JLabel UID = new JLabel("UID : 00000000   ");
	JLabel RID = new JLabel("RID : 00000000");
	
	public MessengerChat() {
		setTitle("채팅방");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,600);
		newComponent();
		setComponent();
		addComponent();
		tableSet();
		setVisible(true);
	}
	void newComponent() {
		item[0] = new JMenuItem("친구목록");
		item[1] = new JMenuItem("로그아웃");
		item[2] = new JMenuItem("관리자 모드");
		item[3] = new JMenuItem("종료");
	}
	void setComponent() {
		chatID.setHorizontalAlignment(SwingConstants.CENTER);
		inviteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noneRoom.setHorizontalAlignment(SwingConstants.CENTER);
		nowChatRoom.setLayout(new BorderLayout());
		adminPanel.setLayout(new BorderLayout());
		ta.setEditable(false);
		ta.setBackground(Color.LIGHT_GRAY);
		ta.setFont(new Font("맑은 고딕",Font.BOLD,15));
	}
	void addComponent() {
		for(int i = 0; i<item.length; i++) {	//메뉴 내용 추가
			settingMenu.add(item[i]);
			if(i < item.length - 1)
				settingMenu.addSeparator();
		}
		mb.add(settingMenu);
		setJMenuBar(mb);		//프레임에 메뉴바 붙이기
		
		chatSelect.add(roomName);
		chatSelect.add(inputRoomName);
		chatSelect.add(chatID);
		chatSelect.add(inputRID);
		chatSelect.add(enter);
		chatSelect.add(deleteRoom);
		add(chatSelect,BorderLayout.NORTH);
		
		personalChatRoom.add(new JScrollPane(personalRooms));
		
		openChatRoom.add(new JScrollPane(openRooms));
		
		invitePanel.add(RID);
		invitePanel.add(inviteLabel);
		invitePanel.add(inviteTF);
		invitePanel.add(invite);
		invitePanel.add(kick);
		nowChatRoom.add(invitePanel,BorderLayout.NORTH);
		nowChatRoom.add(noneRoom);
		nowChatRoom.add(tf,BorderLayout.SOUTH);
		
		adminPanel.add(new JScrollPane(accountTable));
		adminPanel.add(deleteAccount,BorderLayout.SOUTH);
		
		pane.add("채팅방 선택", personalChatRoom);
		pane.add("오픈 채팅방", openChatRoom);
		pane.add("현재 채팅방", nowChatRoom);
		add(pane);
		
		add(UID,BorderLayout.SOUTH);
	}
	void tableSet() {
		DefaultTableCellRenderer ca = new DefaultTableCellRenderer();	// 테이블 기본 설정 만들기
		ca.setHorizontalAlignment(SwingConstants.CENTER);	// 가운데 정렬
		personalRooms.setPreferredScrollableViewportSize(new Dimension(250, 400));
		personalRooms.setAutoCreateRowSorter(true);	// 자동 정렬
		personalRooms.getTableHeader().setReorderingAllowed(false);
		openRooms.setPreferredScrollableViewportSize(new Dimension(250, 400));
		openRooms.setAutoCreateRowSorter(true);	// 자동 정렬
		openRooms.getTableHeader().setReorderingAllowed(false);
		accountTable.setPreferredScrollableViewportSize(new Dimension(250, 400));
		accountTable.setAutoCreateRowSorter(true);	// 자동 정렬
		accountTable.getTableHeader().setReorderingAllowed(false);
		for (int i = 0; i < roomList.length; i++) {
			personalRooms.getColumnModel().getColumn(i).setCellRenderer(ca);
			openRooms.getColumnModel().getColumn(i).setCellRenderer(ca);
			accountTable.getColumnModel().getColumn(i).setCellRenderer(ca);
		}
	}
}

class Administrator extends JFrame{
	JButton enter = new JButton("관리자 모드 전환");
	JLabel password = new JLabel("암호");
	JTextField input = new JTextField(15);
	JPanel adminPW = new JPanel();
	String PW = "Admin";
	
	public Administrator() {
		setTitle("관리자 암호를 입력해주세요");
		setSize(340,100);
		setResizable(false);
		newComponent();
		setComponent();
		addComponent();
		setVisible(true);
	}
	void newComponent() {
		
	}
	void setComponent() {
		password.setHorizontalAlignment(SwingConstants.CENTER);
	}
	void addComponent() {
		adminPW.add(password);
		adminPW.add(input);
		add(adminPW);
		add(enter,BorderLayout.SOUTH);
	}
}

class FriendMenu extends JFrame{
	JLabel UID = new JLabel("친구 UID");
	JTextField tf = new JTextField(10);
	JButton confirm = new JButton("등록");
	JButton delete = new JButton("삭제");
	JPanel friendEnter = new JPanel();
	
	String friendList[] = {"닉네임","UID"};
	DefaultTableModel friendTM = new DefaultTableModel(friendList, 0);
	JPanel personalChatRoom = new JPanel();	
	JTable friendTable = new JTable(friendTM);
	
	public FriendMenu() {
		setTitle("친구목록");
		setSize(340,600);
		setResizable(false);
		newComponent();
		setComponent();
		addComponent();
		setVisible(true);
	}
	void newComponent() {
		
	}
	void setComponent() {
		UID.setHorizontalAlignment(SwingConstants.CENTER);
	}
	void addComponent() {
		friendEnter.add(UID);
		friendEnter.add(tf);
		friendEnter.add(confirm);
		friendEnter.add(delete);
		add(friendEnter,BorderLayout.NORTH);
		add(new JScrollPane(friendTable));
	}
}

public class Messenger extends JFrame implements ActionListener,Runnable{
	ChatServerObject CSO;	//서버 생성(현재 포트 9500고정)
	ChatServerObject CSO1;
	ChatServerObject CSO2;
	MessengerLogin ML;
	MessengerChat MC;
	MessengerSignUp MSU;
	MessengerPassword MP;
	Administrator Admin;
	FriendMenu FM;
	
	MessengerCustomerModel MCM;
	MessengerRoomModel MRM;
	MessengerChattingModel MCRM;
	MessengerFriendModel MFM;
	
	Statement stmt = null; // SQL조작을 위한 선언
	Connection con = null;
	ResultSet rs = null;
	private Socket socket;
	private ObjectInputStream reader=null;
	private ObjectOutputStream writer=null;
	
	int check = 0;
	int joinCheck = 0;
	String nowCustomer = "";
	String nowNickName = "";
	String nowRID = "";
	int nowRoomNum = 0;
	int lastRoomNum = 0;
	JTextArea ta[] = new JTextArea[10];

	public Messenger() {
		ML = new MessengerLogin();
		for(int i = 0; i<3; i++)
			ML.btn[i].addActionListener(new loginBtn());
		for(int j = 0; j<10; j++) {
			ta[j] = new JTextArea();
			ta[j].setEditable(false);
			ta[j].setBackground(Color.LIGHT_GRAY);
		}
		CSO = new ChatServerObject(9500);
	}
	
	class loginBtn implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == ML.btn[0]) {
				login();
			}
			if(e.getSource() == ML.btn[1]) {
				MSU = new MessengerSignUp();
				MSU.confirm.addActionListener(new signUpBtn());
				MSU.signUp.addActionListener(new signUpBtn());
			}
			if(e.getSource() == ML.btn[2]) {
				MP = new MessengerPassword();
				MP.confirm.addActionListener(new passwordBtn());
				MP.changePW.addActionListener(new passwordBtn());
			}
		}
	}
	
	class signUpBtn implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == MSU.confirm) {
				confirmID();
			}
			else if(e.getSource() == MSU.signUp) {
				signUp();
			}
		}
	}
	class passwordBtn implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == MP.confirm) {
				confirmID();
			}
			else if(e.getSource() == MP.changePW) {
				changePW();
			}
		}
	}
	
	class chatBtn implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == MC.enter) {
				addChatRoom();
			}
			else if(e.getSource() == MC.deleteRoom) {
				deleteChatRoom();
			}
			
			String cmd = e.getActionCommand();
			switch (cmd) {
				case "친구목록" : {
					FM = new FriendMenu();
					FM.confirm.addActionListener(new friendBtn());
					FM.delete.addActionListener(new friendBtn());
					FM.friendTable.addMouseListener(new JTableClick());
					break;
				}
				case "로그아웃" : {
					MC.dispose();
					ML = new MessengerLogin();
					for(int i = 0; i<3; i++)
						ML.btn[i].addActionListener(new loginBtn());
					break;
				}
				case "관리자 모드" : {
					Admin = new Administrator();			//비번 입력창 띄우고 버튼클릭 시 관리 탭 추가
					Admin.enter.addActionListener(new enterAdmin());
					break;
				}
				case "종료" : {
					System.exit(0);		//프로세스 종료
					break;
				}
			}
		}
	}
	
	class friendBtn implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == FM.confirm) {
				addFriend();
			}
			else if(e.getSource() == FM.delete) {
				deleteFriend();
			}
		}
	}
	
	class roomManager implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == MC.invite) {
				inviteChatRoom();
			}
			else if(e.getSource() == MC.kick) {
				kickChatRoom();
			}
		}
	}
	
	class enterAdmin implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(Admin.PW.equals(Admin.input.getText())) {
				Admin.dispose();
				MC.pane.add("계정 관리", MC.adminPanel);
			}
		}
	}
	
	class JTableClick extends JFrame implements MouseListener {	// 마우스 리스너 추가
		public void mouseClicked(MouseEvent e) {	// 클릭 시
			if(e.getSource() == MC.personalRooms) {
				int row = MC.personalRooms.getSelectedRow();	// 선택한 행 번호 받기
				makeConnection();
				String sql = "";
				sql = "SELECT * FROM chat_room WHERE room_id='" + MC.personalRooms.getModel().getValueAt(row, 1) + "'";	// 선택된 행의 PK값을 가져와
				try {																								// 조건으로 넣기
					rs = stmt.executeQuery(sql);
					if (rs.next()) {
						MRM = new MessengerRoomModel(rs.getString("room_id"), rs.getString("room_name"), rs.getInt("room_num"), rs.getString("host_id"));
						modelToView_MRM();
						if(nowRID.equals(MRM.RID)) {
							
						}
						else {
							MC.ta.setText("");
							nowRID = MRM.RID;
						}
						openChatRoom();
					}
				} catch (SQLException sqle) {
					System.out.println("is Not Exist");
				}
				disConnection();
			}
			else if(e.getSource() == MC.openRooms) {
				int row = MC.openRooms.getSelectedRow();	// 선택한 행 번호 받기
				makeConnection();
				String sql = "";
				sql = "SELECT * FROM chat_room WHERE room_id='" + MC.openRooms.getModel().getValueAt(row, 1) + "'";	// 선택된 행의 PK값을 가져와
				try {																								// 조건으로 넣기
					rs = stmt.executeQuery(sql);
					if (rs.next()) {
						MRM = new MessengerRoomModel(rs.getString("room_id"), rs.getString("room_name"), rs.getInt("room_num"), rs.getString("host_id"));
						if(nowRID.equals(MRM.RID)) {
							//원래있던 채팅방
						}
						else {
							MC.ta.setText("");
							nowRID = MRM.RID;
						}
						openChatRoom();
					}
				} catch (SQLException sqle) {
					System.out.println("is Not Exist");
				}
				disConnection();
				
			}
			else if(e.getSource() == FM.friendTable) {
				int row = FM.friendTable.getSelectedRow();	// 선택한 행 번호 받기
				MC.inviteTF.setText((String)FM.friendTable.getModel().getValueAt(row, 1));
			}
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
	
	public Connection makeConnection() { // SQL 접속
		String url = "jdbc:mysql://localhost/messenger_db?serverTimezone=Asia/Seoul";
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
	
	public void disConnection() { // SQL연결 종료
		try {
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	void modelToView_MRM() {
		MC.inputRoomName.setText(MRM.room_name);
		MC.inputRID.setText(MRM.RID);
	}
	void modelToView_MCM() {
		FM.tf.setText(MCM.UID);
	}
	
	void viewToModel_MRM() {
		MRM = new MessengerRoomModel(MC.inputRID.getText(), MC.inputRoomName.getText(), lastRoomNum, nowCustomer);
	}
	
	void Setting() {
		ML.dispose();
		MC = new MessengerChat();
		MC.enter.addActionListener(new chatBtn());
		MC.deleteRoom.addActionListener(new chatBtn());
		for(int i = 0; i<MC.item.length; i++) {
			MC.item[i].addActionListener(new chatBtn());
		}
		MC.tf.addActionListener(this);
		MC.remove(MC.UID);
		MC.UID = new JLabel("UID : " + MCM.UID + "   ");
		MC.UID.setHorizontalAlignment(SwingConstants.RIGHT);
		MC.add(MC.UID,BorderLayout.SOUTH);
		
		makeConnection();
		String sql = "";
		sql = "SELECT * FROM chat_room WHERE room_id LIKE'" + "002%" + "'";
		try {
			rs = stmt.executeQuery(sql);
			MC.openTM.setNumRows(0);	// 테이블 초기화
			while (rs.next()) {
				MC.openTM.addRow(	// 오픈 채팅방 목록 불러오기
						new Object[] { rs.getString("room_name"), rs.getString("room_id")});
			}
		} catch (SQLException sqle) {
			System.out.println("getData: SQL Error");
		}
		
		sql = "SELECT * FROM chat_room";
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next())
				lastRoomNum = rs.getRow();
		} catch (SQLException sqle) {
			System.out.println("getData: SQL Error");
		}
		personalRoom();
		
		disConnection();
		
		MC.personalRooms.addMouseListener(new JTableClick());
		MC.openRooms.addMouseListener(new JTableClick());
	}
	
	void login() {
		if(ML.tf[0].getText().equals("")) {
			JOptionPane.showMessageDialog(null, "아이디가 입력되지 않았습니다!", "아이디 미입력",
                    JOptionPane.ERROR_MESSAGE);
		}
		else if(ML.tf[1].getText().equals("")) {
			JOptionPane.showMessageDialog(null, "비밀번호가 입력되지 않았습니다!", "비밀번호 미입력",
                    JOptionPane.ERROR_MESSAGE);
		}
		else {
			makeConnection();
			String sql = "";
			sql = "SELECT * FROM customer WHERE login_id = " + ML.tf[0].getText();
			try {
				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					MCM = new MessengerCustomerModel(rs.getString("login_id"), rs.getString("password"), 
							rs.getString("nickname"), rs.getString("user_id"));
					if(MCM.password.equals(ML.tf[1].getText())) {
						nowCustomer = MCM.UID;
						Setting();
						nowNickName = MCM.nickname;
					}
					else {
						JOptionPane.showMessageDialog(null, "비밀번호가 틀렸습니다!\n값을 다시 확인해주세요!", "비밀번호 불일치",
                                JOptionPane.ERROR_MESSAGE);
					}
				}
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "아이디가 틀렸습니다!\n값을 다시 확인해주세요!", "아이디 불일치",
                        JOptionPane.ERROR_MESSAGE);
			}
			disConnection();
		}
	}
	
	void addChatRoom() {
		makeConnection();
		viewToModel_MRM();
		String sql = "";
		try {
			sql = "INSERT INTO chat_room (room_id,room_name,room_num,host_id) values ";
			sql += "('" + MRM.RID + "','" + MRM.room_name + "','" + MRM.room_num + "','" + MRM.host_id + "')";
			stmt.executeUpdate(sql);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		personalRoom();
		disConnection();
		clearTextFields();
	}
	
	void deleteChatRoom() {
		if(nowCustomer != MRM.host_id)
			JOptionPane.showMessageDialog(null, "채팅방 주인이 아니면 삭제할수 없습니다!", "삭제 불가",
                    JOptionPane.PLAIN_MESSAGE);
		else if(nowCustomer == MRM.host_id) {
			int isDelete = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까 ?");	//다이얼로그로 삭제시 확인
			if (isDelete == 0) {
				makeConnection();
				String sql = "";
				sql = "DELETE FROM chat_room where room_id='" + MRM.RID + "'";
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException sqle) {
					System.out.println("isExist: DELETE SQL Error");
				}
				personalRoom();
				disConnection();
			}
			clearTextFields();
		}
	}
	
	void inviteChatRoom() {
		
	}
	
	void kickChatRoom() {
		
	}
	
	void addFriend() {
		
	}
	
	void deleteFriend() {
		
	}
	
	void confirmID() {
		
	}
	
	void signUp() {
		
	}
	
	void changePW() {
		
	}
	
	void openChatRoom() {
		if(check == 0) {
			MC.nowChatRoom.remove(MC.noneRoom);
		}
		if(check!=0) {
			try{
				InfoDTO dto = new InfoDTO();
				dto.setNickName(nowNickName);
				dto.setCommand(Info.EXIT);
				writer.writeObject(dto);
				writer.flush();
			}catch(IOException io){
				io.printStackTrace();
			}
		}
		MC.nowChatRoom.remove(ta[nowRoomNum]);
		MC.RID = new JLabel("RID : " + MRM.RID);
		MC.nowChatRoom.add(new JScrollPane(ta[MRM.room_num]));
		service();
		check++;
		nowRoomNum = MRM.room_num;
	}
	
	public void clearTextFields() { // 모든 텍스트 필드 초기화
		MC.inputRoomName.setText("");
		MC.inputRID.setText("");
	}
	
	void personalRoom() {
		makeConnection();
		String sql = "";
		sql = "SELECT * FROM chat_room WHERE host_id LIKE'" + nowCustomer +"' OR user_id LIKE'" + nowCustomer + "'" ;
		try {
			rs = stmt.executeQuery(sql);
			MC.personalTM.setNumRows(0);	// 테이블 초기화
			while (rs.next()) {
				MC.personalTM.addRow(	// 개인 채팅방 목록 불러오기
						new Object[] { rs.getString("room_name"), rs.getString("room_id")});
			}
		} catch (SQLException sqle) {
			System.out.println("getData: SQL Error");
		}
		disConnection();
	}
	
	public void actionPerformed(ActionEvent e){
		try{
			//서버로 보냄 
			//JTextField값을 서버로보내기
			//버퍼 비우기
			String msg=MC.tf.getText();
			InfoDTO dto = new InfoDTO();
			//dto.setNickName(nickName);
			if(msg.equals("exit")){
				dto.setCommand(Info.EXIT);
			} else {
				dto.setCommand(Info.SEND);
				dto.setMessage(msg);
				dto.setNickName(nowNickName);
			}
			writer.writeObject(dto);
			writer.flush();	//아마 행 넘기기
			MC.tf.setText("");	//치고 난 후 비우기
			
		}catch(IOException io){
			io.printStackTrace();
		}
	}
	
	public void run(){
		//서버로부터 데이터 받기
		InfoDTO dto= null;
		while(true){
			try{
				dto = (InfoDTO) reader.readObject();
				if(dto.getCommand()==Info.EXIT){  //서버로부터 내 자신의 exit를 받으면 종료됨
					reader.close();
					writer.close();
					socket.close();
				} else if(dto.getCommand()==Info.SEND){
					ta[nowRoomNum].append(dto.getMessage()+"\n");
					
					int pos=ta[nowRoomNum].getText().length();
					ta[nowRoomNum].setCaretPosition(pos);
				}
			}catch(IOException e){
				e.printStackTrace();
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			}	
		}
	}
	
	public void service(){
		//서버 IP 입력받기
		String serverIP = "192.168.0.3";  //기본적으로 아이피 값이 입력되어 들어가게 됨
		try{
			socket = new Socket(serverIP,9500);	//서버의 IP를 받아오고 임의의 포트 설정(같은 포트여야 서로 만날 수 있음(서버 개념))
			//에러 발생
			reader= new ObjectInputStream(socket.getInputStream());
			writer = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("전송 준비 완료!");
			
		} catch(UnknownHostException e ){
			System.out.println("서버를 찾을 수 없습니다.");
			e.printStackTrace();
			System.exit(0);
		} catch(IOException e){
			System.out.println("서버와 연결이 안되었습니다.");
			e.printStackTrace();
			System.exit(0);
		}
		try{
			//서버로 닉네임 보내기
			InfoDTO dto = new InfoDTO();
			dto.setCommand(Info.JOIN);
			dto.setNickName(nowNickName);
			writer.writeObject(dto);
			writer.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
		//스레드 생성
		Thread t = new Thread(this);
		t.start();
	}

	public static void main(String[] args) {
		new Messenger();
	}
}

class ChatServerObject 	//채팅방 서버 생성
{
	private ServerSocket serverSocket;
	private ArrayList <ChatHandlerObject> list;
	static HashMap<String, Object> hash;
	public ChatServerObject(int port){
		try{
			serverSocket= new ServerSocket (port);		//서버 포트 설정
			hash = new HashMap<String, Object>();
			System.out.println("서버 준비 완료");
			list = new ArrayList<ChatHandlerObject>();
			while(true){
				Socket socket = serverSocket.accept();
				ChatHandlerObject handler = new  ChatHandlerObject(socket,list);  //스레드를 생성한 것이랑 동일함
				handler.start();  //스레드 시작- 스레드 실행
				list.add(handler);  //핸들러를 담음(이 리스트의 개수가 클라이언트의 갯수)
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

class ChatHandlerObject extends Thread //처리해주는 곳(소켓에 대한 정보가 담겨있는 곳. 소켓을 처리함)
{
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private Socket socket;
	private ArrayList <ChatHandlerObject> list;
	
	public ChatHandlerObject(Socket socket, ArrayList <ChatHandlerObject> list) throws IOException {
		this.socket = socket;
		this.list = list;
		writer = new ObjectOutputStream(socket.getOutputStream());
		reader = new ObjectInputStream(socket.getInputStream());
	}
	public void run(){
		InfoDTO dto = null;
		String nickName;														//닉네임 데베로 처리 변경
		try{
			while(true){
				dto=(InfoDTO)reader.readObject();
				nickName=dto.getNickName();
				if(dto.getCommand()==Info.EXIT){	//사용자 접속 해제
					InfoDTO sendDto = new InfoDTO();
					//나가려고 exit를 보낸 클라이언트에게 답변 보내기
					//sendDto.setCommand(Info.EXIT);
					//writer.writeObject(sendDto);
					//writer.flush();

					//reader.close();
					//writer.close();
					//socket.close();
					//남아있는 클라이언트에게 퇴장메세지 보내기
					list.remove(this);

					sendDto.setCommand(Info.SEND);
					sendDto.setMessage(nickName+"님 퇴장하였습니다");
					broadcast(sendDto);
					break;
				} else if(dto.getCommand()==Info.JOIN){
					//모든 사용자에게 메세지 보내기
					//모든 클라이언트에게 입장 메세지를 보내야 함
					InfoDTO sendDto = new InfoDTO();
					sendDto.setCommand(Info.SEND);
					sendDto.setMessage(nickName+"님 입장하였습니다");
					broadcast(sendDto);
				} else if(dto.getCommand()==Info.SEND){
					InfoDTO sendDto = new InfoDTO();
					sendDto.setCommand(Info.SEND);
					sendDto.setMessage("["+nickName+"]"+ dto.getMessage());
					broadcast(sendDto);
				}
			}//while

		} catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	
	}
	//다른 클라이언트에게 전체 메세지 보내주기
	public void broadcast(InfoDTO sendDto) throws IOException {
		for(ChatHandlerObject handler: list){
			handler.writer.writeObject(sendDto); //핸들러 안의 writer에 값을 보내기
			handler.writer.flush();  //핸들러 안의 writer 값 비워주기
		}
	}
}
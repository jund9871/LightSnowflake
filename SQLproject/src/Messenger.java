import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import org.ietf.jgss.Oid;

import java.sql.*;

class MessengerRoomModel{
	String RID;		//Room_ID
	String room_name;
	public MessengerRoomModel(String RID, String room_name) {
		this.RID = RID;
		this.room_name = room_name;
	}
}

class MessengerChattingModel{
	int count_num;
	String chat_log;
	String write_time;
	String RID;		//참조용 RID
	String UID;		//참조용 UID
	public MessengerChattingModel(int count_num, String chat_log, String write_time, String RID, String UID) {
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
	JButton confirm = new JButton("확인");
	JButton signUp = new JButton("등록");
	JPanel input = new JPanel();
	JPanel id = new JPanel();
	JPanel password = new JPanel();
	
	public MessengerSignUp() {
		setTitle("");
		setSize(340,160);
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

class MessengerChat extends JFrame{
	String roomList[] = {"채팅방 이름","RID"};
	JMenuBar mb = new JMenuBar();	//메뉴
	JMenu settingMenu = new JMenu("설정");
	JMenuItem item[] = new JMenuItem[3];
	
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
	JLabel inviteLabel = new JLabel("초대 UID");
	JTextField inviteTF = new JTextField(10);
	JButton invite = new JButton("초대");
	JPanel invitePanel = new JPanel();
	JScrollPane taScrollPane = new JScrollPane();
	JTextArea ta = new JTextArea();
	JTextField tf = new JTextField(10);
	
	String account[] = {"customer_id","UID","nickname"};
	DefaultTableModel accountTM = new DefaultTableModel(account,0);
	JPanel adminPanel = new JPanel();
	JTable accountTable = new JTable(accountTM);
	JButton deleteAccount = new JButton("삭제");
	
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
		item[0] = new JMenuItem("로그아웃");
		item[1] = new JMenuItem("관리자 모드");
		item[2] = new JMenuItem("종료");
	}
	void setComponent() {
		chatID.setHorizontalAlignment(SwingConstants.CENTER);
		UID.setHorizontalAlignment(SwingConstants.RIGHT);
		inviteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RID.setHorizontalAlignment(SwingConstants.CENTER);
		nowChatRoom.setLayout(new BorderLayout());
		adminPanel.setLayout(new BorderLayout());
		ta.setEditable(false);
		ta.setBackground(Color.LIGHT_GRAY);
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
		taScrollPane.add(ta);
		
		adminPanel.add(new JScrollPane(accountTable));
		adminPanel.add(deleteAccount,BorderLayout.SOUTH);
		
		pane.add("채팅방 선택", personalChatRoom);
		pane.add("오픈 채팅방", openChatRoom);
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
	
	public Administrator() {
		setTitle("관리자 암호를 입력해주세요");
		setSize(340,100);
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

public class Messenger {
	MessengerLogin ML;
	MessengerChat MC;
	MessengerSignUp MSU;
	Administrator Admin;

	public Messenger() {
		ML = new MessengerLogin();
		for(int i = 0; i<3; i++)
			ML.btn[i].addActionListener(new LoginBtn());
	}
	
	class LoginBtn implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == ML.btn[0]) {
				ML.dispose();
				MC = new MessengerChat();
				MC.enter.addActionListener(new ChatBtn());
				for(int i = 0; i<MC.item.length; i++) {
					MC.item[i].addActionListener(new ChatBtn());
				}
			}
			if(e.getSource() == ML.btn[1]) {
				MSU = new MessengerSignUp();
			}
			if(e.getSource() == ML.btn[2]) {
				MSU = new MessengerSignUp();
			}
		}
	}
	
	class ChatBtn implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == MC.enter) {
				MC.nowChatRoom.remove(MC.taScrollPane);
				MC.nowChatRoom.remove(MC.tf);
				MC.nowChatRoom.add(MC.invitePanel,BorderLayout.NORTH);
				MC.nowChatRoom.add(MC.taScrollPane);
				MC.nowChatRoom.add(MC.tf,BorderLayout.SOUTH);
				MC.pane.add("현재 채팅방", MC.nowChatRoom);
			}
			
			String cmd = e.getActionCommand();
			switch (cmd) {
				case "로그아웃" : {
					MC.dispose();
					ML = new MessengerLogin();
					for(int i = 0; i<3; i++)
						ML.btn[i].addActionListener(new LoginBtn());
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
	
	class enterAdmin implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Admin.dispose();
			MC.pane.add("계정 관리", MC.adminPanel);
		}
	}

	public static void main(String[] args) {
		new Messenger();
	}
}

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MessengerRoomModel{
	String room_num;
	String room_name;
	String chat_log;
	public MessengerRoomModel(String room_num, String room_name, String chat_log) {
		this.room_num = room_num;
		this.room_name = room_name;
		this.chat_log = chat_log;
	}
}

class MessengerCustomerModel {
	String customer_id;
	String password;
	String nickname;
	MessengerCustomerModel(String customer_id, String password, String nickname) {
		this.customer_id = customer_id;
		this.password = password;
		this.nickname = nickname;
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
		btn[2] = new JButton("비밀번호 찾기");
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
	JLabel info[] = new JLabel[3];
	JTextField tf[] = new JTextField[3];
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
		info[2] = new JLabel("비밀번호 재입력");
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
		add(signUp,BorderLayout.SOUTH);
	}
}

class MessengerChat extends JFrame{
	JMenuBar mb = new JMenuBar();	//메뉴
	JMenu settingMenu = new JMenu("설정");
	JMenuItem item[] = new JMenuItem[3];
	
	JTabbedPane pane = new JTabbedPane();	//탭
	
	JLabel chatID = new JLabel("CID");	//채팅창 아이디로 선택 (즐겨찾기 추가필요)
	JTextField inputCID = new JTextField(10);
	JButton enter = new JButton("입장");
	JPanel chatSelect = new JPanel();
	
	JPanel personalChatRoom = new JPanel();	//채팅방 즐겨찾기 테이블로 만들기
	
	JPanel openChatRoom = new JPanel();	//오픈채팅방 목록 테이블로 만들기
	
	JPanel nowChatRoom = new JPanel();	//현재 들어가진 채팅방 표시
	JLabel noneRoom = new JLabel("열린 채팅방이 없습니다.");
	
	JLabel UID = new JLabel("UID");
	
	public MessengerChat() {
		setTitle("Chat Room");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,600);
		newComponent();
		setComponent();
		addComponent();
		setVisible(true);
	}
	void newComponent() {
		item[0] = new JMenuItem("로그아웃");
		item[1] = new JMenuItem("관리자 모드");
		item[2] = new JMenuItem("종료");
	}
	void setComponent() {
		chatID.setHorizontalAlignment(SwingConstants.CENTER);
	}
	void addComponent() {
		for(int i = 0; i<item.length; i++) {	//메뉴 내용 추가
			settingMenu.add(item[i]);
			if(i < item.length - 1)
				settingMenu.addSeparator();
		}
		mb.add(settingMenu);
		setJMenuBar(mb);		//프레임에 메뉴바 붙이기
		
		chatSelect.add(chatID);
		chatSelect.add(inputCID);
		chatSelect.add(enter);
		add(chatSelect,BorderLayout.NORTH);
		
		nowChatRoom.add(noneRoom);
		
		pane.add("채팅방 선택", personalChatRoom);
		pane.add("오픈 채팅방", openChatRoom);
		pane.add("현재 채팅방", nowChatRoom);
		add(pane);
		
		add(UID,BorderLayout.SOUTH);
	}
}

public class Messenger {
	MessengerLogin ML;
	MessengerChat MC;
	MessengerSignUp MSU;

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
			}
			if(e.getSource() == ML.btn[1]) {
				MSU = new MessengerSignUp();
			}
			if(e.getSource() == ML.btn[2]) {
				MSU = new MessengerSignUp();
			}
		}
	}

	public static void main(String[] args) {
		new Messenger();
	}
}

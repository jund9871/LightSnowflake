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
	String RID;		//������ RID
	String UID;		//������ UID
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
	JPanel input = new JPanel();	//info���̺�� �ؽ�Ʈ �ʵ� 2���� �� �г�
	JPanel login = new JPanel();	//input�гΰ� login��ư�� �� �г�
	JPanel otherBtn = new JPanel();	//ȸ������ ��ư�� ��й�ȣ ã�� ��ư�� �� �г�
	GridLayout grid = new GridLayout(2,2,5,5);
	
	public MessengerLogin() {
		setTitle("�α���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(340,140);
		setResizable(false);
		newComponent();
		setComponent();
		addComponent();
		setVisible(true);
	}
	void newComponent() {
		info[0] = new JLabel("���̵�");
		info[1] = new JLabel("��й�ȣ");
		tf[0] = new JTextField(10);
		tf[1] = new JTextField(10);
		btn[0] = new JButton("�α���");
		btn[1] = new JButton("ȸ������");
		btn[2] = new JButton("��й�ȣ ã��");		//������ �������� �ʿ� ���⼱ X
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
	JButton confirm = new JButton("�ߺ�Ȯ��");
	JButton signUp = new JButton("���");
	JPanel input = new JPanel();
	JPanel id = new JPanel();
	JPanel password = new JPanel();
	
	public MessengerSignUp() {
		setTitle("ȸ������");
		setSize(360,200);
		setResizable(false);
		newComponent();
		setComponent();
		addComponent();
		setVisible(true);
	}
	void newComponent() {
		info[0] = new JLabel("���̵�");
		info[1] = new JLabel("��й�ȣ");
		info[2] = new JLabel("��й�ȣ ��Ȯ��");
		info[3] = new JLabel("�г���");
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
	JButton confirm = new JButton("���̵� Ȯ��");
	JButton changePW = new JButton("��й�ȣ ����");
	JPanel input = new JPanel();
	JPanel id = new JPanel();
	JPanel password = new JPanel();
	
	public MessengerPassword() {
		setTitle("��й�ȣ ã��(����)");
		setSize(360,160);
		setResizable(false);
		newComponent();
		setComponent();
		addComponent();
		setVisible(true);
	}
	void newComponent() {
		info[0] = new JLabel("���̵�");
		info[1] = new JLabel("��й�ȣ");
		info[2] = new JLabel("��й�ȣ ��Ȯ��");
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
	String roomList[] = {"ä�ù� �̸�","RID"};
	JMenuBar mb = new JMenuBar();	//�޴�
	JMenu settingMenu = new JMenu("����");
	JMenuItem item[] = new JMenuItem[4];
	
	JTabbedPane pane = new JTabbedPane();	//��
	
	JLabel roomName = new JLabel("ä�ù� �̸�");
	JLabel chatID = new JLabel("RID");	//ä��â ���̵�� ���� (���ã�� �߰��ʿ�)
	JTextField inputRoomName = new JTextField(10);
	JTextField inputRID = new JTextField(10);
	JButton enter = new JButton("���");
	JPanel chatSelect = new JPanel();
	
	JButton deleteRoom = new JButton("����");
	DefaultTableModel personalTM = new DefaultTableModel(roomList, 0);
	JPanel personalChatRoom = new JPanel();	//ä�ù� ���ã�� ���̺�� �����
	JTable personalRooms = new JTable(personalTM);
	
	DefaultTableModel openTM = new DefaultTableModel(roomList, 0);
	JPanel openChatRoom = new JPanel();	//����ä�ù� ��� ���̺�� �����
	JTable openRooms = new JTable(openTM);
	
	JPanel nowChatRoom = new JPanel();	//���� ���� ä�ù� ǥ��
	JLabel inviteLabel = new JLabel("UID :");
	JTextField inviteTF = new JTextField(10);
	JButton invite = new JButton("�ʴ�");
	JButton kick = new JButton("�߹�");
	JPanel invitePanel = new JPanel();
	JTextArea ta = new JTextArea();
	JTextField tf = new JTextField(10);
	
	String account[] = {"customer_id","UID","nickname"};
	DefaultTableModel accountTM = new DefaultTableModel(account,0);
	JPanel adminPanel = new JPanel();
	JTable accountTable = new JTable(accountTM);
	JButton deleteAccount = new JButton("����");
	
	JLabel noneRoom = new JLabel("���� ä�ù��� �����ϴ�.");
	JLabel UID = new JLabel("UID : 00000000   ");
	JLabel RID = new JLabel("RID : 00000000");
	
	public MessengerChat() {
		setTitle("ä�ù�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,600);
		newComponent();
		setComponent();
		addComponent();
		tableSet();
		setVisible(true);
	}
	void newComponent() {
		item[0] = new JMenuItem("ģ�����");
		item[1] = new JMenuItem("�α׾ƿ�");
		item[2] = new JMenuItem("������ ���");
		item[3] = new JMenuItem("����");
	}
	void setComponent() {
		chatID.setHorizontalAlignment(SwingConstants.CENTER);
		inviteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noneRoom.setHorizontalAlignment(SwingConstants.CENTER);
		nowChatRoom.setLayout(new BorderLayout());
		adminPanel.setLayout(new BorderLayout());
		ta.setEditable(false);
		ta.setBackground(Color.LIGHT_GRAY);
		ta.setFont(new Font("���� ���",Font.BOLD,15));
	}
	void addComponent() {
		for(int i = 0; i<item.length; i++) {	//�޴� ���� �߰�
			settingMenu.add(item[i]);
			if(i < item.length - 1)
				settingMenu.addSeparator();
		}
		mb.add(settingMenu);
		setJMenuBar(mb);		//�����ӿ� �޴��� ���̱�
		
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
		
		pane.add("ä�ù� ����", personalChatRoom);
		pane.add("���� ä�ù�", openChatRoom);
		pane.add("���� ä�ù�", nowChatRoom);
		add(pane);
		
		add(UID,BorderLayout.SOUTH);
	}
	void tableSet() {
		DefaultTableCellRenderer ca = new DefaultTableCellRenderer();	// ���̺� �⺻ ���� �����
		ca.setHorizontalAlignment(SwingConstants.CENTER);	// ��� ����
		personalRooms.setPreferredScrollableViewportSize(new Dimension(250, 400));
		personalRooms.setAutoCreateRowSorter(true);	// �ڵ� ����
		personalRooms.getTableHeader().setReorderingAllowed(false);
		openRooms.setPreferredScrollableViewportSize(new Dimension(250, 400));
		openRooms.setAutoCreateRowSorter(true);	// �ڵ� ����
		openRooms.getTableHeader().setReorderingAllowed(false);
		accountTable.setPreferredScrollableViewportSize(new Dimension(250, 400));
		accountTable.setAutoCreateRowSorter(true);	// �ڵ� ����
		accountTable.getTableHeader().setReorderingAllowed(false);
		for (int i = 0; i < roomList.length; i++) {
			personalRooms.getColumnModel().getColumn(i).setCellRenderer(ca);
			openRooms.getColumnModel().getColumn(i).setCellRenderer(ca);
			accountTable.getColumnModel().getColumn(i).setCellRenderer(ca);
		}
	}
}

class Administrator extends JFrame{
	JButton enter = new JButton("������ ��� ��ȯ");
	JLabel password = new JLabel("��ȣ");
	JTextField input = new JTextField(15);
	JPanel adminPW = new JPanel();
	String PW = "Admin";
	
	public Administrator() {
		setTitle("������ ��ȣ�� �Է����ּ���");
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
	JLabel UID = new JLabel("ģ�� UID");
	JTextField tf = new JTextField(10);
	JButton confirm = new JButton("���");
	JButton delete = new JButton("����");
	JPanel friendEnter = new JPanel();
	
	String friendList[] = {"�г���","UID"};
	DefaultTableModel friendTM = new DefaultTableModel(friendList, 0);
	JPanel personalChatRoom = new JPanel();	
	JTable friendTable = new JTable(friendTM);
	
	public FriendMenu() {
		setTitle("ģ�����");
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
	ChatServerObject CSO;	//���� ����(���� ��Ʈ 9500����)
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
	
	Statement stmt = null; // SQL������ ���� ����
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
				case "ģ�����" : {
					FM = new FriendMenu();
					FM.confirm.addActionListener(new friendBtn());
					FM.delete.addActionListener(new friendBtn());
					FM.friendTable.addMouseListener(new JTableClick());
					break;
				}
				case "�α׾ƿ�" : {
					MC.dispose();
					ML = new MessengerLogin();
					for(int i = 0; i<3; i++)
						ML.btn[i].addActionListener(new loginBtn());
					break;
				}
				case "������ ���" : {
					Admin = new Administrator();			//��� �Է�â ���� ��ưŬ�� �� ���� �� �߰�
					Admin.enter.addActionListener(new enterAdmin());
					break;
				}
				case "����" : {
					System.exit(0);		//���μ��� ����
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
				MC.pane.add("���� ����", MC.adminPanel);
			}
		}
	}
	
	class JTableClick extends JFrame implements MouseListener {	// ���콺 ������ �߰�
		public void mouseClicked(MouseEvent e) {	// Ŭ�� ��
			if(e.getSource() == MC.personalRooms) {
				int row = MC.personalRooms.getSelectedRow();	// ������ �� ��ȣ �ޱ�
				makeConnection();
				String sql = "";
				sql = "SELECT * FROM chat_room WHERE room_id='" + MC.personalRooms.getModel().getValueAt(row, 1) + "'";	// ���õ� ���� PK���� ������
				try {																								// �������� �ֱ�
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
				int row = MC.openRooms.getSelectedRow();	// ������ �� ��ȣ �ޱ�
				makeConnection();
				String sql = "";
				sql = "SELECT * FROM chat_room WHERE room_id='" + MC.openRooms.getModel().getValueAt(row, 1) + "'";	// ���õ� ���� PK���� ������
				try {																								// �������� �ֱ�
					rs = stmt.executeQuery(sql);
					if (rs.next()) {
						MRM = new MessengerRoomModel(rs.getString("room_id"), rs.getString("room_name"), rs.getInt("room_num"), rs.getString("host_id"));
						if(nowRID.equals(MRM.RID)) {
							//�����ִ� ä�ù�
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
				int row = FM.friendTable.getSelectedRow();	// ������ �� ��ȣ �ޱ�
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
	
	public Connection makeConnection() { // SQL ����
		String url = "jdbc:mysql://localhost/messenger_db?serverTimezone=Asia/Seoul";
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
	
	public void disConnection() { // SQL���� ����
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
			MC.openTM.setNumRows(0);	// ���̺� �ʱ�ȭ
			while (rs.next()) {
				MC.openTM.addRow(	// ���� ä�ù� ��� �ҷ�����
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
			JOptionPane.showMessageDialog(null, "���̵� �Էµ��� �ʾҽ��ϴ�!", "���̵� ���Է�",
                    JOptionPane.ERROR_MESSAGE);
		}
		else if(ML.tf[1].getText().equals("")) {
			JOptionPane.showMessageDialog(null, "��й�ȣ�� �Էµ��� �ʾҽ��ϴ�!", "��й�ȣ ���Է�",
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
						JOptionPane.showMessageDialog(null, "��й�ȣ�� Ʋ�Ƚ��ϴ�!\n���� �ٽ� Ȯ�����ּ���!", "��й�ȣ ����ġ",
                                JOptionPane.ERROR_MESSAGE);
					}
				}
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "���̵� Ʋ�Ƚ��ϴ�!\n���� �ٽ� Ȯ�����ּ���!", "���̵� ����ġ",
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
			JOptionPane.showMessageDialog(null, "ä�ù� ������ �ƴϸ� �����Ҽ� �����ϴ�!", "���� �Ұ�",
                    JOptionPane.PLAIN_MESSAGE);
		else if(nowCustomer == MRM.host_id) {
			int isDelete = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ� ?");	//���̾�α׷� ������ Ȯ��
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
	
	public void clearTextFields() { // ��� �ؽ�Ʈ �ʵ� �ʱ�ȭ
		MC.inputRoomName.setText("");
		MC.inputRID.setText("");
	}
	
	void personalRoom() {
		makeConnection();
		String sql = "";
		sql = "SELECT * FROM chat_room WHERE host_id LIKE'" + nowCustomer +"' OR user_id LIKE'" + nowCustomer + "'" ;
		try {
			rs = stmt.executeQuery(sql);
			MC.personalTM.setNumRows(0);	// ���̺� �ʱ�ȭ
			while (rs.next()) {
				MC.personalTM.addRow(	// ���� ä�ù� ��� �ҷ�����
						new Object[] { rs.getString("room_name"), rs.getString("room_id")});
			}
		} catch (SQLException sqle) {
			System.out.println("getData: SQL Error");
		}
		disConnection();
	}
	
	public void actionPerformed(ActionEvent e){
		try{
			//������ ���� 
			//JTextField���� �����κ�����
			//���� ����
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
			writer.flush();	//�Ƹ� �� �ѱ��
			MC.tf.setText("");	//ġ�� �� �� ����
			
		}catch(IOException io){
			io.printStackTrace();
		}
	}
	
	public void run(){
		//�����κ��� ������ �ޱ�
		InfoDTO dto= null;
		while(true){
			try{
				dto = (InfoDTO) reader.readObject();
				if(dto.getCommand()==Info.EXIT){  //�����κ��� �� �ڽ��� exit�� ������ �����
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
		//���� IP �Է¹ޱ�
		String serverIP = "192.168.0.3";  //�⺻������ ������ ���� �ԷµǾ� ���� ��
		try{
			socket = new Socket(serverIP,9500);	//������ IP�� �޾ƿ��� ������ ��Ʈ ����(���� ��Ʈ���� ���� ���� �� ����(���� ����))
			//���� �߻�
			reader= new ObjectInputStream(socket.getInputStream());
			writer = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("���� �غ� �Ϸ�!");
			
		} catch(UnknownHostException e ){
			System.out.println("������ ã�� �� �����ϴ�.");
			e.printStackTrace();
			System.exit(0);
		} catch(IOException e){
			System.out.println("������ ������ �ȵǾ����ϴ�.");
			e.printStackTrace();
			System.exit(0);
		}
		try{
			//������ �г��� ������
			InfoDTO dto = new InfoDTO();
			dto.setCommand(Info.JOIN);
			dto.setNickName(nowNickName);
			writer.writeObject(dto);
			writer.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
		//������ ����
		Thread t = new Thread(this);
		t.start();
	}

	public static void main(String[] args) {
		new Messenger();
	}
}

class ChatServerObject 	//ä�ù� ���� ����
{
	private ServerSocket serverSocket;
	private ArrayList <ChatHandlerObject> list;
	static HashMap<String, Object> hash;
	public ChatServerObject(int port){
		try{
			serverSocket= new ServerSocket (port);		//���� ��Ʈ ����
			hash = new HashMap<String, Object>();
			System.out.println("���� �غ� �Ϸ�");
			list = new ArrayList<ChatHandlerObject>();
			while(true){
				Socket socket = serverSocket.accept();
				ChatHandlerObject handler = new  ChatHandlerObject(socket,list);  //�����带 ������ ���̶� ������
				handler.start();  //������ ����- ������ ����
				list.add(handler);  //�ڵ鷯�� ����(�� ����Ʈ�� ������ Ŭ���̾�Ʈ�� ����)
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

class ChatHandlerObject extends Thread //ó�����ִ� ��(���Ͽ� ���� ������ ����ִ� ��. ������ ó����)
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
		String nickName;														//�г��� ������ ó�� ����
		try{
			while(true){
				dto=(InfoDTO)reader.readObject();
				nickName=dto.getNickName();
				if(dto.getCommand()==Info.EXIT){	//����� ���� ����
					InfoDTO sendDto = new InfoDTO();
					//�������� exit�� ���� Ŭ���̾�Ʈ���� �亯 ������
					//sendDto.setCommand(Info.EXIT);
					//writer.writeObject(sendDto);
					//writer.flush();

					//reader.close();
					//writer.close();
					//socket.close();
					//�����ִ� Ŭ���̾�Ʈ���� ����޼��� ������
					list.remove(this);

					sendDto.setCommand(Info.SEND);
					sendDto.setMessage(nickName+"�� �����Ͽ����ϴ�");
					broadcast(sendDto);
					break;
				} else if(dto.getCommand()==Info.JOIN){
					//��� ����ڿ��� �޼��� ������
					//��� Ŭ���̾�Ʈ���� ���� �޼����� ������ ��
					InfoDTO sendDto = new InfoDTO();
					sendDto.setCommand(Info.SEND);
					sendDto.setMessage(nickName+"�� �����Ͽ����ϴ�");
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
	//�ٸ� Ŭ���̾�Ʈ���� ��ü �޼��� �����ֱ�
	public void broadcast(InfoDTO sendDto) throws IOException {
		for(ChatHandlerObject handler: list){
			handler.writer.writeObject(sendDto); //�ڵ鷯 ���� writer�� ���� ������
			handler.writer.flush();  //�ڵ鷯 ���� writer �� ����ֱ�
		}
	}
}
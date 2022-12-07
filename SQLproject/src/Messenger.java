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
	int room_port;
	String host_id;
	String UID;
	MessengerRoomModel(String RID, String room_name, int room_port, String host_id, String UID) {
		this.RID = RID;
		this.room_name = room_name;
		this.room_port = room_port;
		this.host_id = host_id;
		this.UID = UID;
	}
}

class MessengerCustomerModel {
	String login_id;
	String password;
	String nickname;
	int UID;
	MessengerCustomerModel(String login_id, String password, String nickname, int UID) {
		this.login_id = login_id;
		this.password = password;
		this.nickname = nickname;
		this.UID = UID;
	}
}

class AccountDeleteModel {
	String login_id;
	String password;
	String nickname;
	int UID;
	AccountDeleteModel(String login_id, String password, String nickname, int UID) {
		this.login_id = login_id;
		this.password = password;
		this.nickname = nickname;
		this.UID = UID;
	}
}

class MessengerFriendModel{
	String UID;
	String FID;
	String friend_nickname;
	MessengerFriendModel(String UID, String FID, String friend_nickname) {
		this.UID = UID;
		this.FID = FID;
		this.friend_nickname = friend_nickname;
	}
}

class MessengerLogin extends JFrame{
	JLabel info[] = new JLabel[2];
	JTextField tf[] = new JTextField[2];
	JButton btn[] = new JButton[3];
	JPanel input = new JPanel();
	JPanel login = new JPanel();
	JPanel otherBtn = new JPanel();
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
		btn[2] = new JButton("��й�ȣ ã��");
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
	JMenuBar mb = new JMenuBar();
	JMenu settingMenu = new JMenu("����");
	JMenuItem item[] = new JMenuItem[5];
	
	JTabbedPane pane = new JTabbedPane();
	
	JLabel roomName = new JLabel("ä�ù� �̸�");
	JLabel chatID = new JLabel("RID");
	JTextField inputRoomName = new JTextField(10);
	JTextField inputRID = new JTextField(10);
	JButton enter = new JButton("���");
	JPanel chatSelect = new JPanel();
	
	JButton deleteRoom = new JButton("����");
	DefaultTableModel personalTM = new DefaultTableModel(roomList, 0);
	JPanel personalChatRoom = new JPanel();
	JTable personalRooms = new JTable(personalTM);
	
	DefaultTableModel openTM = new DefaultTableModel(roomList, 0);
	JPanel openChatRoom = new JPanel();
	JTable openRooms = new JTable(openTM);
	
	JPanel nowChatRoom = new JPanel();
	JLabel inviteLabel = new JLabel("UID :");
	JTextField inviteTF = new JTextField(10);
	JButton invite = new JButton("�ʴ�");
	JButton kick = new JButton("�߹�");
	JPanel invitePanel = new JPanel();
	JTextArea ta = new JTextArea();
	JTextField tf = new JTextField(10);
	
	String account[] = {"login_id","UID","nickname"};
	DefaultTableModel accountTM = new DefaultTableModel(account,0);
	JPanel adminPanel = new JPanel();
	JTable accountTable = new JTable(accountTM);
	JButton deleteAccount = new JButton("����");
	JButton deleteChat = new JButton("ä�ù� ����");
	
	JLabel noneRoom = new JLabel("���� ä�ù��� �����ϴ�.");
	JLabel UID = new JLabel("UID : 0   ");
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
		item[1] = new JMenuItem("�г��� ����");
		item[2] = new JMenuItem("�α׾ƿ�");
		item[3] = new JMenuItem("������ ���");
		item[4] = new JMenuItem("����");
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
		setComponent();
		addComponent();
		setVisible(true);
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
		setComponent();
		addComponent();
		setVisible(true);
	}
	void setComponent() {
		UID.setHorizontalAlignment(SwingConstants.CENTER);
		DefaultTableCellRenderer ca = new DefaultTableCellRenderer();	// ���̺� �⺻ ���� �����
		ca.setHorizontalAlignment(SwingConstants.CENTER);	// ��� ����
		friendTable.setAutoCreateRowSorter(true);
		friendTable.getTableHeader().setReorderingAllowed(false);
		for(int i = 0; i<2; i++) {
			friendTable.getColumnModel().getColumn(i).setCellRenderer(ca);
		}
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

class ChangeNickname extends JFrame{
	JButton change = new JButton("�г��� ����");
	JLabel nickname = new JLabel("�г���");
	JTextField input = new JTextField(15);
	JPanel nickPanel = new JPanel();
	
	public ChangeNickname() {
		setTitle("�г��� ����");
		setSize(340,100);
		setResizable(false);
		setComponent();
		addComponent();
		setVisible(true);
	}
	void setComponent() {
		nickname.setHorizontalAlignment(SwingConstants.CENTER);
		input.setHorizontalAlignment(SwingConstants.CENTER);
	}
	void addComponent() {
		nickPanel.add(nickname);
		nickPanel.add(input);
		add(nickPanel);
		add(change,BorderLayout.SOUTH);
	}
}

public class Messenger extends JFrame implements ActionListener,Runnable{
	MessengerLogin ML;
	MessengerChat MC;
	MessengerSignUp MSU;
	MessengerPassword MP;
	Administrator Admin;
	FriendMenu FM;
	ChangeNickname CN;
	
	MessengerCustomerModel MCM;
	MessengerRoomModel MRM;
	MessengerFriendModel MFM;
	AccountDeleteModel ADM;
	
	Statement stmt = null; // SQL������ ���� ����
	Connection con = null;
	ResultSet rs = null;
	private Socket socket;
	private ObjectInputStream reader=null;
	private ObjectOutputStream writer=null;
	
	int check = 0;
	int nowCustomer = 0;
	String nowNickName = "";
	String nowRID = "";
	int lastRoomNum = 0;
	int lastUID = 0;
	String user = "";

	public Messenger() {
		ML = new MessengerLogin();
		for(int i = 0; i<3; i++)
			ML.btn[i].addActionListener(new loginBtn());
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
				confirmID_signUp();
			}
			else if(e.getSource() == MSU.signUp) {
				if(MSU.tf[1].getText().equals(MSU.tf[2].getText())) {
					signUp();
				}
				else {
					JOptionPane.showMessageDialog(null, "�� ��й�ȣ�� �ٸ��ϴ�!", "��й�ȣ ���Է�",
				            JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	class passwordBtn implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == MP.confirm) {
				confirmID_password();
			}
			else if(e.getSource() == MP.changePW) {
				if(MP.tf[1].getText().equals(MP.tf[2].getText()))
					changePW();
				else {
					JOptionPane.showMessageDialog(null, "�� ��й�ȣ�� �ٸ��ϴ�!", "��й�ȣ ���Է�",
				            JOptionPane.ERROR_MESSAGE);
				}
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
					friendList();
					FM.confirm.addActionListener(new friendBtn());
					FM.delete.addActionListener(new friendBtn());
					FM.friendTable.addMouseListener(new FriendClick());
					break;
				}
				case "�г��� ����" : {
					CN = new ChangeNickname();
					CN.change.addActionListener(new changeNick());
					break;
				}
				case "�α׾ƿ�" : {
					try{
						InfoDTO dto = new InfoDTO();
						dto.setNickName(nowNickName);
						dto.setCommand(Info.EXIT);
						writer.writeObject(dto);
						writer.flush();
					}catch(IOException io){
						io.printStackTrace();
					}
					check = 0;
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
					try{
						InfoDTO dto = new InfoDTO();
						dto.setNickName(nowNickName);
						dto.setCommand(Info.EXIT);
						writer.writeObject(dto);
						writer.flush();
					}catch(IOException io){
						io.printStackTrace();
					}
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
				MC.add(MC.deleteChat, BorderLayout.SOUTH);
				accountList();
				MC.accountTable.addMouseListener(new AccountClick());
				MC.deleteAccount.addActionListener(new accountDelete());
				MC.deleteChat.addActionListener(new roomDelete_admin());
			}
		}
	}
	
	class accountDelete implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int isDelete = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ� ?");	//���̾�α׷� ������ Ȯ��
			if (isDelete == 0) {
				makeConnection();
				String sql = "";
				sql = "DELETE FROM customer WHERE login_id='" + ADM.login_id + "'";
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException sqle) {
					System.out.println("isExist: DELETE SQL Error");
				}
				disConnection();
			}
			accountList();
		}
	}
	
	class roomDelete_admin implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == MC.deleteChat) {
				int isDelete = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ� ?");
				if (isDelete == 0) {
					makeConnection();
					String sql = "";
					sql = "DELETE FROM chat_room WHERE room_id='" + MRM.RID + "'";
					try {
						stmt.executeUpdate(sql);
					} catch (SQLException sqle) {
						System.out.println("isExist: DELETE SQL Error");
					}
					sql = "SELECT * FROM chat_room WHERE room_id LIKE'002%'";	//002�� �����ϴ� RID�� ����ä�ù�
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
					
					sql = "SELECT * FROM chat_room ORDER BY room_port DESC LIMIT 1";
					try {
						rs = stmt.executeQuery(sql);
						if(rs.next())
							lastRoomNum = rs.getInt("room_port") + 1;
					} catch (SQLException sqle) {
						System.out.println("getData: SQL Error");
					}
					personalRoom();
					disConnection();
					MC.pane.remove(MC.nowChatRoom);
				}
			}
		}
	}
	
	class changeNick implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == CN.change) {
				changeNickname();
			}
		}
	}
	
	class JTableClick extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {	// Ŭ�� ��
			if(e.getSource() == MC.personalRooms) {
				int row = MC.personalRooms.getSelectedRow();	// ������ �� ��ȣ �ޱ�
				makeConnection();
				String sql = "";
				sql = "SELECT * FROM chat_room WHERE room_id='" + MC.personalRooms.getModel().getValueAt(row, 1) + "'";	// ���õ� ���� PK���� ������
				try {																								// �������� �ֱ�
					rs = stmt.executeQuery(sql);
					if (rs.next()) {
						MRM = new MessengerRoomModel(rs.getString("room_id"), rs.getString("room_name"), rs.getInt("room_port"), 
								rs.getString("host_id"), rs.getString("user_id"));
						MC.inputRoomName.setText(MRM.room_name);
						MC.inputRID.setText(MRM.RID);
						if(nowRID.equals(MRM.RID)) {
							//�����ִ� ä�ù�
						}
						else {
							MC.ta.setText("");
						}
						nowRID = MRM.RID;
						MC.RID.setText("RID : "+nowRID);
						user = MRM.UID;
						System.out.println(MRM.room_port);
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
						MRM = new MessengerRoomModel(rs.getString("room_id"), rs.getString("room_name"), rs.getInt("room_port"), 
								rs.getString("host_id"), rs.getString("user_id"));
						if(nowRID.equals(MRM.RID)) {
							//�����ִ� ä�ù�
						}
						else {
							MC.ta.setText("");
						}
						nowRID = MRM.RID;
						MC.RID.setText("RID : "+nowRID);
						user = MRM.UID;
						openChatRoom();
					}
				} catch (SQLException sqle) {
					System.out.println("is Not Exist");
				}
				disConnection();
			}
		}
	}
	
	class FriendClick extends MouseAdapter {	// ���콺 ������ �߰�
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == FM.friendTable) {
				int row = FM.friendTable.getSelectedRow();	// ������ �� ��ȣ �ޱ�
				FM.tf.setText((String)FM.friendTable.getModel().getValueAt(row, 1));
			}
		}
	}
	
	class AccountClick extends MouseAdapter {	// ���콺 ������ �߰�
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == MC.accountTable) {
				int row = MC.accountTable.getSelectedRow();	// ������ �� ��ȣ �ޱ�
				makeConnection();
				String sql = "";
				sql = "SELECT * FROM customer WHERE login_id='" + MC.accountTable.getModel().getValueAt(row, 0) + "'";	// ���õ� ���� PK���� ������
				try {																								// �������� �ֱ�
					rs = stmt.executeQuery(sql);
					if (rs.next()) {
						ADM = new AccountDeleteModel(rs.getString("login_id"), rs.getString("password"), 
								rs.getString("nickname"), rs.getInt("user_id"));
					}
				} catch (SQLException sqle) {
					System.out.println("is Not Exist");
				}
				disConnection();
			}
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
	
	void viewToModel_MCM() {
		makeConnection();
		String sql = "";
		sql = "SELECT * FROM customer ORDER BY user_id DESC LIMIT 1";
		try {
			rs = stmt.executeQuery(sql);
			if(rs.next())
				lastUID = rs.getInt("user_id") + 1;
		} catch (SQLException sqle) {
			System.out.println("getData: SQL Error");
		}
		disConnection();
		
		MCM = new MessengerCustomerModel(MSU.tf[0].getText(), MSU.tf[1].getText(), MSU.tf[3].getText(), lastUID);
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
		MC.invite.addActionListener(new roomManager());
		MC.kick.addActionListener(new roomManager());
		MC.remove(MC.UID);
		MC.UID = new JLabel("UID : " + MCM.UID + "   ");
		MC.UID.setHorizontalAlignment(SwingConstants.RIGHT);
		MC.add(MC.UID,BorderLayout.SOUTH);
		
		makeConnection();
		String sql = "";
		sql = "SELECT * FROM chat_room WHERE room_id LIKE'002%'";	//002�� �����ϴ� RID�� ����ä�ù�
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
		
		sql = "SELECT * FROM chat_room ORDER BY room_port DESC LIMIT 1";
		try {
			rs = stmt.executeQuery(sql);
			if(rs.next())
				lastRoomNum = rs.getInt("room_port") + 1;
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
							rs.getString("nickname"), rs.getInt("user_id"));
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
		MRM = new MessengerRoomModel(MC.inputRID.getText(), MC.inputRoomName.getText(), lastRoomNum, Integer.toString(nowCustomer), "");
		String sql = "";
		try {
			sql = "INSERT INTO chat_room (room_id,room_name,room_port,host_id,user_id) values ";
			sql += "('" + MRM.RID + "','" + MRM.room_name + "','" + MRM.room_port + "','" + MRM.host_id + "','" + MRM.UID + "')";
			stmt.executeUpdate(sql);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		personalRoom();
		disConnection();
		clearTextFields();
		lastRoomNum++;
	}
	
	void deleteChatRoom() {
		if(Integer.toString(nowCustomer).equals(MRM.host_id) ) {
			int isDelete = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ� ?");	//���̾�α׷� ������ Ȯ��
			if (isDelete == 0) {
				makeConnection();
				String sql = "";
				sql = "DELETE FROM chat_room WHERE room_id='" + MRM.RID + "'";
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
		else {
			JOptionPane.showMessageDialog(null, "ä�ù� ������ �ƴϸ� �����Ҽ� �����ϴ�!", "���� �Ұ�",
                    JOptionPane.PLAIN_MESSAGE);
		}
	}	
	
	void inviteChatRoom() {
		makeConnection();
		if(user.equals("")) {
			user = MC.inviteTF.getText();
			String sql = "";
			sql = "UPDATE chat_room SET user_id = '" + user + "' WHERE room_id = '" + MRM.RID + "'";
			try {
				stmt.executeUpdate(sql);
				JOptionPane.showMessageDialog(null, "�ʴ밡 �Ϸ�Ǿ����ϴ�!", "�ʴ�Ϸ�",
	                    JOptionPane.PLAIN_MESSAGE);
				MC.inviteTF.setText("");
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "�ʴ밡 �����Ͽ����ϴ�.", "�ʴ����",
	                    JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(user.equals(MC.inviteTF.getText())) {
			JOptionPane.showMessageDialog(null, "�̹� �����ϴ� ���̵��Դϴ�!", "�ʴ����",
                    JOptionPane.ERROR_MESSAGE);
		}
		else {
			user = user +","+ MC.inviteTF.getText();
			String sql = "";
			sql = "UPDATE chat_room SET user_id = '" + user + "' WHERE room_id = '" + MRM.RID + "'";
			try {
				stmt.executeUpdate(sql);
				JOptionPane.showMessageDialog(null, "�ʴ밡 �Ϸ�Ǿ����ϴ�!", "�ʴ�Ϸ�",
	                    JOptionPane.PLAIN_MESSAGE);
				MC.inviteTF.setText("");
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "�ʴ밡 �����Ͽ����ϴ�.", "�ʴ����",
	                    JOptionPane.ERROR_MESSAGE);
			}
		}
		disConnection();
	}
	
	void kickChatRoom() {
		String users[] = user.split(",");
		String changeUsers = "";
		for(int i = 0; i<users.length; i++) {
			if(users[i].equals(MC.inviteTF.getText())) {
				users[i] = "";
			}
			changeUsers += users[i];
			if(i>0) {
				changeUsers += ",";
			}
		}
		user = changeUsers;
		makeConnection();
		String sql = "";
		sql = "UPDATE chat_room SET user_id = '" + changeUsers + "' WHERE room_id = '" + MRM.RID + "'";
		try {
			stmt.executeUpdate(sql);
			JOptionPane.showMessageDialog(null, MC.inviteTF.getText()+"���� �߹��Ͽ����ϴ�!", "�߹�Ϸ�",
                    JOptionPane.PLAIN_MESSAGE);
			MC.inviteTF.setText("");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "�߹��� �����Ͽ����ϴ�.", "�߹����",
                    JOptionPane.ERROR_MESSAGE);
		}
		disConnection();
	}
	
	void addFriend() {
		makeConnection();
		String nickname = "";
		String sql = "";
		sql = "SELECT * FROM customer WHERE user_id = " + FM.tf.getText();
		try {
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				nickname = rs.getString("nickname");
			}
			sql = "INSERT INTO friends (user_id,friend_id,friend_nickname) values ";
			sql += "('" + nowCustomer + "','" + FM.tf.getText() + "','" + nickname + "')";
			try {
				stmt.executeUpdate(sql);
				JOptionPane.showMessageDialog(null, "�߰��� �Ϸ�Ǿ����ϴ�!", "ģ���߰��Ϸ�",
		            JOptionPane.PLAIN_MESSAGE);
				sql = "INSERT INTO friends (user_id,friend_id,friend_nickname) values ";
				sql += "('" + FM.tf.getText() + "','" + nowCustomer + "','" + nowNickName + "')";
				try {
					stmt.executeUpdate(sql);
				}catch (SQLException e) {}
				FM.tf.setText("");
				friendList();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "�߰��� �����Ͽ����ϴ�.", "ģ���߰�����",
		            JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "��밡 �������� �ʽ��ϴ�.", "�˻�����",
		            JOptionPane.ERROR_MESSAGE);
		}
		disConnection();
	}
	
	void deleteFriend() {
		makeConnection();
		String sql = "";
		sql = "DELETE FROM friends WHERE friend_id = " + FM.tf.getText() + " AND user_id = " + nowCustomer;
		try {
			stmt.executeUpdate(sql);
			JOptionPane.showMessageDialog(null, FM.tf.getText()+"���� ģ�������Ͽ����ϴ�!", "�����Ϸ�",
                    JOptionPane.PLAIN_MESSAGE);
			sql = "DELETE FROM friends WHERE friend_id = " + nowCustomer + " AND user_id = " + FM.tf.getText();
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {}
			FM.tf.setText("");
			friendList();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ģ�������� �����Ͽ����ϴ�.", "��������",
                    JOptionPane.ERROR_MESSAGE);
		}
		disConnection();
	}
	
	void confirmID_password() {
		makeConnection();
		String sql = "";
		sql = "SELECT * FROM customer WHERE login_id = " + MP.tf[0].getText();
		try {
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				JOptionPane.showMessageDialog(null, "���̵� Ȯ���߽��ϴ�.", "Ȯ�οϷ�",
						JOptionPane.PLAIN_MESSAGE);
				nowCustomer = rs.getInt("user_id");
				MP.tf[1].setEditable(true);
				MP.tf[2].setEditable(true);
				MP.changePW.setEnabled(true);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "���̵� �����ϴ�!", "���̵� ����",
		            JOptionPane.ERROR_MESSAGE);
			
		}
		disConnection();
	}
	void confirmID_signUp() {
		makeConnection();
		String sql = "";
		sql = "SELECT * FROM customer WHERE login_id = " + MSU.tf[0].getText();
		try {
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				JOptionPane.showMessageDialog(null, "���̵� �����մϴ�.", "�ߺ��� ���̵�",
						JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "���̵� ����� �����մϴ�!", "Ȯ�οϷ�",
			            JOptionPane.PLAIN_MESSAGE);
				MSU.tf[1].setEditable(true);
				MSU.tf[2].setEditable(true);
				MSU.tf[3].setEditable(true);
				MSU.signUp.setEnabled(true);
			}
		} catch (SQLException e) {}
		disConnection();
	}
	
	void signUp() {
		viewToModel_MCM();
		makeConnection();
		String sql = "";
		sql = "INSERT INTO customer (login_id,password,nickname,user_id) values";
		sql += "('"+MCM.login_id+"','"+MCM.password+"','"+MCM.nickname+"','"+MCM.UID+"')";
		try {
			stmt.executeUpdate(sql);
			JOptionPane.showMessageDialog(null, "���̵� ��ϵǾ����ϴ�.", "���̵� ��ϼ���",
		            JOptionPane.PLAIN_MESSAGE);
			MSU.dispose();
		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, "���̵� ��Ͽ� �����Ͽ����ϴ�!", "����",
		            JOptionPane.ERROR_MESSAGE);
		}
		disConnection();
	}
	
	void changePW() {
		makeConnection();
		String sql = "";
		sql = "UPDATE customer SET password = '" + MP.tf[2].getText() + "' WHERE user_id = " + nowCustomer;
		try {
			stmt.executeUpdate(sql);
			JOptionPane.showMessageDialog(null, "��й�ȣ�� ����Ǿ����ϴ�!", "����Ϸ�",
                    JOptionPane.PLAIN_MESSAGE);
			MP.dispose();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "��й�ȣ ������ �����Ͽ����ϴ�.", "�������",
                    JOptionPane.ERROR_MESSAGE);
		}
		disConnection();
	}
	
	void changeNickname() {
		makeConnection();
		String sql = "";
		sql = "UPDATE customer SET nickname = '" + CN.input.getText() + "' WHERE user_id = " + nowCustomer;
		try {
			stmt.executeUpdate(sql);
			JOptionPane.showMessageDialog(null, "�г����� ����Ǿ����ϴ�!", "����Ϸ�",
                    JOptionPane.PLAIN_MESSAGE);
			nowNickName = CN.input.getText();
			CN.dispose();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "�г��� ������ �����Ͽ����ϴ�.", "�������",
                    JOptionPane.ERROR_MESSAGE);
		}
		disConnection();
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
		MC.pane.remove(MC.nowChatRoom);
		MC.invitePanel.remove(MC.RID);
		MC.invitePanel.remove(MC.inviteLabel);
		MC.invitePanel.remove(MC.inviteTF);
		MC.invitePanel.remove(MC.invite);
		MC.nowChatRoom.remove(MC.invitePanel);
		MC.invitePanel.add(MC.RID);
		MC.invitePanel.add(MC.inviteLabel);
		MC.invitePanel.add(MC.inviteTF);
		MC.invitePanel.add(MC.invite);
		MC.invitePanel.add(MC.kick);
		MC.nowChatRoom.add(MC.invitePanel,BorderLayout.NORTH);
		MC.ta.setText("");
		MC.nowChatRoom.remove(MC.ta);
		MC.nowChatRoom.add(new JScrollPane(MC.ta));
		MC.pane.add("���� ä�ù�",MC.nowChatRoom);
		service();
		check++;
	}
	
	public void clearTextFields() { // ��� �ؽ�Ʈ �ʵ� �ʱ�ȭ
		MC.inputRoomName.setText("");
		MC.inputRID.setText("");
	}
	
	void personalRoom() {
		makeConnection();
		String sql = "";
		sql = "SELECT * FROM chat_room WHERE host_id LIKE'" + nowCustomer +"' OR user_id LIKE'%" + nowCustomer + "%'" ;
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
	
	void friendList() {
		makeConnection();
		String sql =  "";
		sql = "SELECT * FROM friends WHERE user_id LIKE'" + nowCustomer + "'";
		try {
			rs = stmt.executeQuery(sql);
			FM.friendTM.setNumRows(0);	// ���̺� �ʱ�ȭ
			while (rs.next()) {
				FM.friendTM.addRow(	// ���� ä�ù� ��� �ҷ�����
						new Object[] { rs.getString("friend_nickname"), rs.getString("friend_id")});
			}
		} catch (SQLException sqle) {
			System.out.println("getData: SQL Error");
		}
		disConnection();
	}
	
	void accountList() {
		makeConnection();
		String sql =  "";
		sql = "SELECT * FROM customer";
		try {
			rs = stmt.executeQuery(sql);
			MC.accountTM.setNumRows(0);	// ���̺� �ʱ�ȭ
			while (rs.next()) {
				MC.accountTM.addRow(	// ���� ��� �ҷ�����
						new Object[] { rs.getString("login_id"), rs.getString("user_id"), rs.getString("nickname")});
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
				dto.setNickName(nowNickName);
			} else {
				dto.setCommand(Info.SEND);
				dto.setMessage(msg);
				dto.setNickName(nowNickName);
			}
			writer.writeObject(dto);
			writer.flush();	//�Է�
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
					MC.ta.append(dto.getMessage()+"\n");
					
					int pos=MC.ta.getText().length();
					MC.ta.setCaretPosition(pos);
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
		String serverIP = "192.168.0.3";  //�⺻������ ������ ���� �ԷµǾ� ���� �� //�б� �����Ƿ� ����
		try{
			socket = new Socket(serverIP,MRM.room_port);	//ä�ù渶�� ��Ʈ �ٸ��� �ؾߵ�
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
	public ChatServerObject(int port){
		try{
			serverSocket= new ServerSocket (port);		//���� ��Ʈ ����
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
		String nickName;
		try{
			while(true){
				dto=(InfoDTO)reader.readObject();
				nickName=dto.getNickName();
				if(dto.getCommand()==Info.EXIT){	//����� ���� ����
					InfoDTO sendDto = new InfoDTO();
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
			}

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
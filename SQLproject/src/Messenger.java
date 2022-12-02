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
	String RID;		//������ RID
	String UID;		//������ UID
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
	JPanel input = new JPanel();	//info���̺�� �ؽ�Ʈ �ʵ� 2���� �� �г�
	JPanel login = new JPanel();	//input�гΰ� login��ư�� �� �г�
	JPanel otherBtn = new JPanel();	//ȸ������ ��ư�� ��й�ȣ ã�� ��ư�� �� �г�
	GridLayout grid = new GridLayout(2,2,5,5);
	
	public MessengerLogin() {
		setTitle("�α���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(340,140);
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
	JButton confirm = new JButton("Ȯ��");
	JButton signUp = new JButton("���");
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

class MessengerChat extends JFrame{
	String roomList[] = {"ä�ù� �̸�","RID"};
	JMenuBar mb = new JMenuBar();	//�޴�
	JMenu settingMenu = new JMenu("����");
	JMenuItem item[] = new JMenuItem[3];
	
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
	JLabel inviteLabel = new JLabel("�ʴ� UID");
	JTextField inviteTF = new JTextField(10);
	JButton invite = new JButton("�ʴ�");
	JPanel invitePanel = new JPanel();
	JScrollPane taScrollPane = new JScrollPane();
	JTextArea ta = new JTextArea();
	JTextField tf = new JTextField(10);
	
	String account[] = {"customer_id","UID","nickname"};
	DefaultTableModel accountTM = new DefaultTableModel(account,0);
	JPanel adminPanel = new JPanel();
	JTable accountTable = new JTable(accountTM);
	JButton deleteAccount = new JButton("����");
	
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
		item[0] = new JMenuItem("�α׾ƿ�");
		item[1] = new JMenuItem("������ ���");
		item[2] = new JMenuItem("����");
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
		taScrollPane.add(ta);
		
		adminPanel.add(new JScrollPane(accountTable));
		adminPanel.add(deleteAccount,BorderLayout.SOUTH);
		
		pane.add("ä�ù� ����", personalChatRoom);
		pane.add("���� ä�ù�", openChatRoom);
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
	
	public Administrator() {
		setTitle("������ ��ȣ�� �Է����ּ���");
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
				MC.pane.add("���� ä�ù�", MC.nowChatRoom);
			}
			
			String cmd = e.getActionCommand();
			switch (cmd) {
				case "�α׾ƿ�" : {
					MC.dispose();
					ML = new MessengerLogin();
					for(int i = 0; i<3; i++)
						ML.btn[i].addActionListener(new LoginBtn());
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
	
	class enterAdmin implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Admin.dispose();
			MC.pane.add("���� ����", MC.adminPanel);
		}
	}

	public static void main(String[] args) {
		new Messenger();
	}
}

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
	JLabel info[] = new JLabel[3];
	JTextField tf[] = new JTextField[3];
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
		info[2] = new JLabel("��й�ȣ ���Է�");
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
	JMenuBar mb = new JMenuBar();	//�޴�
	JMenu settingMenu = new JMenu("����");
	JMenuItem item[] = new JMenuItem[3];
	
	JTabbedPane pane = new JTabbedPane();	//��
	
	JLabel chatID = new JLabel("CID");	//ä��â ���̵�� ���� (���ã�� �߰��ʿ�)
	JTextField inputCID = new JTextField(10);
	JButton enter = new JButton("����");
	JPanel chatSelect = new JPanel();
	
	JPanel personalChatRoom = new JPanel();	//ä�ù� ���ã�� ���̺�� �����
	
	JPanel openChatRoom = new JPanel();	//����ä�ù� ��� ���̺�� �����
	
	JPanel nowChatRoom = new JPanel();	//���� ���� ä�ù� ǥ��
	JLabel noneRoom = new JLabel("���� ä�ù��� �����ϴ�.");
	
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
		item[0] = new JMenuItem("�α׾ƿ�");
		item[1] = new JMenuItem("������ ���");
		item[2] = new JMenuItem("����");
	}
	void setComponent() {
		chatID.setHorizontalAlignment(SwingConstants.CENTER);
	}
	void addComponent() {
		for(int i = 0; i<item.length; i++) {	//�޴� ���� �߰�
			settingMenu.add(item[i]);
			if(i < item.length - 1)
				settingMenu.addSeparator();
		}
		mb.add(settingMenu);
		setJMenuBar(mb);		//�����ӿ� �޴��� ���̱�
		
		chatSelect.add(chatID);
		chatSelect.add(inputCID);
		chatSelect.add(enter);
		add(chatSelect,BorderLayout.NORTH);
		
		nowChatRoom.add(noneRoom);
		
		pane.add("ä�ù� ����", personalChatRoom);
		pane.add("���� ä�ù�", openChatRoom);
		pane.add("���� ä�ù�", nowChatRoom);
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

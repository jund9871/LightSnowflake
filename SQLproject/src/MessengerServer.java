
public class MessengerServer {
	ChatServerObject CSO;

	public MessengerServer() {
		CSO = new ChatServerObject(9500);
	}

	public static void main(String[] args) {
		new MessengerServer();
	}

}
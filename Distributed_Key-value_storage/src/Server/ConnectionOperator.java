package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import Usables.InfoHelp;
import Usables.LinkInfomation;

public class ConnectionOperator extends Thread {
	private final Socket connection;
	private final ServerData data;

	ConnectionOperator(Socket connection, ServerData data) {
		this.data = data;
		this.connection = connection;
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

			String inputLine = InfoHelp.readFromInput(in);
			String[] message = inputLine.split(":");
			System.out.println("Received: " + inputLine);
			
			// SET Method
			if (message[0].equals("SET")) {
				data.getKeyMap().put(message[2], message[3]);
				System.out.println("Set: " + data.getKeyMap().get(message[2]));
				InfoHelp.writeToOutput(out,
						"RES:" + System.currentTimeMillis() % 100000 + ":" + message[2] + ":" + message[3]);
				System.out.println("Response sent");
				
			// GET Method
			} else if (message[0].equals("GET")) {
				if (data.getKeyMap().get(message[2]) != null) {
					InfoHelp.writeToOutput(out, "RES:" + System.currentTimeMillis() % 100000 + ":" + message[2]
							+ ":" + data.getKeyMap().get(message[2]));
				} else {
					boolean foundKey = false;
					for (LinkInfomation neighbor : data.getNeighborAddresses()) {
						String response = requestAnswer(neighbor.getIp(), neighbor.getPort(), message[0] + ":" + message[1] + ":" + message[2]);
						String[] responseMessage = response.split(":");
						if (responseMessage[0].equals("RES")) {
							foundKey = true;
							InfoHelp.writeToOutput(out, response);
							break;
						}
					}
					if (!foundKey) {
						System.out.println("Key not found.");
						InfoHelp.writeToOutput(out, "RES:" + System.currentTimeMillis() % 100000 + ":" + message[2]
								+ ":null");
					}
				}
			} else {
				System.out.println(message[0] + " is a wrong package type. Only SET or GET packages are accepted.");
			}
			// Write code here
		} catch (IOException e) {
			System.err.println("Connection with " + connection + " has been terminated due to an error");
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Connection with " + connection + " has been closed");
		}
	}

	private static String requestAnswer(String ip, int port, String messageToSend) throws IOException {
		Socket connectionToOtherNode = null;
		try {
			connectionToOtherNode = new Socket(ip, port);

			BufferedReader in = new BufferedReader(new InputStreamReader(connectionToOtherNode.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connectionToOtherNode.getOutputStream()));

			InfoHelp.writeToOutput(out, messageToSend);
			return InfoHelp.readFromInput(in);
		} finally {
			if (connectionToOtherNode != null)
				connectionToOtherNode.close();
		}
}
}

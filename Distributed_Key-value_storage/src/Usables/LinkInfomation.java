package Usables;

public class LinkInfomation {
	private String IP = "";
	private int port = 0;
	
	public LinkInfomation(String ip, int port) {
		this.IP = ip;
		this.port = port;
	}
	
	public String getIp() {
		return this.IP;
	}
	
	public int getPort() {
		return this.port;
}
}

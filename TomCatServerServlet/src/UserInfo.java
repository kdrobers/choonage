
public class UserInfo {
	protected String ip_addr;
	protected String title;
	
	public UserInfo(String ip_addr, String title) {
		this.ip_addr = ip_addr;
		this.title = title;
	}
	
	public String getIP() {
		return this.ip_addr;
	}
	
	
	
	@Override
	public String toString() {
		return "{\"address\":\"" + ip_addr + "\",\"title\":\"" + title + "\"}";
	}
}

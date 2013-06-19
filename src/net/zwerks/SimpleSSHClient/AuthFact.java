/**
 * 
 */
package net.zwerks.SimpleSSHClient;

/**
 * @author irvin
 *
 */
public class AuthFact {

	/**
	 * 
	 */
	private String uname;
	private String secret;	//Password
	//Public Key?
	
	public AuthFact(String usrname, String pass) {
		this.uname = usrname;
		this.secret = pass;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public String getUname(){
		return this.uname;
	}
	
	public String getPass(){
		return this.secret;
	}
	
	public void setUname(String usrname){
		this.uname = usrname;
	}
	
	public void setPass(String pass){
		this.secret = pass;
	}

}

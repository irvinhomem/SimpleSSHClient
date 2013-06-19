/**
 * 
 */
package net.zwerks.SimpleSSHClient;

import java.util.ArrayList;

import net.schmizz.sshj.*;

/**
 * @author irvin
 *
 */
public class OpenConnections {

	/**
	 * 
	 */
	private int sshCliID;
	private AuthFact myAuthFact;
	private SSHClient sshCli;
	
	public OpenConnections( SSHClient mySshCli, int myID, AuthFact credentials) {
		//
		sshCli = mySshCli;
		sshCliID = myID;
		myAuthFact = credentials;
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public SSHClient getSSHClient(){
		return this.sshCli;
	}

}

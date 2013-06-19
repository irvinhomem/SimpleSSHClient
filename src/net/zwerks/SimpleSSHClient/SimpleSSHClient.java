/**
 * 
 */
package net.zwerks.SimpleSSHClient;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import net.schmizz.*;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.xfer.scp.SCPDownloadClient;
import net.schmizz.sshj.xfer.scp.SCPUploadClient;
import net.schmizz.sshj.xfer.scp.SCPFileTransfer;
import net.schmizz.sshj.xfer.FileSystemFile;
import net.schmizz.sshj.xfer.LocalDestFile;
import net.schmizz.sshj.xfer.LocalFileFilter;
import net.schmizz.sshj.xfer.LocalSourceFile;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import net.zwerks.SimpleSSHClient.OpenConnections;
import net.zwerks.SimpleSSHClient.FileSystemSourceFile;

/**
 * @author irvin
 *Testing sshj library
 */
public class SimpleSSHClient {

	/**
	 * 
	 */
	private ArrayList<OpenConnections> openConns;
	private File myKnownHostsFile;
	//private SSHServer sshs;
	
	public SimpleSSHClient(File theKnownHostsFile) {
		// TODO Auto-generated constructor stub
		this.myKnownHostsFile = theKnownHostsFile;
		this.openConns = new ArrayList<OpenConnections>();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String theHostName = "192.168.1...."; //Put an IP here
		int thePort = 22;
		String myUname = ""; //Put a username here ... e.g. root, user1, etc
		String myPass = "";
		AuthFact theCredentials = new AuthFact(myUname, myPass);
		
		String theRemotePath = "/linuxrc";
		String theHDFSLocalPath = System.getProperty("user.dir")+"\\DumpDirectory\\";
		
		String knownHostsPath = System.getProperty("user.dir")+"\\known_hosts.txt";
		File knownHostsFile = new File(knownHostsPath);
		
		System.out.println(theHDFSLocalPath);
		System.out.println(knownHostsPath);
		
		SimpleSSHClient mySimpleSSHClient = new SimpleSSHClient(knownHostsFile);
		//SSHClient sshc = mySimpleSSHClient.InitiateConn(theHostName, thePort, theCredentials);
		mySimpleSSHClient.InitiateConn(theHostName, thePort, theCredentials);
		
		SSHClient sshc = mySimpleSSHClient.getCurrSSHClient();
		//mySimpleSSHClient.callSCPDownloader(sshc, theRemotePath, theHDFSLocalPath);
		//++//mySimpleSSHClient.callSCPDownloader(sshc, theRemotePath, theHDFSLocalPath);
		
		//String myComm ="ping -c 1 google.com";
		//String myComm ="ifconfig";
		//String myComm ="ls -al";
		//String myComm = "dd";
		//String myComm = "netcat";
		//String myComm ="ls -al /etc/";
		String myComm ="gzip";
		mySimpleSSHClient.callSSHExecutor(sshc, myComm);
		
	}
	
	public SSHClient InitiateConn(String myHostName, int myPort, AuthFact myCredentials){
		SSHClient mySSHClient = new SSHClient();
		
		try{
			mySSHClient.useCompression();
			mySSHClient.loadKnownHosts(getKnownHostsFile());
			mySSHClient.addHostKeyVerifier("d3:52..........");	//Add certificate fingerprint here
			//mySSHClient.addHostKeyVerifier("public-key-fingerprint");
			//mySSHClient.addHostKeyVerifier(new PromiscuousVerifier());
			mySSHClient.connect(myHostName, myPort);
			mySSHClient.authPassword(myCredentials.getUname(), myCredentials.getPass());
		
		} catch (IOException ex){
			//Catch IO Exception, Transport Exception
			System.err.println("Caught Exception: " + ex.getMessage());
		}
		
		OpenConnections newOpenConn = new OpenConnections(mySSHClient, this.openConns.size()+1, myCredentials);
		boolean myflag = this.openConns.add(newOpenConn);
		//System.out.println(myflag);
		
		return mySSHClient;
	}
	
	public void callSCPDownloader(SSHClient mySSHc, String myRemotePath, String myHDFSLocalPath){
		SCPDownloader mySCPdldr = new SCPDownloader(mySSHc, myRemotePath, myHDFSLocalPath);
		mySCPdldr.doSCPDownload(myRemotePath, myHDFSLocalPath);
	}
	
	public void doSCPUpload(){
		
	}
	
	public void callSSHExecutor(SSHClient mySSHc, String myCommand){
		SSHExecutor mySSHExecutor = new SSHExecutor(mySSHc);
		mySSHExecutor.executeCommand(myCommand);
	}
	
	public SSHClient getCurrSSHClient(){
		//System.out.println(this.openConns.size());
		return this.openConns.get(0).getSSHClient();
	}
	
	
	public File getKnownHostsFile(){
		return this.myKnownHostsFile;
	}
	

}

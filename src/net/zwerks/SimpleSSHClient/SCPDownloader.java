/**
 * 
 */
package net.zwerks.SimpleSSHClient;

import java.io.IOException;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.xfer.FileSystemFile;
import net.schmizz.sshj.xfer.scp.SCPFileTransfer;

/**
 * @author irvin
 *
 */
public class SCPDownloader {

	/**
	 * 
	 */
	private String theRemotePath;
	private String theHDFSLocalPath;
	private SSHClient theSSHclient;
	
	public SCPDownloader(SSHClient mySSHc, String myRemotePath, String myHDFSLocalPath) {
		this.theRemotePath = myRemotePath;
		this.theHDFSLocalPath = myHDFSLocalPath;
		this.theSSHclient = mySSHc;
	}

	public void doSCPDownload(String remotePath, String hdfsLocalPath){
		try {
			SCPFileTransfer transfert = theSSHclient.newSCPFileTransfer();
			//transfert.upload(new FileSystemSourceFile(fs, hdfsPathSource), basePath);
			//transfert.download(basePath, new FileSystemSourceFile(fs, hdfsPathSource));
			//FileSystem fs;
			//LocalDestFile myDestinationFile = new Lo;
			FileSystemFile myLocalFile = new FileSystemFile(hdfsLocalPath);
			transfert.download(remotePath, myLocalFile);
 
			theSSHclient.close();
		} catch (IOException ioe){
			System.err.println("Caught Exception: " + ioe.getMessage());
		}
		
	}
}

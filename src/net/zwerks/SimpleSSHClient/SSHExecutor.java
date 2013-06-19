/**
 * 
 */
package net.zwerks.SimpleSSHClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.fs.Trash;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.TransportException;

/**
 * @author irvin
 *
 */
public class SSHExecutor {
	/**
	 * 
	 */
	private SSHClient theSSHClient;
	
	public SSHExecutor(SSHClient mySSHClient) {
		this.theSSHClient = mySSHClient;
		
	}
	
	public void executeCommand(String theCommand){
		try {
            final Session session = this.theSSHClient.startSession();
            try {
                final Command cmd = session.exec(theCommand);
                System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
                cmd.join(5, TimeUnit.SECONDS);
                System.out.println("\n** exit status: " + cmd.getExitStatus());
            } catch(IOException ioe){
            	System.err.println("Caught Exception: " + ioe.getMessage());
            } finally {
                session.close();
            }
        } catch(TransportException | ConnectionException ex){
        	System.err.println("Caught Exception: " + ex.getMessage());
        } 
		finally {
			try{
				this.theSSHClient.disconnect();
			}catch (IOException ioe){
				System.err.println("Caught Exception: " + ioe.getMessage());
			}
        }
	}



}

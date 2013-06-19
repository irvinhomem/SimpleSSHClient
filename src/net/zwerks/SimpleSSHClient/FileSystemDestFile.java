/**
 * 
 */
package net.zwerks.SimpleSSHClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import net.schmizz.sshj.xfer.LocalDestFile;
import net.schmizz.sshj.xfer.LocalFileFilter;
import net.schmizz.sshj.xfer.LocalSourceFile;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * @author irvin
 *
 */
public class FileSystemDestFile implements LocalDestFile {

	/**
	 * 
	 */
	private FileSystem fs;
	private FileStatus rootStatus;
	
	public FileSystemDestFile(FileSystem fs, String path) throws IOException{
		this.fs = fs;
		rootStatus = fs.getFileStatus(new Path(path));
	}
	
	public FileSystemDestFile(FileSystem fs, FileStatus rootStatus) throws IOException {
		this.fs = fs;
		this.rootStatus = rootStatus;
	}


	@Override
	public void setPermissions(int arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setLastModifiedTime(long arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setLastAccessedTime(long arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public LocalDestFile getTargetFile(String arg0) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public LocalDestFile getTargetDirectory(String arg0) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public OutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public LocalDestFile getChild(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	

}

/**
 * 
 */
package net.zwerks.SimpleSSHClient;

/**
 * @author irvin
 *
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import net.schmizz.sshj.xfer.LocalFileFilter;
import net.schmizz.sshj.xfer.LocalSourceFile;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class FileSystemSourceFile implements LocalSourceFile {
	
	private FileSystem fs;
	private FileStatus rootStatus;

	public FileSystemSourceFile(FileSystem fs, String path) throws IOException {
		this.fs = fs;
		rootStatus = fs.getFileStatus(new Path(path));
	}

	public FileSystemSourceFile(FileSystem fs, FileStatus rootStatus) throws IOException {
		this.fs = fs;
		this.rootStatus = rootStatus;
	}
	
	public String getName() {
		return rootStatus.getPath().getName();
	}
	
	public long getLength() {
		return rootStatus.getLen();
	}
	
	public InputStream getInputStream() throws IOException {
		return fs.open(rootStatus.getPath());
	}
	
	public int getPermissions() throws IOException {
		return rootStatus.getPermission().toShort();
	}
	
	public boolean isFile() {
		//return rootStatus.isFile();
		return !rootStatus.isDir();
	}
	
	public boolean isDirectory() {
		//return rootStatus.isDirectory();
		return rootStatus.isDir();
	}
	
	public Iterable<? extends LocalSourceFile> getChildren(LocalFileFilter filter) throws IOException {
	
		FileStatus[] shopStatuses = fs.listStatus(rootStatus.getPath());
		final List<FileSystemSourceFile> children = new LinkedList<FileSystemSourceFile>();
	  
		for (FileStatus f : shopStatuses) {
			children.add(new FileSystemSourceFile(fs, f));
		}
		return children;
	}
	
	public boolean providesAtimeMtime() {
		return true;
	}
	
	public long getLastAccessTime() throws IOException {
		return rootStatus.getAccessTime() / 1000;
	}
	
	public long getLastModifiedTime() throws IOException {
		return rootStatus.getModificationTime() / 1000;
	}
	
}


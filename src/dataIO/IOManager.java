package dataIO;

import java.io.*;

public class IOManager {
    public static final String diskpath=System.getProperty("user.dir") + "\\disk.dat";
    public static final int fatblocknum=2;
    public static final int rootdirblock=2;
    public static final int blocksize=64;
    private Byte[] readbuf;
    private Byte[] writebuf;
    
    private File diskdir;
    public IOManager() {
    	this.diskdir=new File(diskpath);
    	this.readbuf= new Byte[64];
    	this.writebuf=new Byte[64];
    	initFile();
    	
    }
    private void initFile() {
    	if(!this.diskdir.exists()) {
    		
    	}
    }
    
}

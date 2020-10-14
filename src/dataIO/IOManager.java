package dataIO;

import java.io.*;

public class IOManager {
    public static final String diskpath=System.getProperty("user.dir") + "\\disk.dat";
    public static final int fatblocknum=2;
    public static final int rootdirblock=2;
    public static final int blocksize=64;
    public static final int disksize=128;
    private byte[] readbuf;
    private byte[] writebuf;
    
    private File diskdir;
    public IOManager() {
    	this.diskdir=new File(diskpath);
    	this.readbuf= new byte[blocksize];//
    	this.writebuf=new byte[blocksize];
    	//initFile();
    	
    }
    private void initDisk() {
    	try {
    		this.writebuf=new byte[blocksize];
			FileOutputStream out=new FileOutputStream(this.diskdir);
			for(int i=0;i<disksize;i++) {
				out.write(writebuf, 0, blocksize);
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public byte[] readoneblock(int blocknum) {
    	this.readbuf= new byte[blocksize];
    	try {
			RandomAccessFile temp=new RandomAccessFile(this.diskdir,"r");
			
				temp.skipBytes(blocksize*blocknum);
				temp.read(readbuf,0,blocksize);
				temp.close();
				return (byte[])this.readbuf.clone();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    
    public void writeoneblock(int blocknum,byte[] datas) {
    	this.writebuf=(byte[])datas.clone();
    	try {
			RandomAccessFile temp=new RandomAccessFile(this.diskdir,"rw");
			
				temp.skipBytes(blocksize*blocknum);
				temp.write(writebuf,0,this.writebuf.length);
				temp.close();
				return ;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ;
    }
    
}

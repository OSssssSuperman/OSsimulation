package dataIO;

import java.io.*;

public class IOManager {
    public static final String diskpath=System.getProperty("user.dir") + "\\disk.dat";
    public static final int fatblocknum=2;
    public static final int rootdirblock=2;
    public static final int blocksize=64;
    public static final int disksize=128;
    private  static byte[] readbuf;
    private static byte[] writebuf;
    
    private static File diskdir;
    public static File getDiskdir() {
		return diskdir;
	}
	public static void setDiskdir(File diskdir) {
		IOManager.diskdir = diskdir;
	}
	public IOManager() {
    	IOManager.diskdir=new File(diskpath);
    	IOManager.readbuf= new byte[blocksize];//
    	IOManager.writebuf=new byte[blocksize];
    	//initFile();
    	
    }
    private static void initDisk() {
    	try {
    		IOManager.writebuf=new byte[blocksize];
			FileOutputStream out=new FileOutputStream(IOManager.diskdir);
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
    
    public static byte[] readoneblock(int blocknum) {
    	IOManager.readbuf= new byte[blocksize];
    	try {
			RandomAccessFile temp=new RandomAccessFile(IOManager.diskdir,"r");
			
				temp.skipBytes(blocksize*blocknum);
				temp.read(readbuf,0,blocksize);
				temp.close();
				return (byte[])IOManager.readbuf.clone();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    
    public static void writeoneblock(int blocknum,byte[] datas) {
    	IOManager.writebuf=(byte[])datas.clone();
    	try {
			RandomAccessFile temp=new RandomAccessFile(IOManager.diskdir,"rw");
			
				temp.skipBytes(blocksize*blocknum);
				temp.write(writebuf,0,IOManager.writebuf.length);
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
    
    public static int bytetoint(byte temp) {
    	return temp & 0xFF;
    }
    
    
}

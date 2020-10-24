package fileSystem;


import java.util.ArrayList;
import java.util.Arrays;

import dataIO.*;
import fat.Fat;
public class MyFile extends FileModel {
    private String path;
    private String ext;
    private final String seperator="/";
    private final String point=".";
    private byte[] contents;
    public MyFile(String fpath) {
    	if(fpath.startsWith("/"))fpath=fpath.substring(1);
    	if(fpath.endsWith("seperator"))fpath=fpath.substring(0, fpath.length()-1);
    	this.path=fpath;
    	this.ext=this.getExt();
    	this.contents=this.foundblock(this.path);
    }
    public MyFile(String parent,byte[] contents) {
    	this(parent+MyFile.getnamefromcontents(contents));
    }
    public static String getnamefromcontents(byte[] con) {
    	if(Byte.toString(con[3]).equals(" ")||Byte.toString(con[3])=="")
    	return Byte.toString(con[0])+Byte.toString(con[1])+Byte.toString(con[2]);
    	return Byte.toString(con[0])+Byte.toString(con[1])+Byte.toString(con[2])+"."+Byte.toString(con[3]);
    }
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		if(this.path.lastIndexOf(seperator)>=0) {
			return this.path.substring(this.path.lastIndexOf(seperator));
		}
		return this.path;
	}
	
	public String getNamewithoutext() {
		if(this.path.lastIndexOf(seperator)>=0) {
			return this.path.substring(this.path.lastIndexOf(seperator)).split(point)[0];
		}
		return this.path.split(point)[0];
	}
    public byte[] getconFromString(String filepath) {
    	String[] split;
    	String name;
    	byte[] con=new byte[8];
    	if(filepath.lastIndexOf("/")>=0) 
    	     name=filepath.substring(filepath.lastIndexOf(seperator)+1);
    	else 
    		 name=filepath;
    	split=name.split(point);
    	
    	if(split[0].length()>3)return null;
    	
    	
    	
    	
    }
	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return this.path;
	}

	@Override
	public String getAttribute() {
		// TODO Auto-generated method stub
		if(this.contents==null) return null;
		return Byte.toString(this.contents[4]);
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		if(this.contents==null) return -1;
		int i=0;
		i+=(this.contents[6]&0xFF)<<8;
		i+=(this.contents[7]&0xff);
		return 0;
	}

	@Override
	public int getStartBlock() {
		// TODO Auto-generated method stub
		if(this.contents==null)return -1;
		return Byte.toUnsignedInt(this.contents[5]);
	}

	@Override
	public boolean isDir() {
		// TODO Auto-generated method stub
		if(this.contents==null)return false;
		if(Byte.toString(this.contents[3]).equals(" "))return true;
		return false;
	}

	@Override
	public boolean exist() {
		// TODO Auto-generated method stub
		if(this.contents==null)return false;
		return true;
	}

	@Override
	public MyFile getParent() {
		// TODO Auto-generated method stub
		if(this.path.lastIndexOf(seperator)<0)return null;
		return new MyFile(this.path.substring(0,this.path.lastIndexOf(seperator)));
	}

	@Override
	public String getExt() {
		// TODO Auto-generated method stub
		if(this.path.contains(point))return this.path.substring(this.path.lastIndexOf(point)+1);
		return "";
	}

	@Override
	public MyFile[] listFile() {
		// TODO Auto-generated method stub
		if(!exist())return null;
		if(!isDir())return null;
		int num=this.getStartBlock();
		int end=this.foundlastblock();
		int endindex=this.foundtailindex();
		ArrayList<MyFile> filelist=new ArrayList<MyFile>();
		while(num!=end) {
			byte[] i=IOManager.readoneblock(num);
			for(int j=0;j<i.length;j+=8) {
				filelist.add(new MyFile(this.path,Arrays.copyOfRange(i, j, j+8)));
			}
		}
		byte[] i=IOManager.readoneblock(num);
		for(int j=0;j<endindex;j+=8) {
			filelist.add(new MyFile(this.path,Arrays.copyOfRange(i, j, j+8)));
		}
		MyFile[] temp=filelist.toArray(new MyFile[filelist.size()]);
		return temp;
	}

	@Override
	public boolean exists() {
		if(this.contents==null)return false;
		return true;
	}

	@Override
	public boolean isDirectory() {
		// TODO Auto-generated method stub
				if(this.contents==null)return false;
				if(Byte.toString(this.contents[3]).equals(" "))return true;
				return false;
	}

	@Override
	public boolean isSystemFile() {
		// TODO Auto-generated method stub
		if(this.contents==null)return false;
		if(Byte.toString(this.contents[4]).equals("s"))return true;
		return false;
	}

	@Override
	public boolean isNormalFile() {
		if(this.contents==null)return false;
		if(Byte.toString(this.contents[4]).equals("w"))return true;
		return false;
	}

	@Override
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		if(this.contents==null)return false;
		if(Byte.toString(this.contents[4]).equals("r"))return true;
		return false;
	}

	@Override
	public boolean createNewFile() {
		// TODO Auto-generated method stub
		if(exist()) {System.out.println("文件已经存在");return false;}
		this.getParent().addcontents(contents);
		this.contents=this.foundblock(this.path);
		return false;
	}

	@Override
	public boolean mkdir() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}
	public byte[] foundblock(String temp) {
    	return foundblocks(temp,2);   //从根目录开始找
    }
    
    private byte[] foundblocks(String temp,int blocknum) {
    	if(blocknum==-1)return null;
    	if(temp.lastIndexOf(seperator)<0)return foundbyte(temp,blocknum); //如果没有‘/’了就返回相应目录项
    	String[] i=temp.split(seperator);
    	temp.substring(i[0].length()+1);
    	return foundblocks(temp,foundnum(i[0],blocknum));//有‘/’就先找到次级目录的位置递归
    }
    
    
    private int foundnum(String temp,int blocknum) {//在当前块以及它的所有后续中
    	if(blocknum==256||blocknum==0||blocknum==1)return -1;
    	byte[] j=IOManager.readoneblock(blocknum);
    	
    	for(int i=0;i<j.length;i+=8) {
    		String fileName="";
    		byte[] nameTemp = null;
    		System.arraycopy(j, i, nameTemp, 0,3 );
    		fileName+=new String(nameTemp);
    		if(fileName.equals(temp))return j[i+5];
    		
    	}
    	return foundnum(temp,Fat.getnextblock(blocknum));
    }
    
    private byte[] foundbyte(String temp,int blocknum) {
    	if(blocknum==256||blocknum==0||blocknum==1)return null;
    	String[] i=temp.split(point);
    	String name=i[0];
    	String ext;
    	if(i.length==1)ext=" ";
    	else ext=i[1];
    	byte[] j=IOManager.readoneblock(blocknum);
    	for(int k=0;k<j.length;k+=8) {
    		String fileName="";
    		byte[] nameTemp = null;
    		System.arraycopy(j, k, nameTemp, 0,3 );
    		fileName+=new String(nameTemp);
    		if(fileName.equals(temp)) {
    			nameTemp=null;
    			System.arraycopy(j, k+3, nameTemp, 0, 1);
    			String ext1=new String(nameTemp);
    			if(ext1.equals(ext)) {
    				byte[] filecontents=null;
    				System.arraycopy(j, k, filecontents, 0, 8);
    				return filecontents;
    			}
    			
    		}
    		
    	}
    	return foundbyte(temp,Fat.getnextblock(blocknum));
    }
    public int foundlastblock () {//找到文件的最后一块
    	int num=this.getStartBlock();
    	while(Fat.getnextblock(num)!=-1) {
    		num=Fat.getnextblock(num);
    	}
    	return num;
    }
    public int foundtailindex() {//找到文件最后一块的第一个未写入字节的位置
    	int num=this.getStartBlock();
    	int length=this.getLength();
    	while(Fat.getnextblock(num)!=-1) {
    		num=Fat.getnextblock(num);
    		length-=64;
    	}
    	return length;
    }
    
    
    public int addcontents(byte[] contents) {
    	return 0;
    }
    
    private void inclength() {
    	
    }
}

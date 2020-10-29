package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Tool;

public class File implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7723410987381496076L;
    
	private String name;
	private String type;
	private int diskNum;
	private String position; 
	private int blockcount;
	private String content;

	private Directory parent;

	
	private double size;
	private String length; 
	private int flag;
	private boolean opened;
	
	public boolean isOpened() {
		return opened;
	}
	public void setOpened(boolean opened) {
		this.opened = opened;
	}
	public File(String name) {
		this.name = name;
		this.setOpened(false);
		
		setNameP();
	}
	
	private transient StringProperty nameP = new SimpleStringProperty();
	public StringProperty getNameP() {
		return nameP;
	}
	public void setNameP() {
		nameP.set(name);
	}
	public StringProperty getDiskNumP() {
		return diskNumP;
	}
	public void setDiskNumP() {
		this.diskNumP.set(String.valueOf(diskNum));
	}
	public StringProperty getPositionP() {
		return positionP;
	}
	public void setPositionP() {
		this.positionP.set(position);
	}
	public StringProperty getBlockcountP() {
		return blockcountP;
	}
	public void setBlockcountP() {
		this.blockcountP.set(String.valueOf(blockcount));;
	}
	public StringProperty getFlagP() {
		return flagP;
	}
	public void setFlagP() {
		this.flagP .set(String.valueOf(flag));;
	}
	private transient StringProperty diskNumP = new SimpleStringProperty();
	private transient StringProperty positionP = new SimpleStringProperty();
	private transient StringProperty blockcountP = new SimpleStringProperty();
	private transient StringProperty flagP = new SimpleStringProperty();
	
	public File(String name, String position, int diskNum, Directory parent) {
		this.name = name;
		this.type = Tool.FILE;
		this.diskNum = diskNum;
		this.blockcount = 1;
		this.content = "";

		this.position = position;
		this.size = Tool.getSize(content.length());
		this.length = size + "B";
		this.parent = parent;
		
		this.setOpened(false);
		
		setNameP();
		setFlagP();
		setDiskNumP();
		setPositionP();
		setBlockcountP();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Directory getParent() {
		return parent;
	}
	public void setParent(Directory parent) {
		this.parent = parent;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		setNameP();
	}
	public int getDiskNum() {
		return diskNum;
	}
	public void setDiskNum(int diskNum) {
		this.diskNum = diskNum;
		setDiskNumP();
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
		setPositionP();
	}
	public int getBlockcount() {
		return blockcount;
	}
	public void setBlockcount(int blockcount) {
		this.blockcount = blockcount;
		setBlockcountP();
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
		setFlagP();
	}
	
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    	s.defaultReadObject();
    	nameP = new SimpleStringProperty(name);
    	flagP = new SimpleStringProperty(flag == Tool.READFLAG ? "只读" : "读写");
    	diskNumP = new SimpleStringProperty(String.valueOf(type));
    	positionP = new SimpleStringProperty(position);
    	blockcountP = new SimpleStringProperty(String.valueOf(blockcount));
    }

	@Override
	public String toString() {
		return name;
	}
	
	public void setNameP(StringProperty nameP) {
		this.nameP = nameP;
	}
	public void setDiskNumP(StringProperty diskNumP) {//说不定有用
		this.diskNumP = diskNumP;
	}
	public void setPositionP(StringProperty PositionP) {
		this.positionP = positionP;
	}
	public void setBlockcountP(StringProperty blockcountP) {
		this.blockcountP = blockcountP;
	}
	public void setFlagP(StringProperty flagP) {
		this.flagP = flagP;
	}
	
	
	
	
	
}

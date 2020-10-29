package model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.Path;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class Directory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
      
	private String name;
	private String type;
	private int diskNum;
	private String position; 
	


	private Directory parent;

	private double size;
	private String length; 


	
	private List<Object> children;
//	private Path path;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getDiskNum() {
		return diskNum;
	}
	public void setDiskNum(int diskNum) {
		this.diskNum = diskNum;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
/*	public int getBlockcount() {
		return blockcount;
	}
	public void setBlockcount(int blockcount) {
		this.blockcount = blockcount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}*/
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
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	/*public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public boolean isOpened() {
		return opened;
	}
	public void setOpened(boolean opened) {
		this.opened = opened;
	}*/
	public List<Object> getChildren() {
		return children;
	}
	public void setChildren(List<Object> children) {
		this.children = children;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	public void setNameP(StringProperty nameP) {
		this.nameP = nameP;
	}
	private Path path;
	
	private transient StringProperty nameP = new SimpleStringProperty();
	public StringProperty getNameP() {
		return nameP;
	}
	public void setNameP() {
		nameP.set(name);
	}
	public Directory(String name) {
		this.name=name;
		setNameP();
	}
	public Directory(String name, String position, int diskNum, Directory parent) {
		this.name = name;
		this.type = Tool.DIRECTORY;
		this.diskNum = diskNum;

		this.setChildren(new ArrayList<>());
		this.position = position;
		this.size =0;
		this.length = size + "B";
		this.parent = parent;
		

		
		setNameP();
	}
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    	s.defaultReadObject();
    	nameP = new SimpleStringProperty(name);
    }
	public boolean hasParent() {
		return (parent == null) ? false : true;
	}


	public void addChildren(Object child) {
		this.children.add(child);
	}

	public void removeChildren(Object child) {
		this.children.remove(child);
	}

	public boolean hasChild() {
		return children.isEmpty() ? false : true;
	}


}

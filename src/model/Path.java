package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Path implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Path parent;
	private List<Path> children;
	
	public String getName() {
		return this.name;
	}
    
	public void setName(String name) {
		this.name=name;
	}

	public Path getParent() {
		return parent;
	}

	public void setParent(Path parent) {
		this.parent = parent;
	}
    
	public Path(String name,Path parent) {
		this.name=name;
		this.setParent(parent);
		this.children=new ArrayList<Path>();
		
	}
	public List<Path> getChildren() {
		return children;
	}

	public void setChildren(List<Path> children) {
		this.children = children;
	}
	
	public boolean hasParent() {
		if(this.parent!=null)return true;
		return false;
	}
	
	public boolean haschildren() {
		if(!this.children.isEmpty())return true;
		return false;
	}
	
	public String toString() {
		return "Path :pathName=" + name ;
	}
	
	
	public void addChildren(Path child) {
		this.children.add(child);
	}

	public void removeChildren(Path child) {
		this.children.remove(child);
	}

}

package model;
import model.*;
import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
public class DiskBlock implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
     
	private int num;
	private int next;
	private String type;
	private Object obj;
	private boolean first;
	
	private transient StringProperty numP= new SimpleStringProperty();
	private transient StringProperty nextP= new SimpleStringProperty();
	private transient StringProperty typeP= new SimpleStringProperty();
	public StringProperty getNumP() {
		return numP;
	}
	public void setNumP() {
		this.numP.set(String.valueOf(num));
	}
	private transient StringProperty objP= new SimpleStringProperty();;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
		setNumP();
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
		setNumP();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		setTypeP();
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
		if (obj instanceof File) {
			this.objP.bind(((File)obj).getNameP());
		} else if (obj instanceof Directory){
			this.objP.bind(((Directory)obj).getNameP());
		} else {
			this.objP.unbind();
			setObjP();
		}
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public StringProperty getNextP() {
		return nextP;
	}
	public void setNextP() {
		nextP.set(String.valueOf(next));
	}
	public StringProperty getTypeP() {
		return typeP;
	}
	public void setTypeP() {
		this.typeP.set(type);
	}
	public StringProperty getObjP() {
		return objP;
	}
	public void setObjP() {
		this.objP.set(obj==null ? "":obj.toString());
	}
	
	public DiskBlock(int num, int next, String type, Object obj) {
		super();
		this.num=num;
		this.next=next;
		this.type=type;this.first=false;
		this.obj=obj;
		
		setNumP();
		setTypeP();
		setNextP();
		setObjP();
	}
	
	public void setBlock(boolean first,int next,String type,Object obj) {
		setFirst(first);
		setNext(next);
		setType(type);
		setObj(obj);
		
	}
	
	public void clear() {
		setNext(0);
		setType(Tool.EMPTY);
		setObj(null);
		setFirst(false);
	}
	
	public boolean unused() {
		return this.next==0;
	}
	
	public boolean isEnd() {
		return this.next==-1;
	}
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    	s.defaultReadObject();
    	numP = new SimpleStringProperty(String.valueOf(num));
    	nextP= new SimpleStringProperty(String.valueOf(next));
    	typeP = new SimpleStringProperty(String.valueOf(type));
    	objP = new SimpleStringProperty(obj==null ? "":obj.toString());
    	setObj(obj);
    }
	
	public String toString() {
		Object obj = getObj();
		if (obj instanceof File) {
			return ((File)obj).toString();
		} else {
			return ((Directory)obj).toString();
		}
	}
	
	public StringProperty numPProperty() {
		return numP;
	}	
	public StringProperty nextPProperty() {
		return nextP;
	}
	public StringProperty typePProperty() {
		return typeP;
	}
	public StringProperty objPProperty() {
		return objP;
		
	}

}

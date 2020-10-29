package model;
import model.File;
import model.Directory;
import java.util.List;

public class Tool {
	
	public static final String ICO = "res/ico.png";
	public static final String DIRECTORY_IMG = "res/directory.png";
	public static final String FILE_IMG = "res/file.png";
	public static final String DISK_IMG = "res/disk.png";
	public static final String TREE_NODE_IMG = "res/node.png";
	public static final String FORWARD_IMG = "res/turnnext.png";
	public static final String BACK_IMG = "res/turnback.png";
	public static final String SAVE_IMG = "res/save.png";
	public static final String CLOSE_IMG = "res/close.png";
	
	public static final int END = 255;
	public static final int ERROR = -1;
	public static final int FREE = 0;
	
	public static final String DISK = "磁盘";
	public static final String DIRECTORY = "文件夹";
	public static final String FILE = "文件";
	public static final String EMPTY = "空";
			
	public static final int READFLAG = 0;
	public static final int WRITEFLAG = 1;	
	
	
	public static int blocksCount(int length){
		if (length <= 64){
			return 1;
		} else {
			int n = 0;
			if (length % 64 == 0){
				n = length / 64;
			} else {
				n = length / 64;
				n++;
			}
			return n;
		}
	}
	
	public static double getSize(int length) {
		return length;
	}
	

	public static double getDirectorySize(Directory directory) {
		List<Object> children = directory.getChildren();
		double size = 0;
		for (Object child : children) {
			if (child instanceof File) {
				size += ((File)child).getSize();
			} else {
				size += getDirectorySize((Directory)child);
			}			
		}
		return size;
	}
	
}

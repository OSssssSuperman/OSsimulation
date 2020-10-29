package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FAT implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private DiskBlock[] diskBlocks;
	private transient ObservableList<File> openedFiles;
	private Directory root;
	private Path rootPath = new Path("root", null);
	private List<Path> paths;
	
	public FAT() {
		root = new Directory("root", "root", 0, null);
		diskBlocks = new DiskBlock[128];
		diskBlocks[0] = new DiskBlock(0, Tool.END, Tool.DISK, root);
		diskBlocks[0].setFirst(true);
		diskBlocks[1] = new DiskBlock(1, Tool.END, Tool.DISK, root);
		diskBlocks[1].setFirst(true);
		for (int i = 2; i < 128; i++) {
			diskBlocks[i] = new DiskBlock(i, Tool.FREE, Tool.EMPTY, null);
		}
		openedFiles = FXCollections.observableArrayList(new ArrayList<File>());
		paths = new ArrayList<Path>();
		paths.add(rootPath);
		root.setPath(rootPath);
	}

	public DiskBlock[] getDiskBlocks() {
		return diskBlocks;
	}

	public void setDiskBlocks(DiskBlock[] diskBlocks) {
		this.diskBlocks = diskBlocks;
	}

	public ObservableList<File> getOpenedFiles() {
		return openedFiles;
	}

	public void setOpenedFiles(ObservableList<File> openedFiles) {
		this.openedFiles = openedFiles;
	}

	public List<Path> getPaths() {
		return paths;
	}

	public void setPaths(List<Path> paths) {
		this.paths = paths;
	}

	public void addOpenedFile(DiskBlock block) {
		File thisFile = (File) block.getObj();
		openedFiles.add(thisFile);
		thisFile.setOpened(true);
	}

	public void removeOpenedFile(DiskBlock block) {
		File thisFile = (File) block.getObj();
		for (int i = 0; i < openedFiles.size(); i++) {
			if (openedFiles.get(i) == thisFile) {
				openedFiles.remove(i);
				thisFile.setOpened(false);
				break;
			}
		}
	}
	
	/**
	 * 判断指定盘块中的文件是否已打开
	 * @param block
	 * @return
	 */
	public boolean isOpenedFile(DiskBlock block) {
		if (block.getObj() instanceof Directory) {
			return false;
		}
		return ((File) block.getObj()).isOpened();
	}

	/**
	 * 在指定路径下创建文件夹
	 * @param path
	 * @return
	 */
	public int createDirectory(String path) {
		String directoryName = null;
		boolean canName = true;
		int index = 1;
		// 得到文件夹名
		do {
			directoryName = "文件夹";
			canName = true;
			directoryName += index;
			for (int i = 2; i < diskBlocks.length; i++) {
				if (!diskBlocks[i].unused()) {
					if (diskBlocks[i].getType().equals(Tool.DIRECTORY)) {
						Directory directory = (Directory) diskBlocks[i].getObj();
						if (path.equals(directory.getPosition())) {
							if (directoryName.equals(directory.getName())) {
								canName = false;
							}
						}
					}
				}
			}
			index++;
		} while (!canName);
		int index2 = searchEmptyDiskBlock();
		if (index2 == Tool.ERROR) {
			return Tool.ERROR;
		} else {
			Directory parent = getDirectory(path);
			Directory directory = new Directory(directoryName, path, index2, parent);
			if (parent instanceof Directory) {
				parent.addChildren(directory);
			}
			diskBlocks[index2].setBlock( true,Tool.END, Tool.DIRECTORY, directory);
			Path parentP = getPath(path);
			Path thisPath = new Path(path + "\\" + directoryName, parentP);
			if (parentP != null) {
				parentP.addChildren(thisPath);
			}
			paths.add(thisPath);
			directory.setPath(thisPath);
		}
		return index2;
	}

	/**
	 * 在指定路径下创建文件
	 * @param path
	 * @return
	 */
	public int createFile(String path) {
		String fileName = null;
		boolean canName = true;
		int index = 1;
		// 得到文件名
		do {
			fileName = "文件";
			canName = true;
			fileName += index;
			for (int i = 2; i < diskBlocks.length; i++) {
				if (!diskBlocks[i].unused()) {
					if (diskBlocks[i].getType().equals(Tool.FILE)) {
						File file = (File) diskBlocks[i].getObj();
						if (path.equals(file.getPosition())) {
							if (fileName.equals(file.getName())) {
								canName = false;
							}
						}
					}
				}
			}
			index++;
		} while (!canName);
		int index2 = searchEmptyDiskBlock();
		if (index2 == Tool.ERROR) {
			return Tool.ERROR;
		} else {
			Directory parent = getDirectory(path);
			File file = new File(fileName, path, index2, parent);
			file.setFlag(Tool.WRITEFLAG);
			if (parent instanceof Directory) {
				parent.addChildren(file);
			}
			diskBlocks[index2].setBlock(true,Tool.END, Tool.FILE, file);
		}
		return index2;
	}

	/**
	 * 返回第一个空闲盘块的盘块号
	 * @return
	 */
	public int searchEmptyDiskBlock() {
		for (int i = 2; i < diskBlocks.length; i++) {
			if (diskBlocks[i].unused()) {
				return i;
			}
		}
		return Tool.ERROR;
	}

	/**
	 * 计算已使用盘块数
	 * @return
	 */
	public int usedBlocksCount() {
		int n = 0;
		for (int i = 2; i < diskBlocks.length; i++) {
			if (!diskBlocks[i].unused()) {
				n++;
			}
		}
		return n;
	}

	/**
	 * 计算空闲盘块数
	 * @return
	 */
	public int freeBlocksCount() {
		int n = 0;
		for (int i = 2; i < diskBlocks.length; i++) {
			if (diskBlocks[i].unused()) {
				n++;
			}
		}
		return n;
	}

	/**
	 * 文件长度变更时重新分配盘块
	 * @param num
	 * @param block
	 * @return
	 */
	public boolean reallocBlocks(int num, DiskBlock block) {
		File thisFile = (File) block.getObj();
		int begin = thisFile.getDiskNum();
		int index = diskBlocks[begin].getNext();
		int oldNum = 1;
		while (index != Tool.END) {
			oldNum++;
			if (diskBlocks[index].getNext() == Tool.END) {
				begin = index;
			}
			index = diskBlocks[index].getNext();
		}

		if (num > oldNum) {
			// 增加磁盘块
			int n = num - oldNum;
			if (freeBlocksCount() < n) {
				// 超过磁盘容量
				return false;
			}
			int space = searchEmptyDiskBlock();
			diskBlocks[begin].setNext(space);
			for (int i = 1; i <= n; i++) {
				space = searchEmptyDiskBlock();
				if (i == n) {
					diskBlocks[space].setBlock(false,Tool.END, Tool.FILE, thisFile);
				} else {
					diskBlocks[space].setBlock(false,Tool.END, Tool.FILE, thisFile);// 同一个文件的所有磁盘块拥有相同的对象
					int space2 = searchEmptyDiskBlock();
					diskBlocks[space].setNext(space2);
				}
				System.out.println(thisFile);
			}
		} else if (num < oldNum) {
			// 减少磁盘块
			int end = thisFile.getDiskNum();
			while (num > 1) {
				end = diskBlocks[end].getNext();
				num--;
			}
			int next = 0;
			for (int i = diskBlocks[end].getNext(); i != Tool.END; i = next) {
				next = diskBlocks[i].getNext();
				diskBlocks[i].clear();
			}
			diskBlocks[end].setNext(Tool.END);
		} else {
			// 不变
		}
		thisFile.setBlockcount(num);
		return true;
	}

	/**
	 * 返回指定路径下所有文件夹
	 * @param path
	 * @return
	 */
	public List<Directory> getDirectorys(String path) {
		List<Directory> list = new ArrayList<Directory>();
		for (int i = 2; i < diskBlocks.length; i++) {
			if (!diskBlocks[i].unused()) {
				if (diskBlocks[i].getObj() instanceof Directory) {
					if (((Directory) (diskBlocks[i].getObj())).getPosition().equals(path)) {
						list.add((Directory) diskBlocks[i].getObj());
					}
				}
			}
		}
		return list;
	}

	/**
	 * 返回所有文件夹和文件的起始盘块
	 * @param path
	 * @return
	 */
	public List<DiskBlock> getBlockList(String path) {
		List<DiskBlock> bList = new ArrayList<DiskBlock>();
		for (int i = 2; i < diskBlocks.length; i++) {
			if (!diskBlocks[i].unused()) {
				if (diskBlocks[i].getObj() instanceof Directory) {
					if (((Directory) (diskBlocks[i].getObj())).getPosition().equals(path)
							&& diskBlocks[i].isFirst()) {
						bList.add(diskBlocks[i]);
					}
				}
			}
		}
		for (int i = 2; i < diskBlocks.length; i++) {
			if (!diskBlocks[i].unused()) {
				if (diskBlocks[i].getObj() instanceof File) {
					if (((File) (diskBlocks[i].getObj())).getPosition().equals(path)
							&& diskBlocks[i].isFirst()) {
						bList.add(diskBlocks[i]);
					}
				}
			}
		}
		return bList;
	}

	/**
	 * 返回指定路径指向的文件夹
	 * @param path
	 * @return
	 */
	public Directory getDirectory(String path) {
		if (path.equals("root")) {
			return root;
		}
		int split = path.lastIndexOf('\\');
		String location = path.substring(0, split);
		String directoryName = path.substring(split + 1);
		List<Directory> directorys = getDirectorys(location);
		for (Directory directory : directorys) {
			if (directory.getName().equals(directoryName)) {
				return directory;
			}
		}
		return null;
	}

	/**
	 * 给出路径名返回路径对象
	 * @param path
	 * @return
	 */
	public Path getPath(String path) {
		for (Path p : paths) {
			if (p.getName().equals(path)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * 删除
	 * @param block
	 * @return
	 */
	public int delete(DiskBlock block) {
		if (block.getObj() instanceof File) {
			if (isOpenedFile(block)) {
				// 文件已打开，不能删除
				return 3;
			}
			File thisFile = (File) block.getObj();
			Directory parent = thisFile.getParent();
			if (parent instanceof Directory) {
				parent.removeChildren(thisFile);
				parent.setSize(Tool.getDirectorySize(parent));
				while (parent.hasParent()) {
					parent = parent.getParent();
					parent.setSize(Tool.getDirectorySize(parent));
				}
			}
			for (int i = 2; i < diskBlocks.length; i++) {
				if (!diskBlocks[i].unused() && diskBlocks[i].getObj() instanceof File) {
					System.out.println("yes");
					if (((File) diskBlocks[i].getObj()).equals(thisFile)) {// 同一个对象
						System.out.println("yes2");
						diskBlocks[i].clear();
					}
				}
			}
			return 1;
		} else {
			String directoryPath = ((Directory) block.getObj()).getPosition() + "\\"
					+ ((Directory) block.getObj()).getName();
			int index = 0;
			for (int i = 2; i < diskBlocks.length; i++) {
				if (!diskBlocks[i].unused()) {
					Object obj = diskBlocks[i].getObj();
					if (diskBlocks[i].getType().equals(Tool.DIRECTORY)) {
						if (((Directory) obj).getPosition().equals(directoryPath)) {
							// 文件夹不为空，不能删除
							return 2;
						}
					} else {
						if (((File) obj).getPosition().equals(directoryPath)) {
							// 文件夹不为空，不能删除
							return 2;
						}
					}
					if (diskBlocks[i].getType().equals(Tool.DIRECTORY)) {
						if (((Directory) diskBlocks[i].getObj()).equals(block.getObj())) {
							index = i;
						}
					}
				}
			}
			Directory thisDirectory = (Directory) block.getObj();
			Directory parent = thisDirectory.getParent();
			if (parent instanceof Directory) {
				parent.removeChildren(thisDirectory);
				parent.setSize(Tool.getDirectorySize(parent));
			}
			paths.remove(getPath(directoryPath));
			diskBlocks[index].clear();
			return 0;
		}
	}


	public DiskBlock getBlock(int index) {
		return diskBlocks[index];
	}

	public void addPath(Path path) {
		paths.add(path);
	}

	public void removePath(Path path) {
		paths.remove(path);
		if (path.hasParent()) {
			path.getParent().removeChildren(path);
		}
	}

	public void replacePath(Path oldPath, String newName) {
		oldPath.setName(newName);
	}

	public boolean hasPath(Path path) {
		for (Path p : paths) {
			if (p.equals(path)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断指定路径下是否有同名文件夹或文件
	 * @param path
	 * @param name
	 * @return
	 */
	public boolean hasName(String path, String name) {
		Directory thisDirectory = getDirectory(path);
		for (Object child : thisDirectory.getChildren()) {
			if (child.toString().equals(name)) {
				return true;
			}
		}		
		return false;
	}
	
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    	s.defaultReadObject();
		openedFiles = FXCollections.observableArrayList(new ArrayList<File>());
    }

	
}

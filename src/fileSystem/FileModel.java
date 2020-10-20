package fileSystem;

public abstract class FileModel {
 public abstract String getName() ;
 public abstract String getPath() ;
 public abstract String getAttribute();
 public abstract int getLength();
 public abstract int getStartBlock();
 public abstract boolean isDir();
 public abstract boolean exist();

 public abstract MyFile getParent();
 
 public abstract String getExt();
 
 public abstract MyFile[] listFile();
 
 public abstract boolean exists();
 
 public abstract boolean isDirectory();
 
 public abstract boolean isSystemFile();
 
 public abstract boolean isNormalFile();
 
 public abstract boolean isReadOnly();
 
 public abstract boolean createNewFile();
 
 public abstract boolean mkdir();
 
 public abstract boolean delete();
 
}

package fileSystem;

public class Fileservice {
       public boolean createFile(String path) {
    	   MyFile s=new MyFile(path);
    	   if(s.exist()) {
    		   System.out.println("已存在");
    		   return true;
    	   }
    	   if(s.getParent()!=Fileservice.getRootFile()&&!s.getParent().exist()) {
    		   System.out.println("父目录不存在");
    		   return false;
    	   }
    	   return s.createNewFile();
       }
       
       public static MyFile getRootFile() {
    	   return new MyFile("root");
       }
}

package fat;
import java.io.*;
import dataIO.IOManager;
public class Fat {
 private byte[] fatlist=new byte[2*IOManager.blocksize];
 private IOManager iomanager;
 public Fat() {
	 this.iomanager=new IOManager();
	 initFat();
 }
 private void initFat() {
	 byte[] buf = this.iomanager.readoneblock(0);
	 System.arraycopy(buf, 0, fatlist, 0, 64);
	 buf = this.iomanager.readoneblock(1);
   System.arraycopy(buf, 0, fatlist, 64, 64);
	 
 }
public byte[] getFat() {
	return fatlist;
}
public void setFat(byte[] fatlist) {
	this.fatlist = fatlist;
}
 
}

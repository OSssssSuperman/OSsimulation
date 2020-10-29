package fat;
import java.io.*;
import dataIO.IOManager;
public class Fat {
 private static byte[] fatlist=new byte[2*IOManager.blocksize];
 public Fat() {
	 initFat();
 }
 private void initFat() {
	 byte[] buf = IOManager.readoneblock(0);
	 System.arraycopy(buf, 0, fatlist, 0, 64);
	 buf = IOManager.readoneblock(1);
   System.arraycopy(buf, 0, fatlist, 64, 64);
	 setnextblock(0, -1);
	 setnextblock(1,-1);
	 setnextblock(2,-1);
 }
public static byte[] getFat() {
	return Fat.fatlist;
}
public void setFat(byte[] fatlist) {
	Fat.fatlist = fatlist;
}

public static int getnextblock(int num) {
	return IOManager.bytetoint(fatlist[num]);//得到下一块的位置
}
public static void setnextblock(int num,int next) {
	fatlist[num]=(byte) (next&0xff);
	Fatservice.updateFat(fatlist);
}
public static int searchForUnusedblock() {
	for(int i=3;i<IOManager.disksize;i++) {
		if((fatlist[i] & 0xff)==0) {
			return i;
		}
	}
	return 0;
}

 
}

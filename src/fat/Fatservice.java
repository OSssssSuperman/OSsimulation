package fat;

import java.util.Arrays;

import dataIO.IOManager;

public class Fatservice {
        
	public static int unused=0;
	public static int broken=254;
	public Fatservice() {
		
	}
	public int getstatus(int index) {
		int temp=Fat.getnextblock(index);
		if(temp==unused)return unused;
		if(temp==broken)return broken;
		else return 1;
	}
	
	public int getnextblock(int num) {
		return Fat.getnextblock(num);
	}
	public static void updateFat(byte[] fatlist) {
		byte[] writebuf=Arrays.copyOfRange(fatlist, 0, 64);
		IOManager.writeoneblock(0, writebuf);
		writebuf =Arrays.copyOfRange(fatlist, 64, 128);
		IOManager.writeoneblock(1, writebuf);
	}
}

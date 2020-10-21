package memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
内存空间分配表，
 */
public class MAT {
    private List<MATRecord> matRecords = new ArrayList<>();

    public int size(){
        return this.matRecords.size();
    }
    public boolean isContains(MATRecord o){
        return this.matRecords.contains(o);
    }
    public void add(MATRecord o){
        this.matRecords.add(o);
        sort();
    }
    public boolean remove(MATRecord o){
        boolean result=this.matRecords.remove(o);
        sort();
        return result;
    }
    public MATRecord remove(int index){
        MATRecord result=this.matRecords.remove(index);
        sort();
        return result;
    }
    public MATRecord getRecord(int index){
        return this.matRecords.get(index);
    }
    public  List<MATRecord> getMATRecords(){
        return this.matRecords;
    }
    public void clear(){
        this.matRecords.clear();
    }

    public void sort(){
        Collections.sort(this.matRecords);
    }

    public boolean removeByPID(int pid){//to be continue...
        return true;
    }

}

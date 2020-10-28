package memory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

import java.util.LinkedHashMap;
import java.util.Map;
import pcb.PCB;

/*
内存
 */
public class Memory {
    private ObservableList<PCB> pcbList;
    private Map<MATRecord,MemoryBlock> oldRecords;
    private MAT mat;
    private byte[] memoryBytes;
    private static Memory memory;
    private Pane memoryPane;

    public Memory(byte[] memoryBytes){
        this.memoryBytes=memoryBytes;
        this.pcbList= FXCollections.observableArrayList();
        this.mat=new MAT();
        this.oldRecords=new LinkedHashMap<>();
    }

    public static Memory getInstance(){
        if(memory==null){
            memory=new Memory(new byte[512]);
        }
        return memory;
    }

    public ObservableList<PCB> getPcbList() {
        return this.pcbList;
    }
    public PCB getPCB(int pid){//to be continue...
        for(int i=0;i<this.pcbList.size();i++){

            if(this.pcbList.get(i).getPID()==pid){
                return this.pcbList.get(i);
            }
        }

    }


    public void loadOS(){//to

    }

    public int findSpareMemory(int size){//采用首次适配方法查找空闲块
        int startAddress;
        if(mat.getMATRecords().size()==0){
            startAddress=50;
            return startAddress;
        }

    }

}

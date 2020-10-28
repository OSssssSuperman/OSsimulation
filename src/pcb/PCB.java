package pcb;

import lombok.Setter;
import lombok.Getter;
import org.omg.CORBA.PRIVATE_MEMBER;

@Setter
@Getter
public class PCB {
    private String idOfProcess;
    private int PC;//程序计数器
    private boolean stateOfPCB ;//表示该PCB块是否已被分配,true表示可用，false表示不可用
    private int PID  ;//进程标识符（暂）
    private int AX;     //数据寄存器（暂）
    private String PSW;//程序状态字（暂）
    private String IR ;//指令寄存器（暂）
    private int stateOfProcess ;//进程状态，1为就绪，0为阻塞
    private int pos;//PCB块在数组的位置下标
    private int runningTime;

    public PCB(String idOfProcess,int PC, boolean stateOfPCB, int PID, int AX, String PSW, String IR, int stateOfProcess){
        this.idOfProcess = idOfProcess;
        this.stateOfPCB = stateOfPCB;
        this.PID = PID;
        this.AX = AX;
        this.PSW = PSW;
        this.IR = IR;
        this.stateOfProcess = stateOfProcess;
        this.PC = PC;
        this.runningTime = 6; //时间片长度为6
    }




}

package cpu;

import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Tab;
import lombok.Getter;
import lombok.Setter;
import pcb.PCB;
import sample.Controller;
import scheduling.Scheduling;

import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class Cpu {
    public static int AX;//数据寄存器（暂）
    public static String PSW;//程序状态字
    public static String IR;//指令寄存器（暂）
    public static int  PC;//程序计数器
    private double sysTime ;//系统时间
    private double procTime;//时间片
    private boolean stopFlag = true;//CPU运行标识符
    private static PCB currentProcess;

    public static void setCpu(PCB pcb){
        AX = pcb.getAX();
        PSW = pcb.getPSW();
        IR = pcb.getIR();
        PC = pcb.getPC();
        currentProcess = pcb;
    }
    public static void cpuEnd(){
            System.out.println(AX);
            Scheduling.destroy(currentProcess);//调用完了就撤销
    }
    public void timeOver(){
            System.out.println("寄存器的值存回PCB中\n");
            Scheduling.scheduling(currentProcess);

    }
    public static void deviceOver(){

    }

    private void CPU(){//模拟单个中央处理器
            //检查PSW（三位）看看有无中断，若有则先处理中断，然后解释运行指令
            switch (PSW){
                case "001":  //(PSW的是001),软中断,执行cpuEnd()函数->输出x的值,调用进程撤销原语，进程调度
                case "010":  //相对时钟减到0，时间片结束(PSW为010)，执行timeOver()函数->寄存器的值存入PCB中，进行进程调度
                /*case "100": wakeup process*/    //设备使用时间倒计时至0，发生I/O中断(PSW为100),执行deviceOver()->唤醒完成输入输出的进程。和等待该设备的进程
            }



        //解释可执行文件

                //程序计数器跟踪指令
                        if(IR.startsWith("x=")) {//if指令是加减或赋值指令  则显示中间结果
                                System.out.println(AX);
                                PC++;
                        }else if(IR.startsWith("x++")){
                                AX++; PC++;System.out.println(AX);
                        } else if(IR.startsWith("x--")){
                                AX--; PC++;System.out.println(AX);
                        } else if(IR.startsWith("!")) {//elseif设备申请指令则与设备管理联系起来
                               //if(可分配){
                                PC++;
                                Scheduling.block(currentProcess);
                                Scheduling.scheduling(currentProcess);
                        }
                        //若可分配则分配，中断，进程等待
                                //不可分配则进程等待
                        //elseif是end指令则释放内存结束进程



    }





}

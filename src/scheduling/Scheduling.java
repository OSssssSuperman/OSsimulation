package scheduling;

import cpu.Cpu;
import pcb.PCB;

import java.util.LinkedList;

public class Scheduling {//进程调度

    private static final LinkedList<PCB> readyQueue = new LinkedList<>();
    private static final LinkedList<PCB> blockedQueue = new LinkedList<>();
    private static final PCB[] pcbs = new PCB[10];


    private static void protecting(PCB pcb){//现场保护
        pcb.setAX(Cpu.AX);
        pcb.setIR(Cpu.IR);
        pcb.setPC(Cpu.PC);
        pcb.setPSW(Cpu.PSW);
    }

    public static void scheduling(PCB pcb){
        protecting(pcb);//保护正在进行的进程的现场进入该进程的PCB中
        if (!readyQueue.isEmpty()) {//if就绪队不为空
            pcb = readyQueue.remove();//就绪队列中选择一个进程
            Cpu.setCpu(pcb);//将其PCB中的记录恢复到CPU中
            //根据程序计数器PC逐条执行指令
        }
        //else执行闲逛进程
    }

    public static void create(){  //进程创建
            for(int i = 1;i<pcbs.length;i++){//申请空白PCB
                if (pcbs[i].isStateOfPCB()){
                    pcbs[i].setStateOfPCB(false);
                    pcbs[i].setPos(i);
                    break;
                }
            }
            //申请内存空间
                //if申请成功则装入主存
            //初始化PCB
            //显示进程执行结果，进程撤销
    }
    public static void destroy(PCB pcb){
            //回收进程所占内存
            pcbs[pcb.getPos()].setStateOfPCB(true);//回收进程控制块
            //显示进程执行结果，进程撤销
    }
    private void block(PCB pcb){
        pcb.setStateOfProcess(0);//修改进程为阻塞状态,1为就绪态，0为阻塞态
        blockedQueue.add(pcb);  //进程进入阻塞队列
        scheduling(pcb);        //转向进程调度
    }
    private void awake(){
        final PCB tmpPcb = blockedQueue.removeFirst();//将进程从阻塞队列中摘下
        tmpPcb.setStateOfProcess(1);//修改进程状态为就绪
        readyQueue.add(tmpPcb);//进程进入就绪队列
    }

}

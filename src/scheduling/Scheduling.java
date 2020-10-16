package scheduling;

import cpu.Cpu;
import pcb.PCB;

import java.util.ArrayList;
import java.util.LinkedList;

public class Scheduling {//进程调度

    private LinkedList<PCB> readyQueue;
    private LinkedList<PCB> blockedQueue;
    private PCB[] pcbs;
    public Scheduling(Cpu cpu) {
       this.readyQueue = new LinkedList<>();
       this.blockedQueue = new LinkedList<>();
       this.pcbs = new PCB[10];//最多10个PCB块
       this.cpuNow = cpu;
    }

    private void protecting(Cpu cpu,PCB pcb){//现场保护
        pcb.setAX(cpu.getAX());
        pcb.setIR(cpu.getIR());
        pcb.setPC(cpu.getPC());
        pcb.setPSW(cpu.getPSW());
    }

    private void scheduling(Cpu cpu,PCB pcb){
        protecting(cpu,pcb);//保护正在进行的进程的现场进入该进程的PCB中
        if (!readyQueue.isEmpty()) {//if就绪队不为空
            //就绪队列中选择一个进程
            //将其PCB中的记录恢复到CPU中
            //根据程序计数器PC逐条执行指令
        }
        //else执行闲逛进程
    }

    private void create(){  //进程创建
            for(int i = 0;i<pcbs.length;i++){//申请空白PCB
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
    private void destroy(PCB pcb){
            //回收进程所占内存
            pcbs[pcb.getPos()].setStateOfPCB(true);//回收进程控制块
            //显示进程执行结果，进程撤销
    }
    private void block(Cpu cpu,PCB pcb){
        this.protecting(cpu,pcb);//保护进程的CPU现场
        pcb.setStateOfProcess(0);//修改进程状态,1为就绪态，0为阻塞态
        this.blockedQueue.add(pcb);//进程进入阻塞队列
        //转向进程调度
    }
    private void awake(){
        PCB pcb = this.blockedQueue.removeFirst();//将进程从阻塞队列中摘下
        pcb.setStateOfProcess(1);//修改进程状态为就绪
        this.readyQueue.add(pcb);//进程进入就绪队列
    }

}

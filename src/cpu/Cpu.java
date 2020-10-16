package cpu;

import lombok.Getter;
import lombok.Setter;
import pcb.PCB;
@Getter
@Setter
public class Cpu {
    private int AX;//数据寄存器（暂）
    private String[]PSWStatus;//程序状态字（暂）  000没有中断；001为程序结束软中断；010是时间片结束中断；100是I/O中断
    private String PSW;
    private int IR;//指令寄存器（暂）
    private int PC;//程序计数器
    private int timeOfSystem;//系统时钟（暂）->记录开机后时间单位
    private int relativeTime;//相对时钟（暂）->可运行时间片

    public Cpu(PCB pcb){
        this.AX = pcb.getAX();
        this.PSWStatus = new String[]{"000","001","010","100"};
        this.PSW = pcb.getPSW()
        this.IR = pcb.getIR();
        this.PC = pcb.getPC();
        this.timeOfSystem = 0;
        this.relativeTime = 6;
    }
    private void CPU(){//模拟单个中央处理器
            //检查PSW（三位）看看有无中断，若有则先处理中断，然后解释运行指令
            //if执行end，程序结束(PSW的是001)->输出x的值
            //elseif相对时钟减到0，时间片结束(PSW为010)->寄存器的值存入PCB中，进行进程调度
            //elseif设备使用时间倒计时至0，发生I/O中断（PSW为100）->唤醒完成输入输出的进程。和等待该设备的进程
        //解释可执行文件

                //程序计数器跟踪指令
                        //if指令是加减或赋值指令  则显示中间结果
                        //elseif设备申请指令则与设备管理联系起来
                                //若可分配则分配，中断，进程等待
                                //不可分配则进程等待
                        //elseif是end指令则释放内存结束进程



    }
}

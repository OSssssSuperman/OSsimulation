package memory;
/*
内存空间分配表记录，记录内存块的pid，地址及系统属性
实现Comparable接口，重写compareTo方法实现按首地址排序
 */
public class MATRecord implements Comparable<MATRecord>{
    private int pid;
    private int startAddress;
    private int endAddress;
    private boolean User;

    public MATRecord(int pid,int startAddress,int endAddress,boolean isUser){
        this.pid=pid;
        this.startAddress=startAddress;
        this.endAddress=endAddress;
        this.User=isUser;
    }

    public int getPid(){
        return this.pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getStartAddress() {
        return this.startAddress;
    }

    public void setStartAddress(int startAddress) {
        this.startAddress = startAddress;
    }

    public int getEndAddress() {
        return this.endAddress;
    }

    public void setEndAddress(int endAddress) {
        this.endAddress = endAddress;
    }

    public boolean isUser() {
        return this.User;
    }

    public void setUser(boolean user) {
        User = user;
    }
    public int getSize(){
        return this.endAddress-this.startAddress;
    }

    public int compareTo(MATRecord m){
        if(m==null){
            return 1;
        }
        if(this.startAddress==m.getStartAddress()){
            return 0;
        }
        return (this.startAddress-m.getStartAddress())>0?1:-1;
    }

    public String toString(){
        return "pid:"+this.pid+",start:"+this.startAddress+
                ",end:"+this.endAddress+",isUser:"+this.User;
    }
}

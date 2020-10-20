package memory;
/*
* 地址类，保存进程在内存中的地址
*/
public class Address {
    private int startAddress;//首地址
    private int endAddress;//末地址

    public Address(int startAddress,int endAddress){
        this.startAddress=startAddress;
        this.endAddress=endAddress;
    }
    public int getStartAddress(){
        return this.startAddress;
    }
    public void setStartAddress(int startAddress){
        this.startAddress=startAddress;
    }

    public int getEndAddress(){
        return endAddress;
    }
    public void setEndAddress(int endAddress){
        this.endAddress=endAddress;
    }

    public int getSize(){
        return this.endAddress-this.startAddress+1;
    }

}

package device;
/*
设备管理
 */
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DevicesManager{
    public static DevicesManager devicesManager;
    private ObservableList<Device> devicesList;

    private DevicesManager(){
        this.devicesList= FXCollections.observableArrayList();
        this.devicesList.add(new Device("A"));
        this.devicesList.add(new Device("A"));
        this.devicesList.add(new Device("B"));
        this.devicesList.add(new Device("B"));
        this.devicesList.add(new Device("B"));
        this.devicesList.add(new Device("C"));
        this.devicesList.add(new Device("C"));
        this.devicesList.add(new Device("C"));
    }
    public static DevicesManager getInstance(){
        if(devicesManager==null){
            devicesManager=new DevicesManager();
        }
        return devicesManager;
    }
    public Boolean hasSpareDevice(String type){//查询是否有空闲设备
        for(int i=0;i<this.devicesList.size();i++){
            if(this.devicesList.get(i).getType().equals(type)
            &&!this.devicesList.get(i).isOcupied()) {
                return true;
            }
        }
        return false;
    }
    public Device getSpareDevice(String type){//获取设备
        for(int i=0;i<this.devicesList.size();i++){
            if(this.devicesList.get(i).getType().equals(type)
                &&!this.devicesList.get(i).isOcupied()){
                return this.devicesList.get(i);
            }
        }
        return null;
    }
    public void ocupiedSpareDevice(String type,int pid){//占用设备
        for(int i=0;i<this.devicesList.size();i++){
            if(this.devicesList.get(i).getType().equals(type)
                    &&!this.devicesList.get(i).isOcupied()){
                this.devicesList.get(i).setDevicePID(pid);
            }
        }
    }

    public void freeDevice(String type,int pid){//释放设备
        for(int i=0;i<this.devicesList.size();i++){
            if(this.devicesList.get(i).getType().equals(type)
                    &&this.devicesList.get(i).getDevicePID()==pid){
                this.devicesList.get(i).free();
            }
        }
    }

    public void setDevicesTableView(TableView<Device> tv, TableColumn<Device, String> tcDeviceType
            , TableColumn<Device, String> tcOcupyPID) {//设置设备列表
        tcDeviceType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tcOcupyPID.setCellValueFactory(new PropertyValueFactory<>("ocupyPID"));
        tv.setItems(this.devicesList);
    }



}

/*
设备工厂
 */
class Device{
    private StringProperty type;
    private BooleanProperty ocupied;
    private IntegerProperty devicePID;


    public Device(String type){
        this.type=new SimpleStringProperty(type);
        this.devicePID=new SimpleIntegerProperty();
        this.ocupied=new SimpleBooleanProperty();
    }

    public StringProperty typeProperty(){
        return this.type;
    }
    public BooleanProperty ocupiedProperty(){
        return this.ocupied;
    }

    public IntegerProperty devicePIDProperty(){
        return this.devicePID;
    }
    public String getType(){
        return this.type.get();
    }
    public boolean isOcupied(){
        return this.ocupied.get();
    }
    public void setOcupied(){
        this.ocupied.set(true);
    }
    public int getDevicePID(){
        return this.devicePID.get();
    }
    public void setDevicePID(int pid){
        this.devicePID.set(pid);
        setOcupied();
    }
    public void free(){
        this.ocupied.set(false);
        this.devicePID.set(0);
    }

}


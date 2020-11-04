package view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import model.DiskBlock;
import model.FAT;
import model.File;
import model.Directory;
import model.Path;



public class delView {

	private DiskBlock block;
	private FAT fat;
	private MainView mainView;
	private Alert mainAlert, okAlert, errAlert;	
	
	public delView(DiskBlock block, FAT fat, MainView mainView) {
		this.block = block;
		this.fat = fat;
		this.mainView = mainView;
		showView();
	}
	
	private void showView() {
		String mesg = "";
		if (block.getObj() instanceof Directory) {
			Directory directory = (Directory)block.getObj();
			mesg = directory.getName()
					+ "\n类型: " + directory.getType()
					+ "\n大小: " + directory.getSize();

		} else {
			File file = (File)block.getObj();
			mesg = file.getName()
					+ "\n类型: " + file.getType()
					+ "\n大小: " + file.getSize() + "KB";
					//+ "\n创建时间: " + file.getCreateTime();
		}
		mainAlert = new Alert(AlertType.CONFIRMATION);
		mainAlert.setHeaderText("确认删除");
		mainAlert.setContentText(mesg);
		
		okAlert = new Alert(AlertType.INFORMATION);
		okAlert.setTitle("成功");
		okAlert.setHeaderText(null);
		
		errAlert = new Alert(AlertType.ERROR);
		errAlert.setHeaderText(null);
		
		showAlert();
	}
	
	private void showAlert() {
				
		Optional<ButtonType> result = mainAlert.showAndWait();	
		Path thisPath = null;
		if (result.get() == ButtonType.OK) {
			if (block.getObj() instanceof Directory) {
				thisPath = ((Directory)block.getObj()).getPath();
			}
			int res = fat.delete(block);
			if (res == 0) {//删除文件夹成功
				mainView.removeNode(mainView.getRecentNode(), thisPath);												
				okAlert.setContentText("删除文件夹成功");
				okAlert.show();
			} else if (res == 1) {
				okAlert.setContentText("删除文件成功");
				okAlert.show();
			} else if (res == 2) {//文件夹不为空
				errAlert.setHeaderText("文件夹不为空");
				errAlert.show();
			} else {//文件未关闭				
				errAlert.setHeaderText("文件未关闭");
				errAlert.show();
			}
		} else {
			
		}
	}
	
}

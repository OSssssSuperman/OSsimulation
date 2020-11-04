package view;

import java.util.Map;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DiskBlock;
import model.FAT;
import model.File;
import model.Directory;
import model.Path;


public class RenameView {

	private DiskBlock block;
	private FAT fat;
	private Label icon;
	private Map<Path, TreeItem<String>> pathMap;
	private Stage stage;
	private Scene scene;
	private HBox hBox;
	private TextField nameField;
	private Button okButton, cancelButton;
	private String oldName, location;

	public RenameView(DiskBlock block, FAT fat, Label icon, Map<Path, TreeItem<String>> pathMap) {
		this.block = block;
		this.fat = fat;
		this.icon = icon;
		this.pathMap = pathMap;
		showView();
	}

	private void showView() {
		if (block.getObj() instanceof Directory) {
			oldName = ((Directory) block.getObj()).getName();
			location = ((Directory) block.getObj()).getPosition();
		} else {
			oldName = ((File) block.getObj()).getName();
			location = ((File) block.getObj()).getPosition();
		}
		
		nameField = new TextField(oldName);
		
		okButton = new Button("更改");
		okButton.setStyle("-fx-background-color:#d3d3d3;");
		okButton.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				okButton.setStyle("-fx-background-color: #ffffff;");
			}
		});
		okButton.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				okButton.setStyle("-fx-background-color: #d3d3d3;");
			}
		});
		okButton.setOnAction(ActionEvent -> {
			String newName = nameField.getText();
			if (!newName.equals(oldName)) {
				if (fat.hasName(location, newName)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("此位置已包含同名文件/文件夹");
					alert.showAndWait();
				} else {
					if (block.getObj() instanceof Directory) {
						Directory thisDirectory = (Directory) block.getObj();
						thisDirectory.setName(newName);
						pathMap.get(thisDirectory.getPath()).setValue(newName);
						reLoc(location, location, oldName, newName, thisDirectory);
					} else {
						((File) block.getObj()).setName(newName);
					}
					icon.setText(newName);
				}					
			}
			stage.close();
		});
		
		cancelButton = new Button("取消");
		cancelButton.setStyle("-fx-background-color:#d3d3d3;");
		cancelButton.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				cancelButton.setStyle("-fx-background-color: #ffffff;");
			}
		});
		cancelButton.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				cancelButton.setStyle("-fx-background-color: #d3d3d3;");
			}
		});
		cancelButton.setOnAction(ActionEvent -> stage.close());
		
		hBox = new HBox(nameField, okButton, cancelButton);
		hBox.setSpacing(5);
		hBox.setPadding(new Insets(5));
		hBox.setStyle("-fx-background-color:#a9a9a9");

		scene = new Scene(hBox);
		stage = new Stage();
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setAlwaysOnTop(true);
		stage.show();
	}	

	private void reLoc(String oldP, String newP, String oldN,
			String newN, Directory directory) {
		String oldLoc = oldP + "\\" + oldN;
		String newLoc = newP + "\\" + newN;
		Path oldPath = fat.getPath(oldLoc);
		fat.replacePath(oldPath, newLoc);
		for (Object child : directory.getChildren()) {
			if (child instanceof File) {
				((File) child).setPosition(newLoc);
			} else {
				Directory nextDirectory = (Directory) child;
				nextDirectory.setPosition(newLoc);
				if (nextDirectory.hasChild()) {
					reLoc(oldLoc, newLoc, nextDirectory.getName(),
							nextDirectory.getName(), nextDirectory);
				} else {
					Path nextPath = fat.getPath(oldLoc + "\\" +
							nextDirectory.getName());
					fat.replacePath(nextPath, newLoc + "\\" +
							nextDirectory.getName());
				}
			}
		}
	}
	
}

package view;

import java.util.Optional;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.DiskBlock;
import model.FAT;
import model.File;
import model.Directory;
import model.*;

public class FileView {

	private File file;
	private FAT fat;
	private DiskBlock block;
	private String newContent, oldContent;
	private Stage stage;
	private Scene scene;
	private BorderPane borderPane;
	private TextArea contentField;
	private MenuBar menuBar;
	private Menu fileMenu;
	private MenuItem saveItem, closeItem;

	public FileView(File file, FAT fat, DiskBlock block) {
		this.file = file;
		this.fat = fat;
		this.block = block;
		showView();
	}

	private void showView() {
		contentField = new TextArea();
		contentField.setPrefRowCount(25);
		contentField.setWrapText(true);
		contentField.setText(file.getContent());
		if (file.getFlag() == Tool.READFLAG) {
			contentField.setDisable(true);
		}

		saveItem = new MenuItem("保存");
		saveItem.setGraphic(new ImageView(Tool.SAVE_IMG));
		saveItem.setOnAction(ActionEvent -> {
			newContent = contentField.getText();
			oldContent = file.getContent();
			if (newContent == null) {
				newContent = "";
			}
			if (!newContent.equals(oldContent)) {
				saveContent(newContent);
			}
		});
		
		closeItem = new MenuItem("关闭");
		closeItem.setGraphic(new ImageView(Tool.CLOSE_IMG));
		closeItem.setOnAction(ActionEvent -> onClose(ActionEvent));		
		
		fileMenu = new Menu("File", null, saveItem, closeItem);
		menuBar = new MenuBar(fileMenu);
		menuBar.setPadding(new Insets(0));

		borderPane = new BorderPane(contentField, menuBar, null, null, null);

		scene = new Scene(borderPane);
		stage = new Stage();
		stage.setScene(scene);
		stage.setTitle(file.getName());
		stage.titleProperty().bind(file.getNameP());
		stage.getIcons().add(new Image(Tool.FILE_IMG));
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				onClose(event);
			}
		});
		stage.show();
	}

	private void onClose(Event event) {
		newContent = contentField.getText();
		oldContent = file.getContent();
		boolean isCancel = false;
		if (newContent == null) {
			newContent = "";
		}
		System.out.println(newContent + " newContent");
		if (!newContent.equals(oldContent)) {
			event.consume();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("保存更改");
			alert.setHeaderText(null);
			alert.setContentText("文件内容已更改，是否保存?");
			ButtonType saveType = new ButtonType("保存");
			ButtonType noType = new ButtonType("不保存");
			ButtonType cancelType = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().setAll(saveType, noType, cancelType);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == saveType) {
				saveContent(newContent);
			} else if (result.get() == cancelType) {
				isCancel = true;
			}
		}
		if (!isCancel) {
			fat.removeOpenedFile(block);
			stage.close();
		}
	}
	
	private void saveContent(String newContent) {
		int newLength = newContent.length();
		int blockCount = Tool.blocksCount(newLength);
		file.setBlockcount(blockCount);
		file.setContent(newContent);
		file.setSize(Tool.getSize(newLength));
		if (file.hasParent()) {
			Directory parent = (Directory) file.getParent();
			parent.setSize(Tool.getDirectorySize(parent));
			while (parent.hasParent()) {
				parent = (Directory) parent.getParent();
				parent.setSize(Tool.getDirectorySize(parent));
			}
		}
		fat.reallocBlocks(blockCount, block);
	}
	
}

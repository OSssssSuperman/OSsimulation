
package diskview;

import java.net.URL;
import java.util.ResourceBundle;
import fat.*;
import fat.Fat;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.Node;

public class Blockpane extends StackPane {
    
	private DropShadow dropShadow;
	private int index;
	private Rectangle rectangle;
	private int status;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane unusedblock;

    @FXML
    private Label blocknum;

    @FXML
    void initialize() {
        assert unusedblock != null : "fx:id=\"unusedblock\" was not injected: check your FXML file 'BlockPane.fxml'.";
        assert blocknum != null : "fx:id=\"blocknum\" was not injected: check your FXML file 'BlockPane.fxml'.";

    }
    public Blockpane(int index) {
    	    this.rectangle = new Rectangle(15.0D, 10.0D, Color.ALICEBLUE);
    	     this.rectangle.setStroke(Color.BLACK);
    	     this.rectangle.setStrokeLineCap(StrokeLineCap.ROUND);
    	     this.index = index;
    	     this.setstatus();
    		 this.blocknum = new Label(index + "");
    	     this.blocknum.setFont(Font.font(10.0D));
    	     this.dropShadow = new DropShadow();
    	     getChildren().addAll(new Node[] { this.rectangle, this.blocknum });
    	     initializeListener();
    	   }
    
    private void initializeListener() {
    	     setOnMouseEntered(new EventHandler<MouseEvent>()
    	        {
    	           public void handle(MouseEvent event) {
    	             Blockpane.this.setEffect(Blockpane.this.dropShadow);
    	           }
    	         });
    	     
    	     setOnMouseExited(new EventHandler<MouseEvent>()
    	         {
    	           public void handle(MouseEvent event) {
    	             Blockpane.this.setEffect(null);
    	           }
    	         });
    	  }
    	   
    	   public int getIndex() {
    	     return this.index;
    	   }
    	   public void setstatus() {
    		   if(Fat.getFat()[this.index])
    	   }
    	   public void setUsed() {
    	     this.status = 1;
    	     this.rectangle.setFill(Color.GREEN);
    	   }
    	   
    	   public void setUnUsed() {
    	     this.status = 0;
    	     this.rectangle.setFill(Color.ALICEBLUE);
    	   }
    	   
    	   public void setDamage() {
    	     this.status = -1;
    	     this.rectangle.setFill(Color.RED);
    	   }
    	   
    	   public int getStatus() {
    	     return this.status;
    	   }
}

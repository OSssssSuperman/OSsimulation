package memory;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pcb.PCB;


/*
内存块视图
 */
public class MemoryBlock extends Pane {
    private Color[] colors=new Color[]{Color.CORNFLOWERBLUE,Color.YELLOWGREEN,Color.DEEPPINK,Color.MEDIUMSPRINGGREEN,
            Color.YELLOW,Color.GOLDENROD,Color.RED};
    private Rectangle rectangle;
    private ProgressIndicator progressIndicator;//进程指示器
    private Label pidLabel;//pid标签

    public MemoryBlock(double location, double width, double height, PCB pcb){
        this.rectangle=new Rectangle(width,height);
        this.setLayoutX(location);
        this.rectangle.setFill(getRandomColor());
        this.rectangle.setArcWidth(20.0D);
        this.rectangle.setArcHeight(20.0D);
        this.getChildren().add(this.rectangle);

        this.progressIndicator=new ProgressIndicator();
        this.progressIndicator.setLayoutY(10.0D);
        this.progressIndicator.setPrefSize(width,width);
        this.progressIndicator.setProgress(0.0D);




    }

    private Color getRandomColor(){
        return this.colors[(int)(Math.random()*this.colors.length)];
    }


}

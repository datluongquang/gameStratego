import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import sun.plugin2.util.ColorUtil;

import java.io.IOException;

public class Tile extends Rectangle{
    public int row;
    public int column;
    public Tile(int row,int column) throws IOException{
        setWidth(100);
        setHeight(100);
        this.row=row;
        this.column=column;
        if(row==5||row==4){
            if(column==2||column==3||column==6||column==7){
                setFill(Color.BLUE);
            }
            else {
                setFill(Color.WHITE);
            }
        }
        else {
            setFill(Color.WHITE);
        }
        setStroke(Color.GRAY);
        setTranslateX(column*100);
        setTranslateY(row*100);
        setOnMouseClicked(e ->{
            if (Main.setUPPiece){
                try {
                    Main.puttingPiece(row,column);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else {
                Main.Move(row, column);
            }
        });
    }

}

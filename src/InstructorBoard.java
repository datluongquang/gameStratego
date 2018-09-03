import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.Set;

public class InstructorBoard extends Canvas{
    public InstructorBoard(String ins,int height,int width,int size,int x,int y){
        setHeight(height);
        setWidth(width);
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill( Color.RED );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, size);
        gc.setFont( theFont );
        gc.fillText( ins, 60, 50 );
        gc.strokeText( ins, 60, 50 );
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.BOTTOM);
        setTranslateX(x);
        setTranslateY(y);
    }

}

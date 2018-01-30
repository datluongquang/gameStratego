import javafx.scene.image.ImageView;

public class Piece extends ImageView{
    public Tile currentPos;
    public String URL;
    public Piece(String url, Tile Position){
        super(url);
        URL= url;
        currentPos=Position;
        setTranslateX(currentPos.column*100);
        setTranslateY(currentPos.row*100);
        super.setFitHeight(100);
        super.setFitWidth(100);
        setOnMouseClicked(e -> {
            if(!Main.setUPPiece) {
                Main.ReCoMove(url, currentPos.row, currentPos.column);
            }
        }
            );
    }

}

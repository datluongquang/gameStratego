import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public final int screenWidth = 1500;
    public final int screenHeight = 1000;
    public static Pane root;
    public static InstructorBoard iboard;
    public static Tile[][] arrayTile = new Tile[10][10];
    public static Piece[] pieces = new Piece[40];
    public static int turn =1;
    public static boolean readyToAttack= false;
    public static boolean setUPPiece= true;
    public static int k=-1;
    public static int[][] board = {
                    {0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0},
                    {0,0,-1,-1,0,0,-1,-1,0,0},
                    {0,0,-1,-1,0,0,-1,-1,0,0},
                    {0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0},};
    public Pane setTable() throws IOException{
        root=new Pane();
        root.setPrefSize(screenWidth,screenHeight);
        int i,j;
        for(i=0;i<10;i++){
            for (j=0;j<10;j++){
                Tile tile = new Tile(i,j);
                arrayTile[i][j]=tile;
                root.getChildren().add(tile);
            }
        }
        return root;
    }

    @Override
    public void start(Stage mainStage) throws IOException{
        mainStage.setTitle("Stratego");
        mainStage.setScene(new Scene(setTable()));
        mainStage.show();
        iboard = new InstructorBoard("Put your flag");
        root.getChildren().add(iboard);
//        int p,q;
//        for(p=0;p<10;p++){
//            System.out.println();
//            for(q=0;q<10;q++){
//                System.out.print(board[p][q]+" ");
//            }
//        }
    }

    public static Pane puttingPiece(int i, int j) throws IOException{
        if(i>=6&&i<=9&&board[i][j]==0) {
            k+=1;
            int p,q;
            if (k == 0) {
                pieces[k] = new Piece("file:\\gameStratego\\resource\\Bflag.png", arrayTile[i][j]);
                board[i][j]=12;
                root.getChildren().add(pieces[k]);
                root.getChildren().remove(iboard);
                iboard= new InstructorBoard("Put the bomb");
                root.getChildren().add(iboard);
            }
            if (k >=1&&k<=6){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B11Bomb.png", arrayTile[i][j]);
                board[i][j]=11;
                root.getChildren().add(pieces[k]);
                if (k==6) {
                    root.getChildren().remove(iboard);
                    iboard = new InstructorBoard("Put the spy");
                    root.getChildren().add(iboard);
                }

            }
            else if (k == 7 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B01Spy.png", arrayTile[i][j]);
                board[i][j]=1;
                root.getChildren().add(pieces[k]);
                root.getChildren().remove(iboard);
                iboard = new InstructorBoard("Put the scout");
                root.getChildren().add(iboard);
            }
            else if (k>7&&k<16){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B02Scout.png", arrayTile[i][j]);
                board[i][j]=2;
                root.getChildren().add(pieces[k]);
                if (k==15) {
                    root.getChildren().remove(iboard);
                    iboard = new InstructorBoard("Put the miner");
                    root.getChildren().add(iboard);
                }
            }
            else if (k>=16&&k<=20 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B03Miner.png", arrayTile[i][j]);
                board[i][j]=3;
                root.getChildren().add(pieces[k]);
                if (k==20) {
                    root.getChildren().remove(iboard);
                    iboard = new InstructorBoard("Put the sergent");
                    root.getChildren().add(iboard);
                }
            }
            else if (k>=21&&k<=24 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B04Sergent.png", arrayTile[i][j]);
                board[i][j]=4;
                root.getChildren().add(pieces[k]);
                if (k==24) {
                    root.getChildren().remove(iboard);
                    iboard = new InstructorBoard("Put the Lieuteneant");
                    root.getChildren().add(iboard);
                }
            }
            else if (k>=25&&k<=28 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B05Lieutenant.png", arrayTile[i][j]);
                board[i][j]=5;
                root.getChildren().add(pieces[k]);
                if (k==28) {
                    root.getChildren().remove(iboard);
                    iboard = new InstructorBoard("Put the Captain");
                    root.getChildren().add(iboard);
                }
            }
            else if (k>=29&&k<=32 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B06Captain.png", arrayTile[i][j]);
                board[i][j]=6;
                root.getChildren().add(pieces[k]);
                if (k==32) {
                    root.getChildren().remove(iboard);
                    iboard = new InstructorBoard("Put the Major");
                    root.getChildren().add(iboard);
                }
            }
            else if (k>=33&&k<=35 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B07Major.png", arrayTile[i][j]);
                board[i][j]=7;
                root.getChildren().add(pieces[k]);
                if (k==35) {
                    root.getChildren().remove(iboard);
                    iboard = new InstructorBoard("Put the Colonel");
                    root.getChildren().add(iboard);
                }
            }
            else if (k>=36&&k<=37 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B08Colonel.png", arrayTile[i][j]);
                board[i][j]=8;
                root.getChildren().add(pieces[k]);
                if (k==37) {
                    root.getChildren().remove(iboard);
                    iboard = new InstructorBoard("Put the General");
                    root.getChildren().add(iboard);
                }
            }
            else if (k == 38 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B09General.png", arrayTile[i][j]);
                board[i][j]=9;
                root.getChildren().add(pieces[k]);
                root.getChildren().remove(iboard);
                iboard = new InstructorBoard("Put the Marshal");
                root.getChildren().add(iboard);
            }
            else if (k == 39 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B10Marshal.png", arrayTile[i][j]);
                board[i][j]=10;
                root.getChildren().add(pieces[k]);
                setUPPiece= false;
                root.getChildren().remove(iboard);
                Opponent.OpponentSetUp();
            }
        }
        return root;
    }
    public static Pane ReCoMove(String url, int i, int j) {
        if(turn==1&& url.substring(28,29).equals("B")&& !url.substring(29,31).equals("11")&& !url.substring(29,30).equals("f")) {
            readyToAttack=false;
            for (int h = 0; h < 10; h++) {
                for (int k = 0; k < 10; k++) {
                    if (h == 5 || h == 4) {
                        if (k == 2 || k == 3 || k == 6 || k == 7) {
                            arrayTile[h][k].setFill(Color.BLUE);
                        } else {
                            if(board[h][k]>20&&board[h][k]<33){
                                arrayTile[h][k].setFill(Color.BLACK);
                            }
                            else {
                                arrayTile[h][k].setFill(Color.WHITE);
                            }
                        }
                    } else {
                        if(board[h][k]>20&&board[h][k]<33){
                            arrayTile[h][k].setFill(Color.BLACK);
                        }
                        else {
                            arrayTile[h][k].setFill(Color.WHITE);
                        }
                    }
                }
            }
            arrayTile[i][j].setFill(Color.RED);
            if (!url.substring(29,31).equals("02")) {
                if (j < 9) {
                    if (board[i][j + 1] == 0) {
                        arrayTile[i][j + 1].setFill(Color.YELLOW);
                    } else if (board[i][j + 1] > 12) {
                        arrayTile[i][j + 1].setFill(Color.ORANGE);
                    }
                }
                if (i < 9) {
                    if (board[i + 1][j] == 0) {
                        arrayTile[i + 1][j].setFill(Color.YELLOW);
                    } else if (board[i + 1][j] > 12) {
                        arrayTile[i + 1][j].setFill(Color.ORANGE);
                    }
                }
                if (i > 0) {
                    if (board[i - 1][j] == 0) {
                        arrayTile[i - 1][j].setFill(Color.YELLOW);
                    } else if (board[i - 1][j] > 12) {
                        arrayTile[i - 1][j].setFill(Color.ORANGE);
                    }
                }
                if (j > 0) {
                    if (board[i][j - 1] == 0) {
                        arrayTile[i][j - 1].setFill(Color.YELLOW);
                    } else if (board[i][j - 1] > 12) {
                        arrayTile[i][j - 1].setFill(Color.ORANGE);
                    }
                }
            }
            else{
                int k;
                for (k=i+1;k<10;k++){
                    if (board[k][j]==0) {
                        arrayTile[k][j].setFill(Color.YELLOW);
                    }
                    else{
                        if(board[k][j]<33&&board[k][j]>20){
                            arrayTile[k][j].setFill(Color.ORANGE);
                        }
                        break;
                    }
                }
                for (k=j+1;k<10;k++){
                    if (board[i][k]==0) {
                        arrayTile[i][k].setFill(Color.YELLOW);
                    }
                    else{
                        if(board[i][k]>20&&board[i][k]<33){
                            arrayTile[i][k].setFill(Color.ORANGE);
                        }
                        break;
                    }
                }
                for (k=i-1;k>=0;k--){
                    if (board[k][j]==0) {
                        arrayTile[k][j].setFill(Color.YELLOW);
                    }
                    else{
                        if(board[k][j]>20&&board[k][j]<33){
                            arrayTile[k][j].setFill(Color.ORANGE);
                        }
                        break;
                    }
                }
                for (k=j-1;k>=0;k--){
                    if (board[i][k]==0) {
                        arrayTile[i][k].setFill(Color.YELLOW);
                    }
                    else{
                        if(board[i][k]<33&&board[i][k]>20){
                            arrayTile[i][k].setFill(Color.ORANGE);
                        }
                        break;
                    }
                }
            }
            readyToAttack= true;
        }
        return root;
    }
    public static Pane Move(int i, int j){
        root.getChildren().remove(Opponent.line);
        if(turn==1&&arrayTile[i][j].getFill().equals(Color.YELLOW)||arrayTile[i][j].getFill().equals(Color.ORANGE)) {
            int k, h, x = 0, y = 0,indexMover=0,predictIMover=0;
            for (k = 0; k < 10; k++) {
                for (h = 0; h < 10; h++) {
                    if (arrayTile[k][h].getFill().equals(Color.RED)) {
                        x = k;   //initial position
                        y = h;
                        k = 11;
                        h = 11;
                    }
                }
            }
            for (k=0;k<Opponent.predict.size();k++){
                if(Opponent.predict.get(k).p.row==x&&Opponent.predict.get(k).p.column==y){
                    predictIMover=k;
                    break;
                }
            }
            for (k = 0; k < pieces.length; k++) {
                if (pieces[k].currentPos.row == x && pieces[k].currentPos.column == y) {
                    indexMover=k;
                    k=40;
                    break;
                }
            }
            String ah = pieces[indexMover].URL;
            Opponent.changePosition(new Position(x,y), new Position(i,j),Opponent.predict.get(predictIMover),predictIMover);
            root.getChildren().remove(pieces[indexMover]);
            if (arrayTile[i][j].getFill().equals(Color.YELLOW)) {
                pieces[indexMover] = new Piece(ah, arrayTile[i][j]);
                board[i][j] = Integer.parseInt(ah.substring(29, 31));
                root.getChildren().add(pieces[indexMover]);
                turn *= -1;
            }
            if (arrayTile[i][j].getFill().equals(Color.ORANGE)){
                if((board[i][j]-20)<board[x][y]){
                    if(!(board[i][j]==21&&board[x][y]==10)) {
                        pieces[indexMover] = new Piece(ah, arrayTile[i][j]);
                        board[i][j] = Integer.parseInt(ah.substring(29, 31));
                        root.getChildren().add(pieces[indexMover]);
                    }
                }
                else if(board[i][j]==31||board[i][j]-20==board[x][y]){
                    board[i][j]=0;
                    Piece[] b= pieces;
                    int len= pieces.length;
                    pieces= new Piece[len-1];
                    h=0;
                    for(k =0;k<b.length;k++){
                        if(k!=indexMover){
                            pieces[h]=b[k];
                            h+=1;
                        }
                    }
                }
                else{
                    if(board[x][y]==1&&board[i][j]==30){
                        pieces[indexMover] = new Piece(ah, arrayTile[i][j]);
                        board[i][j] = Integer.parseInt(ah.substring(29, 31));
                        root.getChildren().add(pieces[indexMover]);
                    }
                    else if(board[x][y]==3&&board[i][j]==31){
                        pieces[indexMover] = new Piece(ah, arrayTile[i][j]);
                        board[i][j] = Integer.parseInt(ah.substring(29, 31));
                        root.getChildren().add(pieces[indexMover]);
                    }
                    else if(board[i][j]==32){
                        System.out.println("You win");
                    }
                    else {
                        Piece[] b= pieces;
                        int len= pieces.length;
                        h=0;
                        pieces= new Piece[len-1];
                        for(k =0;k<b.length;k++){
                            if(k!=indexMover){
                                pieces[h]=b[k];
                                h+=1;
                            }
                        }
                    }
                }
                turn*=-1;
            }
            board[x][y]=0;

            for (h = 0; h < 10; h++) {
                for (k = 0; k < 10; k++) {
                    if (board[h][k] == -1) {
                        arrayTile[h][k].setFill(Color.BLUE);
                    }
                    else if (board[h][k] > 12) {
                        arrayTile[h][k].setFill(Color.BLACK);
                    }
                    else{
                        arrayTile[h][k].setFill(Color.WHITE);
                    }
                }
            }
            Opponent.OpponentTurn();
        }
        return root;
    }

    public static void main(String[] args) {launch(args);
    }
}

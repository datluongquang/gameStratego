import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public final int screenWidth = 1600;
    public final int screenHeight = 800;
    public static Pane root;
    public static InstructorBoard iboard;
    public static InstructorBoard comPieceleft1;
    public static InstructorBoard comPieceleft2;
    public static InstructorBoard comPieceleft3;
    public static InstructorBoard comPieceleft4;
    public static InstructorBoard humanPieceleft1;
    public static InstructorBoard humanPieceleft2;
    public static InstructorBoard humanPieceleft3;
    public static InstructorBoard humanPieceleft4;


    public static Tile[][] arrayTile = new Tile[8][8];
    public static Piece[] pieces = new Piece[24];
    public static int turn =1;
    public static boolean readyToAttack= false;
    public static boolean setUPPiece= true;
    public static int k=-1;
    public static int[] remainingPiece={1,5,3,2,2,2,1,1,1,1,4,1,1,5,3,2,2,2,1,1,1,1,4,1};
    public static int[][] board = {
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,-1,-1,0,0,-1,-1,0},
                    {0,-1,-1,0,0,-1,-1,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0}};
    public Pane setTable() throws IOException{
        root=new Pane();
        root.setPrefSize(screenWidth,screenHeight);
        int i,j;
        for(i=0;i<8;i++){
            for (j=0;j<8;j++){
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
        iboard = new InstructorBoard("Put your flag",100,500,48,900,0);
        comPieceleft1=new InstructorBoard(remainingPiece[0]+" x spy,"+remainingPiece[1]+" x scout,"+remainingPiece[2]+" x miner",100,700,40,900,70);
        comPieceleft2=new InstructorBoard(remainingPiece[3]+" x sergent, "+remainingPiece[4]+" x lieutenant",100,700,40,900,140);
        comPieceleft3= new InstructorBoard(remainingPiece[5]+" x captain, "+remainingPiece[6]+" x major, "+remainingPiece[7]+" x colonel",100,700,40,900,210);
        comPieceleft4= new InstructorBoard(remainingPiece[8]+" x general, "+remainingPiece[9]+" x marshal, "+remainingPiece[10]+" x bomb",100,700,40,900,280);
        humanPieceleft1=new InstructorBoard(remainingPiece[0]+" x spy,"+remainingPiece[1]+" x scout,"+remainingPiece[2]+" x miner",100,700,40,900,70);
        humanPieceleft2=new InstructorBoard(remainingPiece[3]+" x sergent, "+remainingPiece[4]+" x lieutenant",100,700,40,900,140);
        humanPieceleft3= new InstructorBoard(remainingPiece[5]+" x captain, "+remainingPiece[6]+" x major, "+remainingPiece[7]+" x colonel",100,700,40,900,210);
        humanPieceleft4= new InstructorBoard(remainingPiece[8]+" x general, "+remainingPiece[9]+" x marshal, "+remainingPiece[10]+" x bomb",100,700,40,900,280);

        root.getChildren().add(comPieceleft1);
        root.getChildren().add(comPieceleft2);
        root.getChildren().add(comPieceleft3);
        root.getChildren().add(comPieceleft4);
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
        if(i>=5&&i<=7&&board[i][j]==0) {
            k+=1;
            int p,q;
            //1flag, 4 bomb, 1spy, 5 scout, 3 miner,2 sergent, 2 lieutenant,2 captain,1 major, 1 colonel,1 general, 1 marshall
            if (k == 0) {
                pieces[k] = new Piece("file:\\gameStratego\\resource\\Bflag.png", arrayTile[i][j]);
                board[i][j]=12;
                root.getChildren().add(pieces[k]);
                root.getChildren().remove(iboard);
                iboard= new InstructorBoard("Put the bomb",100,500,48,900,0);
                root.getChildren().add(iboard);
            }
            if (k >=1&&k<=4){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B11Bomb.png", arrayTile[i][j]);
                board[i][j]=11;
                root.getChildren().add(pieces[k]);
                if (k==4) {
                    root.getChildren().remove(iboard);
                    iboard = new InstructorBoard("Put the spy",100,500,48,900,0);
                    root.getChildren().add(iboard);
                }

            }
            else if (k == 5 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B01Spy.png", arrayTile[i][j]);
                board[i][j]=1;
                root.getChildren().add(pieces[k]);
                root.getChildren().remove(iboard);
                iboard = new InstructorBoard("Put the scout",100,500,48,900,0);
                root.getChildren().add(iboard);
            }
            else if (k>5&&k<10){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B02Scout.png", arrayTile[i][j]);
                board[i][j]=2;
                root.getChildren().add(pieces[k]);
                if (k==9) {
                    root.getChildren().remove(iboard);
                    iboard = new InstructorBoard("Put the miner",100,500,48,900,0);
                    root.getChildren().add(iboard);
                }
            }
            else if (k>=10&&k<=12 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B03Miner.png", arrayTile[i][j]);
                board[i][j]=3;
                root.getChildren().add(pieces[k]);
                if (k==12) {
                    root.getChildren().remove(iboard);
                    iboard = new InstructorBoard("Put the sergent",100,500,48,900,0);
                    root.getChildren().add(iboard);
                }
            }
            else if (k>=13&&k<=14 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B04Sergent.png", arrayTile[i][j]);
                board[i][j]=4;
                root.getChildren().add(pieces[k]);
                if (k==14) {
                    root.getChildren().remove(iboard);
                    iboard = new InstructorBoard("Put the Lieuteneant",100,500,48,900,0);
                    root.getChildren().add(iboard);
                }
            }
            else if (k>=15&&k<=16 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B05Lieutenant.png", arrayTile[i][j]);
                board[i][j]=5;
                root.getChildren().add(pieces[k]);
                if (k==16) {
                    root.getChildren().remove(iboard);
                    iboard = new InstructorBoard("Put the Captain",100,500,48,900,0);
                    root.getChildren().add(iboard);
                }
            }
            else if (k>=17&&k<=18 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B06Captain.png", arrayTile[i][j]);
                board[i][j]=6;
                root.getChildren().add(pieces[k]);
                if (k==18) {
                    root.getChildren().remove(iboard);
                    iboard = new InstructorBoard("Put the Major",100,500,48,900,0);
                    root.getChildren().add(iboard);
                }
            }
            else if (k>=19&&k<=20 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B07Major.png", arrayTile[i][j]);
                board[i][j]=7;
                root.getChildren().add(pieces[k]);
                if (k==20) {
                    root.getChildren().remove(iboard);
                    iboard = new InstructorBoard("Put the Colonel",100,500,48,900,0);
                    root.getChildren().add(iboard);
                }
            }
            else if (k==21){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B08Colonel.png", arrayTile[i][j]);
                board[i][j]=8;
                root.getChildren().add(pieces[k]);
                root.getChildren().remove(iboard);
                iboard = new InstructorBoard("Put the General",100,500,48,900,0);
                root.getChildren().add(iboard);
            }
            else if (k == 22 ){
                pieces[k] = new Piece("file:\\gameStratego\\resource\\B09General.png", arrayTile[i][j]);
                board[i][j]=9;
                root.getChildren().add(pieces[k]);
                root.getChildren().remove(iboard);
                iboard = new InstructorBoard("Put the Marshal",100,500,48,900,0);
                root.getChildren().add(iboard);
            }
            else if (k == 23 ){
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
    public static void ReCoMove(String url, int i, int j) {
        if(turn==1&& url.substring(28,29).equals("B")&& !url.substring(29,31).equals("11")&& !url.substring(29,30).equals("f")) {
            readyToAttack=false;
            for (int h = 0; h < 8; h++) {
                for (int k = 0; k < 8; k++) {
                    if (h == 3 || h == 4) {
                        if (k == 2 || k == 1 || k == 6 || k == 5) {
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
                if (j < 7) {
                    if (board[i][j + 1] == 0) {
                        arrayTile[i][j + 1].setFill(Color.YELLOW);
                    } else if (board[i][j + 1] > 12) {
                        arrayTile[i][j + 1].setFill(Color.ORANGE);
                    }
                }
                if (i < 7) {
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
                for (k=i+1;k<8;k++){
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
                for (k=j+1;k<8;k++){
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
    }
    public static void Move(int i, int j){
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                System.out.print(board[x][y]+" ");
            }
            System.out.println();
        }
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                System.out.print(Opponent.AIboardView[x][y]+" ");
            }
            System.out.println();
        }
        root.getChildren().remove(Opponent.line);
        if(turn==1&&arrayTile[i][j].getFill().equals(Color.YELLOW)||arrayTile[i][j].getFill().equals(Color.ORANGE)) {
            int k, h, x = 0, y = 0,indexMover=0,predictIMover=0;
            for (k = 0; k < 8; k++) {
                for (h = 0; h < 8; h++) {
                    if (arrayTile[k][h].getFill().equals(Color.RED)) {
                        x = k;   //initial position
                        y = h;
                        k = 9;
                        h = 9;
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
                    k=24;
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
                int valueHuman=board[x][y];
                int valueCom=board[i][j];
                if((board[i][j]-20)<board[x][y]){
                    if(!(board[i][j]==21&&board[x][y]==10)) {
                        pieces[indexMover] = new Piece(ah, arrayTile[i][j]);
                        board[i][j] = Integer.parseInt(ah.substring(29, 31));
                        remainingPiece[valueCom-9]-=1;
                        root.getChildren().add(pieces[indexMover]);
                    }
                    else remainingPiece[9]-=1;
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
                    if(board[i][j]==31){
                        remainingPiece[valueHuman-1]-=1;
                        remainingPiece[22]-=1;
                    }
                    else {
                        remainingPiece[valueHuman-1]-=1;
                        remainingPiece[valueCom-9]-=1;
                    }
                }
                else{
                    if(board[x][y]==1&&board[i][j]==30){
                        remainingPiece[21]-=1;
                        pieces[indexMover] = new Piece(ah, arrayTile[i][j]);
                        board[i][j] = Integer.parseInt(ah.substring(29, 31));
                        root.getChildren().add(pieces[indexMover]);
                    }
                    else if(board[x][y]==3&&board[i][j]==31){
                        remainingPiece[22]-=1;
                        pieces[indexMover] = new Piece(ah, arrayTile[i][j]);
                        board[i][j] = Integer.parseInt(ah.substring(29, 31));
                        root.getChildren().add(pieces[indexMover]);
                    }
                    else if(board[i][j]==32){
                        remainingPiece[23]-=1;
                        System.out.println("You win");
                    }
                    else {
                        remainingPiece[valueHuman-1]-=1;
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

            for (h = 0; h < 8; h++) {
                for (k = 0; k < 8; k++) {
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
    }

    public static void main(String[] args) {launch(args);
    }
}

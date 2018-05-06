import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Opponent{
    public static int[][] AIboardView= new int[10][10];
    public static ArrayList<GuessingPiece> predict= new ArrayList<GuessingPiece>();
    public static int totalUKnowScore=226;
    public static int totalUnKnowPiece=40;
    public static int OppoTurn=0;
    public static Line line= new Line();
    public static Pane OpponentSetUp() throws IOException {
        int h,k;
        for (h=0;h<4;h++){
            for (k=0;k<10;k++){
                Main.arrayTile[h][k].setFill(Color.BLACK);
            }
        }
        int x,y,i=0,j=0;
        String line = null;
        int range= (int)(Math.random()*40);
        for(x=5*range;x<5*range+4;x++){
            line= Files.readAllLines(Paths.get("D:\\gameStratego\\resource\\StrategoSetUpList.txt")).get(x);
            for (y=0;y<10;y++){
                Main.board[i][j]=Integer.parseInt(line.substring(y*3,y*3+2))+20;
                j+=1;
            }
            j=0;
            i+=1;
        }
        for(x=0;x<6;x++){
            for(y=0;y<10;y++){
                AIboardView[x][y]= Main.board[x][y];
            }
        }
        for(x=0;x<40;x++){
            predict.add(new GuessingPiece(new Position(Main.pieces[x].currentPos.row,Main.pieces[x].currentPos.column),5));
        }
        for(x=0;x<predict.size();x++){
            AIboardView[predict.get(x).p.row][predict.get(x).p.column]=predict.get(x).AILevel;
        }
        for(x=0;x<10;x++){
            for(y=0;y<10;y++){
                System.out.print(Main.board[x][y]+" ");
            }
            System.out.println();
        }
        return Main.root;
    }
    public static void OpponentTurn(){
        int i,j,x;
        boolean notE= true;
        List<List<Position>> moveset= getAllMove(AIboardView,0);
        for(i=0;i<moveset.get(0).size();i++) {
            System.out.println(moveset.get(0).get(i));
            System.out.println(moveset.get(1).get(i));
        }
        List<Integer> moveNum=new ArrayList<Integer>();
        List<int[][]> preBoard1 = new ArrayList<int[][]>() {};
//        List<Integer> grade1 = new ArrayList<Integer>();
        for(i=0;i<moveset.get(0).size();i++){
            preBoard1.add(test(AIboardView, moveset.get(0).get(i), moveset.get(1).get(i)));
            moveNum.add(i);
        }

        List<int[][]> preBoard2= new ArrayList<int[][]>();
        List<Integer> moveNum2= new ArrayList<Integer>();

        generateGuess(preBoard1,moveNum,preBoard2,moveNum2,1);
        System.out.println("a"+moveNum2.size());
        List<int[][]> preBoard3= new ArrayList<int[][]>();
        List<Integer> moveNum3= new ArrayList<Integer>();

        generateGuess(preBoard2,moveNum2,preBoard3,moveNum3,0);
        System.out.println("b"+moveNum3.size());
        List<int[][]> preBoard4= new ArrayList<int[][]>();
        List<Integer> moveNum4= new ArrayList<Integer>();
        generateGuess(preBoard3,moveNum3,preBoard4,moveNum4,1);
        System.out.println("c"+moveNum4.size());
//        List<int[][]> preBoard5= new ArrayList<int[][]>();
//        List<Integer> moveNum5= new ArrayList<Integer>();
//        generateGuess(preBoard4,moveNum4,preBoard5,moveNum5,0);
//        System.out.println("d"+moveNum5.size());
        List<int[][]> preBoard5= new ArrayList<int[][]>();
        List<Integer> moveNum5= new ArrayList<Integer>();
        for(x=0;x<preBoard4.size();x++){
            List<List<Position>> moveset5= getAllMove(preBoard4.get(x),1);
            int m,n;
            for(i=0;i<moveset5.get(0).size();i++){
                preBoard5.add(test(preBoard4.get(x), moveset5.get(0).get(i), moveset5.get(1).get(i)));
                moveNum5.add(moveNum4.get(x));
            }
        }
        System.out.println("f"+moveNum5.size());
        List<Integer> grade5 = new ArrayList<Integer>();
        for (i=0;i<preBoard5.size();i++){
            grade5.add(GradeTable(preBoard5.get(i)));
        }
        List<Integer> gradeCopy5= new ArrayList<Integer>(grade5);
        Collections.sort(gradeCopy5);
        Collections.reverse(gradeCopy5);
        int finGraBoard=0;
        for(i=0;i<grade5.size();i++){
            if(grade5.get(i)==gradeCopy5.get(0)){
                finGraBoard=i;
                break;
            }
        }
        System.out.println(grade5.size());
        Position finalMoveIni= moveset.get(0).get(moveNum5.get(finGraBoard));
        Position finalMoveFin= moveset.get(1).get(moveNum5.get(finGraBoard));
        OpponentMove(finalMoveIni,finalMoveFin);
        OppoTurn+=1;
        for(i=0;i<10;i++){
            System.out.println();
            for (j=0;j<10;j++){
                System.out.print(Main.board[i][j]+" ");
            }
        }
        for(i=0;i<10;i++){
            System.out.println();
            for (j=0;j<10;j++){
                System.out.print(AIboardView[i][j]+" ");
            }
        }
        Main.turn*=-1;
    }
    public static List<List<Position>> getAllMove(int[][] TheBoard,int turn){
        ArrayList<Position> allMoveIni= new ArrayList<>();
        ArrayList<Position> allMovefin= new ArrayList<>();
        int[][] board= new int[10][10];
        int i,j;
        for(i=0;i<10;i++){
            for (j=0;j<10;j++){
                board[i][j]= TheBoard[i][j];
            }
        }
        if(turn==1){
            for(i=0;i<10;i++){
                for (j=0;j<10;j++){
                    if(board[i][j]>20){
                        board[i][j]-=20;
                    }
                    else if(board[i][j]>0&&board[i][j]<20){
                        board[i][j]+=20;
                    }
                }
            }
        }
        for(i=0;i<10;i++){
            for (j=0;j<10;j++){
                if(board[i][j]>20&&board[i][j]<31){
                    if(board[i][j]!=22) {
                        if (j < 9 && board[i][j + 1] < 20 && board[i][j + 1] >= 0) {
                            allMoveIni.add(new Position(i, j));
                            allMovefin.add(new Position(i, j + 1));
                        }
                        if (j > 0 && board[i][j - 1] < 20 && board[i][j - 1] >= 0) {
                            allMoveIni.add(new Position(i, j));
                            allMovefin.add(new Position(i, j - 1));
                        }
                        if (i > 0 && board[i - 1][j] < 20 && board[i - 1][j] >= 0) {
                            allMoveIni.add(new Position(i, j));
                            allMovefin.add(new Position(i - 1, j));
                        }
                        if (i < 9 && board[i + 1][j] < 20 && board[i + 1][j] >= 0) {
                            allMoveIni.add(new Position(i, j));
                            allMovefin.add(new Position(i + 1, j));
                        }
                    }
                    else {
                        int k;
                        for(k=j+1;k<10;k++){
                            if(board[i][k]!=0){
                                if(board[i][k]<20&& board[i][k]>0) {
                                    allMoveIni.add(new Position(i, j));
                                    allMovefin.add(new Position(i, k));
                                }
                                break;
                            }
                            else{
                                allMoveIni.add(new Position(i,j));
                                allMovefin.add(new Position(i,k));
                            }
                        }
                        for(k=j-1;k>=0;k--){
                            if(board[i][k]!=0){
                                if(board[i][k]>0&&board[i][k]<20){
                                    allMoveIni.add(new Position(i,j));
                                    allMovefin.add(new Position(i,k));
                                }
                                break;

                            }
                            else{
                                allMoveIni.add(new Position(i,j));
                                allMovefin.add(new Position(i,k));
                            }
                        }
                        for(k=i+1;k<10;k++){
                            if(board[k][j]!=0){
                                if(board[k][j]>=0&&board[k][j]<20) {
                                    allMoveIni.add(new Position(i, j));
                                    allMovefin.add(new Position(k, j));
                                }
                                break;
                            }
                            else{
                                allMoveIni.add(new Position(i, j));
                                allMovefin.add(new Position(k, j));
                            }
                        }
                        for(k=i-1;k>=0;k--){
                            if(board[k][j]!=0){
                                if(board[k][j]<20&& board[k][j]>=0) {
                                    allMoveIni.add(new Position(i, j));
                                    allMovefin.add(new Position(k, j));
                                }
                                break;
                            }
                            else{
                                allMoveIni.add(new Position(i, j));
                                allMovefin.add(new Position(k, j));
                            }
                        }
                    }
                }
            }
        }
        List<List<Position>> moveset= new ArrayList<List<Position>>();
        moveset.add(allMoveIni);
        moveset.add(allMovefin);
        return moveset;
    }
    public static void changePosition(Position i, Position f,GuessingPiece pi,int predictImover){
        int x;
        if(Math.abs(i.row-f.row)>1||Math.abs(i.column-f.column)>1){
            pi.AILevel=2;
            pi.unKnow=false;
            totalUKnowScore-=2;
            totalUnKnowPiece-=1;
            for(x=0;x<predict.size();x++){
                if(predict.get(x).unKnow){
                    predict.get(x).AILevel= totalUKnowScore/totalUnKnowPiece;
                }
            }
        }
        if(AIboardView[f.row][f.column]==0) {
            AIboardView[f.row][f.column]=pi.AILevel;
            pi.p = f;
        }
        else if((Main.board[f.row][f.column]-20)==Main.board[i.row][i.column]){
            AIboardView[f.row][f.column]=0;
            totalUKnowScore-=Main.board[i.row][i.column];
            predict.remove(predictImover);
            totalUnKnowPiece-=1;
            for(x=0;x<predict.size();x++){
                if(predict.get(x).unKnow){
                    predict.get(x).AILevel= totalUKnowScore/totalUnKnowPiece;
                }
            }
        }
        else if((Main.board[f.row][f.column]-20)<Main.board[i.row][i.column]){
            pi.AILevel= Main.board[i.row][i.column];
            System.out.print(pi.AILevel);
            System.out.print(Main.board[i.row][i.column]);
            System.out.print(Main.board[f.row][f.column]);
            AIboardView[f.row][f.column]=pi.AILevel;
            pi.unKnow=false;
            totalUKnowScore-=pi.AILevel;
            totalUnKnowPiece-=1;
            for(x=0;x<predict.size();x++){
                if(predict.get(x).unKnow){
                    predict.get(x).AILevel= totalUKnowScore/totalUnKnowPiece;
                }
            }
        }
        else if(Main.board[f.row][f.column]==31){
            if(Main.board[i.row][i.column]!=3) {
                AIboardView[f.row][f.column] = 0;
                totalUKnowScore -= Main.board[i.row][i.column];
                totalUnKnowPiece -= 1;
                predict.remove(predictImover);
                for (x = 0; x < predict.size(); x++) {
                    if (predict.get(x).unKnow) {
                        predict.get(x).AILevel = totalUKnowScore / totalUnKnowPiece;
                    }
                }
            }
            else{
                pi.AILevel= Main.board[i.row][i.column];
                AIboardView[f.row][f.column]=pi.AILevel;
                pi.unKnow=false;
                totalUnKnowPiece-=1;
                totalUKnowScore-=pi.AILevel;
                for(x=0;x<predict.size();x++){
                    if(predict.get(x).unKnow){
                        predict.get(x).AILevel= totalUKnowScore/totalUnKnowPiece;
                    }
                }
            }
        }
        AIboardView[i.row][i.column]=0;
    }
    public static int GradeTable(int[][] board){
        int i,j,rowOurFlag=0,columnOurFlag=0;
        for(i=0;i<10;i++){
            for (j=0;j<10;j++){
               if(board[i][j]==32){
                   rowOurFlag=i;
                   columnOurFlag=j;
                   break;
               }
            }
        }
        int flagSafety=0;
        for(i=Math.max(rowOurFlag-2,0);i<=Math.min(rowOurFlag+2,9);i++){
            for(j=Math.max(columnOurFlag-2,0);j<=Math.min(columnOurFlag+2,9);j++){
                if(board[i][j]>0&&board[i][j]<12){
                    flagSafety-=(int)(1.0/(Math.abs(rowOurFlag-i)+Math.abs(columnOurFlag-j))*4*board[i][j]);
                }
                else if(board[i][j]>20&&board[i][j]<32){
                    flagSafety+=(int)(1.0/(Math.abs(rowOurFlag-i)+Math.abs(columnOurFlag-j))*4*(board[i][j]-20));
                }
            }
        }
        int tableValue=0;
        for(i=0;i<10;i++){
            for(j=0;j<10;j++){
                if(board[i][j]>20&&board[i][j]!=22) {
                    tableValue +=Math.pow(board[i][j]-20,2)/10;
                }
                else {
                    tableValue-=Math.pow(board[i][j],2)/10;
                }
            }
        }

        return 2*tableValue+flagSafety;
    }
    public static int[][] test(int[][] theBoard, Position i, Position f){
        int[][] board = new int[theBoard.length][theBoard[0].length];
        for(int x=0; x<theBoard.length; x++) {
            for (int y = 0; y < theBoard[x].length; y++) {
                board[x][y] = theBoard[x][y];
            }
        }
        if(board[f.row][f.column]>0){
            if((board[i.row][i.column]-20)>board[f.row][f.column])
                board[f.row][f.column]=board[i.row][i.column];
        }
        else {
            board[f.row][f.column]=board[i.row][i.column];
        }
        board[i.row][i.column]=0;
        return board;
    }
    public static void OpponentMove(Position ini,Position fin){
        drawArrow(ini,fin);
        int a= Main.board[ini.getRow()][ini.getColumn()];
        Main.board[ini.getRow()][ini.getColumn()]=0;
        AIboardView[ini.getRow()][ini.getColumn()]=0;
        if(Main.board[fin.getRow()][fin.getColumn()]==0){
            Main.board[fin.getRow()][fin.getColumn()]=a;
            AIboardView[fin.getRow()][fin.getColumn()]=a;
        }
        else {
            if(Main.board[fin.getRow()][fin.getColumn()]<(a-20)||Main.board[fin.getRow()][fin.getColumn()]==11){
                Main.board[fin.getRow()][fin.getColumn()]=a;
                AIboardView[fin.getRow()][fin.getColumn()]=a;
                int i,indexattacked=0;
                for(i=0;i<40;i++){
                    if(Main.pieces[i].currentPos.row==fin.getRow()&&Main.pieces[i].currentPos.column==fin.getColumn()){
                        indexattacked=i;
                        break;
                    }
                }
                Main.root.getChildren().remove(Main.pieces[indexattacked]);
            }
            else if(Main.board[fin.getRow()][fin.getColumn()]==(a-20)){
                Main.board[fin.getRow()][fin.getColumn()]=0;
                AIboardView[fin.getRow()][fin.getColumn()]=0;
                int i,indexattacked=0;
                for(i=0;i<Main.pieces.length;i++){
                    if(Main.pieces[i].currentPos.row==fin.getRow()&&Main.pieces[i].currentPos.column==fin.getColumn()){
                        indexattacked=i;
                        break;
                    }
                }
                Main.root.getChildren().remove(Main.pieces[indexattacked]);
            }
        }
        for (int h = 0; h < 10; h++) {
            for (int k = 0; k < 10; k++) {
                if (h == 5 || h == 4) {
                    if (k == 2 || k == 3 || k == 6 || k == 7) {
                        Main.arrayTile[h][k].setFill(Color.BLUE);
                    } else {
                        if(Main.board[h][k]>20&&Main.board[h][k]<33){
                            Main.arrayTile[h][k].setFill(Color.BLACK);
                        }
                        else {
                            Main.arrayTile[h][k].setFill(Color.WHITE);
                        }
                    }
                } else {
                    if(Main.board[h][k]>20&&Main.board[h][k]<33){
                        Main.arrayTile[h][k].setFill(Color.BLACK);
                    }
                    else {
                        Main.arrayTile[h][k].setFill(Color.WHITE);
                    }
                }
            }
        }
    }
    public static void drawArrow(Position ini,Position fin){
        line.setStartX(ini.getColumn()*100+50);
        line.setStartY(ini.getRow()*100+50);
        line.setEndX(fin.getColumn()*100+50);
        line.setEndY(fin.getRow()*100+50);
        line.setStroke(Color.BLUE);
        line.setVisible(true);
        Main.root.getChildren().add(line);
        System.out.println(line);
    }
    public static void generateGuess(List<int[][]> preBoard1,List<Integer> moveNum,List<int[][]> preBoard2,List<Integer> moveNum2,int turn){
        int x,i,j;
        for(x=0;x<preBoard1.size();x++){
            List<List<Position>> moveset2= getAllMove(preBoard1.get(x),turn);
            for(i=0;i<moveset2.get(0).size();i++){
                preBoard2.add(test(preBoard1.get(x), moveset2.get(0).get(i), moveset2.get(1).get(i)));
                moveNum2.add(moveNum.get(x));
            }
        }
    }
}

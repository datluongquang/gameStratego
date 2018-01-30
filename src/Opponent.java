import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
        return Main.root;
    }
    public static void OpponentTurn(){
        int i,j,x;
        boolean notE= true;
        List<List<Position>> moveset= getAllMove(AIboardView,0);
        List<Integer> moveNum=new ArrayList<Integer>();
        List<int[][]> preBoard1 = new ArrayList<int[][]>() {};
        List<Integer> grade1 = new ArrayList<Integer>();
        for(i=0;i<moveset.get(0).size();i++){
            preBoard1.add(test(AIboardView, moveset.get(0).get(i), moveset.get(1).get(i)));
            moveNum.add(i);
        }
        for (i=0;i<moveset.get(0).size();i++){
            grade1.add(GradeTable(preBoard1.get(i)));
        }
        List<Integer> gradeCopy1= new ArrayList<Integer>(grade1);
        Collections.sort(gradeCopy1);
        for(i=0;i<moveset.get(0).size()/2;i++){
            gradeCopy1.remove(i);
        }
        for(i=0;i<preBoard1.size();i++){
            for(j=0;j<gradeCopy1.size();j++){
                if(grade1.get(i)==gradeCopy1.get(j)){
                    gradeCopy1.remove(j);
                    j=gradeCopy1.size();
                    notE= false;
                }
            }
            if(notE){
                preBoard1.remove(i);
                moveNum.remove(i);
                grade1.remove(i);
            }
            notE=true;
        }
        List<int[][]> preBoard2= new ArrayList<int[][]>();
        List<Integer> moveNum2= new ArrayList<Integer>();
        for(x=0;x<preBoard1.size();x++){
            List<List<Position>> moveset2= getAllMove(preBoard1.get(x),1);
            for(i=0;i<moveset2.get(0).size();i++){
                preBoard2.add(test(preBoard1.get(x), moveset2.get(0).get(i), moveset2.get(1).get(i)));
                moveNum2.add(moveNum.get(x));
            }
        }
        List<Integer> grade2 = new ArrayList<Integer>();
        notE=true;
        for (i=0;i<preBoard2.size();i++){
            grade2.add(GradeTable(preBoard2.get(i)));
        }
        List<Integer> gradeCopy2= new ArrayList<Integer>(grade2);
        Collections.sort(gradeCopy2);
        for(i=0;i<preBoard2.size()/2;i++){
            gradeCopy2.remove(i);
        }
        for(i=0;i<preBoard2.size();i++){
            for(j=0;j<gradeCopy2.size();j++){
                if(grade2.get(i)==gradeCopy2.get(j)){
                    gradeCopy2.remove(j);
                    j=gradeCopy2.size();
                    notE= false;
                }
            }
            if(notE){
                preBoard2.remove(i);
                moveNum2.remove(i);
                grade2.remove(i);
            }
            notE=true;
        }
        List<int[][]> preBoard3= new ArrayList<int[][]>();
        List<Integer> moveNum3= new ArrayList<Integer>();
        for(x=0;x<preBoard2.size();x++){
            List<List<Position>> moveset3= getAllMove(preBoard2.get(x),0);
            for(i=0;i<moveset3.get(0).size();i++){
                preBoard3.add(test(preBoard2.get(x), moveset3.get(0).get(i), moveset3.get(1).get(i)));
                moveNum3.add(moveNum2.get(x));
            }
        }
        List<Integer> grade3 = new ArrayList<Integer>();
        notE=true;
        for (i=0;i<preBoard3.size();i++){
            grade3.add(GradeTable(preBoard3.get(i)));
        }
        List<Integer> gradeCopy3= new ArrayList<Integer>(grade3);
        Collections.sort(gradeCopy3);
        for(i=0;i<preBoard3.size()/2;i++){
            gradeCopy3.remove(i);
        }
        for(i=0;i<preBoard3.size();i++){
            for(j=0;j<gradeCopy3.size();j++){
                if(grade3.get(i)==gradeCopy3.get(j)){
                    gradeCopy3.remove(j);
                    j=gradeCopy3.size();
                    notE= false;
                }
            }
            if(notE){
                preBoard3.remove(i);
                moveNum3.remove(i);
                grade3.remove(i);
            }
            notE=true;
        }
        List<int[][]> preBoard4= new ArrayList<int[][]>();
        List<Integer> moveNum4= new ArrayList<Integer>();
        for(x=0;x<preBoard3.size();x++){
            List<List<Position>> moveset4= getAllMove(preBoard3.get(x),1);
            int m,n;
            for(i=0;i<moveset4.get(0).size();i++){
                preBoard4.add(test(preBoard3.get(x), moveset4.get(0).get(i), moveset4.get(1).get(i)));
                moveNum4.add(moveNum3.get(x));
            }
        }
        List<Integer> grade4 = new ArrayList<Integer>();
        for (i=0;i<preBoard4.size();i++){
            grade4.add(GradeTable(preBoard4.get(i)));
        }
        notE=true;
        List<Integer> gradeCopy4= new ArrayList<Integer>(grade3);
        Collections.sort(gradeCopy4);
        Collections.reverse(gradeCopy4);
        int finGraBoard3=0;
        for(i=0;i<grade4.size();i++){
            if(grade4.get(i)==gradeCopy4.get(0)){
                finGraBoard3=i;
                break;
            }
        }
//        System.out.println(grade1.size());
//        System.out.println(grade2.size());
//        System.out.println(grade3.size());
        Position finalMoveIni= moveset.get(0).get(moveNum4.get(finGraBoard3));
        Position finalMoveFin= moveset.get(1).get(moveNum4.get(finGraBoard3));
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
                            if(board[i][k]>20){
                                break;
                            }
                           if(board[i][k]<20&& board[i][k]>=0){
                               allMoveIni.add(new Position(i,j));
                               allMovefin.add(new Position(i,k));
                               if(board[i][k]!=0){
                                   break;
                               }
                           }
                        }
                        for(k=j-1;k>=0;k--){
                            if(board[i][k]>20){
                                break;
                            }
                            if(board[i][k]>=0&&board[i][k]<20){
                                allMoveIni.add(new Position(i,j));
                                allMovefin.add(new Position(i,k));
                                if(board[i][k]!=0){
                                    break;
                                }
                            }
                        }
                        for(k=i+1;k<10;k++){
                            if(board[k][j]>20){
                                break;
                            }
                            if(board[k][j]>=0&&board[k][j]<20){
                                allMoveIni.add(new Position(i,j));
                                allMovefin.add(new Position(k,j));
                                if(board[k][j]!=0){
                                    break;
                                }
                            }
                        }
                        for(k=i-1;k>=0;k--){
                            if(board[i][k]>20){
                                break;
                            }
                            if(board[k][j]<20&& board[k][j]>=0){
                                allMoveIni.add(new Position(i,j));
                                allMovefin.add(new Position(k,j));
                                if(board[k][j]!=0){
                                    break;
                                }
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
        if(Opponent.AIboardView[f.row][f.column]==0) {
            Opponent.AIboardView[f.row][f.column]=pi.AILevel;
            pi.p = f;
        }
        else if((Main.board[f.row][f.column]-20)==Main.board[i.row][i.column]){
            Opponent.AIboardView[f.row][f.column]=0;
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
            Opponent.AIboardView[f.row][f.column]=pi.AILevel;
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
                Opponent.AIboardView[f.row][f.column] = 0;
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
                Opponent.AIboardView[f.row][f.column]=pi.AILevel;
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
        Opponent.AIboardView[i.row][i.column]=0;
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
                if(board[i][j]>20) {
                    tableValue += board[i][j]-20;
                }
                else {
                    tableValue+=board[i][j];
                }
            }
        }

        return tableValue+flagSafety;
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
        int a= Main.board[ini.getRow()][ini.getColumn()];
        Main.board[ini.getRow()][ini.getColumn()]=0;
        AIboardView[ini.getRow()][ini.getColumn()]=0;
        if(Main.board[fin.getRow()][fin.getColumn()]==0){
            Main.board[fin.getRow()][fin.getColumn()]=a;
            AIboardView[fin.getRow()][fin.getColumn()]=a;
        }
        else {
            if(Main.board[fin.getRow()][fin.getColumn()]<(a-20)){
                Main.board[fin.getRow()][fin.getColumn()]=a;
                AIboardView[fin.getRow()][fin.getColumn()]=a;
                int i,indexattacked=0;
                for(i=0;i<40;i++){
                    if(Main.pieces[i].currentPos.row==fin.getRow()){
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
                for(i=0;i<40;i++){
                    if(Main.pieces[i].currentPos.row==fin.getRow()){
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
}

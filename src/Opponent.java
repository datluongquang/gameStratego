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
import java.util.MissingFormatArgumentException;

public class Opponent{
    public static int[][] AIboardView= new int[10][10];
    public static ArrayList<GuessingPiece> predict= new ArrayList<GuessingPiece>();
    public static int totalUKnowScore=226;
    public static int totalUnKnowPiece=40;
    public static int OppoTurn=0;
    public static Line line= new Line();
    public static int MAX_DEPTH=8;
    public static long WATER=439797940224L;
    public static String previousMove="000000";
    public static final long[] columns ={72340172838076673L, 144680345676153346L, 289360691352306692L, 578721382704613384L,
        1157442765409226768L, 2314885530818453536L, 4629771061636907072L, -9187201950435737472L};
    public static final long[] rows = {255L, 65280L, 16711680L, 4278190080L, 1095216660480L,
        280375465082880L, 71776119061217280L, -72057594037927936L};
    public static int count=0;
    public static Pane OpponentSetUp() throws IOException {
        int h,k;
        for (h=0;h<3;h++){
            for (k=0;k<8;k++){
                Main.arrayTile[h][k].setFill(Color.BLACK);
            }
        }
        int x,y,i=0,j=0;
        ArrayList<Integer> a= new ArrayList<>();
        ArrayList<String> b= new ArrayList<>();
        a.add(1);a.add(4);a.add(3);a.add(2);a.add(2);a.add(2);a.add(2);a.add(1);a.add(1);a.add(1);a.add(4);
        b.add("01");b.add("02");b.add("03");b.add("04");b.add("05");b.add("06");b.add("07");b.add("08");b.add("09");b.add("10");b.add("11");
        String lines="";
        for(i=0;i<23;i++){
            x= (int)(Math.random()*b.size());
            lines+=b.get(x)+" ";
            a.set(x,a.get(x)-1);
            if(a.get(x)==0){
                a.remove(x);
                b.remove(x);
            }
        }
        int fp= (int)(Math.random()*8);
        lines=lines.substring(0,fp*3)+"12 "+lines.substring(fp*3);
        y=0;
        for(i=0;i<3;i++){
            for(j=0;j<8;j++){
                Main.board[i][j]=Integer.parseInt(lines.substring(y*3,y*3+2))+20;
                y+=1;
            }
        }
        for(x=0;x<5;x++){
            for(y=0;y<8;y++){
                AIboardView[x][y]= Main.board[x][y];
            }
        }
        for(x=0;x<24;x++){
            predict.add(new GuessingPiece(new Position(Main.pieces[x].currentPos.row,Main.pieces[x].currentPos.column),5));
        }
        for(x=0;x<predict.size();x++){
            AIboardView[predict.get(x).p.row][predict.get(x).p.column]=predict.get(x).AILevel;
        }
        for(x=0;x<8;x++){
            for(y=0;y<8;y++){
                System.out.print(Main.board[x][y]+" ");
            }
            System.out.println();
        }
        return Main.root;
    }
    public static void OpponentTurn(){
        long[] elements= changeToBit(Main.board);
        String move= mainAlgo(elements);
        int oldRow=move.charAt(0)-'0';
        int oldColumn=move.charAt(1)-'0';
        int newRow=move.charAt(2)-'0';
        int newColumn=move.charAt(3)-'0';
        OpponentMove(new Position(oldRow,oldColumn),new Position(newRow,newColumn));
//        int movePiece=Main.board[oldRow][oldColumn];
//        int destination=Main.board[newRow][newColumn];
//        Main.board[oldRow][oldColumn]=0;
//        AIboardView[oldRow][oldColumn]=0;
//        if(move.length()==6){
//            Main.board[newRow][newColumn]=movePiece;
//        }
//        else {
//            int predictIMover=0;
//            for (int k=0;k<Opponent.predict.size();k++){
//                if(Opponent.predict.get(k).p.row==newRow&&Opponent.predict.get(k).p.column==newColumn){
//                    predictIMover=k;
//                    break;
//                }
//            }
//            if (movePiece - 20 == destination) {
//                Main.board[oldRow][oldColumn] = 0;
//                AIboardView[oldRow][oldColumn] =0;
//                predict.remove(predictIMover);
//            } else if ((movePiece - 20) > destination) {
//                if (!(movePiece == 30 && destination == 1)) {
//                    Main.board[newRow][newColumn] = movePiece;
//                    AIboardView[newRow][newColumn] =movePiece;
//                    predict.remove(predictIMover);
//                }
//                else{
//                    AIboardView[newRow][newColumn]=1;
//                    predict.get(predictIMover).AILevel=1;
//                    predict.get(predictIMover).unKnow=false;
//
//                }
//            } else {
//                if (movePiece == 23 && destination == 11) {
//                    Main.board[newRow][newColumn] = movePiece;
//                    AIboardView[newRow][newColumn]=movePiece;
//                    predict.remove(predictIMover);
//                } else if (movePiece == 21 && destination == 10) {
//                    Main.board[newRow][newColumn] = movePiece;
//                    AIboardView[newRow][newColumn]=movePiece;
//                    predict.remove(predictIMover);
//                }
//                else {
//                    if(destination==11){
//                        predict.remove(predictIMover);
//                        Main.board[newRow][newColumn]=0;
//                        AIboardView[newRow][newColumn]=0;
//                    }
//                    if (destination==12){
//                        System.out.println("you lose");
//                    }
//                    else {
//                        AIboardView[newRow][newColumn] = destination;
//                        predict.get(predictIMover).AILevel = destination;
//                        predict.get(predictIMover).unKnow = false;
//                    }
//                }
//            }
//            totalUKnowScore-=destination;
//            totalUnKnowPiece-=1;
//            for(int x=0;x<predict.size();x++){
//                if(predict.get(x).unKnow){
//                    predict.get(x).AILevel= totalUKnowScore/totalUnKnowPiece;
//                }
//            }
//        }
        Main.turn*=-1;
    }
    public static long[] changeToBit(int[][] board){
        long[] elements= new long[24];//Number of type of piece
        for(int k=0;k<24;k++){
            elements[k]=0L;
        }
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                int value = board[i][j];

                if ((1 <= value && value <=12))
                    elements[value-1]=elements[value-1]|(1L<<(8*i+j));
                else if (21 <= value && value <= 32)
                    elements[value-9]=elements[value-9]|(1L<<(8*i+j));

//                switch (board[i][j]){
//
//                    case 1: elements[0]=elements[0]|(1L<<(8*i+j));break;
//                    case 2: elements[1]=elements[1]|(1L<<(8*i+j));break;
//                    case 3: elements[2]=elements[2]|(1L<<(8*i+j));break;
//                    case 4: elements[3]=elements[3]|(1L<<(8*i+j));break;
//                    case 5: elements[4]=elements[4]|(1L<<(8*i+j));break;
//                    case 6: elements[5]=elements[5]|(1L<<(8*i+j));break;
//                    case 7: elements[6]=elements[6]|(1L<<(8*i+j));break;
//                    case 8: elements[7]=elements[7]|(1L<<(8*i+j));break;
//                    case 9: elements[8]=elements[8]|(1L<<(8*i+j));break;
//                    case 10: elements[9]=elements[9]|(1L<<(8*i+j));break;
//                    case 11: elements[10]=elements[10]|(1L<<(8*i+j));break;
//                    case 12: elements[11]=elements[11]|(1L<<(8*i+j));break;
//                    case 21: elements[12]=elements[12]|(1L<<(8*i+j));break;
//                    case 22: elements[13]=elements[13]|(1L<<(8*i+j));break;
//                    case 23: elements[14]=elements[14]|(1L<<(8*i+j));break;
//                    case 24: elements[15]=elements[15]|(1L<<(8*i+j));break;
//                    case 25: elements[16]=elements[16]|(1L<<(8*i+j));break;
//                    case 26: elements[17]=elements[17]|(1L<<(8*i+j));break;
//                    case 27: elements[18]=elements[18]|(1L<<(8*i+j));break;
//                    case 28: elements[19]=elements[19]|(1L<<(8*i+j));break;
//                    case 29: elements[20]=elements[20]|(1L<<(8*i+j));break;
//                    case 30: elements[21]=elements[21]|(1L<<(8*i+j));break;
//                    case 31: elements[22]=elements[22]|(1L<<(8*i+j));break;
//                    case 32: elements[23]=elements[23]|(1L<<(8*i+j));break;
//                }
            }
        }
        return elements;
    }
    public static ArrayList<String> getComputerMoves(long[] elements){
        ArrayList<String> moveList=new ArrayList<>();
        long MOVABLE=~(WATER|elements[12]|elements[23]|elements[13]|elements[14]|elements[15]|elements[16]|elements[17]|elements[18]|elements[19]|elements[20]|elements[21]|elements[22]);
        long HUMANPIECE=elements[1]|elements[2]|elements[10]|elements[3]|elements[4]|elements[5]|elements[6]|elements[7]|elements[8]|elements[9]|elements[11];
        for(int i=12;i<=21;i++){
            if(i!=13){
                long comMoves=((elements[i]&~rows[7])<<8)&MOVABLE;
                while (comMoves!=0){
                    int pos=Long.numberOfTrailingZeros(comMoves);
                    long Eaten=(1L<<pos)&HUMANPIECE;
                    if(Eaten!=0) {
                        moveList.add("" + (pos / 8 - 1) + pos % 8 + pos / 8 + pos % 8 + i+"e");
                    }
                    else{
                        moveList.add("" + (pos / 8 - 1) + pos % 8 + pos / 8 + pos % 8 + i);
                    }
                    comMoves=comMoves ^ (1L << pos);
                }
                comMoves=((elements[i]&~columns[0])>>1)&MOVABLE;
                while (comMoves!=0){
                    int pos=Long.numberOfTrailingZeros(comMoves);
                    long Eaten=(1L<<pos)&HUMANPIECE;//Check for eaten piece
                    if(Eaten!=0) {
                        moveList.add("" + pos / 8 + (pos % 8 + 1) + pos / 8 + pos % 8 + i + "e");
                    }
                    else{
                        moveList.add("" + pos / 8 + (pos % 8 + 1) + pos / 8 + pos % 8 + i);
                    }
                    comMoves=comMoves ^ (1L << pos);
                }
                comMoves=((elements[i]&~columns[7])<<1)&MOVABLE;
                while (comMoves!=0){
                    int pos=Long.numberOfTrailingZeros(comMoves);
                    long Eaten=(1L<<pos)&HUMANPIECE;
                    if(Eaten!=0) {
                        moveList.add("" + pos / 8 + (pos % 8 - 1) + pos / 8 + pos % 8 + i+"e");
                    }
                    else{
                        moveList.add("" + pos / 8 + (pos % 8 - 1) + pos / 8 + pos % 8 + i);
                    }
                    comMoves=comMoves ^ (1L << pos);
                }
                comMoves=((elements[i]&~rows[0])>>8)&MOVABLE;
                while (comMoves!=0){
                    int pos=Long.numberOfTrailingZeros(comMoves);
                    long Eaten=(1L<<pos)&HUMANPIECE;
                    if(Eaten!=0) {
                        moveList.add("" + (pos / 8 + 1) + pos % 8 + pos / 8 + pos % 8 + i +"e");
                    }
                    else{
                        moveList.add("" + (pos / 8 + 1) + pos % 8 + pos / 8 + pos % 8 + i);
                    }
                    comMoves=comMoves ^ (1L << pos);
                }
            }
            else{
                long copyScout= elements[13];
                while (copyScout!=0){
                    int currentScoutPos= Long.numberOfTrailingZeros(copyScout);
                    copyScout=copyScout ^ (1L << currentScoutPos);
                    long currentScoutBit= 1L<<currentScoutPos;
                    int row= currentScoutPos/8;
                    int column=currentScoutPos%8;
                    for(int j=1;j<=8-row;j++){
                        long comMoves=((currentScoutBit&~rows[7])<<(j*8))&MOVABLE;
                        if(comMoves==0){
                            break;
                        }
                        else {
                            if((comMoves&HUMANPIECE)!=0){
                                moveList.add(""+currentScoutPos/8+currentScoutPos%8+(currentScoutPos/8+j)+currentScoutPos%8+i+"e");
                                break;
                            }
                            else {
                                moveList.add("" + currentScoutPos / 8 + currentScoutPos % 8 + (currentScoutPos / 8 + j) + currentScoutPos % 8 + i);
                            }
                        }
                    }
                    for(int j=1;j<=column;j++){
                        long comMoves=((currentScoutBit&~columns[0])>>j)&MOVABLE;
                        if(comMoves==0){
                            break;
                        }
                        else {
                            if((comMoves&HUMANPIECE)!=0){
                                moveList.add(""+currentScoutPos/8+currentScoutPos%8+currentScoutPos/8+(currentScoutPos%8-j)+i+"e");
                                break;
                            }
                            else {
                                moveList.add("" + currentScoutPos / 8 + currentScoutPos % 8 + currentScoutPos / 8 + (currentScoutPos % 8 - j) + i );
                            }
                        }
                    }
                    for(int j=1;j<=8-column;j++){
                        long comMoves=((currentScoutBit&~columns[7])<<j)&MOVABLE;
                        if(comMoves==0){
                            break;
                        }
                        else {
                            if((comMoves&HUMANPIECE)!=0){
                                moveList.add(""+currentScoutPos/8+currentScoutPos%8+currentScoutPos/8+(currentScoutPos%8+j)+i+"e");
                                break;
                            }
                            else {
                                moveList.add("" + currentScoutPos / 8 + currentScoutPos % 8 + currentScoutPos / 8 + (currentScoutPos % 8 + j) + i);
                            }
                        }
                    }
                    for(int j=1;j<=row;j++){
                        long comMoves=((currentScoutBit&~rows[0])>>(j*8))&MOVABLE;
                        if(comMoves==0){
                            break;
                        }
                        else {
                            if((comMoves&HUMANPIECE)!=0){
                                moveList.add(""+currentScoutPos/8+currentScoutPos%8+(currentScoutPos/8-j)+currentScoutPos%8+i+"e");
                                break;
                            }
                            else {
                                moveList.add(""+currentScoutPos/8+currentScoutPos%8+(currentScoutPos/8-j)+currentScoutPos%8+i);
                            }
                        }
                    }

                }
            }
        }
        return moveList;
    }
    public static ArrayList<String> getHumanMoves(long[] elements){
        ArrayList<String> moveList=new ArrayList<>();
        long MOVABLE=~(WATER|elements[1]|elements[2]|elements[10]|elements[3]|elements[4]|elements[5]|elements[6]|elements[7]|elements[8]|elements[9]|elements[11]);
        long COMPUTERPIECE=elements[12]|elements[23]|elements[13]|elements[14]|elements[15]|elements[16]|elements[17]|elements[18]|elements[19]|elements[20]|elements[21]|elements[22];
        for(int i=0;i<=11;i++){
            if(i!=1){
                long comMoves=((elements[i]&~columns[0])>>1)&MOVABLE;
                while (comMoves!=0){
                    int pos=Long.numberOfTrailingZeros(comMoves);
                    long Eaten=(1L<<pos)&COMPUTERPIECE;//Check for eaten piece
                    if(Eaten!=0) {
                        if(i>9){
                            moveList.add("" + pos / 8 + (pos % 8 + 1) + pos / 8 + pos % 8 + i + "e");
                        }
                        else {
                            moveList.add("" + pos / 8 + (pos % 8 + 1) + pos / 8 + pos % 8 + 0 + i + "e");
                        }
                    }
                    else{
                        if(i>9){
                            moveList.add("" + pos / 8 + (pos % 8 + 1) + pos / 8 + pos % 8 + i);
                        }
                        else {
                            moveList.add("" + pos / 8 + (pos % 8 + 1) + pos / 8 + pos % 8 + 0+ i);
                        }
                    }
                    comMoves=comMoves ^ (1L << pos);
                }
                comMoves=((elements[i]&~columns[7])<<1)&MOVABLE;
                while (comMoves!=0){
                    int pos=Long.numberOfTrailingZeros(comMoves);
                    long Eaten=(1L<<pos)&COMPUTERPIECE;
                    if(Eaten!=0) {
                        if(i>9){
                            moveList.add("" + pos / 8 + (pos % 8 - 1) + pos / 8 + pos % 8 + i+"e");
                        }
                        else {
                            moveList.add("" + pos / 8 + (pos % 8 - 1) + pos / 8 + pos % 8 + 0+ i+"e");
                        }
                    }
                    else{
                        if(i>9) {
                            moveList.add("" + pos / 8 + (pos % 8 - 1) + pos / 8 + pos % 8 + i);
                        }
                        else{
                            moveList.add("" + pos / 8 + (pos % 8 - 1) + pos / 8 + pos % 8 + 0+i);
                        }
                    }
                    comMoves=comMoves ^ (1L << pos);
                }
                comMoves=((elements[i]&~rows[0])>>8)&MOVABLE;
                while (comMoves!=0){
                    int pos=Long.numberOfTrailingZeros(comMoves);
                    long Eaten=(1L<<pos)&COMPUTERPIECE;
                    if(Eaten!=0) {
                        if(i>9) {
                            moveList.add("" + (pos / 8 + 1) + pos % 8 + pos / 8 + pos % 8 + i + "e");
                        }
                        else{
                            moveList.add("" + (pos / 8 + 1) + pos % 8 + pos / 8 + pos % 8 + 0+i + "e");
                        }
                    }
                    else{
                        if(i>9) {
                            moveList.add("" + (pos / 8 + 1) + pos % 8 + pos / 8 + pos % 8 + i);
                        }
                        else{
                            moveList.add("" + (pos / 8 + 1) + pos % 8 + pos / 8 + pos % 8 + 0+ i);
                        }
                    }
                    comMoves=comMoves ^ (1L << pos);
                }
                comMoves=((elements[i]&~rows[7])<<8)&MOVABLE;
                while (comMoves!=0){
                    int pos=Long.numberOfTrailingZeros(comMoves);
                    long Eaten=(1L<<pos)&COMPUTERPIECE;
                    if(Eaten!=0) {
                        if(i>9) {
                            moveList.add("" + (pos / 8 - 1) + pos % 8 + pos / 8 + pos % 8 + i + "e");
                        }
                        else{
                            moveList.add("" + (pos / 8 - 1) + pos % 8 + pos / 8 + pos % 8 + 0+ i + "e");
                        }
                    }
                    else{
                        if(i>9) {
                            moveList.add("" + (pos / 8 - 1) + pos % 8 + pos / 8 + pos % 8 + i);
                        }
                        else {
                            moveList.add("" + (pos / 8 - 1) + pos % 8 + pos / 8 + pos % 8 + 0+ i);
                        }
                    }
                    comMoves=comMoves ^ (1L << pos);
                }
            }
            else{
                long copyScout= elements[1];
                while (copyScout!=0){
                    int currentScoutPos= Long.numberOfTrailingZeros(copyScout);
                    copyScout=copyScout ^ (1L << currentScoutPos);
                    long currentScoutBit= 1L<<currentScoutPos;
                    int row= currentScoutPos/8;
                    int column=currentScoutPos%8;
                    for(int j=1;j<=column;j++){
                        long comMoves=((currentScoutBit&~columns[0])>>j)&MOVABLE;
                        if(comMoves==0){
                            break;
                        }
                        else {
                            if((comMoves&COMPUTERPIECE)!=0){
                                moveList.add(""+currentScoutPos/8+currentScoutPos%8+currentScoutPos/8+(currentScoutPos%8-j)+0+i+"e");
                                break;
                            }
                            else {
                                moveList.add("" + currentScoutPos / 8 + currentScoutPos % 8 + currentScoutPos / 8 + (currentScoutPos % 8 - j) + 0+i );
                            }
                        }
                    }
                    for(int j=1;j<=8-column;j++){
                        long comMoves=((currentScoutBit&~columns[7])<<j)&MOVABLE;
                        if(comMoves==0){
                            break;
                        }
                        else {
                            if((comMoves&COMPUTERPIECE)!=0){
                                moveList.add(""+currentScoutPos/8+currentScoutPos%8+currentScoutPos/8+(currentScoutPos%8+j)+0+i+"e");
                                break;
                            }
                            else {
                                moveList.add("" + currentScoutPos / 8 + currentScoutPos % 8 + currentScoutPos / 8 + (currentScoutPos % 8 + j) + 0+i);
                            }
                        }
                    }
                    for(int j=1;j<=row;j++){
                        long comMoves=((currentScoutBit&~rows[0])>>(j*8))&MOVABLE;
                        if(comMoves==0){
                            break;
                        }
                        else {
                            if((comMoves&COMPUTERPIECE)!=0){
                                moveList.add(""+currentScoutPos/8+currentScoutPos%8+(currentScoutPos/8-j)+currentScoutPos%8+0+i+"e");
                                break;
                            }
                            else {
                                moveList.add(""+currentScoutPos/8+currentScoutPos%8+(currentScoutPos/8-j)+currentScoutPos%8+0+i);
                            }
                        }
                    }
                    for(int j=1;j<=8-row;j++){
                        long comMoves=((currentScoutBit&~rows[7])<<(j*8))&MOVABLE;
                        if(comMoves==0){
                            break;
                        }
                        else {
                            if((comMoves&COMPUTERPIECE)!=0){
                                moveList.add(""+currentScoutPos/8+currentScoutPos%8+(currentScoutPos/8+j)+currentScoutPos%8+0+i+"e");
                                break;
                            }
                            else {
                                moveList.add("" + currentScoutPos / 8 + currentScoutPos % 8 + (currentScoutPos / 8 + j) + currentScoutPos % 8 + 0+i);
                            }
                        }
                    }
                }
            }
        }
        return moveList;
    }
    public static double EvaluateScore(long[] elements){
        int[] comPieceValue= {200,30,25,15,25,50,75,100,200,400,20,10000};
        int[] humanPieceValue= {200,30,25,15,25,50,75,100,200,400,20,10000};
        int[] individualCount=new int[elements.length];
        int totalHuman=0;
        int totalCom=0;
        int highestComputer=0;
        int highestHuman=0;
        double totalScore=0;
        for(int i=0;i<elements.length;i++){
            individualCount[i]=Long.bitCount(elements[i]);
            if(i<12){
                totalHuman+=individualCount[i];
            }
            else {
                totalCom+=individualCount[i];
            }
            if(i<=9&&individualCount[i]>0&&i>highestHuman){
                highestHuman=i;
            }
            if(i>=12&&i<=21&&individualCount[i]>0&&i-12>highestComputer){
                highestComputer=i-12;
            }
        }
        if(individualCount[2]<4){
            humanPieceValue[2]*=(4-individualCount[2]);
        }
        if(individualCount[14]<4){
            comPieceValue[2]*=(4-individualCount[14]);
        }
        if(individualCount[0]==1){
            comPieceValue[9]*=0.8;
        }
        if(individualCount[12]==1){
            comPieceValue[9]*=0.8;
        }
        humanPieceValue[10]=comPieceValue[highestComputer]/2;
        comPieceValue[10]=humanPieceValue[highestHuman]/2;
        for(int i=0;i<elements.length;i++) {
            if(i<12){
                totalScore-=individualCount[i]*humanPieceValue[i];
            }
            else {
                totalScore+=individualCount[i] * comPieceValue[i - 12]*(1+(totalCom-totalHuman)/totalHuman);
            }
        }
        return totalScore;
    }
    public static String mainAlgo(long[] e){
        ArrayList<String> moveArray=getComputerMoves(e);
        if(moveArray.size()==1){
            return moveArray.get(0);
        }
        else {
            double result = Integer.MIN_VALUE;
            int topMove = 0;
            for (int i = 0; i < moveArray.size(); i++) {
                if(moveArray.get(i).substring(0,2).equals(previousMove.substring(2,4))&&moveArray.get(i).substring(2,4).equals(previousMove.substring(0,2))){
                    continue;
                }
                long[] elements = e.clone();
                String x = moveArray.get(i);
                int oldPos = (x.charAt(0) - '0') * 8 + (x.charAt(1) - '0');
                int newPos = (x.charAt(2) - '0') * 8 + (x.charAt(3) - '0');
                int movePiece = Integer.parseInt(x.substring(4, 6));
                if (x.length() == 6) {
                    elements[movePiece] = (elements[movePiece] ^ (1L << oldPos)) | (1L << newPos);
                } else {
                    long bombCheck = elements[10] & (1L << newPos);
                    if (bombCheck != 0) {
                        elements[10] = elements[10] ^ (1L << newPos);
                        if (movePiece == 14) {
                            elements[movePiece] = (elements[movePiece] ^ (1L << oldPos)) | (1L << newPos);
                        } else {
                            elements[movePiece] = (elements[movePiece] ^ (1L << oldPos));
                        }
                    } else {
                        if (movePiece == 12) {
                            long GeneralCheck = elements[9] & (1L << newPos);
                            if (GeneralCheck != 0) {
                                elements[9] = elements[9] ^ (1L << newPos);
                                elements[movePiece] = (elements[movePiece] ^ (1L << oldPos)) | (1L << newPos);
                            } else {
                                elements[movePiece] = elements[movePiece] ^ (1L << oldPos);
                            }
                        } else {
                            long sametype = elements[movePiece - 12] & (1L << newPos);
                            if (sametype != 0) {
                                elements[movePiece] = (elements[movePiece] ^ (1L << oldPos));
                                elements[movePiece - 12] = elements[movePiece - 12] ^ (1L << newPos);
                            } else {
                                boolean larger = true;
                                for (int j = 0; j < movePiece - 12; j++) {
                                    long PieceCheck = elements[j] & (1L << newPos);
                                    if (PieceCheck != 0) {
                                        elements[j] = elements[j] ^ (1L << newPos);
                                        elements[movePiece] = (elements[movePiece] ^ (1L << oldPos)) | (1L << newPos);
                                        larger = false;
                                        break;
                                    }
                                }
                                if (larger) {
                                    elements[movePiece] = elements[movePiece] ^ (1L << oldPos);
                                }
                            }
                        }
                    }
                }
                double score = Algo(elements, 1, false, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                System.out.println(score);
                if (score > result) {
                    result = score;
                    topMove = i;
                }
            }
            return moveArray.get(topMove);
        }
    }
    public static double Algo(long[] e, int depth, boolean computerTurn,double lower,double upper){
        count++;
        if (computerTurn) {
            ArrayList<String> moveArray = getComputerMoves(e);
            double result = Integer.MIN_VALUE;
            for (int i = 0; i < moveArray.size(); i++) {
                long[] elements=e.clone();
                if (depth == MAX_DEPTH) {
                    return EvaluateScore(elements);
                }
                String x = moveArray.get(i);
                int oldPos = (x.charAt(0) - '0') * 8 + (x.charAt(1) - '0');
                int newPos = (x.charAt(2) - '0') * 8 + (x.charAt(3) - '0');
                int movePiece = Integer.parseInt(x.substring(4, 6));
                if (x.length() == 6) {
                    elements[movePiece] = (elements[movePiece] ^ (1L << oldPos)) | (1L << newPos);
                } else {
                    long bombCheck = elements[10] & (1L << newPos);
                    if (bombCheck != 0) {
                        elements[10] = elements[10] ^ (1L << newPos);
                        if (movePiece == 14) {
                            elements[movePiece] = (elements[movePiece] ^ (1L << oldPos)) | (1L << newPos);
                        } else {
                            elements[movePiece] = (elements[movePiece] ^ (1L << oldPos));
                        }
                    } else {
                        if (movePiece == 12) {
                            long GeneralCheck = elements[9] & (1L << newPos);
                            if (GeneralCheck != 0) {
                                elements[9] = elements[9] ^ (1L << newPos);
                                elements[movePiece] = (elements[movePiece] ^ (1L << oldPos)) | (1L << newPos);
                            } else {
                                elements[movePiece] = elements[movePiece] ^ (1L << oldPos);
                            }
                        } else {
                            long sametype = elements[movePiece - 12] & (1L << newPos);
                            if (sametype != 0) {
                                elements[movePiece] = (elements[movePiece] ^ (1L << oldPos));
                                elements[movePiece - 12] = elements[movePiece - 12] ^ (1L << newPos);
                            } else {
                                boolean larger = true;
                                for (int j = 0; j < movePiece - 12; j++) {
                                    long PieceCheck = elements[j] & (1L << newPos);
                                    if (PieceCheck != 0) {
                                        elements[j] = elements[j] ^ (1L << newPos);
                                        elements[movePiece] = (elements[movePiece] ^ (1L << oldPos)) | (1L << newPos);
                                        larger = false;
                                        break;
                                    }
                                }
                                if (larger) {
                                    elements[movePiece] = elements[movePiece] ^ (1L << oldPos);
                                }
                            }
                        }
                    }
                }
                double score = Algo(elements, depth + 1, false,lower,upper);
                result = Math.max(score, result);
                lower=Math.max(score,lower);
                if(lower>upper)break;
            }
            return result;
        }
        else{
            ArrayList<String> moveArray = getHumanMoves(e);
            double result = Integer.MAX_VALUE;
            for (int i = 0; i < moveArray.size(); i++) {
                long[] elements=e.clone();
                if (depth == MAX_DEPTH) {
                    return EvaluateScore(elements);
                }
                String x = moveArray.get(i);
                int oldPos = (x.charAt(0) - '0') * 8 + (x.charAt(1) - '0');
                int newPos = (x.charAt(2) - '0') * 8 + (x.charAt(3) - '0');
                int movePiece = Integer.parseInt(x.substring(4, 6));
                if (x.length() == 6) {
                    elements[movePiece] = (elements[movePiece] ^ (1L << oldPos)) | (1L << newPos);
                } else {
                    long bombCheck = elements[22] & (1L << newPos);
                    if (bombCheck != 0) {
                        elements[22] = elements[22] ^ (1L << newPos);
                        if (movePiece == 2) {
                            elements[movePiece] = (elements[movePiece] ^ (1L << oldPos)) | (1L << newPos);
                        } else {
                            elements[movePiece] = (elements[movePiece] ^ (1L << oldPos));
                        }
                    } else {
                        if (movePiece == 0) {
                            long GeneralCheck = elements[21] & (1L << newPos);
                            if (GeneralCheck != 0) {
                                elements[21] = elements[21] ^ (1L << newPos);
                                elements[movePiece] = (elements[movePiece] ^ (1L << oldPos)) | (1L << newPos);
                            } else {
                                elements[movePiece] = elements[movePiece] ^ (1L << oldPos);
                            }
                        } else {
                            long sametype = elements[movePiece + 12] & (1L << newPos);
                            if (sametype != 0) {
                                elements[movePiece] = (elements[movePiece] ^ (1L << oldPos));
                                elements[movePiece + 12] = elements[movePiece + 12] ^ (1L << newPos);
                            } else {
                                boolean larger = true;
                                for (int j = 12; j < movePiece + 12; j++) {
                                    long PieceCheck = elements[j] & (1L << newPos);
                                    if (PieceCheck != 0) {
                                        elements[j] = elements[j] ^ (1L << newPos);
                                        elements[movePiece] = (elements[movePiece] ^ (1L << oldPos)) | (1L << newPos);
                                        larger = false;
                                        break;
                                    }
                                }
                                if (larger) {
                                    elements[movePiece] = elements[movePiece] ^ (1L << oldPos);
                                }
                            }
                        }
                    }
                }
                double score = Algo(elements, depth + 1, true,lower,upper);
                result = Math.min(score, result);
                upper =Math.min(upper,score);
                if(lower>upper)break;
            }
            return result;
        }
    }


    //    int i, j, x;
//    boolean notE = true;
//        List<List<Position>> moveset= getAllMove(AIboardView,0);
//        for(i=0;i<moveset.get(0).size();i++) {
//            System.out.println(moveset.get(0).get(i));
//            System.out.println(moveset.get(1).get(i));
//        }
//        List<Integer> moveNum=new ArrayList<Integer>();
//        List<int[][]> preBoard1 = new ArrayList<int[][]>() {};
////        List<Integer> grade1 = new ArrayList<Integer>();
//        for(i=0;i<moveset.get(0).size();i++){
//            preBoard1.add(test(AIboardView, moveset.get(0).get(i), moveset.get(1).get(i)));
//            moveNum.add(i);
//        }
//
//        List<int[][]> preBoard2= new ArrayList<int[][]>();
//        List<Integer> moveNum2= new ArrayList<Integer>();
//
//        generateGuess(preBoard1,moveNum,preBoard2,moveNum2,1);
//        System.out.println("a"+moveNum2.size());
//        List<int[][]> preBoard3= new ArrayList<int[][]>();
//        List<Integer> moveNum3= new ArrayList<Integer>();
//
//        generateGuess(preBoard2,moveNum2,preBoard3,moveNum3,0);
//        System.out.println("b"+moveNum3.size());
//        List<int[][]> preBoard4= new ArrayList<int[][]>();
//        List<Integer> moveNum4= new ArrayList<Integer>();
//        generateGuess(preBoard3,moveNum3,preBoard4,moveNum4,1);
//        System.out.println("c"+moveNum4.size());
////        List<int[][]> preBoard5= new ArrayList<int[][]>();
////        List<Integer> moveNum5= new ArrayList<Integer>();
////        generateGuess(preBoard4,moveNum4,preBoard5,moveNum5,0);
////        System.out.println("d"+moveNum5.size());
//        List<int[][]> preBoard5= new ArrayList<int[][]>();
//        List<Integer> moveNum5= new ArrayList<Integer>();
//        for(x=0;x<preBoard4.size();x++){
//            List<List<Position>> moveset5= getAllMove(preBoard4.get(x),1);
//            int m,n;
//            for(i=0;i<moveset5.get(0).size();i++){
//                preBoard5.add(test(preBoard4.get(x), moveset5.get(0).get(i), moveset5.get(1).get(i)));
//                moveNum5.add(moveNum4.get(x));
//            }
//        }
//        System.out.println("f"+moveNum5.size());
//        List<Integer> grade5 = new ArrayList<Integer>();
//        for (i=0;i<preBoard5.size();i++){
//            grade5.add(GradeTable(preBoard5.get(i)));
//        }
//        List<Integer> gradeCopy5= new ArrayList<Integer>(grade5);
//        Collections.sort(gradeCopy5);
//        Collections.reverse(gradeCopy5);
//        int finGraBoard=0;
//        for(i=0;i<grade5.size();i++){
//            if(grade5.get(i)==gradeCopy5.get(0)){
//                finGraBoard=i;
//                break;
//            }
//        }
//        System.out.println(grade5.size());
//        Position finalMoveIni= moveset.get(0).get(moveNum5.get(finGraBoard));
//        Position finalMoveFin= moveset.get(1).get(moveNum5.get(finGraBoard));
//        OpponentMove(finalMoveIni,finalMoveFin);
//        OppoTurn+=1;
//        for(i=0;i<10;i++){
//            System.out.println();
//            for (j=0;j<10;j++){
//                System.out.print(Main.board[i][j]+" ");
//            }
//        }
//        for(i=0;i<10;i++){
//            System.out.println();
//            for (j=0;j<10;j++){
//                System.out.print(AIboardView[i][j]+" ");
//            }
//        }
//        Main.turn*=-1;
//    }
//    public static List<List<Position>> getAllMove(int[][] TheBoard,int turn){
//        ArrayList<Position> allMoveIni= new ArrayList<>();
//        ArrayList<Position> allMovefin= new ArrayList<>();
//        int[][] board= new int[10][10];
//        int i,j;
//        for(i=0;i<10;i++){
//            for (j=0;j<10;j++){
//                board[i][j]= TheBoard[i][j];
//            }
//        }
//        if(turn==1){
//            for(i=0;i<10;i++){
//                for (j=0;j<10;j++){
//                    if(board[i][j]>20){
//                        board[i][j]-=20;
//                    }
//                    else if(board[i][j]>0&&board[i][j]<20){
//                        board[i][j]+=20;
//                    }
//                }
//            }
//        }
//        for(i=0;i<10;i++){
//            for (j=0;j<10;j++){
//                if(board[i][j]>20&&board[i][j]<31){
//                    if(board[i][j]!=22) {
//                        if (j < 9 && board[i][j + 1] < 20 && board[i][j + 1] >= 0) {
//                            allMoveIni.add(new Position(i, j));
//                            allMovefin.add(new Position(i, j + 1));
//                        }
//                        if (j > 0 && board[i][j - 1] < 20 && board[i][j - 1] >= 0) {
//                            allMoveIni.add(new Position(i, j));
//                            allMovefin.add(new Position(i, j - 1));
//                        }
//                        if (i > 0 && board[i - 1][j] < 20 && board[i - 1][j] >= 0) {
//                            allMoveIni.add(new Position(i, j));
//                            allMovefin.add(new Position(i - 1, j));
//                        }
//                        if (i < 9 && board[i + 1][j] < 20 && board[i + 1][j] >= 0) {
//                            allMoveIni.add(new Position(i, j));
//                            allMovefin.add(new Position(i + 1, j));
//                        }
//                    }
//                    else {
//                        int k;
//                        for(k=j+1;k<10;k++){
//                            if(board[i][k]!=0){
//                                if(board[i][k]<20&& board[i][k]>0) {
//                                    allMoveIni.add(new Position(i, j));
//                                    allMovefin.add(new Position(i, k));
//                                }
//                                break;
//                            }
//                            else{
//                                allMoveIni.add(new Position(i,j));
//                                allMovefin.add(new Position(i,k));
//                            }
//                        }
//                        for(k=j-1;k>=0;k--){
//                            if(board[i][k]!=0){
//                                if(board[i][k]>0&&board[i][k]<20){
//                                    allMoveIni.add(new Position(i,j));
//                                    allMovefin.add(new Position(i,k));
//                                }
//                                break;
//
//                            }
//                            else{
//                                allMoveIni.add(new Position(i,j));
//                                allMovefin.add(new Position(i,k));
//                            }
//                        }
//                        for(k=i+1;k<10;k++){
//                            if(board[k][j]!=0){
//                                if(board[k][j]>=0&&board[k][j]<20) {
//                                    allMoveIni.add(new Position(i, j));
//                                    allMovefin.add(new Position(k, j));
//                                }
//                                break;
//                            }
//                            else{
//                                allMoveIni.add(new Position(i, j));
//                                allMovefin.add(new Position(k, j));
//                            }
//                        }
//                        for(k=i-1;k>=0;k--){
//                            if(board[k][j]!=0){
//                                if(board[k][j]<20&& board[k][j]>=0) {
//                                    allMoveIni.add(new Position(i, j));
//                                    allMovefin.add(new Position(k, j));
//                                }
//                                break;
//                            }
//                            else{
//                                allMoveIni.add(new Position(i, j));
//                                allMovefin.add(new Position(k, j));
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        List<List<Position>> moveset= new ArrayList<List<Position>>();
//        moveset.add(allMoveIni);
//        moveset.add(allMovefin);
//        return moveset;
//    }
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
//    public static int GradeTable(int[][] board){
//        int i,j,rowOurFlag=0,columnOurFlag=0;
//        for(i=0;i<10;i++){
//            for (j=0;j<10;j++){
//               if(board[i][j]==32){
//                   rowOurFlag=i;
//                   columnOurFlag=j;
//                   break;
//               }
//            }
//        }
//        int flagSafety=0;
//        for(i=Math.max(rowOurFlag-2,0);i<=Math.min(rowOurFlag+2,9);i++){
//            for(j=Math.max(columnOurFlag-2,0);j<=Math.min(columnOurFlag+2,9);j++){
//                if(board[i][j]>0&&board[i][j]<12){
//                    flagSafety-=(int)(1.0/(Math.abs(rowOurFlag-i)+Math.abs(columnOurFlag-j))*4*board[i][j]);
//                }
//                else if(board[i][j]>20&&board[i][j]<32){
//                    flagSafety+=(int)(1.0/(Math.abs(rowOurFlag-i)+Math.abs(columnOurFlag-j))*4*(board[i][j]-20));
//                }
//            }
//        }
//        int tableValue=0;
//        for(i=0;i<10;i++){
//            for(j=0;j<10;j++){
//                if(board[i][j]>20&&board[i][j]!=22) {
//                    tableValue +=Math.pow(board[i][j]-20,2)/10;
//                }
//                else {
//                    tableValue-=Math.pow(board[i][j],2)/10;
//                }
//            }
//        }
//
//        return 2*tableValue+flagSafety;
//    }
//    public static int[][] test(int[][] theBoard, Position i, Position f){
//        int[][] board = new int[theBoard.length][theBoard[0].length];
//        for(int x=0; x<theBoard.length; x++) {
//            for (int y = 0; y < theBoard[x].length; y++) {
//                board[x][y] = theBoard[x][y];
//            }
//        }
//        if(board[f.row][f.column]>0){
//            if((board[i.row][i.column]-20)>board[f.row][f.column])
//                board[f.row][f.column]=board[i.row][i.column];
//        }
//        else {
//            board[f.row][f.column]=board[i.row][i.column];
//        }
//        board[i.row][i.column]=0;
//        return board;
//    }
    public static void OpponentMove(Position ini,Position fin){
        drawArrow(ini,fin);
        int movePiece= Main.board[ini.getRow()][ini.getColumn()];
        int destination= Main.board[fin.row][fin.column];
        int oldRow=ini.row;
        int oldColumn=ini.column;
        int newRow=fin.row;
        int newColumn=fin.column;
        Main.board[ini.getRow()][ini.getColumn()]=0;
        AIboardView[ini.getRow()][ini.getColumn()]=0;
        if(Main.board[fin.getRow()][fin.getColumn()]==0){
            Main.board[fin.getRow()][fin.getColumn()]=movePiece;
            AIboardView[fin.getRow()][fin.getColumn()]=movePiece;
        }
        else {
            int predictIMover=0;
            for (int k=0;k<Opponent.predict.size();k++){
                if(Opponent.predict.get(k).p.row==newRow&&Opponent.predict.get(k).p.column==newColumn){
                    predictIMover=k;
                    break;
                }
            }
            if(destination<(movePiece-20)){
                if(movePiece==30&&destination==1){
                    predict.remove(predictIMover);
                    Main.remainingPiece[21]-=1;
                }
                else {
                    Main.remainingPiece[destination-1]-=1;
                    Main.board[fin.getRow()][fin.getColumn()] = movePiece;
                    AIboardView[fin.getRow()][fin.getColumn()] = movePiece;
                    int i, indexattacked = 0;
                    for (i = 0; i < Main.pieces.length; i++) {
                        if (Main.pieces[i].currentPos.row == fin.getRow() && Main.pieces[i].currentPos.column == fin.getColumn()) {
                            indexattacked = i;
                            break;
                        }
                    }
                    Main.root.getChildren().remove(Main.pieces[indexattacked]);AIboardView[newRow][newColumn]=1;
                    predict.get(predictIMover).AILevel=1;
                    predict.get(predictIMover).unKnow=false;
                }
            }
            else if(destination==(movePiece-20)){
                Main.remainingPiece[destination-1]-=1;
                Main.remainingPiece[movePiece-9]-=1;
                predict.remove(predictIMover);
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
            else{
                if(Main.board[fin.getRow()][fin.getColumn()]==11){
                    Main.remainingPiece[10]-=1;
                    if(movePiece==23){
                        predict.remove(predictIMover);
                    }
                    else {
                        Main.remainingPiece[movePiece-9]-=1;
                        predict.remove(predictIMover);
                        Main.board[fin.getRow()][fin.getColumn()] = 0;
                        AIboardView[fin.getRow()][fin.getColumn()] = 0;
                        int i, indexattacked = 0;
                        for (i = 0; i < Main.pieces.length; i++) {
                            if (Main.pieces[i].currentPos.row == fin.getRow() && Main.pieces[i].currentPos.column == fin.getColumn()) {
                                indexattacked = i;
                                break;
                            }
                        }
                        Main.root.getChildren().remove(Main.pieces[indexattacked]);
                    }
                }
                else{
                    if(movePiece==21&&destination==10){
                        Main.remainingPiece[9]-=1;
                        predict.remove(predictIMover);
                    }
                    else {
                        Main.remainingPiece[movePiece-9]-=1;
                        predict.get(predictIMover).AILevel = destination;
                        predict.get(predictIMover).unKnow = false;
                    }
                }
            }
            totalUKnowScore-=destination;
            totalUnKnowPiece-=1;
            for(int x=0;x<predict.size();x++){
                if(predict.get(x).unKnow){
                    predict.get(x).AILevel= totalUKnowScore/totalUnKnowPiece;
                    AIboardView[predict.get(x).p.getRow()][predict.get(x).p.getColumn()]=predict.get(x).AILevel;
                }
            }
        }
        //        int movePiece=Main.board[oldRow][oldColumn];
//        int destination=Main.board[newRow][newColumn];
//        Main.board[oldRow][oldColumn]=0;
//        AIboardView[oldRow][oldColumn]=0;
//        if(move.length()==6){
//            Main.board[newRow][newColumn]=movePiece;
//        }
//        else {
//            int predictIMover=0;
//            for (int k=0;k<Opponent.predict.size();k++){
//                if(Opponent.predict.get(k).p.row==newRow&&Opponent.predict.get(k).p.column==newColumn){
//                    predictIMover=k;
//                    break;
//                }
//            }
//            if (movePiece - 20 == destination) {
//                Main.board[oldRow][oldColumn] = 0;
//                AIboardView[oldRow][oldColumn] =0;
//                predict.remove(predictIMover);
//            } else if ((movePiece - 20) > destination) {
//                if (!(movePiece == 30 && destination == 1)) {
//                    Main.board[newRow][newColumn] = movePiece;
//                    AIboardView[newRow][newColumn] =movePiece;
//                    predict.remove(predictIMover);
//                }
//                else{
//                    AIboardView[newRow][newColumn]=1;
//                    predict.get(predictIMover).AILevel=1;
//                    predict.get(predictIMover).unKnow=false;
//
//                }
//            } else {
//                if (movePiece == 23 && destination == 11) {
//                    Main.board[newRow][newColumn] = movePiece;
//                    AIboardView[newRow][newColumn]=movePiece;
//                    predict.remove(predictIMover);
//                } else if (movePiece == 21 && destination == 10) {
//                    Main.board[newRow][newColumn] = movePiece;
//                    AIboardView[newRow][newColumn]=movePiece;
//                    predict.remove(predictIMover);
//                }
//                else {
//                    if(destination==11){
//                        predict.remove(predictIMover);
//                        Main.board[newRow][newColumn]=0;
//                        AIboardView[newRow][newColumn]=0;
//                    }
//                    if (destination==12){
//                        System.out.println("you lose");
//                    }
//                    else {
//                        AIboardView[newRow][newColumn] = destination;
//                        predict.get(predictIMover).AILevel = destination;
//                        predict.get(predictIMover).unKnow = false;
//                    }
//                }
//            }
//            totalUKnowScore-=destination;
//            totalUnKnowPiece-=1;
//            for(int x=0;x<predict.size();x++){
//                if(predict.get(x).unKnow){
//                    predict.get(x).AILevel= totalUKnowScore/totalUnKnowPiece;
//                }
//            }
//        }
        for (int h = 0; h < 8; h++) {
            for (int k = 0; k < 8; k++) {
                if (h == 3 || h == 4) {
                    if (k == 2 || k == 1 || k == 6 || k == 5) {
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
//    public static void generateGuess(List<int[][]> preBoard1,List<Integer> moveNum,List<int[][]> preBoard2,List<Integer> moveNum2,int turn){
//        int x,i,j;
//        for(x=0;x<preBoard1.size();x++){
//            List<List<Position>> moveset2= getAllMove(preBoard1.get(x),turn);
//            for(i=0;i<moveset2.get(0).size();i++){
//                preBoard2.add(test(preBoard1.get(x), moveset2.get(0).get(i), moveset2.get(1).get(i)));
//                moveNum2.add(moveNum.get(x));
//            }
//        }
//    }
}

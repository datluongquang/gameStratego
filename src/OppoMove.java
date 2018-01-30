import javafx.scene.paint.Color;

public class OppoMove {
    public OppoMove(Position ini,Position fin){
        int a= Main.board[ini.getRow()][ini.getColumn()];
        Main.board[ini.getRow()][ini.getColumn()]=0;
        if(Main.board[fin.getRow()][fin.getColumn()]==0){
            Main.board[fin.getRow()][fin.getColumn()]=a;
        }
        else {
            if(Main.board[fin.getRow()][fin.getColumn()]<(a-20)){
                Main.board[fin.getRow()][fin.getColumn()]=a;
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

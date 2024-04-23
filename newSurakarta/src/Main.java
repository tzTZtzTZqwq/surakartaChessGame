import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {



        int p1WinCount = 0;

        for(int j=1;j<=5000;j++){
            Grid gameGrid = new Grid(6,6);
            GridDrawer gameGridDrawer = new GridDrawer(gameGrid);
            gameGrid.generateSurakartaPath();
            ExamplePlayer player1 = new ExamplePlayer(gameGrid,"p1",1);
            ExamplePlayer2 player2 = new ExamplePlayer2(gameGrid,"p2",2);
        for(int i=1;i<=100;i++){

            if(!player1.nextMove()){
                //System.out.println("p2 win");
                break;
            }
            //gameGridDrawer.printGrid();
            if(gameGrid.checkGameStatus()){
                p1WinCount+=1;
                break;
            }
            if(!player2.nextMove()){
                //System.out.println("p1 win");
                p1WinCount += 1;
                break;
            }
            if(gameGrid.checkGameStatus()){
                break;
            }
        }}

        System.out.println((double) p1WinCount/5000);
/*

        for(int i=0;i<=5;i++){
            gameGrid.findNode(i,4).status=1;
            gameGrid.findNode(i,5).status=1;
            gameGrid.findNode(i,0).status=2;
            gameGrid.findNode(i,1).status=2;
        }
        gameGrid.findNode(0,1).status=0;
        gameGrid.findNode(1,0).status=0;
        gameGrid.findNode(1,1).status=0;
        gameGrid.findNode(0,4).status=0;
        gameGrid.findNode(1,5).status=0;
        gameGrid.findNode(2,4).status=0;
        gameGrid.findNode(2,1).status=0;

        gameGridDrawer.highlight(gameGrid.getAvalablePath(gameGrid.findNode(3,1)).get(0),"red");
        gameGridDrawer.highlight(gameGrid.getAvalablePath(gameGrid.findNode(3,1)).get(1),"yellow");
        gameGridDrawer.highlight(gameGrid.getAvalablePath(gameGrid.findNode(3,1)).get(2),"green");


        gameGridDrawer.printGrid();
*/
    }
}
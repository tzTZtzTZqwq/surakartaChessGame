import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Grid gameGrid = new Grid(6,6);
        GridDrawer gameGridDrawer = new GridDrawer(gameGrid);
        gameGrid.generateSurakartaPath();

        ExamplePlayer player1 = new ExamplePlayer(gameGrid,"p1",1);
        ExamplePlayer player2 = new ExamplePlayer(gameGrid,"p2",2);
        for(int i=0;i<=gameGrid.totalColumn;i++){
            gameGrid.findNode(i,0).status=1;
            gameGrid.findNode(i,1).status=1;
            gameGrid.findNode(i,4).status=2;
            gameGrid.findNode(i,5).status=2;
        }
        gameGridDrawer.printGrid();
        for(int i=1;i<=20;i++){
            player1.nextMove();
            //gameGridDrawer.printGrid();
            System.out.println("-------------");
            player2.nextMove();
            //gameGridDrawer.printGrid();
            System.out.println("-------------");
        }


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
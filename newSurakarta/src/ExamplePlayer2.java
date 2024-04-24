import java.util.*;

public class ExamplePlayer2 extends GamePlayer{
    GridDrawer Gd = new GridDrawer(gameGrid);

    Random rand = new Random();

    ExamplePlayer2(Grid gameGrid, String name, int side) {
        super(gameGrid, name, side);
    }

    public Boolean nextMove(){
        ArrayList<Node> myNode = new ArrayList<>();
        for(int i=0;i<=gameGrid.totalColumn;i++){
            for(int j=0;j<= gameGrid.totalRow;j++){
                if(gameGrid.findNode(i,j).status == side){
                    myNode.add(gameGrid.findNode(i,j));
                }
            }
        }
        Collections.shuffle(myNode);

        for(Node thisNode:myNode){
            ArrayList<ArrayList<Node>> AllPossibleEnd = gameGrid.getAvailablePath(thisNode);
            ArrayList<Node> possibleEnds = new ArrayList<>();
            //possibleEnds.addAll(AllPossibleEnd.get(0));
            possibleEnds.addAll(AllPossibleEnd.get(1));
            Collections.shuffle(possibleEnds);
            if(!possibleEnds.isEmpty()){
                return (gameGrid.moveChess(thisNode,possibleEnds.get(0)));
            }

        }

        for(Node thisNode:myNode){
            ArrayList<ArrayList<Node>> AllPossibleEnd = gameGrid.getAvailablePath(thisNode);
            ArrayList<Node> possibleEnds = new ArrayList<>();
            //possibleEnds.addAll(AllPossibleEnd.get(0));
            possibleEnds.addAll(AllPossibleEnd.get(0));
            Collections.shuffle(possibleEnds);
            if(!possibleEnds.isEmpty()){
                return (gameGrid.moveChess(thisNode,possibleEnds.get(0)));
            }

        }
        return false;
    }
}

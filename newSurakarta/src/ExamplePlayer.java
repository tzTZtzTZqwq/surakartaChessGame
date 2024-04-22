import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class ExamplePlayer extends GamePlayer{
    GridDrawer Gd = new GridDrawer(gameGrid);

    Random rand = new Random();
    class statNode extends Node{

        statNode(int column, int row) {
            super(column, row);
        }

        statNode(Node node) {
            super(node.column, node.row, node.status);
            this.railwayID = node.railwayID;
        }

        Boolean IsProtected = false;
        Boolean IsCaptured = false;

        public String toString(){
            String result = new String();
            result = "column:" + Integer.toString(column) + " row:" + Integer.toString(row) + " status:" + Integer.toString(status) + " rail" + Integer.toString(railwayID) + " protected " + (IsProtected?"yes":"no")  + " captured " + (IsCaptured?"yes":"no");;
            return result;
        }
    }

    ExamplePlayer(Grid gameGrid, String name, int side) {
        super(gameGrid, name, side);
    }


    /**
     *
     * @return returns if successfully moved
     */
    public Boolean nextMove(){

        ArrayList<ArrayList<statNode>> nodeStatus = new ArrayList<ArrayList<statNode>>();
        for(int i=0;i<=gameGrid.totalColumn;i++){
            nodeStatus.add(new ArrayList<statNode>());
            for(int j=0;j<=gameGrid.totalRow;j++){
                statNode newNode = new statNode(gameGrid.findNode(i,j));
                nodeStatus.get(i).add(newNode);
            }
        }
        ArrayList<ArrayList<Node>> res = new ArrayList<ArrayList<Node>>();
        for(int i=0;i<=gameGrid.totalColumn;i++){
            for(int j=0;j<=gameGrid.totalRow;j++){
                if(gameGrid.findNode(i,j).status != side && gameGrid.findNode(i,j).status != 0){
                    res = gameGrid.getAvalablePath(gameGrid.findNode(i,j));
                    for(Node thisNode:res.get(2)){
                        nodeStatus.get(thisNode.column).get(thisNode.row).IsProtected = true;
                    }
                    for(Node thisNode:res.get(0)){
                        nodeStatus.get(thisNode.column).get(thisNode.row).IsProtected = true;
                    }
                    for(Node thisNode:res.get(1)){
                        nodeStatus.get(thisNode.column).get(thisNode.row).IsProtected = true;
                    }
                }
            }
        }

        ArrayList<ArrayList<Node>> moves = new ArrayList<ArrayList<Node>>();
        for(int i=1;i<=5;i++){
            moves.add(new ArrayList<Node>());
        }
        for(int i=0;i<=gameGrid.totalColumn;i++){
            for(int j=0;j<=gameGrid.totalRow;j++){
                if(!nodeStatus.get(i).get(j).IsProtected && nodeStatus.get(i).get(j).status!=side && nodeStatus.get(i).get(j).status!=0){
                    moves.get(0).add(nodeStatus.get(i).get(j));//can capture,not protected,good move
                }else if(nodeStatus.get(i).get(j).IsProtected && nodeStatus.get(i).get(j).status!=side && nodeStatus.get(i).get(j).status!=0){
                    moves.get(1).add(nodeStatus.get(i).get(j));//can capture,is protected,risky move
                }else if(!nodeStatus.get(i).get(j).IsProtected && nodeStatus.get(i).get(j).status==0){
                    moves.get(2).add(nodeStatus.get(i).get(j));//can not capture,not protected,normal move
                }else if(nodeStatus.get(i).get(j).IsProtected && nodeStatus.get(i).get(j).status==0){
                    moves.get(3).add(nodeStatus.get(i).get(j));//can not capture,is protected,bad move
                }
            }
        }

        Gd.clearAllHighlight();
        String[] colors = new String[]{"blue","green","yellow","red"};
        for(int i=0;i<=4;i++){
            for(Node thisNode:moves.get(i)) {
                Gd.highlight(thisNode.column,thisNode.row, colors[i]);
            }
        }
        Gd.printGrid();

        for(int i=0;i<moves.size();i++){
            for(Node thisNode:moves.get(i)){
                if(thisNode.status!=side){
                    Node newNode = new Node(thisNode.column, thisNode.row);
                    newNode.status = -1;
                    newNode.railwayID = thisNode.railwayID;
                    ArrayList<ArrayList<Node>> possibleStart = gameGrid.getAvalablePath(newNode);
                    Collections.shuffle(possibleStart);
                    for(Node startNode: possibleStart.get(1)){
                        ArrayList<Node> possibleNodes = new ArrayList<Node>();
                        if(startNode.status == side){
                            possibleNodes.add(startNode);
                        }
                        if(!possibleNodes.isEmpty()){
                            startNode = possibleNodes.get((int) (possibleNodes.size()*rand.nextDouble()));
                            if(gameGrid.moveChess(gameGrid.findNode(startNode.column,startNode.row),gameGrid.findNode(thisNode.column,thisNode.row))){
                                return true;
                            }else{
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}

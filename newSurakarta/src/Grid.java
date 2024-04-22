import java.util.ArrayList;
import java.util.HashMap;

public class Grid {
    private static final Offset[] DIRECTIONS8 = {
            new Offset(-1, -1), new Offset(-1, 0), new Offset(-1, 1),
            new Offset(0, -1), new Offset(0, 1),
            new Offset(1, -1), new Offset(1, 0), new Offset(1, 1)
    };
    int columnSize;
    int rowSize;
    int totalColumn;//竖列
    int totalRow;//横行
    ArrayList<ArrayList<Node>> nodes = new ArrayList<ArrayList<Node>>();

    ArrayList<ArrayList<Node>> railways = new ArrayList<ArrayList<Node>>();

    Grid(int columnSize, int rowSize){
        this.columnSize = columnSize;
        this.rowSize = rowSize;
        totalColumn = columnSize-1;
        totalRow = rowSize-1;
        for(int i=0;i<columnSize;i++){
            nodes.add(new ArrayList<Node>());
            for(int j=0;j<rowSize;j++){
                nodes.get(i).add(new Node(i,j));
            }
        }
    }

    /**
     *
     * @param column pos
     * @param row pos
     * @return if pos is inside the grid
     */
    public boolean isLegalPosition(int column,int row){
        return(column >= 0 && column <= totalColumn && row >= 0 && row <= totalRow);
    }

    /**
     *
     * @param column pos
     * @param row pos
     * @return Node
     */
    public Node findNode(int column, int row){
        return nodes.get(column).get(row);
    }
    public Node mirrowNode(int column, int row, int mode){
        switch (mode){
            case(1): //midRow
                return findNode(totalColumn-column,row);
            case(2): //midCol
                return findNode(column,totalRow-row);
            case(3): //y=x
                return findNode(totalRow-row,totalColumn-column);
            case(4): //y=-x
                return findNode(row,column);
            case(5): //rotate clockwise 90
                return findNode(totalRow-row,column);
            default:
                return nodes.get(column).get(row);
        }
    }
    public Node mirrowNode(Node thisNode, int mode){
        return mirrowNode(thisNode.column, thisNode.row, mode);
    }

    /**
     * @param thisNode Node beginningNode
     *
     * @return ArrayList[0] normalMoveDestination node.status=0
     *         ArrayList[1] captureMoveDestination node.status!=thisNode.status && node.status!=0
     *         ArrayList[2] ProtectedMove node.status=thisNode.status
     */
    public ArrayList<ArrayList<Node>> getAvalablePath(Node thisNode){

        ArrayList<Node> normalMove = new ArrayList<Node>();
        ArrayList<Node> captureMove = new ArrayList<Node>();
        ArrayList<Node> protectedMove = new ArrayList<Node>();
        ArrayList<ArrayList<Node>> result = new ArrayList<ArrayList<Node>>();
        HashMap<Node,Integer> visited = new HashMap<Node,Integer>();//if a node has been checked

        //check if the node is on a chain, a node may appear on multiple chains
        for(ArrayList<Node> thisRail:railways){
            for(Node node:thisRail){
                //find node's index on the chain, a node may appear twice on a chain
                if(node.isSamePosition(thisNode)){
                    ArrayList<Node> railway = thisRail;
                    for(int i=0;i<railway.size();i++){
                        if(railway.get(i).column == thisNode.column && railway.get(i).row == thisNode.row) {
                            //get approachable nodes clockwise, a single node is checked once maximum
                            int j = i;
                            j = (j + 1) % railway.size();
                            while (visited.get(j) == null && railway.get(j).status == 0) {
                                normalMove.add(railway.get(j));
                                j = (j + 1) % railway.size();
                            }
                            if (visited.get(j) != null) {

                            } else if (railway.get(j).status == thisNode.status && !railway.get(j).equals(thisNode)) {
                                protectedMove.add(railway.get(j));
                            } else if (railway.get(j).status != thisNode.status) {
                                captureMove.add(railway.get(j));
                            }
                            //get approachable nodes counterclockwise, a single node is checked once maximum
                            j = i;
                            j = j==0?railway.size()-1:(j - 1);
                            while (visited.get(j) == null && railway.get(j).status == 0) {
                                normalMove.add(railway.get(j));
                                j = j==0?railway.size()-1:(j - 1);
                            }
                            if (visited.get(j) != null) {

                            } else if (railway.get(j).status == thisNode.status && !railway.get(j).equals(thisNode)) {
                                protectedMove.add(railway.get(j));
                            } else if (railway.get(j).status != thisNode.status) {
                                captureMove.add(railway.get(j));
                            }
                        }
                    }
                    break;
                }
            }
        }

        //check adjacent pos
        for(Offset thisOffset:DIRECTIONS8){
            if(isLegalPosition(thisNode.column+thisOffset.x,thisNode.row+thisOffset.y)){
                Node newNode = findNode(thisNode.column+thisOffset.x,thisNode.row+thisOffset.y);
                if(newNode.status==0 && visited.get(newNode) == null) {
                    normalMove.add(newNode);
                }else if(newNode.status==thisNode.status && visited.get(newNode) == null) {
                    protectedMove.add(newNode);
                }else {
                    captureMove.add(newNode);
                }
            }
        }


        result.add(normalMove);
        result.add(captureMove);
        result.add(protectedMove);
        return result;
    }

    /**
     * railways[n] is a chain of nodes on the nth railway, one node might appear multiple times
     * the nth railway is thd nth railway from outside to inside
     */
    public void generateSurakartaPath(){
        for(int i=1;i<=totalColumn/2;i++){
            railways.add(new ArrayList<Node>());
            for(int j=0;j<=totalColumn;j++) {
                railways.get(i-1).add(findNode(j,i));
                findNode(j,i).railwayID=i-1;
            }
            for(int j=0;j<=totalColumn*3+3;j++){
                railways.get(i-1).add(mirrowNode(railways.get(i-1).get(j),5));
                mirrowNode(railways.get(i-1).get(j),5).railwayID=i-1;
            }
        }
    }

    /**
     *
     * @param from Node
     * @param to Node
     * @return returns if it is a legal move
     */
    public Boolean moveChess(Node from,Node to){
        if(from.status == 0){
            return false;
        }else{
            if(to.status == from.status){
                return false;
            }else{
                to.status= from.status;
                from.status = 0;
                return true;
            }
        }
    }
}

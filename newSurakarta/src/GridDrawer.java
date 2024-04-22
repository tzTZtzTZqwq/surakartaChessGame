import java.util.ArrayList;
import java.util.HashMap;

public class GridDrawer {
    HashMap<String,Integer> colors = new HashMap<String,Integer>(){
        {
            put("red", 41);
            put("yellow", 43);
            put("green", 42);
            put("blue", 44);
        }
    };
    Grid gameGrid;
    GridDrawer(Grid gameGrid){
        this.gameGrid =gameGrid;
    }

    ArrayList<Node> highlighted = new ArrayList<Node>();
    ArrayList<String> highlightColor = new ArrayList<String>();

    public void highlight(int column,int row,String color){
        highlight(gameGrid.findNode(column,row),color);
    }

    public void highlight(Node thisNode,String color){
        highlighted.add(thisNode);
        highlightColor.add(String.valueOf(colors.get(color)));
    }

    public void highlight(ArrayList<Node> nodes,String color){
        for(Node thisNode:nodes){
            highlight(thisNode,color);
        }
    }

    public void clearAllHighlight(){
        highlighted.clear();
        highlightColor.clear();
    }

    public void clearHighlight(Node thisNode){
        if(highlighted.contains(thisNode)){
            highlightColor.remove(highlighted.indexOf(thisNode));
            highlighted.remove(thisNode);
        }
    }
    public void printGrid(){
        for(int i=0;i<=gameGrid.totalColumn;i++){
            for(int j=0;j<=gameGrid.totalRow;j++){
                System.out.print("\u001b[0m");
                if(highlighted.contains(gameGrid.findNode(j,i))){
                    System.out.print("\u001b["+highlightColor.get(highlighted.indexOf(gameGrid.findNode(j,i)))+"m");
                }
                switch (gameGrid.findNode(j,i).status){
                    case(0):
                        System.out.print("\u001b[37m. ");
                        break;
                    case(1):
                        System.out.print("\u001b[31m* ");
                        break;
                    case(2):
                        System.out.print("\u001b[34m* ");
                        break;
                }
            }
            System.out.print("\u001b[0m");
            System.out.println("");
        }
    }
}

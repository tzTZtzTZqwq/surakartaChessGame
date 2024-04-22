import java.util.ArrayList;

public class Node {
    int column;
    int row;
    int status;
    int railwayID;
    Node(int column, int row){
        this.column = column;
        this.row = row;
    }

    Node(int column, int row,int status){
        this.column = column;
        this.row = row;
        this.status = status;
    }

    public String toString(){
        String result = new String();
        result = "column:" + Integer.toString(column) + " row:" + Integer.toString(row) + " status:" + Integer.toString(status) + " rail" + Integer.toString(railwayID);
    return result;
    }

    public Boolean isSamePosition(Node node){
        return (this.row == node.row && this.column == node.column);
    }
}

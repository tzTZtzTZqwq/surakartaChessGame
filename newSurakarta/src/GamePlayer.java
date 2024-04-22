import java.util.ArrayList;
import java.util.Vector;

public class GamePlayer {
    Grid gameGrid;
    String name;
    int side;
    GamePlayer(Grid gameGrid,String name,int side){
        this.gameGrid = gameGrid;
        this.name = name;
        this.side = side;
    }

    public Boolean nextMove(){
        return true;
    }

}

package comp1140.ass2;

/**
 * Created by Administrator on 2015/8/26/0026.
 */
public enum Player {

    BLUE,
    YELLOW,
    RED,
    GREEN;

    /**
     * Convert a player to an Int to represent the order in the game.
     * @return  an Integer.
     */
    //e.g. to call this method: Player.BLUE.toInt() .

    public int toInt() {
        int i = 0;
        switch (this) {
            case BLUE: i = 0; break;
            case YELLOW: i = 1; break;
            case RED:i = 2; break;
            case GREEN: i = 3; break;
        }
        return  i;
    }

}

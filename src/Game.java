public class Game {

    /**
     * A board has nine squares. Each square is either unowned or
     * it is owned by a player. So we use a simple array of player
     * refrences. If null, the corresponding square is unowned,
     * otherwise the array cell stores a reference to the player that
     * owns it.
     */
    private Player[] board={
            null, null, null,
            null, null, null,
            null, null, null};
    /**
     * The current player
     */
    Player currentPlayer;

    /**
     * Returns wheather the current state of the board is such that one
     * of the players is a winnter.
     */

    public boolean hasWinner(){
        return
                (board[0] != null && board[0] == board[1] && board[0] == board[2])
                        ||(board[3] != null && board[3] == board[4] && board[3] == board[5])
                        ||(board[6] != null && board[6] == board[7] && board[6] == board[8])
                        ||(board[0] != null && board[0] == board[3] && board[0] == board[6])
                        ||(board[1] != null && board[1] == board[4] && board[1] == board[7])
                        ||(board[2] != null && board[2] == board[5] && board[2] == board[8])
                        ||(board[0] != null && board[0] == board[4] && board[0] == board[8])
                        ||(board[2] != null && board[2] == board[4] && board[2] == board[6]);
    }
    /**
     * Returns whether there are no more empty squares.
     */

    public boolean boardFilledUp(){
        for(int i = 0; i < board.length; i++) {
            if (board[i] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Call by the player threads when a player tries to make a
     * move. This method checks to see if the move is legal: that
     * is, the player requesting the move must be the current player
     * and the square in which she is trying to move must not already
     * be occupied. If the move is legal the game state is updated
     * (the square is set and the next player becomes current) and
     * the other player is notified of the move so it can update its
     * client
     */

    public synchronized boolean legalMove(int location, Player player){
        if(player == currentPlayer && board [location] == null){
            board[location] = currentPlayer;
            currentPlayer = currentPlayer.opponent;
            currentPlayer.otherPlayerMoved(location);
            return true;
        }
        return false;
    }
    /**
     * The class for the helper threads in this multithreaded server
     * application. A player is identified by a character mark
     * which is either 'X' or 'O'. For communication with the
     * client the player has a socket with its input and output
     * streams. Since only text is being communicated we use a
     * reader and a writer.
     */


}

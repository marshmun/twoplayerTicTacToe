import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A server for a network multi-player tic tac toe game. Modified and
 * extended from the class presented in Deitel and Deitel " Java How to
 * Program" book. I made a bunch of enhancements and rewrote large sections
 * of the code. The main change is instead of passing *data* between the
 * client and the server, I made a TTTP( tic tac toe protocol) which is totally
 * plain text, so you can test the game with Talnet (always a good idea.)
 * The strings that are sent in TTTP are:
 *
 * Client -> Server                     Server -> Client
 * ----------------                     -----------------
 * Move <n> (0<= n < =8)                WELCOME <char> (char in {X,O})
 * QUIT                                 VALID_MOVE
 *                                      OTHER_PLAYER_MOVED <n>
 *                                      VICTORY
 *                                      DEFEAT
 *                                      TIE
 *                                      MESSAGE <text>
 *
 *A second change is that it allows an unlimited number of pairs of
 * players to play
 */
public class TicTacToeServer {
    /**
     * Runs the application. Pairs up clients that connect
     */
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(8901 );
        System.out.println("Tic Tac Toe Server is Running");
        try{
            while(true){
                Game game = new Game();
                Player playerx = new Player(listener.accept(), 'X');
                Player playero = new Player(listener.accept(), 'O');
                playerx.setOpponent(playero);
                playero.setOpponent(playerx);
                game.currentPlayer = playerx;
                playerx.start();
                playero.start();
            }
        }finally {
            listener.close();
        }

    }
}

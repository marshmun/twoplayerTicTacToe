import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player extends Thread {
    char mark;
    Player opponent;
    Socket socket;
    BufferedReader input;
    PrintWriter output;

    /**
     * Constructs a handler threat for a given socket and mark
     * initializes the stream fields, displays the first two
     * welcoming messages
     */

    public Player(Socket socket, char mark){
        this.socket = socket;
        this.mark = mark;
        try{
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME " + mark);
            output.println("MESSAGE Waiting for opponent to connect");
        }catch(IOException e){
            System.out.println("player died: " + e);
        }
    }

    /**
     * Accepts notification of who the opponent is
     */
    public void setOpponent(Player opponent){
        this.opponent = opponent;
    }

    /**
     * Handles the otherPlayerMoved  message
     */
    public void otherPlayerMoved(int location){
        Game game = new Game();
        output.println("OPPONENT_MOVED " + location);
        output.println(
                game.hasWinner()? "DEFEAT" : game.boardFilledUp() ? "TIE" : "");
    }

    /**
     * The run method of this thread.
     */
    public void run(){
        try{
            //The threat is only started after everyone connects.
            output.println("MESSAGE ALL players connected");

            //Tell the first player that it is their turn.
            if (mark == 'X'){
                output.println("MESSAGE Your move");
            }

            //Repeatedly get commands from the clients and process them.
            while (true){
                Game game = new Game();
                String command = input.readLine();
                if(command.startsWith("MOVE")){
                    int location = Integer.parseInt(command.substring(5));
                    if(game.legalMove(location, this)){
                        output.println("VALID_MOVE");
                        output.println((game.hasWinner() ? "VICTORY"
                                :game.boardFilledUp()? "TIE"
                                :""));
                    }else{
                        output.println("MESSAGE ?");
                    }
                }else if (command.startsWith("QUIT")){
                    return;
                }
            }
        }catch(IOException e){
            System.out.println("Player died : " + e);
        }finally {
            try{socket.close();} catch (IOException e){}
        }
    }
}

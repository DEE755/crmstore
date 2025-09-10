package servercommunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import util.Constants;

public class ServerCom
{
private Socket socket;
public BufferedReader reader;
private PrintWriter writer;

public Socket getSocket(){
    return this.socket;
    }
 

public void ServerConnection() throws IOException {
        socket = new Socket(Constants.HOST, Constants.PORT);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public String sendCommandAndGetResponse(String command) throws IOException {
        writer.println(command);
        return reader.readLine();
    }

    public void emptyBuffer() throws IOException {
        while (reader.ready()) {
            reader.readLine();
        }
    }


    public void close() throws IOException {
        reader.close();
        writer.close();
        socket.close();
    }
    

}
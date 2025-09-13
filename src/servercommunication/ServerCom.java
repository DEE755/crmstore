package servercommunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import model.Branch;
import util.Constants;

public class ServerCom
{
private Socket socket;
public BufferedReader reader;
private PrintWriter writer;
private static Branch associatedBranch;//automatic branch recognition according to client config
private static ServerCom instance;



    private ServerCom() {}

    public static ServerCom getInstance() {
        if (instance == null) 
        instance=new ServerCom();
        
        else associatedBranch = Branch.getClientBranch();
         
        return instance;
        }

        

    public Socket getSocket(){
        return this.socket;
        }
    

    public void ServerConnection() throws IOException {
            socket = new Socket(Constants.HOST, Constants.PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        }

    public String sendCommandAndGetResponse(String command, boolean verbose) throws IOException 
    {
        String responseString;
        writer.println(command);
        responseString = reader.readLine();

        if (verbose) {
            System.out.println("Sent command to server: " + command);
            System.out.println("Response from server: " + responseString);
        }
        
        return responseString;
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
    
    public void revealBranchToServer() throws IOException {
        String branchInfo = associatedBranch.getName() + " " + associatedBranch.getId();
        writer.println(branchInfo);
        writer.flush();
        }

}
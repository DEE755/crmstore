package view;

import java.util.Scanner;
import model.Branch;
import servercommunication.ServerCom;

public class GeneralDisplay {
    
    protected Scanner scanner = new Scanner(System.in);
    protected  static ServerCom serverCom;
    protected Branch associatedBranch;

    protected GeneralDisplay() 
    {

        serverCom=ServerCom.getInstance();
        associatedBranch=Branch.getClientBranch();

    }


    

    public enum Mode {
        VIEW,
        EDIT,
        SELECT,
        DELETE
    }


}

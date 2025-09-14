package serialization;

import java.net.Socket;
import java.util.List;
import java.util.Set;
import model.Branch;
import servercommunication.ServerCom;

public class BranchSerializer {
    ServerCom serverCom=ServerCom.getInstance();
    Socket socket=serverCom.getSocket();
    String responseString;

    private static BranchSerializer instance;

    private BranchSerializer() {
        // Private constructor to prevent instantiation
    }

    public static BranchSerializer getInstance() {
        if (instance == null) {
            instance = new BranchSerializer();
        }
        return instance;
    }



    public Set<Branch> loadBranchSetFromText(String textBranchesInfo) {
        System.out.println("Deserializing branches from text... =" + textBranchesInfo);
        Set<model.Branch> branches = new java.util.HashSet<>();

        List<String> branchStrings = java.util.Arrays.asList(textBranchesInfo.split("\n"));

        for (String braString: branchStrings)
        {
            branches.add(util.TypeConverter.stringToBranch(braString));
        }

        return branches;
    }
}

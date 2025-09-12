package model;

public class Branch {
    private String name;
    private int id;
    private boolean isConnected;
    private static final Branch clientBranch = new Branch(util.Constants.BRANCH_NAME);
   // private static List<Socket> branchClients;


    public Branch() {
        this.name = util.Constants.BRANCH_NAME;
        this.id = hashIdFromName(name);
        this.isConnected = false;

        System.err.println("Generating new Branch from config file: Name: " + name + ", ID: " + id);
    }
    public Branch(String name) {
        this.name = name;
        this.id = hashIdFromName(name);
        this.isConnected = false;
    }

    public Branch(String name, int id, boolean isConnected) {
        this.name = name;
        this.id = id;
        this.isConnected = isConnected;
    }


    public static Branch getClientBranch() {
        return clientBranch;
    }
    public String getName() {
        return name;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connectionStatus) {
        this.isConnected = connectionStatus;
    }

    public int getId() {
        return id;
    }

    public static int hashIdFromName(String name) {   
    return Math.abs(name.hashCode());
}

    
}
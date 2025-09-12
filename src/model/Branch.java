package model;

public class Branch {
    private String name;
    private int id;
    private boolean isConnected;
   // private static List<Socket> branchClients;


    public Branch() {
        this.name = util.Constants.BRANCH_NAME;
        this.id = generateIdFromName(name);
        this.isConnected = false;

        System.err.println("Generating new Branch from config file: Name: " + name + ", ID: " + id);
    }

    public Branch(String name, int id, boolean isConnected) {
        this.name = name;
        this.id = id;
        this.isConnected = isConnected;
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

    public static int generateIdFromName(String name) {
    return Math.abs(name.hashCode());
}

    
}
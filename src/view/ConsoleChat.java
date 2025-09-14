package view;

import java.util.Set;
import main.Main;
import model.Branch;
import model.ChatSession;

public class ConsoleChat extends GeneralDisplay {

    private static ConsoleChat instance;

    private ConsoleChat()  {
        // Private constructor to prevent instantiation
    }

    public static ConsoleChat getInstance() {
        if (instance == null) {
            instance = new ConsoleChat();
        }
        return instance;
    }

    public int chatManagementPrompt() {
        System.out.println("=== CHAT ===");
        System.out.println("1. Start New Chat Session");
        System.out.println("2. View Existing Chat Sessions");
        System.out.println("3. Send Message");
        System.out.println("4. Back to Main Menu");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();
        return Integer.parseInt(choice);
    }



    public void chatManagement() throws Exception {
        boolean exit = false;
        while (!exit) {
            int choice = chatManagementPrompt();
            switch (choice) {
                case 1:
                    System.out.println("Starting a new chat session...");
                    Set<Branch> branches = commands.getExistingBranchesFromServer();

                    displayBranches(branches);
                    Branch chosenBranch;
                    
                    do {
                         chosenBranch = promptBranchSelection(branches);
                    } while (chosenBranch == null);

                    ChatSession newChatSession = new ChatSession(Main.associatedBranch, chosenBranch);

                    newChatSession.addMessage(promptForMessage());

                    commands.startChatSession(newChatSession);
                    

                    break;
                case 2:
                    System.out.println("Viewing existing chat sessions...");
                    // Implement view existing chat sessions logic here
                    break;
                case 3:
                    System.out.println("Sending a message...");
                    // Implement send message logic here
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


private String promptForMessage() {
    System.out.print("Enter your message: ");
    return scanner.nextLine();
}

public void displayBranches(Set<Branch> branches) {
    System.out.println("=== BRANCHES ===");
    if (branches.isEmpty()) {
        System.out.println("No branches available.");
    } else {
        StringBuilder branchInfo = new StringBuilder();
        int i = 0;
        for (Branch branch : branches) {
            branchInfo.append(++i).append(": ")
            .append("Branch Name: ").append(branch.getName())
            .append(" ID:").append(branch.getId())
            .append("\n");
        }
        System.out.println(branchInfo.toString());
    }
}

public Branch promptBranchSelection(Set<Branch> branches) {

   
        System.out.print("Select a branch by ID: ");
        String input = scanner.nextLine();
        int branchId = Integer.parseInt(input);
        for (Branch branch : branches) {
            if (branch.getId() == branchId) {
                return branch;
            }
        }
       return null;

   }   
   
}


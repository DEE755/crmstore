package view;

import java.util.Set;
import main.Main;
import model.Branch;
import model.ChatSession;
import servercommunication.ServerCom;

public class ConsoleChat extends GeneralDisplay {
    ChatSession newChatSession;

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
        System.out.println("\n\n=== CHAT ===");
        System.out.println("1. Send New Message");
        System.out.println("2. View Existing Chat Sessions");
        System.out.println("3. Back to Main Menu");
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

                    newChatSession = new ChatSession(Main.associatedBranch, chosenBranch);

                    newChatSession.addMessage(promptForMessage());

                    commands.startOrContinueChatSession(newChatSession);
                    

                    break;
                case 2:
                    System.out.println("Viewing existing chat sessions...");

                    newChatSession = commands.getChatSessionsForCurrentBranch();


                    newChatSession.printNewMessages();

                    String answer = promptForAnswer();
                    if (!answer.isEmpty()) {
                        newChatSession.addMessage(answer);
                        newChatSession.switchSourceAndDestination();

                        System.out.println("Branch: " + newChatSession.getDestinationBranch().getName() + " ID: " + newChatSession.getDestinationBranch().getId());
                        commands.startOrContinueChatSession(newChatSession);
                        
                    }

                    ConsoleMenuDisplay.getInstance().promptToContinue();

                    break;
               
                case 3:
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
    System.out.println("\n\n=== BRANCHES ===");
    if (branches.isEmpty()) {
        System.out.println("No branches available.");
    } else {
        StringBuilder branchInfo = new StringBuilder();
        int i = 0;
        for (Branch branch : branches) {
            branchInfo.append(++i).append(": ")
            .append("Branch Name: ").append(branch.getName())
            .append(" ID:").append(branch.getId());

            if (branch.isConnected()) {
                branchInfo.append(" [ONLINE]");
            } else {
                branchInfo.append(" [OFFLINE]");
            }
            if(branch.getId()==ServerCom.getInstance().getAssociatedBranch().getId()) {
                branchInfo.append(" (Your Branch)");
            }

            branchInfo.append("\n");
        }
        System.out.println(branchInfo.toString());
    }
}


public String promptForAnswer(){
    System.out.print("Do you want to answer this chat? (Y/N): ");
    String input = scanner.nextLine();
    if (input.equalsIgnoreCase("Y")) {
       return promptForMessage();
    } else {
        return "";
    }
}


public Branch promptBranchSelection(Set<Branch> branches) {

   
        System.out.print("Select a branch by ID: ");
        String input = scanner.nextLine();
        int branchId = Integer.parseInt(input);
        if (branchId==ServerCom.getInstance().getAssociatedBranch().getId()) {
            System.out.println("You cannot select your own branch. Please choose a different branch.");
            return null;
        }
        for (Branch branch : branches) {
            if (branch.getId() == branchId) {
                return branch;
            }
        }
       return null;

   }   
   
}


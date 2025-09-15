package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class ChatSession {
   
    private Set<Participant> participants;
    private int chatSessionId;
    private Queue<String> messageQueue;
    private List<String> messagesHistory;
    private Branch destinationBranch;
    private Branch sourceBranch;

    public ChatSession(Branch sourceBranch, Branch destinationBranch) {
        participants = new HashSet<>();
        this.chatSessionId = generateId();
        this.messageQueue = new LinkedList<>();
        this.messagesHistory = new ArrayList<>();
        this.destinationBranch = destinationBranch;
        this.sourceBranch = sourceBranch;//includes the connected employee
    }

    public void addMessage(String message) {
        Employee sender = sourceBranch.getConnectedEmployee();

        String addedInfoMessage = "[" + java.time.LocalDateTime.now() +" - Sent By: " + sender.getBranch().getName() + " " + sender.getFullName() + "] " + message;
        messageQueue.offer(addedInfoMessage);

    }

    public String getNextMessage() {
        String message= messageQueue.poll();
        messagesHistory.add(message);
        return message;
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public List<String> getMessagesHistory() {
        return messagesHistory;
    }

    public int getId() {
        return chatSessionId;
    }

    private int generateId() {
        Random rand = new Random();
        return rand.nextInt(1000); 
    }

    public Branch getSourceBranch() {
        return sourceBranch;
    }
    public Branch getDestinationBranch() {
        return destinationBranch;
    }

    public Employee getSourceBranchEmployee() {
        return sourceBranch.getConnectedEmployee();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(destinationBranch.getId()).append(" ")
        .append(getNextMessage());
        return sb.toString();
    }

    public void switchSourceAndDestination() {
        Branch temp = sourceBranch;
        sourceBranch = destinationBranch;
        destinationBranch = temp;
    }

    public void printNewMessages() {
        System.out.println("\n=== New Messages ===");
        if (messageQueue.isEmpty()) {
            System.out.println("No new messages.");
        } else {
            for (String message : messageQueue) {
                System.out.println(message);
            }
            messageQueue.clear(); // Clear the queue after printing
        }
        System.out.println("====================\n");
    }

    public void printMessageHistory() {
        System.out.println("\n=== Message History ===");
        if (messagesHistory.isEmpty()) {
            System.out.println("No message history.");
        } else {
            for (String message : messagesHistory) {
                System.out.println(message);
            }
        }
        System.out.println("=======================\n");
    }

}

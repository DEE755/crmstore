package servercommunication;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import model.Branch;
import model.ChatSession;
import model.inventory.StockItem;
import serialization.BranchSerializer;
import serialization.StockItemSerializer;

public class Commands  {
    private static Commands instance;
    private StockItemSerializer stockItemSerializer = StockItemSerializer.getInstance();
    private ServerCom serverCom = ServerCom.getInstance();
    private Scanner scanner = new Scanner(System.in);
    private BranchSerializer branchSerializer = BranchSerializer.getInstance();
    private Commands() {
        // Private constructor to prevent instantiation
    }

    public static Commands getInstance() {
        if (instance == null) {
            instance = new Commands();
        }
        return instance;
    }

public List<StockItem> getItemListFromServer() throws Exception {
    String responseString;
    responseString=serverCom.sendCommandAndGetResponse("ListItems", util.Constants.VERBOSE_OVERRIDE);

                    if (responseString.equals("SUCCESS")) {
                        System.out.println("Receiving items data from server...");
                        List<StockItem> items = stockItemSerializer.loadStockItemListFromText();
                        return items;
                    }

                    else if (responseString.equals("EMPTY")) {
                        System.err.println("No item data in the server yet.");
                        throw new Exception("No item data in the server yet."); 
                    }
                    else {
                        throw new Exception("Failed to retrieve items. Server response: " + responseString);
                    }

                }





public Set<Branch> getExistingBranchesFromServer() throws Exception {
    String responseString;
    responseString=serverCom.sendCommandAndGetResponse("ListBranches", util.Constants.VERBOSE_OVERRIDE);

                    if (responseString.equals("SUCCESS")) {
                        System.out.println("Receiving branches data from server...");
                        StringBuilder textBranchesInfo = new StringBuilder();
                        String line;
                        while (!(line = serverCom.reader.readLine()).equals("ENDLIST")) {
                            textBranchesInfo.append(line).append("\n");
                        }
                        if (textBranchesInfo==null || textBranchesInfo.isEmpty()) {
                            System.err.println("No branch data in the server.");
                            throw new Exception("No branch data in the server");
                        }

                        Set<Branch> branches = branchSerializer.loadBranchSetFromText(textBranchesInfo.toString());
                        return branches;
                    }

                    else if (responseString.equals("EMPTY")) {
                        System.err.println("No branch data in the server yet.");
                        throw new Exception("No branch data in the server yet."); 
                    }
                    else {
                        throw new Exception("Failed to retrieve branches. Server response: " + responseString);
                    }

                }
                
                
                public void startChatSession(ChatSession chatSession) throws Exception {
                    String command = "StartChatSession " + chatSession.toString();

                    String response = serverCom.sendCommandAndGetResponse(command, util.Constants.VERBOSE_OVERRIDE);
                    if (!response.equals("SUCCESS")) {
                        throw new Exception("Failed to start chat session. Server response: " + response);
                    }
                    System.out.println("Chat session started successfully.");
                }


                public void viewChatSessions() throws Exception {
                    String command = "ListChat " + ServerCom.getInstance().getAssociatedBranch().getId();

                    String response = serverCom.sendCommandAndGetResponse(command, util.Constants.VERBOSE_OVERRIDE);
                    if (response.equals("EMPTY")) {
                        System.out.println("No chat sessions available.");
                        return;
                    } else if (!response.equals("SUCCESS")) {
                        throw new Exception("Failed to retrieve chat sessions. Server response: " + response);
                    }

                    System.out.println("Chat Sessions:");
                    String line;
                    while (!(line = serverCom.reader.readLine()).equals("ENDLIST")) {
                        System.out.println(line);
                    }
                }

                public void displayLogs() {
                   
                    String command = "GetLogs";

                    try {
                        String response = serverCom.sendCommandAndGetResponse(command, util.Constants.VERBOSE_OVERRIDE);
                        if (response.equals("EMPTY")) {
                            System.out.println("No logs available.");
                            return;
                        } else if (!response.equals("SUCCESS")) {
                            throw new Exception("Failed to retrieve logs. Server response: " + response);
                        }

                        System.out.println("Logs:");
                        String line;
                        while (!(line = serverCom.reader.readLine()).equals("ENDLIST")) {
                            System.out.println(line);
                        }
                    } catch (Exception e) {
                        System.err.println("Error retrieving logs: " + e.getMessage());
                    }

                }
            }


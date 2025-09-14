package servercommunication;

import java.util.List;
import model.inventory.StockItem;
import serialization.StockItemSerializer;

public class Commands  {
    private static Commands instance;
    private StockItemSerializer stockItemSerializer = StockItemSerializer.getInstance();
    private ServerCom serverCom = ServerCom.getInstance();
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

}


package serialization;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import model.inventory.StockItem;
import servercommunication.ServerCom;

public class StockItemSerializer {

    ServerCom serverCom = ServerCom.getInstance();
    Socket socket=serverCom.getSocket();

    private static StockItemSerializer instance;
    public static StockItemSerializer getInstance() {
        if (instance == null) {
            instance = new StockItemSerializer();
        }
        return instance;
    }
    private StockItemSerializer() {


    }
    
    


    public StockItem loadStockItem(int stockItemId) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("stockitem_" + stockItemId + ".ser"))) {
            return (StockItem) in.readObject();
        }
    }

    public void saveStockItem(StockItem stockItem) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            out.writeObject(stockItem);
        }
    }
    
   
    

    public List<StockItem> loadStockItemListFromText() throws IOException, ClassNotFoundException {
        BufferedReader reader = serverCom.reader;
        try {
            String stockItemListRawText;
            System.out.println("Reading stock item data from server...:" + reader);


            List<StockItem> stockItems = new ArrayList<StockItem>();
            while (!"ENDLIST".equals(stockItemListRawText = reader.readLine()) && stockItemListRawText.length() > 2) {
                StockItem stockItem = util.TypeConverter.stringToStockItem(stockItemListRawText);
                if (stockItem != null)
                {
                    stockItems.add(stockItem);
                }
            }
            
            return stockItems;
        }
        catch (IOException e) {
            // If the file does not exist or is empty, return an empty list
            System.err.println("There is no stock item data available. Please add stock items first.");
            return new java.util.ArrayList<>();
        }
    }



    
    
}

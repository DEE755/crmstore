package view;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import model.inventory.StockItem;
import servercommunication.ServerCom;

public class ConsoleInventoryDisplay extends GeneralDisplay {
    private static ConsoleInventoryDisplay instance;
    private ConsoleMenuDisplay consoleMenuDisplay = ConsoleMenuDisplay.getInstance();
    private ServerCom serverCom = ServerCom.getInstance();

    public static ConsoleInventoryDisplay getInstance() {
        if (instance == null) {

            instance = new ConsoleInventoryDisplay();
        }
        return instance;

    }


    private ConsoleInventoryDisplay() {
    }



public void displayStockItemList(List<StockItem> stockItems) {
    System.out.println("=== STOCK ITEM LIST ===");
    for (StockItem stockItem : stockItems) {
        System.out.println(" Product Name: " + stockItem.getName() + " ID: " + stockItem.getId()  + " Quantity: " + stockItem.getQuantity() );
    }
    System.out.println("=======================");
    }


    public Optional<StockItem> viewEditDeleAllItems(Mode mode, List<StockItem> items) throws IOException, ClassNotFoundException {
        String responseString;
        StockItem selectedItem;
        int itemId = -1;
    

        displayStockItemList(items);

        do{

        if (mode == Mode.VIEW) {
            System.out.println("\nWrite the ID of the Item you want to see and press Enter to continue... Or cancel by pressing Enter without typing anything.");
        }
        else if (mode == Mode.DELETE) {
            System.out.println("\nWrite the ID of the Item you want to delete and press Enter to continue... Or cancel by pressing Enter without typing anything.");
        }

        else if (mode == Mode.EDIT) {
            System.out.println("\nWrite the ID of the Item you want to edit and press Enter to continue... Or cancel by pressing Enter without typing anything.");
        }

        else if (mode == Mode.SELECT) {
            System.out.println("\nWrite the ID of the Item you want to sell and press Enter to continue... Or cancel by pressing Enter without typing anything.");
        }

        // ID of the item to view/edit/delete
            String input = scanner.nextLine();

            
            try {
        
                //Item itself
                itemId = Integer.parseInt(input);

                if (String.valueOf(itemId).matches("\\d+")) {
                    selectedItem = StockItem.findStockItemById(items, itemId);
                    if (selectedItem == null) {
                        System.err.println("No item found with the given ID.");
                    }
                }
                else throw new NumberFormatException();

            } catch (NumberFormatException e) {
                if (input.trim().isEmpty()) {
                    System.out.println("Action cancelled.");
                    return Optional.empty();
                } else {
                    System.err.println("Invalid input. Please enter a valid item ID.");
                    selectedItem = null;
                }
            }
        }
        while (selectedItem == null);
            
                try{
                    if (mode==Mode.VIEW)
                    {
                        
                        selectedItem.displayStockItemDetails();

                    }
                    else if (mode==Mode.DELETE){
                        responseString=serverCom.sendCommandAndGetResponse("ListItems DELETEMODE " + itemId + "\n", util.Constants.VERBOSE_OVERRIDE);


                        if (responseString.equals("SUCCESS")) {
                            System.out.println(selectedItem.getName() + " item has been deleted successfully.");
                        } else
                        {
                            System.err.println("Failed to delete item with ID " + itemId + ". Please ensure the ID is correct.");
                        }
                    
                    }

                    else if (mode == Mode.EDIT) {
                        System.out.println("Updating product quantity...\n Enter new quantity for '" + selectedItem.getName() + "' and press Enter to continue...");
                    

                    int newQuantity = scanner.nextInt();
                    responseString = serverCom.sendCommandAndGetResponse("ListItems EDITMODE " + itemId + " " + newQuantity + "\n", true);
                    serverCom.emptyBuffer();
                    System.err.println("Response from server: " + responseString);
                    
                    if (responseString.equals("SUCCESS")) {
                        System.out.println("Item quantity updated successfully.");
                    } 
                    else {
                        System.err.println("Failed to update item quantity.");
                    }                
                    
                }

                else if (mode == Mode.SELECT) {
                    return Optional.of(selectedItem);
                }   
            }
                catch(IndexOutOfBoundsException e)
                {
                    System.out.println("No customer found with the given ID.");
                    
                }
                return Optional.empty();

    }



public StockItem createNewItem() {
    System.out.println("\n=== ADD NEW PRODUCT ===");
    
    System.out.print("Enter product name: ");
    String name = scanner.nextLine().trim();
    
    
    System.out.print("Enter product price: ");
    double price = Double.parseDouble(scanner.nextLine().trim());
    
    System.out.print("Enter product quantity: ");
    int quantity = Integer.parseInt(scanner.nextLine().trim());

    System.out.print("Enter product category (1: ELECTRONICS, 2: CLOTHING, 3: GROCERY, 4: MISC): ");
    int categoryInput = Integer.parseInt(scanner.nextLine().trim());
    StockItem.Category category;
    category = switch (categoryInput) {
        case 1 -> StockItem.Category.ELECTRONICS;
        case 2 -> StockItem.Category.CLOTHING;
        case 3 -> StockItem.Category.GROCERY;
        case 4 -> StockItem.Category.MISC;
        default -> {
            System.err.println("Invalid category. Defaulting to MISC.");
            yield StockItem.Category.MISC;
        }
    };

    StockItem newItem = new StockItem(name, quantity, price, category);
    return newItem;

}

}
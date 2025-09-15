package model;

import java.util.ArrayList;
import java.util.List;

public class Logs {
    private String fullLog;
    private LogType logType;
    private List<Log> infos;
    private List<Log> messages;
    private List<Log> sales;
    private List<Log> connections;
    private List<Log> buyings;
    private List<Log> added;
    private List<Log> others;
    private List<Log> stocks;

    enum LogType {
        ALL,
        INFO,
        ADDED,
        ERROR,
        MESSAGES,
        SALES,
        CONNECTION,
        BUYING,
        STOCK,
        OTHER
    }

public Logs(String fullLog)
{
    infos = new ArrayList<>();
    messages = new ArrayList<>();
    sales = new ArrayList<>();
    connections = new ArrayList<>();
    buyings = new ArrayList<>();
    others = new ArrayList<>();
    added = new ArrayList<>();
    stocks = new ArrayList<>();
    this.fullLog = fullLog;


for (String entry : fullLog.split("\n")) {
        Log log = new Log(entry);

}

}

public List<Log>filterLogsByType(int choice) {
        switch (choice) {
            case 1:
                return List.of(new Log(fullLog));
            case 2: 
                return buyings;
            case 3:
                return messages;
            
            case 4:
                return sales;
                
            case 5:
                return added;

                case 6:
                return stocks;
            case 7:
                return others;
            default:
                System.err.println("Invalid choice.");
                return List.of();
        }
    }

    public class Log {

    String logEntry;
    LogType logType;

        public Log(String logEntry){
            this.logEntry = logEntry;
            categorize();
        }
   
    

        public String getLog() {
            return logEntry;
        }
    public void categorize() {
        if (this.logEntry.contains("INFO")) {
            this.logType = LogType.INFO;
            infos.add(this);
        }
           
        else if (this.logEntry.contains("MESSAGE")) {
            this.logType = LogType.MESSAGES;
            messages.add(this);
        } else if (this.logEntry.contains("SALE")) {
            this.logType = LogType.SALES;
            sales.add(this);
        } else if (this.logEntry.contains("CONNECTION")) {
            this.logType = LogType.CONNECTION;
            connections.add(this);

        }else if (this.logEntry.contains("BUYING")) {
            this.logType = LogType.BUYING;
            buyings.add(this);
        }
        else if (this.logEntry.contains("ADDED")) {
            this.logType = LogType.ADDED;
            added.add(this);
        }
        else if (this.logEntry.contains("STOCK")) {
            this.logType = LogType.STOCK;
            stocks.add(this);
        }
        else {
            this.logType = LogType.OTHER;
            others.add(this); 
        }
    }


    
}
    }




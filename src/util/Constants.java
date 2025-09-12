package util;

import java.util.Properties;

public class Constants {

    private static final Properties props = new Properties();

    static {
        try {
            props.load(Constants.class.getResourceAsStream("/config.properties"));
        } catch (Exception e) {
            System.err.println("Error loading config.properties: " + e.getMessage());
        }
    }
    public static final String BUSINESS_NAME = props.getProperty("BUSINESS_NAME", "MyBusiness");
    public static final String BRANCH_NAME = props.getProperty("BRANCH_NAME", "DefaultBranch");

    public static final String CUSTOMER_DATA_FILE = "customers" +"_" + BRANCH_NAME + ".ser";
    public static final String EMPLOYEE_DATA_FILE = "employees" +"_" + BRANCH_NAME + ".ser";
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "admin123";
    public static final int PORT = 1234;
    public static final String HOST = "localhost";
    public static final boolean VERBOSE_OVERRIDE = true;
}

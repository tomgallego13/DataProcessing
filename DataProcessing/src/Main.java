import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.util.Scanner;

interface InMemoryDB {
    int get(String key);
    void put(String key, int val);
    void begin_transaction();
    void commit();
    void rollback();
}

class Database implements InMemoryDB {
    private Map<String, Integer> dataMap;
    private Stack<Map<String,Integer>> s;

    public Database() {
        this.dataMap = new HashMap<>();
        this.s = new Stack<>();
    }

    public void begin_transaction(){
        s.push(new HashMap<>(dataMap));
    }

    public void put(String key, int val){
        dataMap.put(key, val);
    }

    public int get(String key){
        if (!key.isEmpty()) {
            return dataMap.get(key);
        } else {
            return -1;
        }
    }

    public void commit() {
        if (!s.isEmpty()) {
            s.clear();
        }
    }

    public void rollback() {
        if (!s.isEmpty()) {
            dataMap = s.pop();
        }
    }
}



public class Main {
    public static void main(String[] args) {
        InMemoryDB db = new Database();
        Scanner scan = new Scanner(System.in);
        boolean status = false;
        int option = 0;
        System.out.println("Select an option: ");
        while (option != 6) {
            System.out.println("1. Begin a transaction");
            System.out.println("2. Put in a value");
            System.out.println("3. Get a value");
            System.out.println("4. Commit");
            System.out.println("5. Rollback");
            System.out.println("6. Exit");

            option = scan.nextInt();
            scan.nextLine();

            if (option == 1) {
                db.begin_transaction();
                status = true;
                System.out.println("Next option:");
            } else if (option == 2) {
                if (status) {
                    System.out.println("Enter key: ");
                    String key = scan.nextLine();
                    System.out.println("Enter value: ");
                    int value = scan.nextInt();
                    db.put(key, value);
                    System.out.println("Next option:");
                } else {
                    throw new RuntimeException("Error. Exiting.");
                }
            } else if (option == 3) {
                if (status) {
                    System.out.println("Key you would like to get?");
                    String key = scan.nextLine();
                    int value = db.get(key);
                    if (value == -1) {
                        System.out.println("The key does not exist");
                    } else {
                        System.out.println("The value is: " + value);
                    }
                    System.out.println("Next option:");
                } else {
                    System.out.println("null");
                }
            } else if (option == 4) {
                db.commit();
                System.out.println("Next option:");
            } else if (option == 5) {
                db.rollback();
                System.out.println("Next option");
            } else if (option == 6) {
                System.out.println("Have a good day!");
            } else {
                System.out.println("Invalid option");
            }

        }
        scan.close();
    }
}
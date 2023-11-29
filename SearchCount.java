package CarRent;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SearchCount {

    private Map<String, Integer> searchCountMap;
    //private Scanner scanner;

    // Constructor to initialize the SearchCount object
    public SearchCount() {
        this.searchCountMap = new HashMap<>();
        //this.scanner = new Scanner(System.in);
    }

    // Method to perform search counts
    public void performSearchCounts(String searchTerm) {
        //while (true) {
            // Ask the user for a search term
//            System.out.print("Enter a search term (or 'exit' to end): ");
//            String searchTerm = scanner.nextLine().toLowerCase();
//
//            // Check if the user wants to exit
//            if (searchTerm.equals("exit")) {
//                break;
//            }

            // Update the search count
            int currentCount = searchCountMap.getOrDefault(searchTerm, 0);
            searchCountMap.put(searchTerm, currentCount + 1);

            // Display the current search count
            System.out.println("Search count for '" + searchTerm + "': " + searchCountMap.get(searchTerm));
        }
  //  }

    // Method to display the summary of search counts
    public void displaySearchCountSummary() {
        System.out.println("Search count summary:");
        for (Map.Entry<String, Integer> entry : searchCountMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " times");
        }
    }

    // Method to close the Scanner
//    public void closeScanner() {
//        if (scanner != null) {
//            scanner.close();
//        }
//    }
}

package carRentalAnalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SearchCount {
	
	private static final String FILE_PATH = "D:\\SEM1\\ACC\\project\\searchCount\\searchCount.txt";
    private Map<String, Integer> searchCountMap;
    //private Scanner scanner;

    // Constructor to initialize the SearchCount object
    public SearchCount() {
        this.searchCountMap = loadSearchCountsFromFile();
        //this.scanner = new Scanner(System.in);
    }

    // Method to perform search counts
    public void performSearchCounts(String searchTerm) {

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
    private Map<String, Integer> loadSearchCountsFromFile() {
        Map<String, Integer> loadedSearchCounts = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String searchTerm = parts[0].trim();
                    int count = Integer.parseInt(parts[1].trim());
                    loadedSearchCounts.put(searchTerm, count);
                }
            }
        } catch (IOException | NumberFormatException e) {
            // Handle exceptions, or ignore if the file doesn't exist
            e.printStackTrace();
        }

        return loadedSearchCounts;
    }
    
    private void saveSearchCountsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Integer> entry : searchCountMap.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to close the Scanner
//    public void closeScanner() {
//        if (scanner != null) {
//            scanner.close();
//        }
//    }
    
//    public void performSearchCounts() {
//    	Scanner scanner  = new Scanner(System.in);
//        while (true) {
//            System.out.print("Enter a search term (or 'exit' to end): ");
//            String searchTerm = scanner.nextLine().toLowerCase();
//
//            if (searchTerm.equals("exit")) {
//                break;
//            }
//
//            int currentCount = searchCountMap.getOrDefault(searchTerm, 0);
//            searchCountMap.put(searchTerm, currentCount + 1);
//
//            System.out.println("Search count for '" + searchTerm + "': " + searchCountMap.get(searchTerm));
//        }
//
//        saveSearchCountsToFile();
//    }
//    public static void main(String[] args) {
//        SearchCount searchCount = new SearchCount();
//
//        searchCount.performSearchCounts();
//        searchCount.displaySearchCountSummary();
//        //searchCount.closeScanner();
//    }
}

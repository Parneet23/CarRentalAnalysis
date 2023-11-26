package CarRent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class InvertedIndexing implements Serializable {

    private TrieNode root;
    private int currentDocumentId;
    String folderpath;

    public InvertedIndexing() {
        this.root = new TrieNode();
        this.currentDocumentId = 0;
    }
    
    public InvertedIndexing(String folderpath) {
    	this.folderpath = folderpath;
    }

    public void indexDocument(String filePath) {
        File file = new File(filePath);
        String documentId = filePath; // Use the file name as the document ID
        currentDocumentId++;

        List<String> documentLines = readDocument(filePath);

        for (String line : documentLines) {
            // Treat each line as a separate term
            indexDocument(documentId, line);
        }
    }

    private void indexDocument(String documentId, String term) {
        // Split the term into individual words
        String[] terms = term.split("\\s+");

        for (String word : terms) {
            insertTerm(documentId, word);
        }
    }

    private void insertTerm(String documentId, String term) {
        TrieNode current = root;

        for (char c : term.toLowerCase().toCharArray()) {
            if (current.children == null) {
                current.children = new HashMap<>();
            }
            current = current.children.computeIfAbsent(c, k -> new TrieNode());
        }

        // Create a new list for each term to store document IDs
        if (current.documentIds == null) {
            current.documentIds = new ArrayList<>();
        }

        // Add the document ID only if it's not already present in the list
        if (!current.documentIds.contains(documentId)) {
            current.documentIds.add(documentId);
        }
    }


    public List<String> search(String term) {
        TrieNode current = root;

        for (char c : term.toLowerCase().toCharArray()) {
            if (current == null || current.children == null) {
                // Term not found due to null pointer
                return new ArrayList<>();
            }

            current = current.children.get(c);

            if (current == null) {
                // Term not found
                return new ArrayList<>();
            }
        }

        return (current.documentIds != null) ? current.documentIds : new ArrayList<>();
    }


    public void saveDataStructure(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(root);
            System.out.println("Inverted index data structure saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TrieNode loadDataStructure(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (TrieNode) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static  void invertindex(String folderpath, String searchTerm) {
    	InvertedIndexing invertedIndex = new InvertedIndexing();

        // Specify the path to your folder
        String folderPath = folderpath;

        try {
            // Index each document
            Files.walk(Paths.get(folderPath))
                 .filter(Files::isRegularFile)
                 .forEach(filePath -> invertedIndex.indexDocument(filePath.toString()));

            // Save the inverted index data structure
            invertedIndex.saveDataStructure("CarRent/inverted_index_data_structure.ser");

            // Search for terms
           
            List<String> result = invertedIndex.search(searchTerm);

            

            // Load the saved inverted index data structure
            TrieNode loadedDataStructure = invertedIndex.loadDataStructure("CarRent/inverted_index_data_structure.ser");

            if (loadedDataStructure != null) {
                // Create a new InvertedIndexing with the loaded data structure
                InvertedIndexing loadedIndex = new InvertedIndexing();
                loadedIndex.root = loadedDataStructure;

                // Search again after loading
                List<String> loadedResult = loadedIndex.search(searchTerm);
                System.out.println("Documents containing the term '" + searchTerm + "' after loading: " + loadedResult);
                compareDeals.bestdeals(loadedResult);
               
            }
        } catch (IOException e) {
            e.printStackTrace();
            
        }
      
    }
    
    public static void invertRead(String [] folderNames, String searchTerm) {
    	for(String folderName: folderNames) {
    		invertindex(folderName, searchTerm);
    	}
    }
   /* public static void main(String[] args) throws IOException {
        InvertedIndexing invertedIndex = new InvertedIndexing();

        // Specify the paths to your files
        String[] filePaths = {
                "CarRent/montreal.txt",
                "CarRent/toronto.txt",
                // Add more file paths as needed
        };

        // Index each document
        for (String filePath : filePaths) {
            invertedIndex.indexDocument(filePath);
        }

        // Save the inverted index data structure
        invertedIndex.saveDataStructure("CarRent/inverted_index_data_structure.ser");

        // Search for terms
        String searchTerm = "Automatic";
        List<String> result = invertedIndex.search(searchTerm);

        System.out.println("Documents containing the term '" + searchTerm + "': " + result);

        // Load the saved inverted index data structure
        TrieNode loadedDataStructure = invertedIndex.loadDataStructure("CarRent/inverted_index_data_structure.ser");

        if (loadedDataStructure != null) {
            // Create a new InvertedIndexing with the loaded data structure
            InvertedIndexing loadedIndex = new InvertedIndexing();
            loadedIndex.root = loadedDataStructure;

            // Search again after loading
            List<String> loadedResult = loadedIndex.search(searchTerm);
            System.out.println("Documents containing the term '" + searchTerm + "' after loading: " + loadedResult);
            System.out.print(loadedResult);
            
        }
    }
*/
    private static List<String> readDocument(String filePath) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}

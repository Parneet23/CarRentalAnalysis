package CarRent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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
        // Split the term into individual words by spaces and commas
        String[] terms = term.split("[\\s,]+");

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

        // Print the term and document ID for debug purposes
       // System.out.println("Inserted term '" + term + "' for document ID '" + documentId + "'");

        if (current.documentIds == null) {
            current.documentIds = new ArrayList<>();
        }

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
   

    

    public List<String> search1(String... searchTerm) {
        // Initialize the result with all document IDs
        Set<String> result = new HashSet<>(root.documentIds);

        // Iterate over each search term
        for (int i = 0; i < searchTerm.length; i++) {
            // Capture the value of i for the lambda expression
            final int index = i;
            
            // Split the current search term into individual words
            String[] searchWords = searchTerm[i].toLowerCase().split("\\s+");

            // Filter documents based on the presence of unique search words
            result = result.stream()
                    .filter(documentId -> {
                        int count = countUniqueMatchingWords(documentId, searchWords);
                        boolean condition = (index == 0 && count >= 2);

                       // System.out.println("Document ID: " + documentId + ", Search Term: " + searchTerm[index] +
                        //        ", Matching Words Count: " + count + ", Condition: " + condition);

                        return condition;
                    })
                    .collect(Collectors.toSet());
        }

        return new ArrayList<>(result);
    }




    private int countUniqueMatchingWords(String documentId, String[] searchWords) {
        Set<String> uniqueTerms = new HashSet<>();
        for (String word : searchWords) {
            if (isInDocument(documentId, word)) {
                uniqueTerms.add(word);
            }
        }
        return uniqueTerms.size();
    }
    private boolean isInDocument(String documentId, String word) {
        TrieNode current = root;

        // Search for the current word in the trie
        for (char c : word.toCharArray()) {
            if (current == null || current.children == null || !current.children.containsKey(c)) {
                // Term not found
                //System.out.println("Term '" + word + "' not found for document " + documentId);
                return false;
            }
            current = current.children.get(c);
        }

        // Check if the document ID is present for the current word
        if (current.documentIds == null || !current.documentIds.contains(documentId)) {
            //System.out.println("Document ID '" + documentId + "' not found for term '" + word + "'");
            return false;
        }

       // System.out.println("Term '" + word + "' found for document " + documentId);
        return true;
    }



    private TrieNode findNode(TrieNode current, String term) {
        for (char c : term.toLowerCase().toCharArray()) {
            if (current == null || current.children == null) {
                // Term not found due to null pointer
                return null;
            }

            current = current.children.get(c);

            if (current == null) {
                // Term not found
                return null;
            }
        }

        return current;
    }

    public List<String> search(String term1, String term2) {
        List<String> result = new ArrayList<>();

        TrieNode node1 = findNode(root, term1);
        TrieNode node2 = findNode(root, term2);

        if (node1 != null && node2 != null) {
            // Find the intersection of document IDs for both terms
            List<String> documentsForTerm1 = new ArrayList<>(node1.documentIds);
            List<String> documentsForTerm2 = new ArrayList<>(node2.documentIds);

            // Retain only documents that are common to both terms
            documentsForTerm1.retainAll(documentsForTerm2);

            result.addAll(documentsForTerm1);
        }

        return result;
    }

    public void saveDataStructure(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(root);
           // System.out.println("Inverted index data structure saved successfully.");
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
    public static  void invertindex(String folderpath,long days,String searchTerm1) {
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
           
            List<String> result = invertedIndex.search1(searchTerm1);

            

            // Load the saved inverted index data structure
            TrieNode loadedDataStructure = invertedIndex.loadDataStructure("CarRent/inverted_index_data_structure.ser");

            if (loadedDataStructure != null) {
                // Create a new InvertedIndexing with the loaded data structure
                InvertedIndexing loadedIndex = new InvertedIndexing();
                loadedIndex.root = loadedDataStructure;

                // Search again after loading
                List<String> loadedResult = loadedIndex.search1(searchTerm1);
                System.out.println("Documents containing the term '" + searchTerm1+" ' after loading: " + loadedResult);
                compareDeals.bestdeals(loadedResult,days,searchTerm1);
               
            }
        } catch (IOException e) {
            e.printStackTrace();
            
        }
      
    }
    
    public static void invertRead(String [] folderNames, long days, String search) {
    	for(String folderName: folderNames) {
    		invertindex(folderName,days,search);
    	}
    }
   public static void main(String[] args) throws IOException {
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
        String searchTerm = "Montreal Trudeau International Airport";
        List<String> result = invertedIndex.search1(searchTerm.toLowerCase(),"Audi Q7");

     //   System.out.println("Documents containing the term '" + searchTerm + "': " + result);

        // Load the saved inverted index data structure
        TrieNode loadedDataStructure = invertedIndex.loadDataStructure("CarRent/inverted_index_data_structure.ser");

        if (loadedDataStructure != null) {
            // Create a new InvertedIndexing with the loaded data structure
            InvertedIndexing loadedIndex = new InvertedIndexing();
            loadedIndex.root = loadedDataStructure;

            // Search again after loading
            List<String> loadedResult = loadedIndex.search1(searchTerm.toLowerCase(),"Audi Q7");
            System.out.println("Documents containing the term '" + searchTerm + "' after loading: " + loadedResult);
            System.out.print(loadedResult);
            
        }
    }

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

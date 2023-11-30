package CarRent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



public class CarMain {
	public static DataValidationUtility du = new DataValidationUtility();
	 private static Date parseDate(String dateStr) {
	        try {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            return dateFormat.parse(dateStr);
	        } catch (ParseException e) {
	            System.err.println("Error parsing date. Please enter a valid date format (yyyy-MM-dd).");
	            return null;
	        }
	    }	 
	 public static void main(String[] args) throws InterruptedException, DateException {
		 	
		    Scanner scanner = new Scanner(System.in);
		    MykSplayTreez splayTree = new MykSplayTreez();
		    WordCompletion wordCompletion = new WordCompletion();
		    MySplayTree airportDictionary = new MySplayTree();
		    AirportSpellChecker airportSpellChecker = new AirportSpellChecker();
//		    SearchCount searchCount = new SearchCount();
		    String userInput ="";
		    initializeDictionaries(splayTree, airportDictionary, wordCompletion, airportSpellChecker);

		   
		    
		    
		    
		   // String dropDateInput = getUserDropDateInput();

		    if (shouldProceedWithAvailableData(scanner)) {
		    	userInput = getUserLocationInput(scanner);
		    	processExistingData(scanner, wordCompletion, splayTree, airportDictionary, airportSpellChecker, userInput);
		    	
		        
		    } else {
		    	userInput = getUserLocationInput(scanner);
		    	userInput = processUnexistingData(scanner, wordCompletion, splayTree, airportDictionary, airportSpellChecker, userInput);
		    	du = getUserPickUpDateInput(du);
		    	
		        RentalCarScraping crawler = new RentalCarScraping(userInput,du.getStartdate(),du.getEndDate());
		        generateAndScrapeDataFiles(crawler, userInput);
		    }

		    scanner.close();
		}

		private static void initializeDictionaries(MykSplayTreez splayTree, MySplayTree airportDictionary, WordCompletion wordCompletion,AirportSpellChecker airportSpellChecker) {
		    wordCompletion.mykPopulateDictionary(splayTree);
		    airportSpellChecker.populateDictionary(airportDictionary);
		}

		private static String getUserLocationInput(Scanner scanner) {
		    System.out.print("Enter your pick-up location: ");
		    return scanner.nextLine().trim().toLowerCase();
		}
		private static DataValidationUtility getUserPickUpDateInput(DataValidationUtility du) throws DateException {
			long days =0;
			du = DataValidationUsingRegex.dateValidation();
			return du;
		    //System.out.print("Enter your pick-up date");
		    //return scanner.nextLine().trim().toLowerCase();
		}
	/*	private static String getUserDropDateInput(Scanner scanner) {
		    //System.out.print("Enter your drop date: ");
		    
		    return scanner.nextLine().trim().toLowerCase();
		}
*/
		private static boolean shouldProceedWithAvailableData(Scanner scanner) {
		    System.out.print("Do you want to proceed with available data or fetch latest data (y/n): ");
		    String initialInput = scanner.nextLine().trim().toLowerCase();
		    return initialInput.equals("y");
		}

		private static void processExistingData(
		        Scanner scanner,
		        WordCompletion wordCompletion,
		        MykSplayTreez splayTree,
		        MySplayTree airportDictionary,
		        AirportSpellChecker airportSpellChecker,
		        String userInput) throws DateException {
			String [] folderName= {"CarRent/avis/crawledTxt/txt","CarRent/budget/crawledTxt/txt","CarRent/enterprise/crawledTxt/txt"};
		    List<String> completionSuggestions = getWordCompletionSuggestions(wordCompletion, splayTree, userInput,scanner);
		    if (!completionSuggestions.isEmpty()) {
		        if(displayWordCompletionSuggestions(completionSuggestions)) {
		        	userInput =handleUserSelectionCompletion(scanner,completionSuggestions,userInput,splayTree);
		        };
		        
		    } else {
		        userInput = handleSpellChecking(scanner, airportDictionary, airportSpellChecker, userInput);
		    }
		    du = getUserPickUpDateInput(du);
		    try {
				ParserAndFrequencyCount.entryPoint();
				PageRanking.pageRanking();
				InvertedIndexing.invertRead(folderName, du.getIntervals(),userInput);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println("Location is: " + userInput);
		}
		private static String processUnexistingData(
		        Scanner scanner,
		        WordCompletion wordCompletion,
		        MykSplayTreez splayTree,
		        MySplayTree airportDictionary,
		        AirportSpellChecker airportSpellChecker,
		        String userInput) throws DateException {
			String [] folderName= {"CarRent/avis/crawledTxt/txt","CarRent/budget/crawledTxt/txt","CarRent/enterprise/crawledTxt/txt"};
		    List<String> completionSuggestions = getWordCompletionSuggestions(wordCompletion, splayTree, userInput,scanner);
		    if (!completionSuggestions.isEmpty()) {
		        if(displayWordCompletionSuggestions(completionSuggestions)) {
		        	userInput =handleUserSelectionCompletion(scanner,completionSuggestions,userInput,splayTree);
		        };
		        
		    } else {
		        userInput = handleSpellChecking(scanner, airportDictionary, airportSpellChecker, userInput);
		    }
		    
		   return userInput;
		}

		private static List<String> getWordCompletionSuggestions(WordCompletion wordCompletion, MykSplayTreez splayTree, String userInput, Scanner scanner) {
		    List<String> completionSuggestions = wordCompletion.mykWordCompletion(userInput, splayTree);
		     
		    
		    return completionSuggestions;
		}

		private static boolean displayWordCompletionSuggestions(List<String> completionSuggestions) {
			
		    if (!completionSuggestions.isEmpty()) {
		        System.out.println("Word completion suggestions:");
		        for (int i = 0; i < completionSuggestions.size(); i++) {
			        System.out.println((i + 1) + ". " + completionSuggestions.get(i));
			        
			    }
		        return true;
		        
		    } else {
		        System.out.println("No suggestions found for the given prefix.");
		        return false;
		    }
		}

		private static String handleSpellChecking(
		        Scanner scanner,
		        MySplayTree airportDictionary,
		        AirportSpellChecker airportSpellChecker,
		        String userInput) {
		    if (airportDictionary.search(userInput)) {
		        System.out.println("The airport name '" + userInput + "' is spelled correctly.");
		    } else {
		        List<String> suggestions = airportSpellChecker.findClosestMatchingWords(userInput, airportDictionary, 12, 3);
		        userInput = handleSuggestions(scanner, userInput, suggestions,airportDictionary);
		    }
		    return userInput;
		}

		private static String handleSuggestions(Scanner scanner, String userInput, List<String> suggestions,MySplayTree airportDictionary) {
		    if (!suggestions.isEmpty()) {
		        displaySpellCheckSuggestions(userInput, suggestions);
		        userInput = handleUserSelection(scanner, suggestions, userInput,airportDictionary);
		    } else {
		        System.out.println("The airport name '" + userInput + "' is not found in the database.");
		    }
		    return userInput;
		}

		private static void displaySpellCheckSuggestions(String userInput, List<String> suggestions) {
		    System.out.println("Did you mean:");
		    for (int i = 0; i < suggestions.size(); i++) {
		        System.out.println((i + 1) + ". " + suggestions.get(i));
		    }
		    System.out.println((suggestions.size() + 1) + ". Word not found, add in the dictionary");
		}

		private static String handleUserSelection(Scanner scanner, List<String> suggestions, String userInput,MySplayTree airportDictionary) {
			
			SearchCount searchCount = new SearchCount();
		    System.out.print("Please select a suggestion: ");
		    int suggestionInput = scanner.nextInt();
		    if (suggestionInput == suggestions.size() + 1) {
		        airportDictionary.insert(userInput);
		    } else {
		        userInput = suggestions.get(suggestionInput - 1);
		    }
		    searchCount.performSearchCounts(userInput);
		    return userInput;
		}
		private static String handleUserSelectionCompletion(Scanner scanner, List<String> suggestions, String userInput,MykSplayTreez airportDictionary) {
			
			SearchCount searchCount = new SearchCount();
		    System.out.print("Please select a suggestion: ");
		    int suggestionInput = scanner.nextInt();
		    if (suggestionInput == suggestions.size() + 1) {
		        airportDictionary.mykInsert(userInput);
		    } else {
		        userInput = suggestions.get(suggestionInput - 1);
		       
		    }
		    searchCount.performSearchCounts(userInput);
		    return userInput;
		}

		private static void generateAndScrapeDataFiles(RentalCarScraping crawler, String userInput) throws InterruptedException {
		    GenerateScrapedDataFile avisScraper = new GenerateScrapedDataFile("avis", userInput);
		    GenerateScrapedDataFile budgetScraper = new GenerateScrapedDataFile("budget", userInput);
		    GenerateScrapedDataFile enterpriseScraper = new GenerateScrapedDataFile("enterprise", userInput);

		    String enterpriseDataToWrite = crawler.enterpriseSearch(crawler, new ChromeDriver());
		    enterpriseScraper.scrapeAndWriteData(enterpriseDataToWrite);

		    String avisDataToWrite = crawler.avisSearch(crawler, new ChromeDriver());
		    avisScraper.scrapeAndWriteData(avisDataToWrite);

		    String budgetDataToWrite = crawler.budgetSearch(crawler, new ChromeDriver());
		    budgetScraper.scrapeAndWriteData(budgetDataToWrite);
		}
}
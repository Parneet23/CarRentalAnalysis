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

		    initializeDictionaries(splayTree, airportDictionary, wordCompletion, airportSpellChecker);

		    String userInput = getUserLocationInput(scanner);
		    
		    
		    du = getUserPickUpDateInput(du);
		   // String dropDateInput = getUserDropDateInput();

		    if (shouldProceedWithAvailableData(scanner)) {
		        processExistingData(scanner, wordCompletion, splayTree, airportDictionary, airportSpellChecker, userInput);
		    } else {
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
		        String userInput) {
		    List<String> completionSuggestions = getWordCompletionSuggestions(wordCompletion, splayTree, userInput);
		    if (!completionSuggestions.isEmpty()) {
		        displayWordCompletionSuggestions(completionSuggestions);
		    } else {
		        handleSpellChecking(scanner, airportDictionary, airportSpellChecker, userInput);
		    }
		}

		private static List<String> getWordCompletionSuggestions(WordCompletion wordCompletion, MykSplayTreez splayTree, String userInput) {
		    List<String> completionSuggestions = wordCompletion.mykWordCompletion(userInput, splayTree);
		    displayWordCompletionSuggestions(completionSuggestions);
		    return completionSuggestions;
		}

		private static void displayWordCompletionSuggestions(List<String> completionSuggestions) {
			
		    if (!completionSuggestions.isEmpty()) {
		        System.out.println("Word completion suggestions:");
		        for (String suggestion : completionSuggestions) {
		            System.out.println(" - '" + suggestion + "'");
		        }
		    } else {
		        System.out.println("No suggestions found for the given prefix.");
		    }
		}

		private static void handleSpellChecking(
		        Scanner scanner,
		        MySplayTree airportDictionary,
		        AirportSpellChecker airportSpellChecker,
		        String userInput) {
		    if (airportDictionary.search(userInput)) {
		        System.out.println("The airport name '" + userInput + "' is spelled correctly.");
		    } else {
		        List<String> suggestions = airportSpellChecker.findClosestMatchingWords(userInput, airportDictionary, 12, 3);
		        handleSuggestions(scanner, userInput, suggestions,airportDictionary);
		    }
		}

		private static void handleSuggestions(Scanner scanner, String userInput, List<String> suggestions,MySplayTree airportDictionary) {
		    if (!suggestions.isEmpty()) {
		        displaySpellCheckSuggestions(userInput, suggestions);
		        handleUserSelection(scanner, suggestions, userInput,airportDictionary);
		    } else {
		        System.out.println("The airport name '" + userInput + "' is not found in the database.");
		    }
		}

		private static void displaySpellCheckSuggestions(String userInput, List<String> suggestions) {
		    System.out.println("Did you mean:");
		    for (int i = 0; i < suggestions.size(); i++) {
		        System.out.println((i + 1) + ". " + suggestions.get(i));
		    }
		    System.out.println((suggestions.size() + 1) + ". Word not found, add in the dictionary");
		}

		private static void handleUserSelection(Scanner scanner, List<String> suggestions, String userInput,MySplayTree airportDictionary) {
			String [] folderName= {"CarRent/avis/crawledTxt/txt","CarRent/budget/crawledTxt/txt","CarRent/enterprise/crawledTxt/txt"};
			SearchCount searchCount = new SearchCount();
		    System.out.print("Please select a suggestion: ");
		    int suggestionInput = scanner.nextInt();
		    if (suggestionInput == suggestions.size() + 1) {
		        airportDictionary.insert(userInput);
		    } else {
		        userInput = suggestions.get(suggestionInput - 1);
		    }
		    searchCount.performSearchCounts(userInput);
		    try {
				ParserAndFrequencyCount.entryPoint();
				InvertedIndexing.invertRead(folderName, du.getIntervals(),userInput);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println("Location is: " + userInput);
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
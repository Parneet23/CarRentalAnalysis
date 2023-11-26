package CarRent;

import java.io.IOException;
import java.util.Scanner;

public class CarRent {
	
	 public static void main(String[]args) throws DateException {
		 try  {
			 DataValidationUsingRegex.dateValidation();
		 } catch(DateException e) {
			 System.out.println("Error: " + e.getMessage());
		 }
		 
		 String [] folderName= {"CarRent/avis/crawledTxt/txt","CarRent/budget/crawledTxt/txt","CarRent/enterprise/crawledTxt/txt"};
		 try {
			ParserAndFrequencyCount.entryPoint();
			InvertedIndexing.invertRead(folderName, "montreal");;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}

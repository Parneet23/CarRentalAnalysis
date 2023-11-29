package CarRent;

import java.io.IOException;
import java.util.Scanner;

public class CarRent {
	
	 public static void main(String[]args) throws DateException {
		 long days =0;
		 String [] folderName= {"CarRent/avis/crawledTxt/txt","CarRent/budget/crawledTxt/txt","CarRent/enterprise/crawledTxt/txt"};
		 try  {
			days = DataValidationUsingRegex.dateValidation();
		 } catch(DateException e) {
			 System.out.println("Error: " + e.getMessage());
		 }
		 
		 
		 try {
			ParserAndFrequencyCount.entryPoint();
			InvertedIndexing.invertRead(folderName, days,"Montreal Trudeau Intl Airport","BMW X1");;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}

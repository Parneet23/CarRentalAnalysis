package carRentalAnalysis;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GenerateScrapedDataFile {

    private static final String BASE_PATH = "D:\\SEM1\\ACC\\project\\pages\\";
    private static final String FILE_EXTENSION = ".txt";

    private String filePath;
    //private String dataToWrite;

    public GenerateScrapedDataFile(String company, String location) {
        setFilePath(company,location);
        //this.dataToWrite = dataToWrite;
    }
    
//    public String getDataToWrite() {
//        return dataToWrite;
//    }

    private void setFilePath(String company,String location) {
        // Convert the company name to lowercase for consistency
        String lowercaseCompany = company.toLowerCase();
        String lowercaseLocation = location.toLowerCase();
        this.filePath = BASE_PATH + lowercaseCompany +"\\"+lowercaseLocation+ FILE_EXTENSION;
    }

    public void scrapeAndWriteData(String dataToWrite) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the data to the file
            writer.write(dataToWrite);

            System.out.println("Data has been written to the file: " + filePath);

        } catch (IOException e) {
            // Handle potential IOException
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        // Ask the user for the car rental company
//        System.out.print("Enter the car rental company (avis/budget/enterprise): ");
//        String company = scanner.nextLine();
//
//        // Ask the user for the data to write
////        System.out.print("Enter the data to write to the file: ");
////        String dataToWrite = scanner.nextLine();
//
//        // Create an instance of RentalCarScraping with user input
//       // RentalCarScraping scraper = new RentalCarScraping(company);
//
//        // Call the method to scrape and write data
//        scraper.scrapeAndWriteData(scraper.dataToWrite);
//
//        // Close the Scanner
//        scanner.close();
//    }
}

//
package CarRent;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Scanner;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.interactions.Actions;
//
///**
// * 
// */
//public class RentalCarScraping {
//	
//	// Class variables
//    private String location;
//    private String pickupDate;
//    private String dropDate;
//	
// // Constructor with parameters
//    public RentalCarScraping(String location, String pickupDate, String dropDate) {
//        this.location = location;
//        this.pickupDate = pickupDate;
//        this.dropDate = dropDate;
//    }
// // Getter methods
//    public String getLocation() {
//        return location;
//    }
//
//    public String getPickupDate() {
//        return pickupDate;
//    }
//
//    public String getDropDate() {
//        return dropDate;
//    }
//    
////    public String getDataToWrite() {
////    	return dataToWrite;
////    }
//    
//	public String enterprise(String loc, WebDriver driver)throws InterruptedException{
//		driver.get("https://www.enterprise.ca/");
//
//        // Find the search input field
//        Thread.sleep(5000);
////        WebElement enterpriseSearchBox = driver.findElement(By.id("pickupLocationTextBox"));
////        enterpriseSearchBox.click();
////        
////        WebElement enterpriseCurrentLoc = driver.findElement(By.id("location-current-pickup"));
////        enterpriseCurrentLoc.click();
////        Thread.sleep(5000);
////     // Locate the element you want to click
////        WebElement enterpriseBrowse = driver.findElement(By.xpath("//button[@id='continueButton']"));
////
////        Actions actions = new Actions(driver);
////        actions.click(enterpriseBrowse).build().perform();
//        Thread.sleep(50000);
//        //driver.findElement(By.id("1006474")).click();
//       //driver.findElement(By.xpath("//button[@id='continueButton']")).click();
//        
//        // Perform a Google search
////        searchBox.sendKeys("games"); // Replace with your actual search query
////        searchBox.sendKeys(Keys.RETURN); // Press Enter
//        Thread.sleep(15000);
//        // Wait for the search results to load (you may need to add explicit waits)
//        WebElement html = driver.findElement(By.xpath("//body//div[3]//div[1]//div[1]//div[2]//main[1]//div[1]//section[1]//ul[1]"));
//        String elementHTML = html.getAttribute("outerHTML");
//		return elementHTML;
//	}
//	
//	public void budgetSearch(RentalCarScraping crawler,WebDriver driver) throws InterruptedException {
//		driver.get("https://www.budget.ca/");
//		Thread.sleep(10000);
//		
//		WebElement modalDialog = driver.findElement(By.xpath("/html/body/div[14]/div[3]/div/div"));
//		modalDialog.findElement(By.xpath("/html/body/div[14]/div[3]/div/div/div/div[2]/form/div[3]/div[4]/button")).click();
//		
//		Thread.sleep(10000);
//		WebElement location = driver.findElement(By.xpath("//*[@id='PicLoc_value']"));
//		location.sendKeys(crawler.getLocation());
//		WebElement budgetFromDate = driver.findElement(By.xpath("/html/body/div[3]/section/div[2]/div/div[1]/form/div[1]/div[2]/div[7]/div[1]/div[1]/div[2]/input"));
//		budgetFromDate.sendKeys(crawler.getPickupDate());
//		WebElement budgetToDate = driver.findElement(By.xpath("//*[@id='to']"));
//		budgetToDate.sendKeys(crawler.getDropDate());
//		WebElement search = driver.findElement(By.xpath("//*[@id='res-home-select-car']"));
//		search.click();
//		Thread.sleep(15000);
//		WebElement select = driver.findElement(By.xpath("/html/body/div[3]/div[6]/div/footer/div[3]/div/div[1]/div/div/div[2]/div[2]/ul/li[1]/div[2]/a"));
//		select.click();
//		Thread.sleep(15000);
//		WebElement html = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div[1]/div/div/div[2]/div[1]/section[3]/div[1]"));
//		System.out.println(html.getAttribute("outerHTML"));
//		
//	}
//	
//	public String avisSearch(RentalCarScraping crawler,WebDriver driver) throws InterruptedException {
//		driver.get("https://www.avis.com/");
//		Thread.sleep(10000);
//		//driver.manage().window().maximize();
////		WebElement modalDialog = driver.findElement(By.xpath("/html/body/div[18]/div[3]/div/div"));
////		modalDialog.findElement(By.xpath("/html/body/div[18]/div[3]/div/div/div/div[2]/form/div[3]/div[4]/button")).click();
////		
////		Thread.sleep(10000);
//		WebElement location = driver.findElement(By.xpath("//*[@id='PicLoc_value']"));
//		location.sendKeys(crawler.getLocation());
//		WebElement budgetFromDate = driver.findElement(By.xpath("//*[@id='from']"));
//		budgetFromDate.sendKeys(crawler.getPickupDate());
//		WebElement budgetToDate = driver.findElement(By.xpath("//*[@id='to']"));
//		budgetToDate.sendKeys(crawler.getDropDate());
//		WebElement search = driver.findElement(By.xpath("//*[@id='res-home-select-car']"));
//		search.click();
//		Thread.sleep(19000);
//		
//		WebElement modalDialog = driver.findElement(By.xpath("/html/body/div[6]/div[1]/div[3]/div[1]/footer/div[3]/div/div[1]/div/div/div[2]/div[2]/ul/li[1]/div[2]/a"));
//		//modalDialog.findElement(By.xpath("/html/body/div[6]/div[1]/div[3]/div[1]/footer/div[3]/div/div[1]/div/div/div[2]/div[2]/ul/li[1]/div[2]/a")).click();
//		modalDialog.click();
//		Thread.sleep(15000);
//		WebElement html = driver.findElement(By.xpath("/html/body/div[6]/div[2]/div[1]/div/div/div[2]/div[1]/section[3]/div[1]"));
//		System.out.println(html.getAttribute("outerHTML"));
//		return html.getAttribute("outerHTML");
//		
//	}
//	
//	 private static Date parseDate(String dateStr) {
//	        try {
//	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	            return dateFormat.parse(dateStr);
//	        } catch (ParseException e) {
//	            System.err.println("Error parsing date. Please enter a valid date format (yyyy-MM-dd).");
//	            return null;
//	        }
//	    }
//	
//    public static void main(String[] args) throws InterruptedException{
//    	
//    	Scanner scanner = new Scanner(System.in);
//    	WebDriver driver = new ChromeDriver();
//    	try {
//            // Ask the user for location
//            System.out.print("Enter the location: ");
//            String location = scanner.nextLine();
//            
//         // Initialize variables for pickup date and drop date
//            String pickupDateStr = "";
//            String dropDateStr = "";
//            Date pickupDate = null;
//            Date dropDate = null;
//
//            // Loop until valid dates are provided
//            while (true) {
//                // Ask the user for pickup date
//                System.out.print("Enter the pickup date (yyyy-MM-dd): ");
//                pickupDateStr = scanner.nextLine();
//                pickupDate = parseDate(pickupDateStr);
//
//                // Ask the user for drop date
//                System.out.print("Enter the drop date (yyyy-MM-dd): ");
//                dropDateStr = scanner.nextLine();
//                dropDate = parseDate(dropDateStr);
//
//                // Validate that drop date is not less than pickup date
//                if (dropDate != null && dropDate.before(pickupDate)) {
//                    System.err.println("Error: Drop date cannot be before pickup date. Please enter valid dates.");
//                } else {
//                    // Break out of the loop if validation passes
//                    break;
//                }
//            }
//       
//            RentalCarScraping crawler = new RentalCarScraping(location,pickupDateStr,dropDateStr);
//            crawler.avisSearch(crawler, driver);
//            // Perform actions with the collected input (e.g., create a CarRentalBooking object)
//
//        } catch (Exception e) {
//            System.err.println("An error occurred: " + e.getMessage());
//        } finally {
//            // Close the Scanner and driver
//            scanner.close();
//            driver.quit();
//        }
//    
//    	
//    	
//        // Set the path to the ChromeDriver executable
////        System.setProperty("webdriver.chrome.driver", "D:\\SEM1\\ACC\\project\\chromedriver.exe");
////        
////        
////        RentalCarScraping data = new RentalCarScraping("","","");
////        // Create a Chrome WebDriver instance
////        WebDriver driver = new ChromeDriver();
////        driver.get("https://www.budget.ca/");
////
////        data.budgetSearch(driver);
//        //driver.get("https://www.budget.ca/");
//        //driver.get("https://www.avis.com/");
//        // Find the search input field
////        Thread.sleep(5000);
////        Thread.sleep(5000);
////        Thread.sleep(5000);
////        WebElement budgetFromDate = driver.findElement(By.id("from"));
////        budgetFromDate.sendKeys("11/05/2023");
//        
//        
//        //Thread.sleep(5000);
//     // Locate the element you want to click
//       
////        WebElement budgetSearchBox = driver.findElement(By.id("PicLoc_value"));
////        //budgetSearchBox.click();
////        budgetSearchBox.sendKeys("Toronto Pearson Intl Airport, Mississauga, Ontario, Canada-(YYZ)");
////        WebElement budgetToDate = driver.findElement(By.xpath("//input[@id='to']"));
////        budgetToDate.sendKeys("11/07/2023");
////        budgetSearchBox.sendKeys(Keys.RETURN);
////        WebElement select = driver.findElement(By.xpath("//body//div[3]//div[6]//div[1]//footer[1]//div[3]//div[1]//div[1]//div[1]//div[1]//div[2]//div[2]//ul[1]//li[1]//div[2]//a[1]"));
////        select.click();
//       // Thread.sleep(5000);
//    
////        WebElement budgetSearch = driver.findElement(By.xpath("//button[@id='res-home-select-car']"));
////        budgetSearch.click();
//        //budget xpath
//        //WebElement html = driver.findElement(By.xpath("//*[@id='reservation-partial']/div/div/div[2]/div[1]/section[3]/div[1]"));
//        //avis xpath
//        //WebElement html = driver.findElement(By.xpath("//*[@id='reservation-partial']/div/div/div[2]/div[1]/section[3]/div[1]"));
//        //String elementHTML = enterprise("",driver);
//        //String elementHTML = html.getAttribute("outerHTML");
//        
//       // System.out.println(elementHTML);
//     // Specify the file path
//        //String filePath = "D:\\SEM1\\ACC\\project\\pages\\enterprise\\windsor.txt";
//
//        // String data to be written to the file
//        //String dataToWrite = "Hello, this is some data to write to the file.";
//
//        // Use try-with-resources to automatically close the BufferedWriter
////        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
////            // Write the data to the file
////            writer.write(elementHTML);
////
////            System.out.println("Data has been written to the file.");
////
////        } catch (IOException e) {
////            // Handle potential IOException
////            e.printStackTrace();
////        }
//
////        Actions actions = new Actions(driver);
////        actions.click(enterpriseBrowse).build().perform();
////        Thread.sleep(10000);
////        driver.findElement(By.id("1006474")).click();
//       //driver.findElement(By.xpath("//button[@id='continueButton']")).click();
//        
//        // Perform a Google search
////        searchBox.sendKeys("games"); // Replace with your actual search query
////        searchBox.sendKeys(Keys.RETURN); // Press Enter
//        Thread.sleep(15000);
////        // Wait for the search results to load (you may need to add explicit waits)
////        WebElement html = driver.findElement(By.xpath("//body//div[3]//div[1]//div[1]//div[2]//main[1]//div[1]//section[1]//ul[1]"));
////        String elementHTML = html.getAttribute("outerHTML");
////		return elementHTML;
//        
//        //driver.quit();
//    }
//}
//
//
//
//
//











import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import carRentalAnalysis.RentalCarScraping;

/**
 * Scraping developed by:
 * Name: Talha Haseeb Mohammed
 * Student ID: 110128322
 * Email: mohamm2z@uwindsor.ca
 */
public class RentalCarScraping {
	
	// Class variables
    private String location;
    private String pickupDate;
    private String dropDate;
	
 // Constructor with parameters
    public RentalCarScraping(String location, String pickupDate, String dropDate) {
        this.location = location;
        this.pickupDate = pickupDate;
        this.dropDate = dropDate;
    }
 // Getter methods
    public String getLocation() {
        return location;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public String getDropDate() {
        return dropDate;
    }
    
		
	public String budgetSearch(RentalCarScraping crawler,WebDriver driver) throws InterruptedException {
		driver.get("https://www.budget.ca/");
		Thread.sleep(10000);
		
		WebElement modalDialog = driver.findElement(By.xpath("/html/body/div[14]/div[3]/div/div"));
		modalDialog.findElement(By.xpath("/html/body/div[14]/div[3]/div/div/div/div[2]/form/div[3]/div[4]/button")).click();
		
		Thread.sleep(10000);
		WebElement location = driver.findElement(By.xpath("//*[@id='PicLoc_value']"));
		location.sendKeys(crawler.getLocation());
		WebElement budgetFromDate = driver.findElement(By.xpath("/html/body/div[3]/section/div[2]/div/div[1]/form/div[1]/div[2]/div[7]/div[1]/div[1]/div[2]/input"));
		budgetFromDate.sendKeys(crawler.getPickupDate());
		WebElement budgetToDate = driver.findElement(By.xpath("//*[@id='to']"));
		budgetToDate.sendKeys(crawler.getDropDate());
		WebElement search = driver.findElement(By.xpath("//*[@id='res-home-select-car']"));
		search.click();
		Thread.sleep(15000);
		WebElement select = driver.findElement(By.xpath("/html/body/div[3]/div[6]/div/footer/div[3]/div/div[1]/div/div/div[2]/div[2]/ul/li[1]/div[2]/a"));
		select.click();
		Thread.sleep(15000);
		WebElement html = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div[1]/div/div/div[2]/div[1]/section[3]/div[1]"));
		System.out.println(html.getAttribute("outerHTML"));
		return html.getAttribute("outerHTML");
	}
	
	public String enterpriseSearch(RentalCarScraping crawler,WebDriver driver)throws InterruptedException{
		driver.get("https://www.enterprise.ca/");

        
		// Find the search input field
        Thread.sleep(5000);
        WebElement enterpriseSearchBox = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[3]/div/div/div[7]/div/div/div[1]/div/div/div[2]/section/div/div/div[3]/div/div[2]/div/form/div/div[1]/div/div[1]/div[1]/input"));
        enterpriseSearchBox.sendKeys(crawler.location);
        
//        WebElement enterpriseCurrentLoc = driver.findElement(By.id("location-current-pickup"));
//        enterpriseCurrentLoc.sendKeys(crawler.getLocation());
        Thread.sleep(10000);
     // Locate the element you want to click
        WebElement selectLocation = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[3]/div/div/div[7]/div/div/div[1]/div/div/div[2]/section/div/div/div[3]/div/div[2]/div/form/div/div[1]/div/div[1]/div[2]/div[2]/ul/li/small"));
        selectLocation.click();
        Thread.sleep(2000);
        WebElement close = driver.findElement(By.xpath("/html/body/div[7]/div[2]/div/div[2]/button"));
        close.click();
        
        WebElement enterpriseBrowse = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[3]/div/div/div[7]/div/div/div[1]/div/div/div[2]/section/div/div/div[3]/div/div[2]/div/div/div[4]/button"));
        enterpriseBrowse.click();
        //Actions actions = new Actions(driver);
        //actions.click(enterpriseBrowse).build().perform();
        //Thread.sleep(20000);
//        driver.findElement(By.id("1006474")).click();
//       driver.findElement(By.xpath("//button[@id='continueButton']")).click();
//        
 //        Perform a Google search
       
        Thread.sleep(5000);
        // Wait for the search results to load (you may need to add explicit waits)
        WebElement html = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/main/div/section/ul"));
        String elementHTML = html.getAttribute("outerHTML");
		System.out.println(elementHTML);
		return elementHTML;
	}

	
	public void avisSearch(RentalCarScraping crawler,WebDriver driver) throws InterruptedException {
		//public String avisSearch(RentalCarScraping crawler,WebDriver driver) throws InterruptedException {
			driver.get("https://www.avis.com/");
			Thread.sleep(10000);
			//driver.manage().window().maximize();
//			WebElement modalDialog = driver.findElement(By.xpath("/html/body/div[18]/div[3]/div/div"));
//			modalDialog.findElement(By.xpath("/html/body/div[18]/div[3]/div/div/div/div[2]/form/div[3]/div[4]/button")).click();
//			
//			Thread.sleep(10000);
			WebElement location = driver.findElement(By.xpath("//*[@id='PicLoc_value']"));
			location.sendKeys(crawler.getLocation());
			WebElement budgetFromDate = driver.findElement(By.xpath("//*[@id='from']"));
			budgetFromDate.sendKeys(crawler.getPickupDate());
			WebElement budgetToDate = driver.findElement(By.xpath("//*[@id='to']"));
			budgetToDate.sendKeys(crawler.getDropDate());
			WebElement search = driver.findElement(By.xpath("//*[@id='res-home-select-car']"));
			search.click();
			Thread.sleep(19000);
			
			//WebElement modalDialog = driver.findElement(By.xpath("/html/body/div[6]/div[1]/div[3]/div[1]/footer/div[3]/div/div[1]/div/div/div[2]/div[2]/ul/li[1]/div[2]/a"));
			WebElement modalDialog = driver.findElement(By.xpath("/html/body/div[4]/div[6]/div[1]/footer/div[3]/div/div[1]/div/div"));
			modalDialog.findElement(By.xpath("/html/body/div[4]/div[6]/div[1]/footer/div[3]/div/div[1]/div/div/div[2]/div[2]/ul/li[1]/div[2]/a")).click();
			//modalDialog.click();
			Thread.sleep(15000); 
			WebElement html = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div[1]/div/div/div[2]/div[1]/section[3]/div[1]"));
			System.out.println(html.getAttribute("outerHTML"));
			//return html.getAttribute("outerHTML");
			
		}
	
	 private static Date parseDate(String dateStr) {
	        try {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            return dateFormat.parse(dateStr);
	        } catch (ParseException e) {
	            System.err.println("Error parsing date. Please enter a valid date format (yyyy-MM-dd).");
	            return null;
	        }
	    }
	
//    public static void main(String[] args) throws InterruptedException{
//    	
//    	Scanner scanner = new Scanner(System.in);
//    	WebDriver driver = new ChromeDriver();
//    	try {
//            // Ask the user for location
//            System.out.print("Enter the location: ");
//            String location = scanner.nextLine();
//            
//         // Initialize variables for pickup date and drop date
//            String pickupDateStr = "";
//            String dropDateStr = "";
//            Date pickupDate = null;
//            Date dropDate = null;
//
//             //Loop until valid dates are provided
//            while (true) {
//                // Ask the user for pickup date
//                System.out.print("Enter the pickup date (yyyy-MM-dd): ");
//                pickupDateStr = scanner.nextLine();
//                pickupDate = parseDate(pickupDateStr);
//
//                // Ask the user for drop date
//                System.out.print("Enter the drop date (yyyy-MM-dd): ");
//                dropDateStr = scanner.nextLine();
//                dropDate = parseDate(dropDateStr);
//
//                // Validate that drop date is not less than pickup date
//                if (dropDate != null && dropDate.before(pickupDate)) {
//                    System.err.println("Error: Drop date cannot be before pickup date. Please enter valid dates.");
//                } else {
//                    // Break out of the loop if validation passes
//                    break;
//                }
//            }
//       
//            RentalCarScraping crawler = new RentalCarScraping(location,pickupDateStr,dropDateStr);
//            //RentalCarScraping crawler = new RentalCarScraping("","","");
//            crawler.enterpriseSearch(crawler, driver);
//         //   crawler.avisSearch(crawler, driver);
//           
//
//        } catch (Exception e) {
//            System.err.println("An error occurred: " + e.getMessage());
//        } finally {
//            // Close the Scanner and driver
//            scanner.close();
//            driver.quit();
//        }
//    }
}
//

//while(true) {
//	
//	
//    // Ask the user for pickup date
//    System.out.print("Enter the pickup date (yyyy-MM-dd): ");
//    pickupDateStr = scanner.nextLine();
//    pickupDate = parseDate(pickupDateStr);
//
//    // Ask the user for drop date
//    System.out.print("Enter the drop date (yyyy-MM-dd): ");
//    dropDateStr = scanner.nextLine();
//    dropDate = parseDate(dropDateStr);
//
//    // Validate that drop date is not less than pickup date
//    if (dropDate != null && dropDate.before(pickupDate)) {
//        System.err.println("Error: Drop date cannot be before pickup date. Please enter valid dates.");
//    } else {
//        // Break out of the loop if validation passes
//        break;
//    }
//}

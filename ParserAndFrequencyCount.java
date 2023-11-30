package CarRent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//Creating class to store parse information and storing them in dictionary for counting the frequency using website name
public class ParserAndFrequencyCount {
	String websiteName;
    String carType;
    String carModel;
    String pickUpLocation;
    float price;
    int numberOfBags;
    int numberOfSeats;
    Dictionary dict;
    Set<String> set= new HashSet<String>();
    
    //Constructor
	public ParserAndFrequencyCount() {
		websiteName="";
		carType="";
		carModel="";
		pickUpLocation="";
		price=0;
		numberOfBags=0;
		numberOfSeats=0;
		dict = new Hashtable();
	}
	
	//As key is the integer getting the maximum value of key for adding further values in dictionary
	public int getMaxKey() {
		Enumeration<Integer> k = dict.keys();
		int maxKey=Integer.MIN_VALUE;
        while (k.hasMoreElements()) {
            int key = k.nextElement();
            if(key > maxKey) {
            	maxKey = key;
            }
        }
        return (maxKey != Integer.MIN_VALUE)? maxKey:-1;
	}
	public void freqCount(String location, int enterprise, int budget, int avis) throws IOException {
		System.out.println("For enterprise.ca, avis.ca, budget.ca got " + enterprise + ", " + avis + " and " + budget + " entries respectively in dictionary");
		String outFile=location+ "\\" + "count.txt";
		BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
		String output="";
		output += "https://www.enterprise.ca  = " + enterprise + "\n";
		output += "https://www.budget.ca =  " + budget + "\n";
		output += "https://www.avis.ca =  " + avis + "\n"; 
		bw.write(output);
	    bw.close();
	}
        public void countCarModel() {
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String str = it.next();
			Enumeration<Integer> k = dict.keys();
			ParserAndFrequencyCount freqObject = new ParserAndFrequencyCount();
			int model = 0;
			while (k.hasMoreElements()) {
				int key = k.nextElement();
				freqObject = (ParserAndFrequencyCount) dict.get(key);
				if (str.equals(freqObject.carModel)) {
					model++;
				}
			}
			if(model>0)
			System.out.println("For " + str + " model we have " + model + " option(s)");
		}
	}
	//For printing the dictionary and frequency of values present in dictionary based on website name
	public void printDict(String fileLocation) throws IOException {
    	Enumeration<Integer> k = dict.keys();
    	int enterprise=0;
    	int budget=0;
    	int avis=0;
        while (k.hasMoreElements()) {
        	int key = k.nextElement();
            ParserAndFrequencyCount freqObject=new ParserAndFrequencyCount();
            freqObject=(ParserAndFrequencyCount) dict.get(key);
            if(freqObject.websiteName.equals("https://www.enterprise.ca"))
            	enterprise++;
            if(freqObject.websiteName.equals("https://www.budget.ca"))
            	budget++;
            if(freqObject.websiteName.equals("https://www.avis.ca"))
            	avis++;
            System.out.println("Key: " + key + ", Name: " + freqObject.websiteName + ", Pick Up Location: " + freqObject.pickUpLocation + ", Type: " + freqObject.carType + ", Model: " + 
            		freqObject.carModel + ", Price: " + freqObject.price + ", Bag Count: " + freqObject.numberOfBags + ", Seat Count: " + freqObject.numberOfSeats);
        }
        freqCount(fileLocation, enterprise, budget, avis);
        
    }
	//flow starts from here, parsing the important details from enterprise, avis and budget and printing the frequency count
	public static  void entryPoint() throws IOException {
		ParserAndFrequencyCount freqObject = new ParserAndFrequencyCount();
		int key = freqObject.getMaxKey();
		AvisParser avisParserObject=new AvisParser();
		File folder1 = new File("CarRent/avis/crawledTxt");
		avisParserObject.listFileNameForFolder(folder1, freqObject.dict, key+1, freqObject.set);
		key = freqObject.getMaxKey();
		BudgetParser budgetParserObject=new BudgetParser();
		File folder2 = new File("CarRent/budget/crawledTxt");
		budgetParserObject.listFileNameForFolder(folder2, freqObject.dict, key+1, freqObject.set);
		EnterpriseParser enterpriseParserObject = new EnterpriseParser();
		key = freqObject.getMaxKey();
		File folder3 = new File("CarRent/enterprise/crawledTxt");
		enterpriseParserObject.listFileNameForFolder(folder3, freqObject.dict, key+1, freqObject.set);
		freqObject.printDict("CarRent");
		freqObject.countCarModel();
	}
	
}

package pkg;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class FrequencyCount {
	String websiteName;
    String carType;
    String carModel;
    float price;
    int numberOfBags;
    int numberOfSeats;
    Dictionary dict;
    
	public FrequencyCount() {
		websiteName="";
		carType="";
		carModel="";
		price=0;
		numberOfBags=0;
		numberOfSeats=0;
		dict = new Hashtable();
	}
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
	public void printDict() {
    	Enumeration<Integer> k = dict.keys();
    	int enterprise=0;
    	int budget=0;
    	int avis=0;
        while (k.hasMoreElements()) {
        	int key = k.nextElement();
            FrequencyCount freqObject=new FrequencyCount();
            freqObject=(FrequencyCount) dict.get(key);
            if(freqObject.websiteName.equals("https://www.enterprise.ca"))
            	enterprise++;
            if(freqObject.websiteName.equals("https://www.budget.ca"))
            	budget++;
            if(freqObject.websiteName.equals("https://www.avis.ca"))
            	avis++;
            System.out.println("Key: " + key + ", Name: " + freqObject.websiteName + ", Type: " + freqObject.carType + ", Model: " + 
            		freqObject.carModel + ", Price: " + freqObject.price + ", Bag Count: " + freqObject.numberOfBags + ", Seat Count: " + freqObject.numberOfSeats);
        }
        System.out.println("For enterprise.ca, avis.ca, budget.ca got " + enterprise + " " + avis + " " + budget + " entries respectively in dictionary");
    }
	
	public static void main(String [] args) throws IOException {
		FrequencyCount freqObject = new FrequencyCount();
		int key = freqObject.getMaxKey();
		AvisParser avisParserObject=new AvisParser();
		File folder1 = new File("F:\\University\\Semester1\\ACC\\Project\\Avis\\crawledTxt");
		avisParserObject.listFileNameForFolder(folder1, freqObject.dict, key+1);
		key = freqObject.getMaxKey();
		BudgetParser budgetParserObject=new BudgetParser();
		File folder2 = new File("F:\\University\\Semester1\\ACC\\Project\\Budget\\crawledTxt");
		budgetParserObject.listFileNameForFolder(folder2, freqObject.dict, key+1);
		EnterpriseParser enterpriseParserObject = new EnterpriseParser();
		key = freqObject.getMaxKey();
		File folder3 = new File("F:\\University\\Semester1\\ACC\\Project\\Enterprise\\crawledTxt");
		enterpriseParserObject.listFileNameForFolder(folder3, freqObject.dict, key+1);
		freqObject.printDict();
		
		
	}
    
}

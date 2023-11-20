package pkg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class EnterpriseParser {
	public void convertFileToWebPage (File folder, String filename, Dictionary dict, int key) {
		try {
			FrequencyCount freqCount=new FrequencyCount();
	    	freqCount.websiteName="https://www.enterprise.ca";
			String loc=folder+"\\"+filename;
			BufferedReader bfReader = new BufferedReader(new FileReader(loc));
			String readFromFile;
			String fileContents = "";
			String fileName = filename.substring(0,filename.lastIndexOf("."));
			while((readFromFile = bfReader.readLine()) != null) {
				fileContents += "\n" + readFromFile;
			}
			String file=folder+"\\html\\"+fileName+".html";
			String outFile=folder + "\\txt\\"+fileName+ ".txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    writer.write(fileContents);
		    writer.close();
		    File input = new File(file);
		    Document doc = Jsoup.parse(input, "UTF-8", "");
		    BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
		    Elements car_price=doc.getElementsByClass("pricing-details__price-total");
		    String output="";
		    int itr2=0;
		    Elements summary_containers = doc.getElementsByClass("vehicle-item_summary-container");
		    for(int itr=0; itr<summary_containers.size(); itr++) {
		    	 output += summary_containers.get(itr).select("h2").text() + "\n";
		    	 freqCount.carType=summary_containers.get(itr).select("h2").text();
		    	 output += summary_containers.get(itr).getElementsByClass("vehicle-item__models").text() + "\n";
		    	 freqCount.carModel=summary_containers.get(itr).getElementsByClass("vehicle-item__models").text();
		    	 Elements unordered_list=summary_containers.get(itr).getElementsByClass("vehicle-item__attributes");
		    	 Elements list = unordered_list.select("li");
		    	 for(int itr1=0; itr1<list.size(); itr1++) {
		    		 output += list.get(itr1).text() + "\n";
		    		 String str=list.get(itr1).text();
		    		 str=str.substring(0,1);
		    		 if(itr1==1) {
		    			 freqCount.numberOfSeats=Integer.parseInt(str);
		    		 }
		    		 if(itr1==2) {
		    			 freqCount.numberOfBags=Integer.parseInt(str);
		    		 }
		    	 }
		    	 
		    	 String price=car_price.get(itr2).text();
		    	 price=price.substring(0, 5);
		    	 freqCount.price=Float.parseFloat(price);
		    	 dict.put(key, freqCount);
		    	 key++;
		    	 output += price + "\n";;
		    	 output += "\n";
		    	 itr2++;
		    }
		    bw.write(output);
		    bw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	public void listFileNameForFolder(File folder, Dictionary dict, int key) throws IOException {
		//Iterating the folder and parsing files present inside the folder 
		for (File fileName : folder.listFiles()) {
	        if (fileName.isDirectory()) {
	        	continue;
	        } else {
	        	 convertFileToWebPage(folder,fileName.getName(),dict, key);
	            
	        }
	    }
		
	}
}

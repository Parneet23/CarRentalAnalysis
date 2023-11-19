package pkg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class AvisParser {
	
    public void parseAvisWebsite (File folder, String filename, Dictionary dict) {
		try {
			String loc=folder+"\\"+filename;
			BufferedReader bfReader = new BufferedReader(new FileReader(loc));
			String readFromFile;
			String fileContents = "";
			String fileName = filename.substring(0,filename.lastIndexOf("."));
			while((readFromFile = bfReader.readLine()) != null) {
				fileContents += "\n" + readFromFile;
			}
			String file=folder+"\\html\\"+fileName+".html";
			String outFile=folder+"\\txt\\"+fileName+ ".txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    writer.write(fileContents);
		    writer.close();
		    File input = new File(file);
		    Document doc = Jsoup.parse(input, "UTF-8", "");
		    BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
		    Elements car_option = doc.getElementsByClass("col-sm-5 col-xs-12 avilcardtl dualPayOption");
		    Elements price = doc.getElementsByClass("paybtndtl col-lg-12 col-xs-12");
		    Elements facilities = doc.getElementsByClass("available-car-fac hidden");
		    String output="";
		    int itr2=0;
		    int itr1=0;
		    int itr3=1;
		    for(int itr=0; itr<car_option.size()&&itr1<facilities.size()&&itr2<price.size(); itr++) {
		    	FrequencyCount freqCount=new FrequencyCount();
		    	freqCount.websiteName="https://www.avis.ca";
			    output += car_option.get(itr).select("h3").text() + "\n";
			    freqCount.carType=car_option.get(itr).select("h3").text();
		    	output += car_option.get(itr).getElementsByClass("featurecartxt similar-car").text() + "\n";
		    	freqCount.carModel=car_option.get(itr).getElementsByClass("featurecartxt similar-car").text();
		    	output += facilities.get(itr1).getElementsByClass("four-door-feat").text() + "\n";
		    	output += facilities.get(itr1).getElementsByClass("four-seats-feat").text() + "\n";
		    	String str=facilities.get(itr1).getElementsByClass("four-seats-feat").text();
		    	if(str.indexOf(" ") > 0) {
			    	str=str.substring(0, str.indexOf(" "));
			    	freqCount.numberOfSeats=Integer.parseInt(str);
		    	}
		    	else {
		    		freqCount.numberOfSeats=0;
		    	}
		    	output += facilities.get(itr1).getElementsByClass("ac-seats-feat").text() + "\n";
		    	output += facilities.get(itr1).getElementsByClass("four-automatic-feat").text() + "\n";
		    	output += facilities.get(itr1).getElementsByClass("four-bags-feat").text() + "\n";
		    	str = facilities.get(itr1).getElementsByClass("four-bags-feat").text();
		    	int number1=0, number2=0;
		    	if(str.indexOf(" ") > 0) {
		    		str=str.substring(0, str.indexOf(" "));
		    		number1=Integer.parseInt(str);
		    	}
		    	output += facilities.get(itr1).getElementsByClass("four-bags-feat-small").text() + "\n";
		    	str = facilities.get(itr1).getElementsByClass("four-bags-feat-small").text();
		    	if(str.indexOf(" ") > 0) {
		    		str=str.substring(0, str.indexOf(" "));
		    		number2=Integer.parseInt(str);
		    	}
		    	freqCount.numberOfBags=number1+number2;
		    	itr1++;
		    	output += price.get(itr2).getElementsByClass("payamntp").text()+ "\n";
		    	str=price.get(itr2).getElementsByClass("payamntp").text();
		    	str=str.substring(str.indexOf("$")+1);
		    	freqCount.price=Float.parseFloat(str);
		    	itr2++;
		    	output += "\n";
		    	dict.put(itr3, freqCount);
		    	itr3++;
		    }
		    bw.write(output);
		    bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void listFileNameForFolder(File folder, Dictionary dictObject) throws IOException {
		//Iterating the folder and parsing files present inside the folder 
		for (File fileName : folder.listFiles()) {
	        if (fileName.isDirectory()) {
	        	continue;
	        } else {
	        	parseAvisWebsite(folder,fileName.getName(), dictObject);
	            
	        }
	    }
	}
	
}

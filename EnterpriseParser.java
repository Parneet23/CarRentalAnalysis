package pkg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class EnterpriseParser {
	public void convertFileToWebPage (File folder, String filename) {
		try {
			String loc=folder+"\\"+filename;
			System.out.println(loc);
			BufferedReader bfReader = new BufferedReader(new FileReader(loc));
			String readFromFile;
			String fileContents = "";
			String fileName = filename.substring(0,filename.lastIndexOf("."));
			while((readFromFile = bfReader.readLine()) != null) {
				fileContents += "\n" + readFromFile;
			}
			String file="F:\\University\\Semester1\\ACC\\Project\\Enterprise\\html\\"+fileName+".html";
			String outFile="F:\\University\\Semester1\\ACC\\Project\\Enterprise\\txt\\"+fileName+ ".txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    writer.write(fileContents);
		    writer.close();
		    File input = new File(file);
		    Document doc = Jsoup.parse(input, "UTF-8", "");
		    Elements unordered_list1 = doc.getElementsByClass("vehicle-list");
		    Elements item1 = unordered_list1.select("li");
		    BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
		    String output="For the mentioned location windsor we have around ";
		    output += item1.size();
		    output += " car rental options\n";
		    for(int itr=0; itr<item1.size(); itr++) {
		    	output += item1.get(itr).getElementsByClass("vehicle-item__tour-info").text() + "\n";
		    	output += item1.get(itr).getElementsByClass("vehicle-item__title").text() + "\n";
		    	output += item1.get(itr).getElementsByClass("vehicle-item__models").text() + "\n";;
		    	Elements unordered_list2 = item1.get(itr).getElementsByClass("vehicle-item__attributes");
		    	String price = item1.get(itr).getElementsByClass("unit").text();
		    	price += item1.get(itr).getElementsByClass("fraction").text();
		    	price += " ";
		    	price += item1.get(itr).getElementsByClass("symbol").text();
		    	output += price + "\n";
		    	Elements item2 = unordered_list2.select("li");
		    	for(int itr1=0; itr1<item2.size(); itr1++) {
		    		output += item2.get(itr1).text() + "\n";
		    	}
		    	
		    }
		    bw.write(output);
		    bw.close();
		  
		     
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
	}
	public void listFileNameForFolder(File folder) throws IOException {
		//Iterating the folder and parsing files present inside the folder 
		for (File fileName : folder.listFiles()) {
	        if (fileName.isDirectory()) {
	        	//If folder is present inside the folder extracting files present in the folder
	        	listFileNameForFolder(fileName);
	        } else {
	        	convertFileToWebPage(folder,fileName.getName());
	            
	        }
	    }
	}
	public static void main(String [] args) throws IOException {
		System.out.println("Putting content in text file");
		EnterpriseParser entParserObject=new EnterpriseParser();
		File folder = new File("F:\\University\\Semester1\\ACC\\Project\\Enterprise\\crawledTxt");
		entParserObject.listFileNameForFolder(folder);
	}

}

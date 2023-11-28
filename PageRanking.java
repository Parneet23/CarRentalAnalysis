package CarRent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PageRanking {
	String websiteName;
	Integer numberOfOccurance;

	// parameterized constructor to initialize class variables
	public PageRanking(String string1, Integer string2) {
		this.websiteName = string1;
		this.numberOfOccurance = string2;
	}

	// getters for class variables
	public String getWebsiteName() {
		return websiteName;
	}

	public Integer getNumberOfOccurance() {
		return numberOfOccurance;
	}

	// method to divide the array into two parts
	private static int splitArray(PageRanking[] arrayOfwebsites, int lefty, int righty) {

		PageRanking pivotValue = arrayOfwebsites[righty];
		int a = lefty - 1;

		for (int b = lefty; b < righty; b++) {
			// list.get(j).compareTo(pivot) < 0
			if (arrayOfwebsites[b].compareTo(pivotValue) > 0) {
				a = a + 1;

				PageRanking temporary = arrayOfwebsites[a];
				arrayOfwebsites[a] = arrayOfwebsites[b];
				arrayOfwebsites[b] = temporary;
			}
		}
		PageRanking temporary = arrayOfwebsites[a + 1];
		arrayOfwebsites[a + 1] = arrayOfwebsites[righty];
		arrayOfwebsites[righty] = temporary;

		return a + 1;
	}

	// method to compare and return the class variable via which comparison is to be
	// done
	public int compareTo(PageRanking pr) {
		return Integer.compare(this.numberOfOccurance, pr.numberOfOccurance);
	}

	// method to perform in-place quick sorting
	private static void quickSorting(PageRanking[] arrayOfwebsites, int lefty, int righty) {
		if (lefty < righty) {
			int pivotPosition = splitArray(arrayOfwebsites, lefty, righty);
			quickSorting(arrayOfwebsites, lefty, pivotPosition - 1);
			quickSorting(arrayOfwebsites, pivotPosition + 1, righty);
		}
	}

	// main method
	public static void main(String[] args) {

		int totalWebsites = 3, i = 0;
		PageRanking[] pageRanker = new PageRanking[totalWebsites];

		BufferedReader bReader;

		try {
			//get the websites name and their occuarances from the txt files
			bReader = new BufferedReader(new FileReader("CarRent/count.txt"));
			String line = bReader.readLine();
			
			//read the whole file line by line
			while (line != null) {
				String[] stringArray = new String[2];
				line = line.replaceAll("\\s", "");
				stringArray = line.split("=");
				pageRanker[i] = new PageRanking(stringArray[0], Integer.parseInt(stringArray[1]));
				line = bReader.readLine();
				i++;
			}
			// close the reader object
			bReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("BEFORE PAGE RANKING");
		for (int j = 0; j < totalWebsites; j++) {
			System.out.println("websiteName is " + pageRanker[j].websiteName + " and its number Of Occurance is "
					+ pageRanker[j].numberOfOccurance);
		}

		// perform quick sorting to get the website names sorted according to the number of occurances
		quickSorting(pageRanker, 0, pageRanker.length - 1);

		System.out.println("AFTER PAGE RANKING");
		for (int j = 0; j < totalWebsites; j++) {
			System.out.println("websiteName is " + pageRanker[j].websiteName + " and its number Of Occurance is "
					+ pageRanker[j].numberOfOccurance);
		}
	}

}

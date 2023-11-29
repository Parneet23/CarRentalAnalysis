package CarRent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class compareDeals {

    public static void main(String[] args) {
        try {
        
            String filePath = "CarRent/montreal.txt"; // Update this with the actual path to your text file
            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

          //  String cheapestCarDetails = findCheapestCar(fileContent,days,"Chevrolet Malibu");

            //System.out.println("The cheapest car rental option is:\n" + cheapestCarDetails);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void bestdeals(List<String>foldername,long days, String...searchterms) throws IOException {
    	for(String filePath: foldername) {
    		 String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

             String cheapestCarDetails = findCheapestCar(fileContent,days, searchterms[1]);

             System.out.println("The cheapest car rental option is:\n" + cheapestCarDetails);
    	}
    }

    public static String findCheapestCar(String fileContent,long days, String searchTerm) {
        Pattern pattern = Pattern.compile("(?s)Pick up location: ([^\n]+).*?https://([^\n]+)\\n(.*?)C\\$(\\d+\\.\\d{2})");

        Matcher matcher = pattern.matcher(fileContent);

        String cheapestCarDetails = null;
        double minPrice = Double.MAX_VALUE;

        while (matcher.find()) {
            String pickupLocation = matcher.group(1).trim();
            String website = matcher.group(2).trim();
            String details = matcher.group(3).trim();
            double price = Double.parseDouble(matcher.group(4).trim());

            // Check if the details contain the search term
            if (details.toLowerCase().contains(searchTerm.toLowerCase()) && price < minPrice) {
                minPrice = price;
                cheapestCarDetails = "Website: " + website +
                        "\nPickup Location: " + pickupLocation +
                        "\nDetails: " + details +
                        "\nPrice: C$" + price * days;
            }
        }

        if (cheapestCarDetails == null) {
            return "No matching car found.";
        }

        return cheapestCarDetails;
    }







}

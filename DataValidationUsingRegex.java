package CarRent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidationUsingRegex {

	// regex in mm/dd/yyyy format
	private static final String DATE_FORMAT_REGEX = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/(19|20)\\d\\d$";

	// Function to validate the date as per the regex defined above.
	// Takes date input in a string as a parameter
	// Returns true if date matches the pattern else false
	public static boolean dataValidator(String date) {
	    Pattern patternObject = Pattern.compile(DATE_FORMAT_REGEX);
	    Matcher matcherObject = patternObject.matcher(date);
	    return matcherObject.matches();
	}

	//main method
	public static void dateValidation() throws DateException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your date here: ");
            String inputDate = scanner.next();
            try {
                if (!dataValidator(inputDate)) {
                    throw new DateException("Invalid Date Format");
                }

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date currentDate = new Date();
                Date enteredDate = sdf.parse(inputDate);

                if (enteredDate.before(currentDate)) {
                    throw new DateException("Entered date should be greater than today's date");
                }

                break; // Break out of the loop if a valid and future date is entered
            } catch (DateException | ParseException e) {
                System.out.println(e.getMessage());
                // Continue the loop to prompt the user again
            }
        }
	}
}

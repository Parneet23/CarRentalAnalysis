package CarRent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

package package1;

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

	// main method
	public static void dateValidation() throws DateException {
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.println("Enter your date here: ");
				String inputStartDate = scanner.next();
				System.out.println("Enter your  end date here: ");
				String inputEndDate = scanner.next();
				try {
					if (!dataValidator(inputStartDate)) {
						throw new DateException("Invalid Date Format for Start date");
					}
					if (!dataValidator(inputEndDate)) {
						throw new DateException("Invalid Date Format for End date");
					}

					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					Date currentDate = new Date();
					Date startDate = sdf.parse(inputStartDate);
					Date endDate = sdf.parse(inputEndDate);

					if (startDate.before(currentDate) || endDate.before(currentDate)) {
						throw new DateException("Entered date should be greater than today's date");
					}
					if (endDate.before(startDate) ) {
						throw new DateException("Start date should be less than End date");
					}
					System.out.println("Successfully validated the date!!");

					break; // Break out of the loop if a valid and future date is entered
				} catch (DateException | ParseException e) {
					System.out.println(e.getMessage());
					// Continue the loop to prompt the user again
				}
			}
		}
	}
}

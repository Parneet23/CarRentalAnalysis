package CarRent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	// method to return the difference in days between start and end date
	public static long calculateDays(Date startDate, Date endDate) {
		long timeInMiliseconds = endDate.getTime() - startDate.getTime();
		long days = TimeUnit.DAYS.convert(timeInMiliseconds, TimeUnit.MILLISECONDS);
		 return days;
	}

	// method to validate the date if it's correct
	// accepts date and date format as input
	// returns true if correct else false
	public static boolean isDateCorrect(String dateTobeValidated, String dateFormat) {
		try {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);

			LocalDate localDate = LocalDate.parse(dateTobeValidated, dateFormatter);

			return localDate.format(dateFormatter).equals(dateTobeValidated);

		} catch (DateTimeParseException dateException) {

			return false;
		}
	}

	// main method
	public static void dateValidation() throws DateException {
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				try {
					System.out.println("Enter your start date here: ");
					String inputStartDate = scanner.next();
					// check if date format and date is correct for start date input
					if (!dataValidator(inputStartDate) || !isDateCorrect(inputStartDate, "MM/dd/yyyy")) {
						throw new DateException("Invalid Date for start date");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					Date currentDate = new Date();
					Date startDate = sdf.parse(inputStartDate);

					// condition to check if start date is before today's date
					if (startDate.before(currentDate)) {
						throw new DateException("Entered date should be greater than today's date");
					}

					System.out.println("Enter your  end date here: ");
					String inputEndDate = scanner.next();
					Date endDate = sdf.parse(inputEndDate);
					// check if date format and date is correct for end date input
					if (!dataValidator(inputEndDate) || !isDateCorrect(inputEndDate, "MM/dd/yyyy")) {
						throw new DateException("Invalid Date for end date");
					}

					System.out.println("Days diffence is : " + calculateDays(startDate, endDate));
					
					// condition to check if start date and end date is before today's date
					if (endDate.before(currentDate)) {
						throw new DateException("Entered date should be greater than today's date");
					}
					
					// condition to check if end date is before start date
					if (endDate.before(startDate)) {
						throw new DateException("Start date should be less than End date");
					}

					// when every check is passed, print success
					System.out.println("Successfully validated the data!!");

					break;
				} catch (DateException | ParseException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}

package common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.BindingResult;

import travel.tour.apitraveltour.model.Guide;
import travel.tour.apitraveltour.model.Hotel;

public class ValidateFormUtils {

    // =====================================================================
    // Constants
    // =====================================================================
    public static final Pattern WEBSITE_PATTERN = Pattern
            .compile("^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$");

    // =====================================================================
    // Public method
    // =====================================================================
    /**
     * Validate form add empty
     * 
     * @param bingdingResult
     * @param Hotel
     */
    public static void checkEmpty(Hotel hotel, BindingResult bindingResult) {
        // Check name hotel
        if (hotel.getName().isEmpty()) {
            bindingResult.rejectValue("name", "nameHotelEmpty", null, null);
        }

        // Check address hotel
        if (hotel.getAddress().isEmpty()) {
            bindingResult.rejectValue("address", "addressHotelEmpty", null, null);
        }

        // Check phone hotel
        if (hotel.getPhone().isEmpty()) {
            bindingResult.rejectValue("phone", "phoneHotelEmpty", null, null);
        }

        // Check website hotel
        if (hotel.getWebsite().isEmpty()) {
            bindingResult.rejectValue("website", "websiteHotelEmpty", null, null);
        }
        
        // Check phone hotel
        if (hotel.getPrice_room() == 0) {
            bindingResult.rejectValue("price_room", "priceRoomWrong", null, null);
        }

        return;
    }

    /**
     * Validate form add empty
     * 
     * @param bingdingResult
     * @param Guide
     */
    public static void checkEmpty(Guide guide, BindingResult bindingResult) {
        // Check name hotel
        if (guide.getName().isEmpty()) {
            bindingResult.rejectValue("name", "nameEmpty", null, null);
        }

        // Check address hotel
        if (guide.getAddress().isEmpty()) {
            bindingResult.rejectValue("address", "addressEmpty", null, null);
        }

        // Check phone hotel
        if (guide.getPhone().isEmpty()) {
            bindingResult.rejectValue("phone", "phoneEmpty", null, null);
        }

        return;
    }

    /**
     * Validate format phone number
     * 
     * @param bingdingResult
     */
    public static void validatePhoneNumber(String phoneNo, BindingResult bindingResult) {
        // validate phone numbers of format "1234567890"
        // validating phone number with -, . or spaces
        // validating phone number with extension length from 3 to 5
        // validating phone number where area code is in braces ()
        if (phoneNo.matches("\\d{10}") || phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")
                || phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")
                || phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            return;
        } else {
            bindingResult.rejectValue("phone", "phoneNoWrong", null, null);
            return;
        }

    }

    /**
     * Validate format website
     * 
     * @param bingdingResult
     */
    public static void checkFormatWebsite(String website, BindingResult bindingResult) {
        Matcher matcher;
        matcher = WEBSITE_PATTERN.matcher(website);
        if (!matcher.matches()) {
            bindingResult.rejectValue("website", "websiteWrong", null, null);
        }
        return;
    }

    /**
     * Validate format number
     * 
     * @param bingdingResult
     */
    public static void checkFormatNumber(String number, BindingResult bindingResult) {
        // Check if given string is a number (digits only) || if given string is numeric
        // (-+0..9(.)0...9)
        if (number.matches("^\\d+$") || number.matches("^[+]?\\d+(\\.\\d+)?$")) {
            return;
        } else {
            bindingResult.rejectValue("price_room", "priceRoomWrong", null, null);
            return;
        }
    }
}

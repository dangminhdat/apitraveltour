/**
* File ： HotelController.java
* Overview ： Processing handle hotel in system
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.controller.viewController;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.Constants;
import common.Constants.SessionKey;
import common.Constants.Url;
import common.URI.API;
import common.ValidateFormUtils;
import travel.tour.apitraveltour.model.DataHotelAPI;
import travel.tour.apitraveltour.model.Hotel;
import travel.tour.apitraveltour.model.modelResponse.HotelResponse;
import travel.tour.apitraveltour.model.modelResponse.HotelsResponse;

@Controller
@RequestMapping(Url.HANDLER_HOTEL)
public class HotelController extends AbstractUserController {

    // =====================================================================
    // Constants
    // =====================================================================
    // Handle hotel Screen.
    public static final String HANDLE_HOTEL_SCREEN = "admin/hotel/hotel";

    // Redirect hotel Screen.
    public static final String REDIRECT_HOTEL = "redirect:/admin/hotel";

    // Add hotel Screen.
    public static final String ADD_HOTEL_SCREEN = "admin/hotel/hotelAdd";

    // Redirect hotel Add screen.
    public static final String REDIRECT_HOTEL_ADD = "redirect:/admin/hotel/add";

    // Edit hotel Screen.
    public static final String EDIT_HOTEL_SCREEN = "admin/hotel/hotelEdit";

    // Redirect hotel Edit screen.
    public static final String REDIRECT_HOTEL_EDIT = "redirect:/admin/hotel/edit/{id}";

    // Add hotel success message
    public static final String ADD_HOTEL_SUCCESS_MSG = "Add hotel success !";

    // Add hotel success message
    public static final String ADD_HOTEL_FAIL_MSG = "Add hotel Fail !";

    // Edit hotel success message
    public static final String EDIT_HOTEL_SUCCESS_MSG = "Edit hotel success !";

    // Edit hotel success message
    public static final String EDIT_HOTEL_FAIL_MSG = "Edit hotel Fail !";

    // Delete hotel success message
    public static final String DELETE_HOTEL_SUCCESS_MSG = "Delete hotel success !";

    // Delete hotel success message
    public static final String DELETE_HOTEL_FAIL_MSG = "Delete hotel Fail !";

    // =====================================================================
    // Public method
    // =====================================================================
    /**
     * Show handle hotel screen
     * 
     * @return hotel screen
     */
    @RequestMapping(Constants.Characters.BLANK)
    public ModelAndView showHandleHotelScreen() {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Get data from API
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HotelResponse<DataHotelAPI>> response = restTemplate.exchange(API.URI_HOTELS, HttpMethod.GET,
                null, new ParameterizedTypeReference<HotelResponse<DataHotelAPI>>() {
                });
        HotelResponse<DataHotelAPI> hotels = response.getBody();
        // Set data to display.
        ModelAndView mav = new ModelAndView(HANDLE_HOTEL_SCREEN);
        mav.addObject("hotels", hotels);
        mav.addObject("hotelAdd", new Hotel());
        return mav;
    }

    /**
     * Show Add hotel screen
     * 
     * @return add hotel screen
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showAddHotelScreen() {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Set data to display.
        ModelAndView mav = new ModelAndView(ADD_HOTEL_SCREEN);
        mav.addObject("hotelAdd", new Hotel());
        return mav;
    }

    /**
     * Process add hotel
     * 
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addHotelProcess(@Valid @ModelAttribute("hotelAdd") Hotel hotelAdd, BindingResult bindingResult,
            HttpSession session, RedirectAttributes redirectAttr)
            throws JsonParseException, JsonMappingException, IOException {

        // Check session and role of user login
        String result = checkSessionAndRole();

        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Create view return
        ModelAndView mav = new ModelAndView(ADD_HOTEL_SCREEN);

        // Validate check form empty
        ValidateFormUtils.checkEmpty(hotelAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            mav.addObject("hotelAdd", hotelAdd);
            return mav;
        }

        // Check format phone
        ValidateFormUtils.validatePhoneNumber(hotelAdd.getPhone(), bindingResult);

        // Check format website
        ValidateFormUtils.checkFormatWebsite(hotelAdd.getWebsite(), bindingResult);

        if (bindingResult.hasErrors()) {
            mav.addObject("hotelAdd", hotelAdd);
            return mav;
        }

        // Header using Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();

        // App hotel using API
        try {
            HotelResponse<String> hotel = restTemplate.exchange(API.URI_HOTELS, HttpMethod.POST,
                    new HttpEntity<Hotel>(hotelAdd, headers), new ParameterizedTypeReference<HotelResponse<String>>() {
                    }).getBody();
            redirectAttr.addFlashAttribute("successMsg", hotel.getResultMessage());
            return new ModelAndView(REDIRECT_HOTEL);
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> hotelResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            HotelResponse hotel = mapper.readValue(errorMessage, HotelResponse.class);
            // If fail: return add hotel screen with notification
            mav.addObject("failMsg", hotel.getResultMessage());
            mav.addObject("hotelAdd", hotelAdd);
            return mav;
        }
    }

    /**
     * Show edit hotel screen
     * 
     * @return edit hotel screen
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView showEditHotelScreen(@PathVariable int id, Model model) {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        String uri = API.URI_HOTELS + "/" + id;
        RestTemplate restTemplate = new RestTemplate();
        // Set data to display.
        ModelAndView mav = new ModelAndView(EDIT_HOTEL_SCREEN);
        try {
            HotelResponse<Hotel> hotelResponse = restTemplate
                    .exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<HotelResponse<Hotel>>() {
                    }).getBody();
            mav.addObject("hotelEdit", hotelResponse.getData());
            return mav;
        } catch (HttpStatusCodeException exception) {
            return new ModelAndView("admin/404");
        }
    }

    /**
     * Process edit hotel
     * 
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editHotelProcess(@Valid @ModelAttribute("hotelEdit") Hotel hotelEdit,
            BindingResult bindingResult, @PathVariable int id, HttpSession session, RedirectAttributes redirectAttr,
            Model model) throws JsonParseException, JsonMappingException, IOException {

        // Check session and role of user login
        String result = checkSessionAndRole();

        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Create view return
        ModelAndView mav = new ModelAndView(EDIT_HOTEL_SCREEN);

        // Validate check form empty
        ValidateFormUtils.checkEmpty(hotelEdit, bindingResult);
        if (bindingResult.hasErrors()) {
            mav.addObject("hotelEdit", hotelEdit);
            return mav;
        }

        // Check format phone
        ValidateFormUtils.validatePhoneNumber(hotelEdit.getPhone(), bindingResult);

        // Check format website
        ValidateFormUtils.checkFormatWebsite(hotelEdit.getWebsite(), bindingResult);

        // Summary error
        if (bindingResult.hasErrors()) {
            mav.addObject("hotelEdit", hotelEdit);
            return mav;
        }

        // Header using Content-Type: application/json
        String uri = API.URI_HOTELS + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Hotel> requestBody = new HttpEntity<>(hotelEdit, headers);

        // Edit hotel using API
        try {
            // Edit
            HotelResponse<String> hotel = restTemplate.exchange(uri, HttpMethod.PUT, requestBody,
                    new ParameterizedTypeReference<HotelResponse<String>>() {
                    }).getBody();
            redirectAttr.addFlashAttribute("successMsg", hotel.getResultMessage());
            return new ModelAndView(REDIRECT_HOTEL);
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> hotelResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            HotelResponse hotel = mapper.readValue(errorMessage, HotelResponse.class);
            // If fail: return add hotel screen with notification
            mav.addObject("failMsg", hotel.getResultMessage());
            mav.addObject("hotelEdit", hotelEdit);
            return mav;
        }
    }

    /**
     * Process delete hotel
     * 
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteHotelProcess(@Valid @PathVariable int id, HttpSession session,
            RedirectAttributes redirectAttr) throws JsonParseException, JsonMappingException, IOException {
        // Check session and role of user login
        String result = checkSessionAndRole();

        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Header using Content-Type: application/json
        String uri = API.URI_HOTELS + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        // Delete
        try {
            HotelResponse<String> hotel = restTemplate.exchange(uri, HttpMethod.DELETE,
                    new HttpEntity<String>(null, headers), new ParameterizedTypeReference<HotelResponse<String>>() {
                    }).getBody();
            redirectAttr.addFlashAttribute("successMsg", hotel.getResultMessage());
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> hotelResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            HotelResponse hotel = mapper.readValue(errorMessage, HotelResponse.class);
            // If fail: return show hotel screen with notification
            redirectAttr.addFlashAttribute("failMsg", hotel.getResultMessage());
        }
        // Return hotel screen
        return new ModelAndView(REDIRECT_HOTEL);
    }
    // =====================================================================
    // Sub method
    // =====================================================================
}

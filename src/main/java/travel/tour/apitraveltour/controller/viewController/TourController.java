/**
* File ：TourController.java
* Overview ： Processing handle tour in system
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.controller.viewController;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.Constants;
import common.ValidateFormUtils;
import common.Constants.SessionKey;
import common.Constants.Url;
import common.URI.API;
import travel.tour.apitraveltour.model.DataDetailTourAPI;
import travel.tour.apitraveltour.model.DataTourAPI;
import travel.tour.apitraveltour.model.Hotel;
import travel.tour.apitraveltour.model.Tour;
import travel.tour.apitraveltour.model.TourAdd;
import travel.tour.apitraveltour.model.modelResponse.HotelResponse;
import travel.tour.apitraveltour.model.modelResponse.TourResponse;

@Controller
@RequestMapping(Url.HANDLER_TOUR)
public class TourController extends AbstractUserController {

    // =====================================================================
    // Constants
    // =====================================================================
    // Handle tour Screen.
    public static final String HANDLE_TOUR_SCREEN = "admin/tour/tour";

    // Redirect tour Screen.
    public static final String REDIRECT_TOUR = "redirect:/admin/tour";

    // Detail tour Screen.
    public static final String DETAIL_TOUR_SCREEN = "admin/tour/detailTour";

    // Add tour Screen.
    public static final String ADD_TOUR_SCREEN = "admin/tour/tourAdd";

    // Redirect tour Add screen.
    public static final String REDIRECT_TOUR_ADD = "redirect:/admin/tour/add";

    // Edit tour Screen.
    public static final String EDIT_TOUR_SCREEN = "admin/tour/tourEdit";

    // Redirect tour Edit screen.
    public static final String REDIRECT_TOUR_EDIT = "redirect:/admin/tour/edit/{id}";

    // Redirect detail tour screen.
    public static final String REDIRECT_DETAIL_TOUR = "redirect:/admin/tour/detail/{id}";

    // =====================================================================
    // Public method
    // =====================================================================
    /**
     * Show handle tour screen
     * 
     * @return tour screen
     */
    @RequestMapping(Constants.Characters.BLANK)
    public ModelAndView showHandleTourScreen() {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Get data from API
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TourResponse<DataTourAPI>> response = restTemplate.exchange(API.URI_TOUR, HttpMethod.GET, null,
                new ParameterizedTypeReference<TourResponse<DataTourAPI>>() {
                });
        TourResponse<DataTourAPI> tours = response.getBody();
        // Set data to display.
        ModelAndView mav = new ModelAndView(HANDLE_TOUR_SCREEN);
        mav.addObject("tours", tours);
        mav.addObject("tourAdd", new Tour());
        return mav;
    }

    /**
     * Show Add tour screen
     * 
     * @return add tour screen
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showAddTourScreen() {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Set data to display.
        ModelAndView mav = new ModelAndView(ADD_TOUR_SCREEN);
        mav.addObject("tourAdd", new TourAdd());
        return mav;
    }

    /**
     * Process add tour
     * 
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addTourProcess(@Valid @ModelAttribute("tourAdd") TourAdd tourAdd,
            @RequestParam("imagesAdd") MultipartFile imagesAdd, BindingResult bindingResult, HttpSession session,
            RedirectAttributes redirectAttr) throws JsonParseException, JsonMappingException, IOException {

        // Check session and role of user login
        String result = checkSessionAndRole();

        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Create view return
        ModelAndView mav = new ModelAndView(ADD_TOUR_SCREEN);

        // Validate check form empty
        // ValidateFormUtils.checkEmpty(tourAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            mav.addObject("tourAdd", tourAdd);
            return mav;
        }

        // Header using Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("accept", MediaType.MULTIPART_FORM_DATA_VALUE);
        RestTemplate restTemplate = new RestTemplate();
        // restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        // restTemplate.getMessageConverters().add(new
        // MappingJackson2HttpMessageConverter());

        // Repare data (key, value) for form-data
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        tourAdd.setImages(imagesAdd.getOriginalFilename());
        parts.add("name", tourAdd.getName());
        parts.add("number_days", tourAdd.getNumber_days());
        parts.add("item_tour", tourAdd.getItem_tour());
        parts.add("discount", tourAdd.getDiscount());
        parts.add("programs", tourAdd.getPrograms());
        parts.add("note", tourAdd.getNote());
        parts.add("id_type_tour", tourAdd.getId_type_tour());
        parts.add("images", tourAdd.getImages());
        HttpEntity<MultiValueMap<String, Object>> requestTour = new HttpEntity<MultiValueMap<String, Object>>(parts,
                headers);

        // Repare images to add with API.UPLOAD_IMG
        File file = new File(imagesAdd.getOriginalFilename());
//        File file = new File("C:\\Users\\Admin\\Desktop\\Shoes\\1.png");
        imagesAdd.transferTo(file);
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("images", new FileSystemResource(file));
        HttpEntity<LinkedMultiValueMap<String, Object>> requestImage = new HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);
        try {
            TourResponse<String> imgTour = restTemplate.exchange(API.URI_UPLOAD_IMG, HttpMethod.POST, requestImage,
                    new ParameterizedTypeReference<TourResponse<String>>() {
                    }).getBody();
            TourResponse<String> tour = restTemplate.exchange(API.URI_TOUR, HttpMethod.POST, requestTour,
                    new ParameterizedTypeReference<TourResponse<String>>() {
                    }).getBody();
            redirectAttr.addFlashAttribute("successMsg", tour.getResultMessage());
            return new ModelAndView(REDIRECT_TOUR);
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> hotelResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            TourResponse tour = mapper.readValue(errorMessage, TourResponse.class);
            // If fail: return add hotel screen with notification
            mav.addObject("failMsg", tour.getResultMessage());
            mav.addObject("tourAdd", tourAdd);
            return mav;
        }
    }

    /**
     * Show detail tour screen
     * 
     * @return detail tour screen
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ModelAndView showDetailTourScreen(@PathVariable int id, Model model)
            throws JsonParseException, JsonMappingException, IOException {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        String uri = API.URI_TOUR + "/" + id;
        RestTemplate restTemplate = new RestTemplate();
        // Set data to display.
        ModelAndView mav = new ModelAndView(DETAIL_TOUR_SCREEN);
        try {
            ResponseEntity<TourResponse<DataDetailTourAPI>> response = restTemplate.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<TourResponse<DataDetailTourAPI>>() {
                    });
            TourResponse<DataDetailTourAPI> detailTours = response.getBody();
            mav.addObject("detailTours", detailTours);
            mav.addObject("id_tour", id);
            return mav;
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> hotelResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            TourResponse tour = mapper.readValue(errorMessage, TourResponse.class);
            // If fail: return add tour screen with notification
            mav.addObject("failMsg", tour.getResultMessage());
            return new ModelAndView("redirect:/admin/tour");
        }
    }

    /**
     * Process delete tour
     * 
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = "detail/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteDetailHotelProcess(@Valid @PathVariable int id, HttpSession session,
            RedirectAttributes redirectAttr) throws JsonParseException, JsonMappingException, IOException {
        // Check session and role of user login
        String result = checkSessionAndRole();

        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Header using Content-Type: application/json
        String uri = API.URI_DETAIL_TOUR + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        // Delete
        try {
            TourResponse<String> tour = restTemplate.exchange(uri, HttpMethod.DELETE,
                    new HttpEntity<String>(null, headers), new ParameterizedTypeReference<TourResponse<String>>() {
                    }).getBody();
            redirectAttr.addFlashAttribute("successMsg", tour.getResultMessage());
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> hotelResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            TourResponse tour = mapper.readValue(errorMessage, TourResponse.class);
            // If fail: return show hotel screen with notification
            redirectAttr.addFlashAttribute("failMsg", tour.getResultMessage());
        }
        // Return hotel screen
        return new ModelAndView(REDIRECT_TOUR);
    }

    /**
     * Process delete tour
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
        String uri = API.URI_TOUR + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        // Delete
        try {
            TourResponse<String> tour = restTemplate.exchange(uri, HttpMethod.DELETE,
                    new HttpEntity<String>(null, headers), new ParameterizedTypeReference<TourResponse<String>>() {
                    }).getBody();
            redirectAttr.addFlashAttribute("successMsg", tour.getResultMessage());
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> hotelResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            TourResponse tour = mapper.readValue(errorMessage, TourResponse.class);
            // If fail: return show hotel screen with notification
            redirectAttr.addFlashAttribute("failMsg", tour.getResultMessage());
        }
        // Return hotel screen
        return new ModelAndView(REDIRECT_TOUR);
    }
}

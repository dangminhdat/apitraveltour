/**
* File ： GuideController.java
* Overview ： Processing handle guide in system
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
import travel.tour.apitraveltour.model.DataGuideAPI;
import travel.tour.apitraveltour.model.DataHotelAPI;
import travel.tour.apitraveltour.model.Guide;
import travel.tour.apitraveltour.model.Hotel;
import travel.tour.apitraveltour.model.modelResponse.GuideResponse;
import travel.tour.apitraveltour.model.modelResponse.GuidesResponse;
import travel.tour.apitraveltour.model.modelResponse.HotelResponse;

@Controller
@RequestMapping(Url.HANDLER_GUIDE)
public class GuideController extends AbstractUserController {

    // =====================================================================
    // Constants
    // =====================================================================
    // Handle guide Screen.
    public static final String HANDLE_GUIDE_SCREEN = "admin/guide/guide";

    // Redirect guide Screen.
    public static final String REDIRECT_GUIDE = "redirect:/admin/guide";

    // Add guide Screen.
    public static final String ADD_GUIDE_SCREEN = "admin/guide/guideAdd";

    // Redirect guide Add screen.
    public static final String REDIRECT_GUIDE_ADD = "redirect:/admin/guide/add";

    // Edit guide Screen.
    public static final String EDIT_GUIDE_SCREEN = "admin/guide/guideEdit";

    // Redirect guide Edit screen.
    public static final String REDIRECT_GUIDE_EDIT = "redirect:/admin/guide/edit/{id}";

    // Add guide success message
    public static final String ADD_GUIDE_SUCCESS_MSG = "Add guide success !";

    // Add guide success message
    public static final String ADD_GUIDE_FAIL_MSG = "Add guide Fail !";

    // Edit guide success message
    public static final String EDIT_GUIDE_SUCCESS_MSG = "Edit guide success !";

    // Edit guide success message
    public static final String EDIT_GUIDE_FAIL_MSG = "Edit guide Fail !";

    // Delete guide success message
    public static final String DELETE_GUIDE_SUCCESS_MSG = "Delete guide success !";

    // Delete guide success message
    public static final String DELETE_GUIDE_FAIL_MSG = "Delete guide Fail !";

    // =====================================================================
    // Public method
    // =====================================================================
    /**
     * Show handle guide screen
     * 
     * @return guide screen
     */
    @RequestMapping(Constants.Characters.BLANK)
    public ModelAndView showHandleGuideScreen() {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Get data from API
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GuideResponse<DataGuideAPI>> response = restTemplate.exchange(API.URI_GUIDES, HttpMethod.GET,
                null, new ParameterizedTypeReference<GuideResponse<DataGuideAPI>>() {
                });
        GuideResponse<DataGuideAPI> guides = response.getBody();
        // Set data to display.
        ModelAndView mav = new ModelAndView(HANDLE_GUIDE_SCREEN);
        mav.addObject("guides", guides);
        mav.addObject("guideAdd", new Guide());
        return mav;
    }

    /**
     * Show Add guide screen
     * 
     * @return add guide screen
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showAddGuideScreen() {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Set data to display.
        ModelAndView mav = new ModelAndView(ADD_GUIDE_SCREEN);
        mav.addObject("guideAdd", new Guide());
        return mav;
    }

    /**
     * Process add guide
     * 
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addGuideProcess(@Valid @ModelAttribute("guideAdd") Guide guideAdd, BindingResult bindingResult,
            HttpSession session, RedirectAttributes redirectAttr)
            throws JsonParseException, JsonMappingException, IOException {

        // Check session and role of user login
        String result = checkSessionAndRole();

        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Create view return
        ModelAndView mav = new ModelAndView(ADD_GUIDE_SCREEN);

        // Validate check form empty
        ValidateFormUtils.checkEmpty(guideAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            mav.addObject("guideAdd", guideAdd);
            return mav;
        }

        // Check format phone
        ValidateFormUtils.validatePhoneNumber(guideAdd.getPhone(), bindingResult);
        if (bindingResult.hasErrors()) {
            mav.addObject("guideAdd", guideAdd);
            return mav;
        }

        // Header using Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();

        // App guide using API
        try {
            GuideResponse<String> guide = restTemplate.exchange(API.URI_GUIDES, HttpMethod.POST,
                    new HttpEntity<Guide>(guideAdd, headers), new ParameterizedTypeReference<GuideResponse<String>>() {
                    }).getBody();
            redirectAttr.addFlashAttribute("successMsg", guide.getResultMessage());
            return new ModelAndView(REDIRECT_GUIDE);
        } catch (HttpStatusCodeException exception) {
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            GuideResponse guide = mapper.readValue(errorMessage, GuideResponse.class);
            // If fail: return add guide screen with notification
            mav.addObject("failMsg", guide.getResultMessage());
            mav.addObject("guideAdd", guideAdd);
            return mav;
        }
    }

    /**
     * Show edit guide screen
     * 
     * @return edit guide screen
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView showEditGuideScreen(@PathVariable int id, Model model) {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        String uri = API.URI_GUIDES + "/" + id;
        RestTemplate restTemplate = new RestTemplate();
        // Set data to display.
        ModelAndView mav = new ModelAndView(EDIT_GUIDE_SCREEN);
        try {
            GuideResponse<Guide> guideResponse = restTemplate
                    .exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<GuideResponse<Guide>>() {
                    }).getBody();
            mav.addObject("guideEdit", guideResponse.getData());
            return mav;
        } catch (HttpStatusCodeException exception) {
            return new ModelAndView("admin/404");
        }
    }

    /**
     * Process edit guide
     * 
     * @return
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editGuideProcess(@Valid @ModelAttribute("guideEdit") Guide guideEdit,
            BindingResult bindingResult, @PathVariable int id, HttpSession session, RedirectAttributes redirectAttr,
            Model model) throws JsonParseException, JsonMappingException, IOException {

        // Check session and role of user login
        String result = checkSessionAndRole();

        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Create view return
        ModelAndView mav = new ModelAndView(EDIT_GUIDE_SCREEN);

        // Validate check form empty
        ValidateFormUtils.checkEmpty(guideEdit, bindingResult);
        if (bindingResult.hasErrors()) {
            mav.addObject("guideEdit", guideEdit);
            return mav;
        }

        // Check format phone
        ValidateFormUtils.validatePhoneNumber(guideEdit.getPhone(), bindingResult);

        // Summary error
        if (bindingResult.hasErrors()) {
            mav.addObject("guideEdit", guideEdit);
            return mav;
        }

        // Header using Content-Type: application/json
        String uri = API.URI_GUIDES + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Guide> requestBody = new HttpEntity<>(guideEdit, headers);

        // Edit guide using API
        try {
            // Edit
            GuideResponse<String> guide = restTemplate.exchange(uri, HttpMethod.PUT, requestBody,
                    new ParameterizedTypeReference<GuideResponse<String>>() {
                    }).getBody();
            redirectAttr.addFlashAttribute("successMsg", guide.getResultMessage());
            return new ModelAndView(REDIRECT_GUIDE);
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> hotelResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            GuideResponse guide = mapper.readValue(errorMessage, GuideResponse.class);
            // If fail: return add hotel screen with notification
            mav.addObject("failMsg", guide.getResultMessage());
            mav.addObject("guideEdit", guideEdit);
            return mav;
        }
    }

    /**
     * Process delete guide
     * 
     * @return
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteGuideProcess(@Valid @PathVariable int id, HttpSession session,
            RedirectAttributes redirectAttr) throws JsonParseException, JsonMappingException, IOException {
        // Check session and role of user login
        String result = checkSessionAndRole();

        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Header using Content-Type: application/json
        String uri = API.URI_GUIDES + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        // Delete
        try {
            GuideResponse<String> guide = restTemplate.exchange(uri, HttpMethod.DELETE,
                    new HttpEntity<String>(null, headers), new ParameterizedTypeReference<GuideResponse<String>>() {
                    }).getBody();
            redirectAttr.addFlashAttribute("successMsg", guide.getResultMessage());
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> hotelResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            GuideResponse guide = mapper.readValue(errorMessage, GuideResponse.class);
            // If fail: return show hotel screen with notification
            redirectAttr.addFlashAttribute("failMsg", guide.getResultMessage());
        }
        // Return guide screen
        return new ModelAndView(REDIRECT_GUIDE);
    }
}

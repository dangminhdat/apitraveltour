/**
* File ： UserController.java
* Overview ： Processing handle user in system
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
import travel.tour.apitraveltour.model.DataUserAPI;
import travel.tour.apitraveltour.model.User;
import travel.tour.apitraveltour.model.modelResponse.UserResponse;

@Controller
@RequestMapping(Url.HANDLER_USER)
public class UserController extends AbstractUserController {

    // =====================================================================
    // Constants
    // =====================================================================
    // Handle user Screen.
    public static final String HANDLE_USER_SCREEN = "admin/user/user";

    // Profile user Screen.
    public static final String PROFILE_USER_SCREEN = "admin/user/userProfile";

    // Redirect user Screen.
    public static final String REDIRECT_USER = "redirect:/admin/user";

    // Add user Screen.
    public static final String ADD_USER_SCREEN = "admin/user/userAdd";

    // Redirect user Add screen.
    public static final String REDIRECT_USER_ADD = "redirect:/admin/user/add";

    // Edit user Screen.
    public static final String EDIT_USER_SCREEN = "admin/user/userEdit";

    // Redirect user Edit screen.
    public static final String REDIRECT_USER_EDIT = "redirect:/admin/user/edit/{id}";

    // =====================================================================
    // Public method
    // =====================================================================
    /**
     * Show handle user screen
     * 
     * @return user screen
     */
    @RequestMapping(Constants.Characters.BLANK)
    public ModelAndView showHandleUserScreen(HttpSession session) {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Header using Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Get data from API
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse<DataUserAPI>> response = restTemplate.exchange(API.URI_USERS, HttpMethod.GET,
                new HttpEntity<String>(null, headers), new ParameterizedTypeReference<UserResponse<DataUserAPI>>() {
                });
        UserResponse<DataUserAPI> users = response.getBody();
        // Set data to display.
        ModelAndView mav = new ModelAndView(HANDLE_USER_SCREEN);
        mav.addObject("users", users);
        mav.addObject("userAdd", new User());
        return mav;
    }

    /**
     * Show Add user screen
     * 
     * @return add user screen
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showAddUserScreen() {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Set data to display.
        ModelAndView mav = new ModelAndView(ADD_USER_SCREEN);
        mav.addObject("userAdd", new User());
        return mav;
    }

    /**
     * Process add user
     * 
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addUserProcess(@Valid @ModelAttribute("userAdd") User userAdd, BindingResult bindingResult,
            HttpSession session, RedirectAttributes redirectAttr)
            throws JsonParseException, JsonMappingException, IOException {

        // Check session and role of user login
        String result = checkSessionAndRole();

        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Create view return
        ModelAndView mav = new ModelAndView(ADD_USER_SCREEN);

        // Validate check form empty
        ValidateFormUtils.checkEmpty(userAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            mav.addObject("userAdd", userAdd);
            return mav;
        }

        // Check format phone
        ValidateFormUtils.validatePhoneNumber(userAdd.getPhone(), bindingResult);

        // Check format email
        ValidateFormUtils.checkFormatEmail(userAdd.getEmail(), bindingResult);
        ;

        if (bindingResult.hasErrors()) {
            mav.addObject("userAdd", userAdd);
            return mav;
        }

        // Header using Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();

        // App user using API
        try {
            UserResponse<String> user = restTemplate.exchange(API.URI_USERS, HttpMethod.POST,
                    new HttpEntity<User>(userAdd, headers), new ParameterizedTypeReference<UserResponse<String>>() {
                    }).getBody();
            redirectAttr.addFlashAttribute("successMsg", user.getResultMessage());
            return new ModelAndView(REDIRECT_USER);
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> userResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            UserResponse user = mapper.readValue(errorMessage, UserResponse.class);
            // If fail: return add user screen with notification
            mav.addObject("failMsg", user.getResultMessage());
            mav.addObject("userAdd", userAdd);
            return mav;
        }
    }

    /**
     * Show edit user screen
     * 
     * @return edit user screen
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView showEditUserScreen(@PathVariable int id, Model model, HttpSession session) {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        String uri = API.URI_USERS + "/" + id;
        // Header using Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        // Set data to display.
        ModelAndView mav = new ModelAndView(EDIT_USER_SCREEN);
        try {
            UserResponse<User> userResponse = restTemplate.exchange(uri, HttpMethod.GET,
                    new HttpEntity<String>(null, headers), new ParameterizedTypeReference<UserResponse<User>>() {
                    }).getBody();
            mav.addObject("userEdit", userResponse.getData());
            return mav;
        } catch (HttpStatusCodeException exception) {
            return new ModelAndView("admin/404");
        }
    }

    /**
     * Process edit user
     * 
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editUserProcess(@Valid @ModelAttribute("userEdit") User userEdit, BindingResult bindingResult,
            @PathVariable int id, HttpSession session, RedirectAttributes redirectAttr, Model model)
            throws JsonParseException, JsonMappingException, IOException {

        // Check session and role of user login
        String result = checkSessionAndRole();

        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Create view return
        ModelAndView mav = new ModelAndView(EDIT_USER_SCREEN);

        // Validate check form empty
        ValidateFormUtils.checkEmpty(userEdit, bindingResult);
        if (bindingResult.hasErrors()) {
            mav.addObject("userEdit", userEdit);
            return mav;
        }

        // Check format phone
        ValidateFormUtils.validatePhoneNumber(userEdit.getPhone(), bindingResult);

        // Check format website
        ValidateFormUtils.checkFormatEmail(userEdit.getEmail(), bindingResult);

        // Summary error
        if (bindingResult.hasErrors()) {
            mav.addObject("userEdit", userEdit);
            return mav;
        }

        // Header using Content-Type: application/json
        String uri = API.URI_USERS + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> requestBody = new HttpEntity<>(userEdit, headers);

        // Edit user using API
        try {
            // Edit
            UserResponse<String> user = restTemplate
                    .exchange(uri, HttpMethod.PUT, requestBody, new ParameterizedTypeReference<UserResponse<String>>() {
                    }).getBody();
            redirectAttr.addFlashAttribute("successMsg", user.getResultMessage());
            return new ModelAndView(REDIRECT_USER);
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> userResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            UserResponse user = mapper.readValue(errorMessage, UserResponse.class);
            // If fail: return add user screen with notification
            mav.addObject("failMsg", user.getResultMessage());
            mav.addObject("userEdit", userEdit);
            return mav;
        }
    }

    /**
     * Process delete user
     * 
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUserProcess(@Valid @PathVariable int id, HttpSession session,
            RedirectAttributes redirectAttr) throws JsonParseException, JsonMappingException, IOException {
        // Check session and role of user login
        String result = checkSessionAndRole();

        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Header using Content-Type: application/json
        String uri = API.URI_USERS + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        // Delete
        try {
            UserResponse<String> user = restTemplate.exchange(uri, HttpMethod.DELETE,
                    new HttpEntity<String>(null, headers), new ParameterizedTypeReference<UserResponse<String>>() {
                    }).getBody();
            redirectAttr.addFlashAttribute("successMsg", user.getResultMessage());
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> userResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            UserResponse user = mapper.readValue(errorMessage, UserResponse.class);
            // If fail: return show user screen with notification
            redirectAttr.addFlashAttribute("failMsg", user.getResultMessage());
        }
        // Return user screen
        return new ModelAndView(REDIRECT_USER);
    }

    /**
     * Show profile user screen
     * 
     * @return profile user screen
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView showProfileUserScreen(Model model, HttpSession session) {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        String uri = API.URI_PROFILE;
        // Header using Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        // Set data to display.
        ModelAndView mav = new ModelAndView(PROFILE_USER_SCREEN);
        try {
            UserResponse<User> userResponse = restTemplate.exchange(uri, HttpMethod.GET,
                    new HttpEntity<String>(null, headers), new ParameterizedTypeReference<UserResponse<User>>() {
                    }).getBody();
            mav.addObject("userEdit", userResponse.getData());
            return mav;
        } catch (HttpStatusCodeException exception) {
            return new ModelAndView("admin/404");
        }
    }

}

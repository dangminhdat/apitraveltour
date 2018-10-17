/**
* File ： AdLoginController.java
* Overview ： Processing login into system
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.controller.viewController;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import common.Constants.Characters;
import common.Constants.Url;
import travel.tour.apitraveltour.model.User;

@Controller
@RequestMapping(Url.ADMIN_LOGIN)
public class AdLoginController {

    // =====================================================================
    // Constants
    // =====================================================================
    // Screen login
    public static final String ADMIN_LOGIN_SCREEN = "admin/login";

    // =====================================================================
    // Auto-config properties
    // =====================================================================

    // =====================================================================
    // Public method
    // =====================================================================
    /**
     * Show login
     * 
     * @return login screen
     */
    @RequestMapping(Characters.BLANK)
    public ModelAndView showLoginScreen() {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList<User>> response = restTemplate.exchange("http://localhost:8080/api/userTest",
                HttpMethod.GET, null, new ParameterizedTypeReference<ArrayList<User>>() {
                });
        ArrayList<User> users = response.getBody();
        // Show login normal
        ModelAndView mav = new ModelAndView(ADMIN_LOGIN_SCREEN);
        mav.addObject("userLogin", new User());
        mav.addObject("users", users);
        return mav;
    }

    /**
     * Login Process
     * @return 
     */
    @RequestMapping(value =Characters.BLANK, method = RequestMethod.POST)
    public ModelAndView loginProcess(@Valid @ModelAttribute("userLogin") User userLogin, 
            BindingResult bindingResult, HttpSession session) {

        ModelAndView mav = new ModelAndView(ADMIN_LOGIN_SCREEN);

        // Validate username, password
        if(userLogin.getUsername().isEmpty() || userLogin.getPassword().isEmpty()) {
            bindingResult.rejectValue("username", "loginFail");
        }

        // Show message if errors
        if(bindingResult.hasErrors()) {
            return mav;
        }

        return new ModelAndView("redirect:" + Url.TOP_PAGE);
    }
}

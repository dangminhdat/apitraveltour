/**
* File ： AdLoginController.java
* Overview ： Processing login into system
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.controller.viewController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import common.Constants.Characters;
import common.Constants.SessionKey;
import common.Constants.Url;
import travel.tour.apitraveltour.model.modelRequest.User;
import travel.tour.apitraveltour.model.modelResponse.LoginResponse;

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
    public ModelAndView showLoginScreen(HttpSession session) {

        // If have session
        if (session.getAttribute(SessionKey.REMEMBER_TOKEN) != null) {
            return new ModelAndView("redirect:" + Url.TOP_PAGE);
        }
        // Show login normal
        ModelAndView mav = new ModelAndView(ADMIN_LOGIN_SCREEN);
        mav.addObject("userLogin", new User());
        return mav;
    }

    /**
     * Login Process
     * 
     * @return
     */
    @RequestMapping(value = Characters.BLANK, method = RequestMethod.POST)
    public ModelAndView loginProcess(@Valid @ModelAttribute("userLogin") User userLogin, BindingResult bindingResult,
            HttpSession session) {

        ModelAndView mav = new ModelAndView(ADMIN_LOGIN_SCREEN);

        // Validate user name, password
        if (userLogin.getUsername().isEmpty() || userLogin.getPassword().isEmpty()) {
            bindingResult.rejectValue("username", "UsernameOrPasswordEmpty");
        }

        // Show message if errors
        if (bindingResult.hasErrors()) {
            return mav;
        }

        // Check login by API
        String urlAPILogin = "http://apitour.herokuapp.com/api/login";

        // Send parameter user name, password into URI
        UriComponentsBuilder builder = null;
        builder = UriComponentsBuilder.fromHttpUrl(urlAPILogin).queryParam("username", userLogin.getUsername())
                .queryParam("password", userLogin.getPassword());
        String url = builder.build().toString();

        // Get data from URI
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LoginResponse> response = restTemplate.exchange(url, HttpMethod.POST, null,
                new ParameterizedTypeReference<LoginResponse>() {
                });
        LoginResponse user = response.getBody();

        // If login fail -> return operator-login screen
        if (user.getData() == null) {
            bindingResult.rejectValue("username", "loginFail");
            return mav;
        }

        // Get user_id into session.
        session.setAttribute(SessionKey.REMEMBER_TOKEN, user.getData());
        // Redirect ~/admin/top-page screen
        return new ModelAndView("redirect:" + Url.TOP_PAGE);
    }
}

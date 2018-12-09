/**
* File ： LogoutController.java
* Overview ： Processing logout system
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.Constants;
import common.Constants.Characters;
import common.Constants.SessionKey;
import common.Constants.Url;
import travel.tour.apitraveltour.model.modelRequest.UserRequest;
import travel.tour.apitraveltour.model.modelResponse.HotelResponse;
import travel.tour.apitraveltour.model.modelResponse.LoginResponse;

@Controller
@RequestMapping(Url.LOGOUT)
public class LogoutController extends AbstractUserController {

    // =====================================================================
    // Constants
    // =====================================================================
    // Redirect to login
    public static final String REDIRECT_ADMIN_LOGIN_SCREEN = "redirect:/operator-login";

    // =====================================================================
    // Auto-config properties
    // =====================================================================

    // =====================================================================
    // Public method
    // =====================================================================
    /**
     * Logout Process
     * 
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = Characters.BLANK)
    public ModelAndView logoutProcess(@Valid HttpSession session)
            throws JsonParseException, JsonMappingException, IOException {

        // Check login by API
        String urlAPILogout = "http://apitour.herokuapp.com/api/logout";

        // Header using Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();

        // Get data from URI
        try {
            ResponseEntity<LoginResponse> response = restTemplate.exchange(urlAPILogout, HttpMethod.POST,
                    new HttpEntity<String>(null, headers), new ParameterizedTypeReference<LoginResponse>() {
                    });
            LoginResponse user = response.getBody();
            // Destroy user_id in session
            session.invalidate();
            // Check session login and role
            String result = checkSessionAndRole();
            if (!result.equals(Constants.Characters.BLANK)) {
                return new ModelAndView(result);
            }
            String test = (String) session.getAttribute(SessionKey.REMEMBER_TOKEN);

            // Check session and role of user login
            return new ModelAndView(REDIRECT_ADMIN_LOGIN_SCREEN);

        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> hotelResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            LoginResponse loginUser = mapper.readValue(errorMessage, LoginResponse.class);
            return new ModelAndView(REDIRECT_ADMIN_LOGIN_SCREEN);
        }

    }
}

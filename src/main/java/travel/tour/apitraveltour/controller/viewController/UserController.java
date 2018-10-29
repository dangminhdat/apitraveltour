/**
* File ： UserController.java
* Overview ： Processing handle user in system
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.controller.viewController;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import common.Constants;
import common.Constants.Url;
import travel.tour.apitraveltour.model.modelResponse.UserResponse;

@Controller
@RequestMapping(Url.HANDLER_USER)
public class UserController extends AbstractUserController {

    // =====================================================================
    // Constants
    // =====================================================================
    // Handle user Screen.
    public static final String HANDLE_USER_SCREEN = "admin/user/user";

    // =====================================================================
    // Public method
    // =====================================================================
    /**
     * Show handle user screen
     * 
     * @return user screen
     */
    @RequestMapping(Constants.Characters.BLANK)
    public ModelAndView showHandleUserScreen() {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        String urlAPI = "https://apitour.herokuapp.com/api/users";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.exchange(urlAPI, HttpMethod.GET, null,
                new ParameterizedTypeReference<UserResponse>() {
                });
        UserResponse users = response.getBody();
        // Set data to display.
        ModelAndView mav = new ModelAndView(HANDLE_USER_SCREEN);
        mav.addObject("users", users);
        return mav;
    }
}

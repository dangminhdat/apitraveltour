/**
* File ： GuideController.java
* Overview ： Processing handle guide in system
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.controller.viewController;

import java.util.ArrayList;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import common.Constants;
import common.Constants.Url;
import travel.tour.apitraveltour.model.modelRequest.User;

@Controller
@RequestMapping(Url.HANDLER_GUIDE)
public class GuideController {


    // =====================================================================
    // Constants
    // =====================================================================
    // Handle guide Screen.
    public static final String HANDLE_GUIDE_SCREEN = "admin/guide";

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
       /* String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
           return new ModelAndView(result);
        }*/

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList<User>> response = restTemplate.exchange("http://localhost:8080/api/userTest",
                HttpMethod.GET, null, new ParameterizedTypeReference<ArrayList<User>>() {
                });
        ArrayList<User> users = response.getBody();
        // Set data to display.
        ModelAndView mav = new ModelAndView(HANDLE_GUIDE_SCREEN);
        mav.addObject("users", users);
        return mav;
    }
}

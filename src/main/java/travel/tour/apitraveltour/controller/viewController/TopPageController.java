/**
* File ： TopPageController.java
* Overview ： Processing top page system
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
import travel.tour.apitraveltour.model.Hotel;

@Controller
@RequestMapping(Url.TOP_PAGE)
public class TopPageController extends AbstractUserController {

    // =====================================================================
    // Constants
    // =====================================================================
    // Top page Screen.
    public static final String TOP_PAGE_SCREEN = "admin/top-page";

    // =====================================================================
    // Public method
    // =====================================================================
    /**
     * Show Top Page
     * 
     * @return Top page screen
     */
    @RequestMapping(Constants.Characters.BLANK)
    public ModelAndView showTopPageScreen() {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList<Hotel>> response = restTemplate.exchange("http://localhost:8080/api/hotelTest",
                HttpMethod.GET, null, new ParameterizedTypeReference<ArrayList<Hotel>>() {
                });
        ArrayList<Hotel> hotels = response.getBody();
        // Set data to display.
        ModelAndView mav = new ModelAndView(TOP_PAGE_SCREEN);
        mav.addObject("users", hotels);
        return mav;
    }
}

/**
* File ： HotelController.java
* Overview ： Processing control hotel into system
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

import travel.tour.apitraveltour.model.User;

@Controller
@RequestMapping("")
public class HotelController {

    /**
     * Show view
     *
     * @return login screen
     */
    @RequestMapping("hotel")
    public ModelAndView showLogin() {
        ModelAndView mav = new ModelAndView("admin/signin");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList<User>> response = restTemplate.exchange("http://localhost:8080/api/userTest",
                HttpMethod.GET, null, new ParameterizedTypeReference<ArrayList<User>>() {
                });
        ArrayList<User> users = response.getBody();
        // Show login normal
        mav.addObject("users", users);

        return mav;
    }
}

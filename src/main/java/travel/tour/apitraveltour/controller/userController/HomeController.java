/**
* File ： HomeController.java
* Overview ： Processing into system
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.controller.userController;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import common.Constants.Characters;
import common.Constants.SessionKey;
import common.Constants.Url;
import travel.tour.apitraveltour.model.modelRequest.UserRequest;

@Controller
@RequestMapping(Url.HOME)
public class HomeController {
    // =====================================================================
    // Constants
    // =====================================================================
    // Screen home
    public static final String HOME_SCREEN = "user/home";

    // =====================================================================
    // Auto-config properties
    // =====================================================================

    // =====================================================================
    // Public method
    // =====================================================================
    /**
     * Show home screen
     * 
     * @return home screen
     */
    @RequestMapping(Characters.BLANK)
    public ModelAndView showHomeScreen() {
        // Show home normal
        ModelAndView mav = new ModelAndView(HOME_SCREEN);
        return mav;
    }

}

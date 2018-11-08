/**
* File ： AbstractUserController.java
* Overview ： Processing Abstract user class
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.controller.viewController;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import common.Constants;
import common.Constants.SessionKey;

public abstract class AbstractUserController {

    // =====================================================================
    // Constants
    // =====================================================================
    // 403 screen
    public static final String REDIRECT_403 = "redirect:/forbidden";

    // login screen
    public static final String REDIRECT_ADMIN_LOGIN = "redirect:/operator-login";
    // =====================================================================
    // Auto-config properties
    // =====================================================================

    // =====================================================================
    // Public method
    // =====================================================================
    /**
     * Check session and role of user login
     * 
     * @param session
     * @param mav
     * @return if not login then display login page, if not role 'USER' then display
     *         403 screen
     */
    public String checkSessionAndRole() {

        // Get current session
        HttpSession session = getCurrentSession();

        // if not have session -> back to login
        if (session.getAttribute(SessionKey.REMEMBER_TOKEN) == null) {
            return REDIRECT_ADMIN_LOGIN;
        }

        // login normal
        return Constants.Characters.BLANK;

    }

    // =====================================================================
    // Sub method
    // =====================================================================
    /**
     * Get current session
     * 
     * @return HttpSession
     */
    private HttpSession getCurrentSession() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        return attributes.getRequest().getSession();
    }
}

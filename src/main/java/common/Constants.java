/**
* File ： Constants.java
* Overview ： Common constants
*
* @author TrangHTH
* @version 1.0
*/
package common;

public class Constants {

    public class Url {

        // Home url base
        public static final String HOME = "/home";

        // Admin url base
        public static final String ADMIN = "/admin";

        // Admin Login screen
        public static final String ADMIN_LOGIN = "/operator-login";

        // Admin index screen
        public static final String TOP_PAGE = "admin/top-page";

        // User screen
        public static final String HANDLER_USER = "admin/user";

        // Hotel screen
        public static final String HANDLER_HOTEL = "admin/hotel";

        // Guide screen
        public static final String HANDLER_GUIDE = "admin/guide";

        // Tour screen
        public static final String HANDLER_TOUR = "admin/tour";

        // Review screen
        public static final String HANDLER_REVIEW = "admin/review";
        
        // Profile screen
        public static final String HANDLER_PROFILE = "admin/profile";
        
        // Logout
        public static final String LOGOUT = "logout";
    }

    public class Characters {

        // blank character
        public static final String BLANK = "";

        // Wave
        public static final String WAVE = "~";
    }

    public class SessionKey {

        // Key to set/get data userId to session
        public static final String USER_ID = "user-id";

        // Key to set/get data adminId to session
        public static final String ADMIN_ID = "admin-id";

        // Key to set/get data remember_token to session
        public static final String REMEMBER_TOKEN = "remember-token";
    }
}

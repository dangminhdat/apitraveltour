/**
* File ： TopPageController.java
* Overview ： Processing top page system
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.controller.viewController;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.Constants;
import common.Constants.SessionKey;
import common.Constants.Url;
import common.URI.API;
import travel.tour.apitraveltour.model.Statistic;
import travel.tour.apitraveltour.model.modelResponse.StatisticResponse;

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
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    @RequestMapping(Constants.Characters.BLANK)
    public ModelAndView showTopPageScreen(HttpSession session) throws JsonParseException, JsonMappingException, IOException {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Header using Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        ModelAndView mav = new ModelAndView(TOP_PAGE_SCREEN);
        // Get data from API
        try {
            ResponseEntity<StatisticResponse<Statistic>> statisticReps = restTemplate.exchange(API.URI_STATISTIC, HttpMethod.GET, new HttpEntity<String>(null, headers),
                    new ParameterizedTypeReference<StatisticResponse<Statistic>>() {
                    });
            Statistic statistic = statisticReps.getBody().getData();
            mav.addObject("statistic", statistic);
            return mav;
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> hotelResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            StatisticResponse tour = mapper.readValue(errorMessage, StatisticResponse.class);
            // If fail: return add tour screen with notification
            mav.addObject("failMsg", tour.getResultMessage());
            return new ModelAndView("admin/404");
        }
    }
}

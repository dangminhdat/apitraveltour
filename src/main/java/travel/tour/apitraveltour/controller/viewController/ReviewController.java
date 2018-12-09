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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.Constants;
import common.Constants.SessionKey;
import common.Constants.Url;
import common.URI.API;
import travel.tour.apitraveltour.model.DataReviewAPI;
import travel.tour.apitraveltour.model.Review;
import travel.tour.apitraveltour.model.modelResponse.ReviewResponse;

@Controller
@RequestMapping(Url.HANDLER_REVIEW)
public class ReviewController extends AbstractUserController {

    // =====================================================================
    // Constants
    // =====================================================================
    // Handle review Screen.
    public static final String HANDLE_REVIEW_SCREEN = "admin/review/review";

    // Detail review Screen.
    public static final String DETAIL_REVIEW_SCREEN = "admin/review/detail";

    // Redirect review Screen.
    public static final String REDIRECT_REVIEW = "redirect:/admin/review";

    // =====================================================================
    // Public method
    // =====================================================================
    /**
     * Show handle review screen
     * 
     * @return review screen
     */
    @RequestMapping(Constants.Characters.BLANK)
    public ModelAndView showHandleReivewScreen() {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Get data from API
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ReviewResponse<DataReviewAPI>> response = restTemplate.exchange(API.URI_REVIEW, HttpMethod.GET,
                null, new ParameterizedTypeReference<ReviewResponse<DataReviewAPI>>() {
                });
        ReviewResponse<DataReviewAPI> reviews = response.getBody();
        // Set data to display.
        ModelAndView mav = new ModelAndView(HANDLE_REVIEW_SCREEN);
        mav.addObject("reviews", reviews);
        return mav;
    }

    /**
     * Show info review screen
     * 
     * @return detail review screen
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ModelAndView showDetailReviewScreen(@PathVariable int id, Model model) {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        String uri = API.URI_REVIEW + "/" + id;
        RestTemplate restTemplate = new RestTemplate();
        // Set data to display.
        ModelAndView mav = new ModelAndView(DETAIL_REVIEW_SCREEN);
        try {
            ReviewResponse<Review> reviewResponse = restTemplate
                    .exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<ReviewResponse<Review>>() {
                    }).getBody();
            mav.addObject("detailReview", reviewResponse.getData());
            return mav;
        } catch (HttpStatusCodeException exception) {
            return new ModelAndView("admin/404");
        }
    }

    /**
     * Process delete review
     * 
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteReviewProcess(@Valid @PathVariable int id, HttpSession session,
            RedirectAttributes redirectAttr) throws JsonParseException, JsonMappingException, IOException {
        // Check session and role of user login
        String result = checkSessionAndRole();

        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Header using Content-Type: application/json
        String uri = API.URI_REVIEW + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        // Delete
        try {
            ReviewResponse<String> review = restTemplate.exchange(uri, HttpMethod.DELETE,
                    new HttpEntity<String>(null, headers), new ParameterizedTypeReference<ReviewResponse<String>>() {
                    }).getBody();
            redirectAttr.addFlashAttribute("successMsg", review.getResultMessage());
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> reviewResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            ReviewResponse review = mapper.readValue(errorMessage, ReviewResponse.class);
            // If fail: return show review screen with notification
            redirectAttr.addFlashAttribute("failMsg", review.getResultMessage());
        }
        // Return review screen
        return new ModelAndView(REDIRECT_REVIEW);
    }
}

/**
* File ： OrderController.java
* Overview ： Processing handle order in system
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
import travel.tour.apitraveltour.model.ActiveOrder;
import travel.tour.apitraveltour.model.DataOrderAPI;
import travel.tour.apitraveltour.model.PersonOrder;
import travel.tour.apitraveltour.model.Review;
import travel.tour.apitraveltour.model.User;
import travel.tour.apitraveltour.model.modelResponse.OrderResponse;
import travel.tour.apitraveltour.model.modelResponse.ReviewResponse;
import travel.tour.apitraveltour.model.modelResponse.TourDetailResponse;
import travel.tour.apitraveltour.model.modelResponse.TourResponse;
import travel.tour.apitraveltour.model.modelResponse.UserResponse;

@Controller
@RequestMapping(Url.HANDLER_ORDER)
public class OrderController extends AbstractUserController {

    // =====================================================================
    // Constants
    // =====================================================================
    // Handle order Screen.
    public static final String HANDLE_ORDER_SCREEN = "admin/order/order";

    // Redirect order Screen.
    public static final String REDIRECT_ORDER = "redirect:/admin/order";

    // Add order Screen.
    public static final String DETAIL_ORDER_SCREEN = "admin/order/detail";

    // Redirect order Add screen.
    public static final String REDIRECT_order_ADD = "redirect:/admin/order/add";

    // Edit order Screen.
    public static final String EDIT_order_SCREEN = "admin/order/orderEdit";

    // Redirect order Edit screen.
    public static final String REDIRECT_order_EDIT = "redirect:/admin/order/edit/{id}";

    // Add order success message
    public static final String ADD_order_SUCCESS_MSG = "Add order success !";

    // Add order success message
    public static final String ADD_order_FAIL_MSG = "Add order Fail !";

    // Edit order success message
    public static final String EDIT_order_SUCCESS_MSG = "Edit order success !";

    // Edit order success message
    public static final String EDIT_order_FAIL_MSG = "Edit order Fail !";

    // Delete order success message
    public static final String DELETE_order_SUCCESS_MSG = "Delete order success !";

    // Delete order success message
    public static final String DELETE_order_FAIL_MSG = "Delete order Fail !";

    // =====================================================================
    // Public method
    // =====================================================================
    /**
     * Show handle order screen
     * 
     * @return order screen
     */
    @RequestMapping(Constants.Characters.BLANK)
    public ModelAndView showHandleOrderScreen(HttpSession session) {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Get data from API
        RestTemplate restTemplate = new RestTemplate();
        // Header using Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<OrderResponse<DataOrderAPI>> response = restTemplate.exchange(API.URI_ORDER, HttpMethod.GET,
                new HttpEntity<String>(null, headers), new ParameterizedTypeReference<OrderResponse<DataOrderAPI>>() {
                });
        OrderResponse<DataOrderAPI> orders = response.getBody();
        // Set data to display.
        ModelAndView mav = new ModelAndView(HANDLE_ORDER_SCREEN);
        mav.addObject("orders", orders);
        return mav;
    }

    /**
     * Show info order screen
     * 
     * @return detail order screen
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ModelAndView showDetailOrderScreen(@PathVariable int id, Model model, HttpSession session) {

        // Check session login and role
        String result = checkSessionAndRole();
        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        String uri = API.URI_ORDER + "/" + id;
        RestTemplate restTemplate = new RestTemplate();
        ModelAndView mav = new ModelAndView(DETAIL_ORDER_SCREEN);
        // Header using Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            OrderResponse<PersonOrder> orderResponse = restTemplate
                    .exchange(uri, HttpMethod.GET, new HttpEntity<String>(null, headers),
                            new ParameterizedTypeReference<OrderResponse<PersonOrder>>() {
                            })
                    .getBody();
            mav.addObject("detailOrder", orderResponse.getData());

            // get detail tour order
            String uri_tour = API.URI_GET_DETAIL_TOUR_BY_ID + "/" + orderResponse.getData().getIdDetailTour();
            TourResponse<TourDetailResponse> tourResponse = restTemplate
                    .exchange(uri_tour, HttpMethod.GET, new HttpEntity<String>(null, headers),
                            new ParameterizedTypeReference<TourResponse<TourDetailResponse>>() {
                            })
                    .getBody();
            mav.addObject("detailTour", tourResponse.getData());

            // get user order
            String uri_user = API.URI_USERS + "/" + orderResponse.getData().getIdUser();
            UserResponse<User> userResponse = restTemplate.exchange(uri_user, HttpMethod.GET,
                    new HttpEntity<String>(null, headers), new ParameterizedTypeReference<UserResponse<User>>() {
                    }).getBody();
            mav.addObject("userOrder", userResponse.getData());
            return mav;
        } catch (HttpStatusCodeException exception) {
            return new ModelAndView("admin/404");
        }
    }

    /**
     * Process delete order
     * 
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteOrderProcess(@Valid @PathVariable int id, HttpSession session,
            RedirectAttributes redirectAttr) throws JsonParseException, JsonMappingException, IOException {
        // Check session and role of user login
        String result = checkSessionAndRole();

        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Header using Content-Type: application/json
        String uri = API.URI_ORDER + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        // Delete
        try {
            OrderResponse<String> order = restTemplate.exchange(uri, HttpMethod.DELETE,
                    new HttpEntity<String>(null, headers), new ParameterizedTypeReference<OrderResponse<String>>() {
                    }).getBody();
            redirectAttr.addFlashAttribute("successMsg", order.getResultMessage());
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> reviewResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            OrderResponse review = mapper.readValue(errorMessage, OrderResponse.class);
            // If fail: return show review screen with notification
            redirectAttr.addFlashAttribute("failMsg", review.getResultMessage());
        }
        // Return review screen
        return new ModelAndView(REDIRECT_ORDER);
    }

    /**
     * Process active order
     * 
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping(value = "/active-order/{id}", method = RequestMethod.POST)
    public ModelAndView activeOrderProcess(@Valid @PathVariable int id, HttpSession session,
            RedirectAttributes redirectAttr) throws JsonParseException, JsonMappingException, IOException {
        // Check session and role of user login
        String result = checkSessionAndRole();

        if (!result.equals(Constants.Characters.BLANK)) {
            return new ModelAndView(result);
        }

        // Header using Content-Type: application/json
        String uri = API.URI_ACTIVE_ORDER;
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", (String) session.getAttribute(SessionKey.REMEMBER_TOKEN));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        ActiveOrder activeOrder = new ActiveOrder();
        activeOrder.setId(id);
        // Active
        try {
            OrderResponse<String> orderActive = restTemplate.exchange(uri, HttpMethod.POST,
                    new HttpEntity<ActiveOrder>(activeOrder, headers), new ParameterizedTypeReference<OrderResponse<String>>() {
                    }).getBody();
            redirectAttr.addFlashAttribute("successMsg", orderActive.getResultMessage());
        } catch (HttpStatusCodeException exception) {
            // Convert json error msg -> reviewResponse
            ObjectMapper mapper = new ObjectMapper();
            String errorMessage = exception.getResponseBodyAsString();
            OrderResponse review = mapper.readValue(errorMessage, OrderResponse.class);
            // If fail: return show review screen with notification
            redirectAttr.addFlashAttribute("failMsg", review.getResultMessage());
        }
        // Return review screen
        return new ModelAndView(REDIRECT_ORDER);
    }

}

package travel.tour.apitraveltour.controller.apiController;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import travel.tour.apitraveltour.model.Hotel;
import travel.tour.apitraveltour.model.User;
import travel.tour.apitraveltour.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by TranHTH
 */
@RestController
@RequestMapping("/api")
public class APITopPageController {

    @Autowired
    UserRepository userRepository;

    /**
     * Show all hotel
     * 
     * @return
     */
    @GetMapping("/hotelTest")
    public List<Hotel> getAllHotelTest() {
        // fake database
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(new Hotel(1, "hotel1", "address1", 07723231211, "abc1.com"));
        hotels.add(new Hotel(2, "hotel2", "address2", 07723231212, "abc2.com"));
        hotels.add(new Hotel(3, "hotel3", "address3", 07723231213, "abc3.com"));
        hotels.add(new Hotel(4, "hotel4", "address4", 07723231214, "abc4.com"));
        hotels.add(new Hotel(5, "hotel5", "address5", 07723231215, "abc5.com"));
        return hotels;
    }
}
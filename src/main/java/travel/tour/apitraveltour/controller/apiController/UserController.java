package travel.tour.apitraveltour.controller.apiController;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import travel.tour.apitraveltour.model.User;
import travel.tour.apitraveltour.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by datdm
 */
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/userTest")
    public List<User> getAllUsersTest() {
		//fake database
    	List<User> users=new ArrayList<>();
    	users.add(new User(1,"trang", "trang1", 1));
    	users.add(new User(2,"trang", "trang2", 1));
    	users.add(new User(3,"trang", "trang3", 1));
    	users.add(new User(4,"trang", "trang4", 1));
    	users.add(new User(5,"trang", "trang5", 1));
    	return users;
    }

	@PostMapping("/users")
	public User createUser(@Valid @RequestBody User user) {
		return userRepository.save(user);
	}

	@GetMapping("/users/{id}")
	public User getEmployeeByempNo(@PathVariable(value = "id") int id) {
		return userRepository.findOne(id);
	}

	@PutMapping("/users/{id}")
	public User updateEmployee(@PathVariable(value = "id") int id, @Valid @RequestBody User user) {

		User users = userRepository.findOne(id);
		users.setUsername(user.getUsername());
		users.setPassword(user.getPassword());
		users.setActive(user.getActive());

		User updatedUser = userRepository.save(users);
		return updatedUser;
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") int id) {
		User user = userRepository.findOne(id);

		userRepository.delete(user);

		return ResponseEntity.ok().build();
	}

}
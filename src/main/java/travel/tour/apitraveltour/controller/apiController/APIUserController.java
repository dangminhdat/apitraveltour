package travel.tour.apitraveltour.controller.apiController;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import travel.tour.apitraveltour.model.modelRequest.User;
import travel.tour.apitraveltour.repository.UserRepository;

/**
 * Created by datdm
 */
@RestController
@RequestMapping("/api")
public class APIUserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
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
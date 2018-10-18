package travel.tour.apitraveltour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import travel.tour.apitraveltour.model.modelRequest.User;

/**
 * Created by datdm
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	//todo
}

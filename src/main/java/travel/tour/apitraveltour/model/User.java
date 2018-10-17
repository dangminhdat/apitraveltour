package travel.tour.apitraveltour.model;

import javax.persistence.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Created by datdm
 */
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    private int active;
 
    public User() {
 
    }
 
    public User(int id, String username, String password, int active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getActive() {
        return active;
    }
 
    public void setActive(int active) {
        this.active = active;
    }
 
}
/**
* File ： User.java
* Overview ： User model
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.model.modelRequest;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    
    private String remember_token;
 
    public User() {
 
    }
 
    public User(int id, String username, String password, int active, String remember_token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;
        this.remember_token = remember_token;
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

    /**
     * @return the remember_token
     */
    public String getRemember_token() {
        return remember_token;
    }

    /**
     * @param remember_token the remember_token to set
     */
    public void setRemember_token(String remember_token) {
        this.remember_token = remember_token;
    }
 
    
}
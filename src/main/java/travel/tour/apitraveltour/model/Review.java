/**
* File ：Review.java
* Overview ：Review class
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Review {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("score")
    private String score;

    @JsonProperty("content")
    private String content;

    @JsonProperty("date_review")
    private String dateReview;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("name_tour")
    private String nameTour;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateReview() {
        return dateReview;
    }

    public void setDateReview(String date_review) {
        this.dateReview = date_review;
    }

    public String getNameTour() {
        return nameTour;
    }

    public void setNameTour(String name_tour) {
        this.nameTour = name_tour;
    }
}

package travel.tour.apitraveltour.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistic {

    @JsonProperty("guide")
    private int guide;

    @JsonProperty("hotel")
    private int hotel;

    @JsonProperty("order")
    private int order;

    @JsonProperty("tour")
    private int tour;
    
    @JsonProperty("review")
    private int review;
    
    @JsonProperty("location")
    private int location;
    
    @JsonProperty("user")
    private int user;

    public int getGuide() {
        return guide;
    }

    public void setGuide(int guide) {
        this.guide = guide;
    }

    public int getHotel() {
        return hotel;
    }

    public void setHotel(int hotel) {
        this.hotel = hotel;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getTour() {
        return tour;
    }

    public void setTour(int tour) {
        this.tour = tour;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
    
    
}

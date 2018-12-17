package travel.tour.apitraveltour.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActiveOrder {

    @JsonProperty("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}

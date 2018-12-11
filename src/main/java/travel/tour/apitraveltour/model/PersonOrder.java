package travel.tour.apitraveltour.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonOrder {

    @JsonProperty("id")
    private int id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("address")
    private String address;
    
    @JsonProperty("note")
    private String note;
    
    @JsonProperty("num_adults")
    private int numAdults;
    
    @JsonProperty("num_childs")
    private int numChilds;
    
    @JsonProperty("date_ordered")
    private String dateOrdered;
    
    @JsonProperty("status")
    private int status;
    
    @JsonProperty("id_detail_tour")
    private int idDetailTour;
    
    @JsonProperty("id_user")
    private int idUser;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
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
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public int getNumAdults() {
        return numAdults;
    }
    public void setNumAdults(int numAdults) {
        this.numAdults = numAdults;
    }
    public int getNumChilds() {
        return numChilds;
    }
    public void setNumChilds(int numChilds) {
        this.numChilds = numChilds;
    }
    public String getDateOrdered() {
        return dateOrdered;
    }
    public void setDateOrdered(String dateOrdered) {
        this.dateOrdered = dateOrdered;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getIdDetailTour() {
        return idDetailTour;
    }
    public void setIdDetailTour(int idDetailTour) {
        this.idDetailTour = idDetailTour;
    }
    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    
}

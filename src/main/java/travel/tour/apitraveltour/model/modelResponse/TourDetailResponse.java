package travel.tour.apitraveltour.model.modelResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TourDetailResponse {

    @JsonProperty("id")
    private int id;
    
    @JsonProperty("date_depart")
    private String dateDepart;

    @JsonProperty("price_adults")
    private Integer priceAdults;

    @JsonProperty("price_childs")
    private Integer priceChilds;

    @JsonProperty("time_depart")
    private String timeDepart;

    @JsonProperty("slot")
    private Integer slot;

    @JsonProperty("address_depart")
    private String addressDepart;

    @JsonProperty("id_guide")
    private int idGuide;
    
    @JsonProperty("id_tour")
    private int idTour;

    @JsonProperty("id_hotel")
    private int idHotel[];
    
    @JsonProperty("deleted_at")
    private int deletedAt;
    
    @JsonProperty("booked")
    private int booked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Integer getPriceAdults() {
        return priceAdults;
    }

    public void setPriceAdults(Integer priceAdults) {
        this.priceAdults = priceAdults;
    }

    public Integer getPriceChilds() {
        return priceChilds;
    }

    public void setPriceChilds(Integer priceChilds) {
        this.priceChilds = priceChilds;
    }

    public String getTimeDepart() {
        return timeDepart;
    }

    public void setTimeDepart(String timeDepart) {
        this.timeDepart = timeDepart;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public String getAddressDepart() {
        return addressDepart;
    }

    public void setAddressDepart(String addressDepart) {
        this.addressDepart = addressDepart;
    }

    public int getIdGuide() {
        return idGuide;
    }

    public void setIdGuide(int idGuide) {
        this.idGuide = idGuide;
    }

    public int getIdTour() {
        return idTour;
    }

    public void setIdTour(int idTour) {
        this.idTour = idTour;
    }

    public int[] getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int[] idHotel) {
        this.idHotel = idHotel;
    }

    public int getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(int deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getBooked() {
        return booked;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }

    
}


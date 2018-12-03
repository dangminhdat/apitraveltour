package travel.tour.apitraveltour.model.modelRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailTourRequest {

    @JsonProperty("date_depart")
    private String date_depart;

    @JsonProperty("price_adults")
    private Integer price_adults;

    @JsonProperty("price_childs")
    private Integer price_childs;

    @JsonProperty("time_depart")
    private String time_depart;

    @JsonProperty("slot")
    private Integer slot;

    @JsonProperty("address_depart")
    private String address_depart;

    @JsonProperty("id_guide")
    private int id_guide;
    
    @JsonProperty("id_tour")
    private int id_tour;

    @JsonProperty("id_hotel")
    private int id_hotel[];

    public String getDate_depart() {
        return date_depart;
    }

    public void setDate_depart(String date_depart) {
        this.date_depart = date_depart;
    }

    public Integer getPrice_adults() {
        return price_adults;
    }

    public void setPrice_adults(Integer price_adults) {
        this.price_adults = price_adults;
    }

    public Integer getPrice_childs() {
        return price_childs;
    }

    public void setPrice_childs(Integer price_childs) {
        this.price_childs = price_childs;
    }

    public String getTime_depart() {
        return time_depart;
    }

    public void setTime_depart(String time_depart) {
        this.time_depart = time_depart;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public String getAddress_depart() {
        return address_depart;
    }

    public void setAddress_depart(String address_depart) {
        this.address_depart = address_depart;
    }

    public int getId_guide() {
        return id_guide;
    }

    public void setId_guide(int id_guide) {
        this.id_guide = id_guide;
    }

    public int getId_tour() {
        return id_tour;
    }

    public void setId_tour(int id_tour) {
        this.id_tour = id_tour;
    }

    public int[] getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(int[] id_hotel) {
        this.id_hotel = id_hotel;
    }

    

}

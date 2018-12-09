/**
* File ： Tour.java
* Overview ： Tour class
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailTour {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("booked")
    private Integer booked;

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

    private Guide guide;

    private java.util.List<Hotel> hotel = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBooked() {
        return booked;
    }

    public void setBooked(Integer booked) {
        this.booked = booked;
    }

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

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public java.util.List<Hotel> getHotel() {
        return hotel;
    }

    public void setHotel(java.util.List<Hotel> hotel) {
        this.hotel = hotel;
    }

}

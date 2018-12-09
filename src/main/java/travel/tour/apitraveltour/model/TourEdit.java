/**
* File ： Tour.java
* Overview ： Tour class
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.model;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TourEdit {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("number_days")
    private Integer numberDays;

    @JsonProperty("item_tour")
    private String itemTour;

    @JsonProperty("discount")
    private Integer discount;

    @JsonProperty("booked")
    private Integer booked;

    @JsonProperty("images")
    private String images;

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

    @JsonProperty("id_type_tour")
    private int typeTour;

    @JsonProperty("programs")
    private String programs;

    @JsonProperty("note")
    private String note;

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

    public Integer getNumberDays() {
        return numberDays;
    }

    public void setNumberDays(Integer numberDays) {
        this.numberDays = numberDays;
    }

    public String getItemTour() {
        return itemTour;
    }

    public void setItemTour(String itemTour) {
        this.itemTour = itemTour;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getBooked() {
        return booked;
    }

    public void setBooked(Integer booked) {
        this.booked = booked;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDateDepart() {
        return date_depart;
    }

    public void setDateDepart(String dateDepart) {
        this.date_depart = dateDepart;
    }

    public Integer getPriceAdults() {
        return price_adults;
    }

    public void setPriceAdults(Integer priceAdults) {
        this.price_adults = priceAdults;
    }

    public Integer getPriceChilds() {
        return price_childs;
    }

    public void setPriceChilds(Integer priceChilds) {
        this.price_childs = priceChilds;
    }

    public String getTimeDepart() {
        return time_depart;
    }

    public void setTimeDepart(String timeDepart) {
        this.time_depart = timeDepart;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public int getTypeTour() {
        return typeTour;
    }

    public void setTypeTour(int typeTour) {
        this.typeTour = typeTour;
    }

    public String getPrograms() {
        return programs;
    }

    public void setPrograms(String programs) {
        this.programs = programs;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}

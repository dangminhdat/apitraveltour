/**
* File ： Tour.java
* Overview ： Tour class
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tour {

    @JsonProperty("id")
    private int id;

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
    private String dateDepart;

    @JsonProperty("price_adults")
    private Integer priceAdults;

    @JsonProperty("price_childs")
    private Integer priceChilds;

    @JsonProperty("time_depart")
    private String timeDepart;

    @JsonProperty("slot")
    private Integer slot;

    @JsonProperty("id_type_tour")
    private int typeTour;

    @JsonProperty("programs")
    private String programs;

    @JsonProperty("note")
    private String note;

    @JsonProperty("date_created")
    private String dateCreated;
    
    @JsonProperty("deleted_at")
    private int deletedAt;

    @JsonProperty("id_guide")
    private int id_guide;
    
    @JsonProperty("id_tour")
    private int id_tour;

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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(int deletedAt) {
        this.deletedAt = deletedAt;
    }

}

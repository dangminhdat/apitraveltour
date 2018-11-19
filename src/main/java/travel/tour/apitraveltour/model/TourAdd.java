/**
* File ： TourAdd.java
* Overview ： TourAdd class
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.model;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TourAdd {

    private String name;

    private int number_days;

    private String item_tour;

    private Integer discount;

    private String images;

    private int id_type_tour;

    private String programs;

    private String note;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber_days() {
        return number_days;
    }

    public void setNumber_days(int number_days) {
        this.number_days = number_days;
    }

    public String getItem_tour() {
        return item_tour;
    }

    public void setItem_tour(String item_tour) {
        this.item_tour = item_tour;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getId_type_tour() {
        return id_type_tour;
    }

    public void setId_type_tour(int id_type_tour) {
        this.id_type_tour = id_type_tour;
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

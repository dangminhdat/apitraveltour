/**
* File ： DataDetailTourAPI.java
* Overview ： DataDetailTourAPI class
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.model;

public class DataDetailTourAPI {

    private Integer total;
    private java.util.List<DetailTour> list = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public java.util.List<DetailTour> getList() {
        return list;
    }

    public void setList(java.util.List<DetailTour> list) {
        this.list = list;
    }
}

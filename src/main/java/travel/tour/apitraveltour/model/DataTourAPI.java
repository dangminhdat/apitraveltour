/**
* File ： DataTourAPI.java
* Overview ： DataTourAPI class
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.model;

public class DataTourAPI {

    private Integer total;
    private java.util.List<Tour> list = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public java.util.List<Tour> getList() {
        return list;
    }

    public void setList(java.util.List<Tour> list) {
        this.list = list;
    }
}

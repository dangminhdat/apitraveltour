/**
* File ： DataHotelAPI.java
* Overview ： DataHotelAPI class
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.model;

public class DataHotelAPI {

    private Integer total;
    private java.util.List<Hotel> list = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public java.util.List<Hotel> getList() {
        return list;
    }

    public void setList(java.util.List<Hotel> list) {
        this.list = list;
    }

}

/**
* File ： DataOrderAPI.java
* Overview ： DataOrderAPI class
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.model;

public class DataOrderAPI {

    private Integer total;
    private java.util.List<PersonOrder> list = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public java.util.List<PersonOrder> getList() {
        return list;
    }

    public void setList(java.util.List<PersonOrder> list) {
        this.list = list;
    }

}
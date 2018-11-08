/**
* File ： DataUserAPI.java
* Overview ： DataUserAPI class
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.model;

import java.util.List;

public class DataUserAPI {

    private Integer total;
    private java.util.List<User> list = null;

    public DataUserAPI() {

    }

    public DataUserAPI(Integer total, List<User> list) {
        this.total = total;
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public java.util.List<User> getList() {
        return list;
    }

    public void setList(java.util.List<User> list) {
        this.list = list;
    }

}

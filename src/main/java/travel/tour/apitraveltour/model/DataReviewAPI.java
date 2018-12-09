/**
* File ：DataReviewAPI.java
* Overview ：DataReviewAPI class
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.model;

public class DataReviewAPI {

    private Integer total;
    private java.util.List<Review> list = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public java.util.List<Review> getList() {
        return list;
    }

    public void setList(java.util.List<Review> list) {
        this.list = list;
    }
}
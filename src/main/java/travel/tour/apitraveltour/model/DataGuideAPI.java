/**
* File ： DataGuideAPI.java
* Overview ： DataGuideAPI class
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.model;

public class DataGuideAPI {

    private Integer total;
    private java.util.List<Guide> list = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public java.util.List<Guide> getList() {
        return list;
    }

    public void setList(java.util.List<Guide> list) {
        this.list = list;
    }

}
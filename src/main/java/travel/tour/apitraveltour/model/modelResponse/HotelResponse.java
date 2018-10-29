/**
* File ： HotelResponse.java
* Overview ： HotelResponse class
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.model.modelResponse;

import travel.tour.apitraveltour.model.Hotel;

public class HotelResponse {
    private int result_code;
    private String result_message;
    private Hotel data;

    public int getResultCode() {
        return result_code;
    }

    public void setResultCode(int resultCode) {
        this.result_code = resultCode;
    }

    public String getResultMessage() {
        return result_message;
    }

    public void setResultMessage(String resultMessage) {
        this.result_message = resultMessage;
    }

    public Hotel getData() {
        return data;
    }

    public void setData(Hotel data) {
        this.data = data;
    }
}

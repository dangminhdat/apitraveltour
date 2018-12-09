/**
* File ： HotelResponse.java
* Overview ： HotelResponse class
*
* @author TrangHTH
* @version 1.0
*/
package travel.tour.apitraveltour.model.modelResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HotelResponse<T> {
    @JsonProperty("result_code")
    private int result_code;
    
    @JsonProperty("result_message")
    private String result_message;
    
    @JsonProperty("data")
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

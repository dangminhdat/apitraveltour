package travel.tour.apitraveltour.model.modelResponse;

import travel.tour.apitraveltour.model.DataUserAPI;

public class UserResponse {

    private Integer resultCode;
    private String resultMessage;
    private DataUserAPI data;

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public DataUserAPI getData() {
        return data;
    }

    public void setData(DataUserAPI data) {
        this.data = data;
    }

}

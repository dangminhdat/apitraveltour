package travel.tour.apitraveltour.model.modelResponse;

public class LoginResponse {

    private int result_code;

    private String result_message;

    private String data;

    public LoginResponse() {

    }

    public LoginResponse(int result_code, String result_message, String data) {
        this.result_code = result_code;
        this.result_message = result_message;
        this.data = data;
    }

    /**
     * @return the result_code
     */
    public int getResult_code() {
        return result_code;
    }

    /**
     * @param result_code
     *            the result_code to set
     */
    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    /**
     * @return the result_message
     */
    public String getResult_message() {
        return result_message;
    }

    /**
     * @param result_message
     *            the result_message to set
     */
    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

}

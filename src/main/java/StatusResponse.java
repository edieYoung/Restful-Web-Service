public enum StatusResponse {
    SUCESS("Success"),
    ERROR("Error");

    private String status;
    StatusResponse(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

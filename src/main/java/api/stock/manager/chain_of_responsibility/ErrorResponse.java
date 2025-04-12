package api.stock.manager.chain_of_responsibility;

public class ErrorResponse {
    public boolean errorFound;
    public String errorMessage;

    public ErrorResponse(boolean found, String message) {
        this.errorFound = found;
        this.errorMessage = message;
    }
}

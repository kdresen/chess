package model.results;

public class ErrorResult extends Exception {
    final private int statusCode;


    public ErrorResult(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int StatusCode() {
        return statusCode;
    }
}

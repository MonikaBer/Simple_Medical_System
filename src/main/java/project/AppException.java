package project;

public class AppException extends Exception {

    private String msg = null;

    public AppException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}

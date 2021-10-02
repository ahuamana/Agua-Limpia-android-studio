package edu.aha.agualimpiafinal.models;

public class Like {

    private String id;
    private String token;
    private boolean status;

    public Like() {
    }

    public Like(String id, String token, boolean status) {
        this.id = id;
        this.token = token;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

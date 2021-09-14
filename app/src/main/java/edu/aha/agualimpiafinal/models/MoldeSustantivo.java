package edu.aha.agualimpiafinal.models;

public class MoldeSustantivo {

    private String name;
    private String tipo;
    private String url;
    private String author;
    private long timestamp;

    public MoldeSustantivo() {
    }

    public MoldeSustantivo(String name, String tipo, String url, String author, long timestamp) {
        this.name = name;
        this.tipo = tipo;
        this.url = url;
        this.author = author;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

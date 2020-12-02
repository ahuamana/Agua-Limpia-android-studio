package edu.aha.agualimpiafinal.Entidades;

public class Ingreso {

    private String idmuestraDB;
    private int cantidadmuestraDB;
    private int tiempoDB;
    private double latitudDB;
    private double longitudDB;
    private String bqvDB;
    private String fotopathDB;
    private String nombrecompleto;
    private String correo;


    public Ingreso() {
    }

    public Ingreso(String idmuestraDB, int cantidadmuestraDB, int tiempoDB, double latitudDB, double longitudDB, String bqvDB, String fotopathDB, String nombrecompleto, String correo) {
        this.idmuestraDB = idmuestraDB;
        this.cantidadmuestraDB = cantidadmuestraDB;
        this.tiempoDB = tiempoDB;
        this.latitudDB = latitudDB;
        this.longitudDB = longitudDB;
        this.bqvDB = bqvDB;
        this.fotopathDB = fotopathDB;
        this.nombrecompleto = nombrecompleto;
        this.correo = correo;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getIdmuestraDB() {
        return idmuestraDB;
    }

    public void setIdmuestraDB(String idmuestraDB) {
        this.idmuestraDB = idmuestraDB;
    }

    public int getCantidadmuestraDB() {
        return cantidadmuestraDB;
    }

    public void setCantidadmuestraDB(int cantidadmuestraDB) {
        this.cantidadmuestraDB = cantidadmuestraDB;
    }

    public int getTiempoDB() {
        return tiempoDB;
    }

    public void setTiempoDB(int tiempoDB) {
        this.tiempoDB = tiempoDB;
    }

    public double getLatitudDB() {
        return latitudDB;
    }

    public void setLatitudDB(double latitudDB) {
        this.latitudDB = latitudDB;
    }

    public double getLongitudDB() {
        return longitudDB;
    }

    public void setLongitudDB(double longitudDB) {
        this.longitudDB = longitudDB;
    }

    public String getBqvDB() {
        return bqvDB;
    }

    public void setBqvDB(String bqvDB) {
        this.bqvDB = bqvDB;
    }

    public String getFotopathDB() {
        return fotopathDB;
    }

    public void setFotopathDB(String fotopathDB) {
        this.fotopathDB = fotopathDB;
    }
}

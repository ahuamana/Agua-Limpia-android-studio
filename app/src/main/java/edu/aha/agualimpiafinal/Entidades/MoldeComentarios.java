package edu.aha.agualimpiafinal.Entidades;

public class MoldeComentarios {

    private String AuthorEmail;
    private String AuthorFirstname;
    private String AuthorLastname;
    private String AuthorAlias;

    private long SugerenciaFechaUnixtime;
    private String SugerenciaMensaje;

    public MoldeComentarios() {
    }

    public MoldeComentarios(String authorEmail, String authorFirstname, String authorLastname, String authorAlias, long sugerenciaFechaUnixtime, String sugerenciaMensaje) {
        AuthorEmail = authorEmail;
        AuthorFirstname = authorFirstname;
        AuthorLastname = authorLastname;
        AuthorAlias = authorAlias;
        SugerenciaFechaUnixtime = sugerenciaFechaUnixtime;
        SugerenciaMensaje = sugerenciaMensaje;
    }

    public String getAuthorEmail() {
        return AuthorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        AuthorEmail = authorEmail;
    }

    public String getAuthorFirstname() {
        return AuthorFirstname;
    }

    public void setAuthorFirstname(String authorFirstname) {
        AuthorFirstname = authorFirstname;
    }

    public String getAuthorLastname() {
        return AuthorLastname;
    }

    public void setAuthorLastname(String authorLastname) {
        AuthorLastname = authorLastname;
    }

    public String getAuthorAlias() {
        return AuthorAlias;
    }

    public void setAuthorAlias(String authorAlias) {
        AuthorAlias = authorAlias;
    }

    public long getSugerenciaFechaUnixtime() {
        return SugerenciaFechaUnixtime;
    }

    public void setSugerenciaFechaUnixtime(long sugerenciaFechaUnixtime) {
        SugerenciaFechaUnixtime = sugerenciaFechaUnixtime;
    }

    public String getSugerenciaMensaje() {
        return SugerenciaMensaje;
    }

    public void setSugerenciaMensaje(String sugerenciaMensaje) {
        SugerenciaMensaje = sugerenciaMensaje;
    }
}

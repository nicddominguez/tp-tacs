package tp.tacs.api.dominio.usuario;

public class Usuario {

    private Long id;

    private String mail;

    private String nombre;

    private String googleId;

    private Boolean isAdmin = false;

    private Long partidasJugadas = 0L;

    private Long partidasGanadas = 0L;

    private Long rachaActual = 0L;

    public Usuario(Long id, String mail, String nombre) {
        this.id = id;
        this.mail = mail;
        this.nombre = nombre;
        RepoUsuarios.instance().agregarUsuario(this);
    }

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Long getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setPartidasJugadas(Long partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    public void aumentarPartidasJugadas() {
        this.partidasJugadas++;
    }

    public Long getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(Long partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public void aumentarPartidasGanadas() {
        this.partidasGanadas++;
    }

    public Long getRachaActual() {
        return rachaActual;
    }

    public void setRachaActual(Long rachaActual) {
        this.rachaActual = rachaActual;
    }

    public void aumentarRachaActual() {
        this.rachaActual++;
    }

    public void reiniciarRacha() {
        this.rachaActual = 0L;
    }

    public boolean mismoId(Long id) {
        return this.id == id;
    }
}

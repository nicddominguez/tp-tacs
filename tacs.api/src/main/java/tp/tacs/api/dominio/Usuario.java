package tp.tacs.api.dominio;

public class Usuario {

    private Long id;

    private String mail;

    private String nombre;

    public Usuario(Long id, String mail, String nombre) {
        this.id = id;
        this.mail = mail;
        this.nombre = nombre;
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

}

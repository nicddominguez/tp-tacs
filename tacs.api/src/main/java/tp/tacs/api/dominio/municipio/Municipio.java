package tp.tacs.api.dominio.municipio;

import lombok.*;
import tp.tacs.api.dominio.usuario.Usuario;

@Builder
@Getter
@Setter
public class Municipio {
    private String id;
    private String nombre;
    private String externalApiId;
    @Builder.Default
    private Integer cantGauchos = 15;
    @Builder.Default
    private boolean bloqueado = false;
    @Builder.Default
    private Especializacion especializacion = new Produccion();
    private Usuario duenio;
    private Integer nivelDeProduccion;
    private Double latitud;
    private Double longitud;
    private Float altura;
    private String urlImagen;
}

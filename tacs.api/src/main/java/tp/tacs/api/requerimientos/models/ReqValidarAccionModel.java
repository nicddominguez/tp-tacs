package tp.tacs.api.requerimientos.models;

import lombok.Builder;
import lombok.Getter;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;

@Builder
@Getter
public class ReqValidarAccionModel {
    private Partida partida;
    private String accion;
    private Usuario duenio;
}

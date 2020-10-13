package tp.tacs.api.dominio.partida;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ModoDeJuego {

    Float multDefEnDefensa;
    Float multDefEnProduccion;
    String nombreAMostrar;

}

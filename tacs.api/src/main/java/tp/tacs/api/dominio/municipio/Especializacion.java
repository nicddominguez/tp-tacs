package tp.tacs.api.dominio.municipio;

import tp.tacs.api.dominio.partida.Partida;

public interface Especializacion {

    Float multDefensa(Partida partida);

    Integer nivelDeProduccion(float multiplicador);

    String getNombreAMostrar();
}

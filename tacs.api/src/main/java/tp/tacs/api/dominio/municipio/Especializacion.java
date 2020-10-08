package tp.tacs.api.dominio.municipio;

import tp.tacs.api.dominio.partida.ModoDeJuego;

public interface Especializacion { //TODO hacer que el multDefensa dependa del modo de juego, como estaba antes.

    Float multDefensa(ModoDeJuego modoDeJuego);

    Integer nivelDeProduccion(float multiplicador);

    String getNombreAMostrar();
}

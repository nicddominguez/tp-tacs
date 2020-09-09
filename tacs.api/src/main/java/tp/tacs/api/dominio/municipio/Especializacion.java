package tp.tacs.api.dominio.municipio;

import tp.tacs.api.dominio.partida.Partida;

public interface Especializacion {

    void producir(Municipio municipio);

    Float multDefensa(Partida partida);

    Integer nivelDeProduccion(Municipio municipio);

    String getNombreAMostrar();
}

package tp.tacs.api.dominio.municipio;

public interface Especializacion { //TODO hacer que el multDefensa dependa del modo de juego, como estaba antes.

    Float multDefensa();

    Integer nivelDeProduccion(float multiplicador);

    String getNombreAMostrar();
}

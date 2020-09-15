package tp.tacs.api.dominio.municipio;

import tp.tacs.api.dominio.partida.Partida;

public class Defensa implements Especializacion {

    @Override
    public Float multDefensa(Partida partida) {
        return partida.getModoDeJuego().getMultDefEnDefensa();
    }

    @Override
    public Integer nivelDeProduccion(float multiplicador) {
        return Math.round(10 * multiplicador);
    }

    @Override
    public String getNombreAMostrar() {
        return "Defensa";
    }

}

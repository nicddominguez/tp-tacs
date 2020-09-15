package tp.tacs.api.dominio.municipio;

import tp.tacs.api.dominio.partida.Partida;

public class Produccion implements Especializacion {

    @Override
    public Float multDefensa(Partida partida) {
        return partida.getModoDeJuego().getMultDefEnProduccion();
    }

    @Override
    public Integer nivelDeProduccion(float multiplicador) {
        return Math.round(15 * multiplicador);
    }

    @Override
    public String getNombreAMostrar() {
        return "Produccion";
    }

}

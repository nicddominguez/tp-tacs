package tp.tacs.api.dominio.municipio;

import tp.tacs.api.dominio.partida.ModoDeJuego;

public class Produccion implements Especializacion {

    @Override
    public Float multDefensa(ModoDeJuego modoDeJuego) {
        return modoDeJuego.getMultDefEnProduccion();
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

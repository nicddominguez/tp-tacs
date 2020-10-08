package tp.tacs.api.dominio.municipio;

import tp.tacs.api.dominio.partida.ModoDeJuego;

public class Defensa implements Especializacion {

    @Override
    public Float multDefensa(ModoDeJuego modoDeJuego) {
        return modoDeJuego.getMultDefEnDefensa();
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

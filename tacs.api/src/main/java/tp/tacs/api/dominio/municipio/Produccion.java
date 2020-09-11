package tp.tacs.api.dominio.municipio;

import tp.tacs.api.dominio.partida.Partida;

public class Produccion implements Especializacion {

    @Override
    public void producir(Municipio municipio) {
        Integer cantGauchos = this.nivelDeProduccion(municipio);
        municipio.agregarGauchos(cantGauchos);
    }

    @Override
    public Float multDefensa(Partida partida) {
        return partida.getModoDeJuego().getMultDefEnProduccion();
    }

    @Override
    public Integer nivelDeProduccion(Municipio municipio) {
        return Math.round(15 * (1 - ((municipio.getAltura() - municipio.getPartida().minAltura())
                / (2 * (municipio.getPartida().maxAltura() - municipio.getPartida().minAltura())))));
    }

    @Override
    public String getNombreAMostrar() {
        return "Produccion";
    }

}

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
        var minAltura = municipio.getPartida().minAltura();
        var maxAltura = municipio.getPartida().maxAltura();

        return Math.round(15 * (1 - ((municipio.getAltura() - minAltura)
                / (2 * (maxAltura - minAltura)))));
    }

    @Override
    public String getNombreAMostrar() {
        return "Produccion";
    }

}

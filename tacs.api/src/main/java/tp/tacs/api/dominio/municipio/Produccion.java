package tp.tacs.api.dominio.municipio;

import tp.tacs.api.dominio.partida.Partida;

public class Produccion implements Especializacion {

    @Override
    public void producir(Municipio municipio) {
        Integer cantGauchos = this.nivelDeProduccion(municipio);
        municipio.agregarGauchos(cantGauchos);
    }

    /**@Autwired
     * ExternalApi
     * MunicipioDao
     * PArtidaDao
     * producir(PartidaId, MunicipioId){
     *     var minAltura = municipio.getPartida().minAltura(); Lo saca de partida dao
 *         var maxAltura = municipio.getPartida().maxAltura(); Lo saca de partida dao
     *
 *         municipio.agregarGauchos(cantGauchos);
     *     agreagarGauchos
     *     municipioDao.save()
     * }
     */

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

package tp.tacs.api.dominio;

public class Produccion implements Especializacion {

    @Override
    public void producir(Municipio municipio) {
        Integer cantGauchos = this.nivelDeProduccion(municipio);
        municipio.agregarGauchos(cantGauchos);
    }

    @Override
    public Float multDensa() {
        return 1f;
    }

    @Override
    public Integer nivelDeProduccion(Municipio municipio) {
        return Math.round(15 * (1 - ((municipio.getAltura() - municipio.getPartida().minAltura())
                / (2 * (municipio.getPartida().maxAltura() - municipio.getPartida().minAltura())))));
    }
}

package tp.tacs.api.dominio;

public class Defensa implements Especializacion {

    @Override
    public void producir(Municipio municipio) {
        Integer cantGauchos = Math.round(10*(1-((municipio.getAltura() - municipio.getPartida().minAltura())/(2*(municipio.getPartida().maxAltura() - municipio.getPartida().minAltura())))));
        municipio.agregarGauchos(cantGauchos);
    }
}

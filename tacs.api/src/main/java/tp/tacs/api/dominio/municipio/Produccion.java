package tp.tacs.api.dominio.municipio;

public class Produccion implements Especializacion {

    @Override
    public Float multDefensa() {
        return 0.25f;
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

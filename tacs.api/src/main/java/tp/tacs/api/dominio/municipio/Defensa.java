package tp.tacs.api.dominio.municipio;

public class Defensa implements Especializacion {

    @Override
    public Float multDefensa() {
        return 1.25f;
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

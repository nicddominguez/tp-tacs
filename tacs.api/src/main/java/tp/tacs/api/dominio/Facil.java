package tp.tacs.api.dominio;

public class Facil implements ModoDeJuego {

    @Override
    public Float getMultDist(Float distanciaMunicipios, Float minDist, Float maxDist) {
        return 1 - (distanciaMunicipios - minDist)/(2*(maxDist-minDist));
    }

    @Override
    public Float getMultAltura(Float alturaMunicipioDefensor, Float minAltura, Float maxAltura) {
        return 1 + (alturaMunicipioDefensor - minAltura)/(2*(maxAltura-minAltura));
    }
}

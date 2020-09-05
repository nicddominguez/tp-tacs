package tp.tacs.api.dominio;

public class ModoDeJuego {
    private Float multDefensaDefensa = 1f;
    private Float multDefnesaProduccion = 0.5f;

    public ModoDeJuego() {
    }

    public Float getMultDefensaDefensa() {
        return multDefensaDefensa;
    }

    public void setMultDefensaDefensa(Float multDefensaDefensa) {
        this.multDefensaDefensa = multDefensaDefensa;
    }
    public Float getMultDefnesaProduccion() {
        return multDefnesaProduccion;
    }

    public void setMultDefnesaProduccion(Float multDefnesaProduccion) {
        this.multDefnesaProduccion = multDefnesaProduccion;
    }

    public Float getMultDist(Float distanciaMunicipios, Float minDist, Float maxDist) {
        return 1 - (distanciaMunicipios - minDist)/(2*(maxDist-minDist));
    }

    public Float getMultAltura(Float alturaMunicipioDefensor, Float minAltura, Float maxAltura) {
        return 1 + (alturaMunicipioDefensor - minAltura)/(2*(maxAltura-minAltura));
    }

}

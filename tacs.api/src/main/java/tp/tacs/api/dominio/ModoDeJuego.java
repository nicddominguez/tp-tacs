package tp.tacs.api.dominio;

public class ModoDeJuego {
    private Float multDefensa = 1f;
    private Float multDist = 0.75f;

    public ModoDeJuego() {
    }

    public Float getMultDefensa() {
        return multDefensa;
    }

    public void setMultDefensa(Float multDefensa) {
        this.multDefensa = multDefensa;
    }

    public Float getMultDist() {
        return multDist;
    }

    public void setMultDist(Float multDist) {
        this.multDist = multDist;
    }
}

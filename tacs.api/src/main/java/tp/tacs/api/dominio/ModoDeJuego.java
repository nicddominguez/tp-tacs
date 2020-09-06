package tp.tacs.api.dominio;

public abstract class ModoDeJuego {

    Float multDefEnDefensa;
    Float multDefEnAtaque;

    public Float getMultDefEnDefensa() {
        return multDefEnDefensa;
    }

    public void setMultDefEnDefensa(Float multDefEnDefensa) {
        this.multDefEnDefensa = multDefEnDefensa;
    }

    public Float getMultDefEnProduccion() {
        return multDefEnAtaque;
    }

    public void setMultDefEnAtaque(Float multDefEnAtaque) {
        this.multDefEnAtaque = multDefEnAtaque;
    }
}

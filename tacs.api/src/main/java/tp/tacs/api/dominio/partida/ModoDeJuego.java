package tp.tacs.api.dominio.partida;

public abstract class ModoDeJuego {

    Float multDefEnDefensa;
    Float multDefEnAtaque;
    String nombreAMostrar;

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

    public Float getMultDefEnAtaque() {
        return multDefEnAtaque;
    }

    public String getNombreAMostrar() {
        return nombreAMostrar;
    }
}

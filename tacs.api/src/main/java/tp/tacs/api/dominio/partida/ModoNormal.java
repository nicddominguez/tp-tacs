package tp.tacs.api.dominio.partida;

public class ModoNormal extends ModoDeJuego {

    public ModoNormal() {
        this.nombreAMostrar = "Normal";
        this.multDefEnProduccion = 1f;
        this.multDefEnDefensa = 2f;
    }
}

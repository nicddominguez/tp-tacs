package tp.tacs.api.dominio.partida;

public class ModoNormal extends ModoDeJuego {

    public ModoNormal() {
        this.nombreAMostrar = "Normal";
        this.multDefEnAtaque = 1.1f;
        this.multDefEnDefensa = 1.3f;
    }
}

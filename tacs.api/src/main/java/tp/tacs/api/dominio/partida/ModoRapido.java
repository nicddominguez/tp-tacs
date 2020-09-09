package tp.tacs.api.dominio.partida;

public class ModoRapido extends ModoDeJuego {

    public ModoRapido() {
        this.nombreAMostrar = "Rapido";
        this.multDefEnAtaque = 0.5f;
        this.multDefEnDefensa = 1.25f;
    }
}

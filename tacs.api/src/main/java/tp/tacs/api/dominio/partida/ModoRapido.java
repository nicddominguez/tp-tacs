package tp.tacs.api.dominio.partida;

public class ModoRapido extends ModoDeJuego {

    public ModoRapido() {
        this.nombreAMostrar = "Rapido";
        this.multDefEnProduccion = 1.2f;
        this.multDefEnDefensa = 2f;
    }
}

package tp.tacs.api.dominio;

import java.util.List;

public class RepoPartidas {
    private static RepoPartidas instanciaRepoPartidas = null;
    public static RepoPartidas instance() {
        if (instanciaRepoPartidas == null) {
            instanciaRepoPartidas = new RepoPartidas();
        }
        return instanciaRepoPartidas;
    }
    private List<Partida> partidas;

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public void agregarPartida(Partida partida) {
        this.partidas.add(partida);
    }
}

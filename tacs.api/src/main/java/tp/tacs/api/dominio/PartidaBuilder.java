package tp.tacs.api.dominio;

import java.util.ArrayList;
import java.util.List;

public class PartidaBuilder {
    private Integer cantMunicipios = 5;
    private List<Usuario> participantes;
    private Estados estado = Estados.EN_JUEGO;
    private String idProvincia;
    private List<Municipio> municipios = new ArrayList<>();
    private ModoDeJuego modoDeJuego = new ModoFacil();

    public PartidaBuilder() { }

    public PartidaBuilder setCantMunicipios(Integer cantMunicipios) {
        this.cantMunicipios = cantMunicipios;
        RepoMunicipios repoMunicipios = new RepoMunicipios();
        this.municipios = repoMunicipios.getMunicipios(this.idProvincia, this.cantMunicipios);
        return this;
    }

    public PartidaBuilder setParticipantes(List<Usuario> participantes) {
        this.participantes = participantes;
        return this;
    }

    public PartidaBuilder setEstado(Estados estado) {
        this.estado = estado;
        return this;
    }

    public PartidaBuilder setIdProvincia(String idProvincia) {
        this.idProvincia = idProvincia;
        return this;
    }

    public PartidaBuilder setMunicipios(List<Municipio> municipios) {
        //No es necesario si se setea la cantidad de municipios
        this.municipios = municipios;
        return this;
    }

    public PartidaBuilder setModoDeJuego(ModoDeJuego modoDeJuego) {
        this.modoDeJuego = modoDeJuego;
        return this;
    }

    public Partida constriur() {
        this.validarAtributos();
        return new Partida(this.participantes, this.estado, this.idProvincia, this.municipios, this.modoDeJuego);
    }

    public void validarAtributos() {
        if (this.participantes == null) {
            throw new RuntimeException("No se puede instanciar una partida sin participantes");
        }

        if (this.municipios.isEmpty()) {
            throw new RuntimeException("No se puede instanciar una partida sin municipios");
        }
    }
}

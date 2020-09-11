package tp.tacs.api.dominio.partida;

import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.municipio.RepoMunicipios;
import tp.tacs.api.http.externalApis.ExternalApis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PartidaBuilder {
    private List<Usuario> participantes;
    private Estado estado = Estado.EN_CURSO;
    private String idProvincia;
    private List<Municipio> municipios = new ArrayList<>();
    private ModoDeJuego modoDeJuego = new ModoRapido();
    private Date fechaCreacion = new Date();

    public PartidaBuilder(String idProvincia) {
        this.idProvincia = idProvincia;
    }

    public PartidaBuilder setCantMunicipios(Integer cantMunicipios) {
        ExternalApis repoMunicipios = ExternalApis.instance();
        this.municipios = repoMunicipios.getMunicipios(this.idProvincia, cantMunicipios);
        return this;
    }

    public PartidaBuilder setParticipantes(List<Usuario> participantes) {
        this.participantes = participantes;
        return this;
    }

    public PartidaBuilder setEstado(Estado estado) {
        this.estado = estado;
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

    public PartidaBuilder setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public Partida constriur() {
        this.validarAtributos();
        return new Partida(this.participantes, this.estado, this.idProvincia, this.municipios, this.modoDeJuego, fechaCreacion);
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

package tp.tacs.api.dominio.partida;

import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.handler.PartidaException;
import tp.tacs.api.http.externalApis.ExternalApis;

import java.util.*;

public class PartidaBuilder {
    private List<Usuario> participantes;
    private Estado estado = Estado.EN_CURSO;
    private String idProvincia;
    private List<Municipio> municipios = new ArrayList<>();
    private ModoDeJuego modoDeJuego = new ModoRapido();
    private Date fechaCreacion = new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime(); //TODO
    private ExternalApis repoMunicipios = ExternalApis.instance();

    public PartidaBuilder() {
    }

    public PartidaBuilder(String idProvincia) {
        this.idProvincia = idProvincia;
    }

    public PartidaBuilder setIdProvincia(String idProvincia) {
        this.idProvincia = idProvincia;
        return this;
    }

    public void setRepoMunicipios(ExternalApis repoMunicipios) {
        this.repoMunicipios = repoMunicipios;
    }

    public PartidaBuilder setCantMunicipios(Integer cantMunicipios) {
        this.municipios = this.repoMunicipios.getMunicipios(this.idProvincia, cantMunicipios);
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
            throw new PartidaException("No se puede instanciar una partida sin participantes");
        }

        if (this.municipios.isEmpty()) {
            throw new PartidaException("No se puede instanciar una partida sin municipios");
        }
    }
}

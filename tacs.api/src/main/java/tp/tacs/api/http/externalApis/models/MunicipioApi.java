package tp.tacs.api.http.externalApis.models;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MunicipioApi {
    private String id;
    private String nombre;
    private Number centroide_lat;
    private Number centroide_lon;

    public String coordenadasParaTopo(){
        return String.format("%s,%s",centroide_lat.toString(),centroide_lon.toString());
    }
}
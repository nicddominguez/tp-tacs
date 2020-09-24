package tp.tacs.api.http.externalApis.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MunicipiosApi {
    private List<MunicipioApi> departamentos;
}
package tp.tacs.api.http.externalApis.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImagenesApi {
    private List<ImagenApi> hits;
}

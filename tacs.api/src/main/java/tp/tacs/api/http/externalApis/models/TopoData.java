package tp.tacs.api.http.externalApis.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopoData {
    private Number elevation;
    private TopoLocation location;
}

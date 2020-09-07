package tp.tacs.api.utils;

import org.springframework.http.ResponseEntity;
import tp.tacs.api.model.UsuarioModel;

import java.util.List;

public class Utils {
    public <T> List<T> obtenerListaPaginada(Long pagina, Long tamanioPagina, List<T> lista){
        Long start = pagina * tamanioPagina;//23
        Long end = start + tamanioPagina;//23

        if(end > lista.size()){
            end = (long) lista.size();
        }

        if(start >= lista.size() || start < 0 || end <= 0)
            return null;

        return lista.subList(start.intValue(), end.intValue());
    }
}

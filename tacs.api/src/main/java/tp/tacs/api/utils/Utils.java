package tp.tacs.api.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Utils {
    public <T> List<T> obtenerListaPaginada(Long pagina, Long tamanioPagina, List<T> lista) {
        Long start = pagina * tamanioPagina;//23
        Long end = start + tamanioPagina;//23

        if (end > lista.size()) {
            end = (long) lista.size();
        }

        if (start >= lista.size() || start < 0 || end <= 0) {
            return new ArrayList<>();
        }

        return lista.subList(start.intValue(), end.intValue());
    }
}

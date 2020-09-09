package tp.tacs.api.controllers;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tp.tacs.api.dominio.municipio.RepoMunicipios;
import tp.tacs.api.model.ListarProvinciasResponse;
import tp.tacs.api.model.ProvinciaModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ProvinciasApiControllerTest {

    @Mock
    RepoMunicipios repoMunicipios;

    ProvinciasApiController provinciasApiController = new ProvinciasApiController();

    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);

        this.mockearRepoMunicipio();
        provinciasApiController.setRepoMunicipios(repoMunicipios);
    }

    private void mockearRepoMunicipio() {
        List<ProvinciaModel> provinciaModels = new ArrayList<>();
        provinciaModels.add(new ProvinciaModel().id(1L).nombre("Buenos Aires"));
        provinciaModels.add(new ProvinciaModel().id(2L).nombre("Catamarca"));
        provinciaModels.add(new ProvinciaModel().id(3L).nombre("Chaco"));
        provinciaModels.add(new ProvinciaModel().id(4L).nombre("Chubut"));
        provinciaModels.add(new ProvinciaModel().id(5L).nombre("Córdoba"));
        provinciaModels.add(new ProvinciaModel().id(6L).nombre("Corrientes"));
        provinciaModels.add(new ProvinciaModel().id(7L).nombre("Entre Ríos"));
        provinciaModels.add(new ProvinciaModel().id(8L).nombre("Formosa"));
        provinciaModels.add(new ProvinciaModel().id(9L).nombre("Jujuy"));
        provinciaModels.add(new ProvinciaModel().id(10L).nombre("La Pampa"));
        provinciaModels.add(new ProvinciaModel().id(11L).nombre("La Rioja"));
        provinciaModels.add(new ProvinciaModel().id(12L).nombre("Mendoza"));
        provinciaModels.add(new ProvinciaModel().id(13L).nombre("Misiones"));
        provinciaModels.add(new ProvinciaModel().id(14L).nombre("Neuquén"));
        provinciaModels.add(new ProvinciaModel().id(15L).nombre("Río Negro"));
        provinciaModels.add(new ProvinciaModel().id(16L).nombre("Salta"));
        provinciaModels.add(new ProvinciaModel().id(17L).nombre("San Juan"));
        provinciaModels.add(new ProvinciaModel().id(18L).nombre("San Luis"));
        provinciaModels.add(new ProvinciaModel().id(19L).nombre("Santa Cruz"));
        provinciaModels.add(new ProvinciaModel().id(20L).nombre("Santa Fe"));
        provinciaModels.add(new ProvinciaModel().id(21L).nombre("Santiago del Estero"));
        provinciaModels.add(new ProvinciaModel().id(22L).nombre("Tierra del Fuego, Antártida e Isla del Atlántico Sur"));
        provinciaModels.add(new ProvinciaModel().id(23L).nombre("Tucumán"));
        doReturn(provinciaModels).when(repoMunicipios).getProvincias();
    }

    @Test
    public void getProvincias() {
        ResponseEntity<ListarProvinciasResponse> provincias = provinciasApiController.listarProvincias(5L, 0L);
        assertEquals("Buenos Aires", provincias.getBody().getProvincias().get(0).getNombre());
        assertEquals(HttpStatus.OK, provincias.getStatusCode());
    }

    @Test
    public void getProvinciasFail() {
        ResponseEntity<ListarProvinciasResponse> provincias = provinciasApiController.listarProvincias(0L, 0L);
        assertEquals(HttpStatus.NOT_FOUND, provincias.getStatusCode());
    }
}

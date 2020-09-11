package tp.tacs.api.http.externalApis;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import tp.tacs.api.http.HttpClientConnector;
import tp.tacs.api.http.externalApis.models.MunicipioApi;
import tp.tacs.api.http.externalApis.models.MunicipiosApi;
import tp.tacs.api.http.externalApis.models.TopoData;
import tp.tacs.api.http.externalApis.models.TopoResult;
import tp.tacs.api.mappers.GeorefMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalApisTest {
    @InjectMocks
    private ExternalApis api;
    @Mock
    private GeorefMapper wrapper;
    @Mock
    private HttpClientConnector connector;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    MunicipiosApi response;
    List<MunicipioApi> municipios;
    MunicipioApi municipio;
    TopoResult responseTopo;
    List<TopoData> resultados;
    TopoData resultado;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);

        municipio = new MunicipioApi();
        municipio.setId("1");
        municipio.setCentroide_lat(123);
        municipio.setCentroide_lon(131);
        municipio.setNombre("Santa Cruz");

        resultado = new TopoData();
        resultado.setElevation(46);

        municipios = Arrays.asList(municipio);
        resultados = Arrays.asList(resultado);

        response = new MunicipiosApi();
        response.setMunicipios(municipios);
        responseTopo = new TopoResult();
        responseTopo.setResults(resultados);

        when(connector.get(Mockito.anyString(),Mockito.eq(MunicipiosApi.class))).thenReturn(response);
        when(connector.get(Mockito.anyString(),Mockito.eq(TopoResult.class))).thenReturn(responseTopo);
    }

    @Test
    public void getNombreOk(){
        String nombre = api.getNombre("1");
        assertEquals(nombre,response.getMunicipios().get(0).getNombre());
    }

    @Test
    public void getLatitud(){
        Double latitud = api.getLatitud("1");
        assertEquals(latitud,response.getMunicipios().get(0).getCentroide_lat().doubleValue());
    }

    @Test
    public void getLongitud(){
        Double longitud = api.getLongitud("1");
        assertEquals(longitud,response.getMunicipios().get(0).getCentroide_lon().doubleValue());
    }

    @Test
    public void getCoordenadas(){
        String coordenadas = api.getCoordenadas("1");
        assertEquals(coordenadas,response.getMunicipios().get(0).coordenadasParaTopo());
    }

    @Test
    public void getAltura(){
        Float altura = api.getAltura("1");
        assertEquals(altura,responseTopo.getResults().get(0).getElevation().floatValue());
    }

    @Test
    public void getMunicipios(){
        assertEquals(api.getMunicipios("1",1), Arrays.asList());
    }

    @Test
    public void getPathImagen(){
        assertNull(api.getPathImagen("1"));
    }
}
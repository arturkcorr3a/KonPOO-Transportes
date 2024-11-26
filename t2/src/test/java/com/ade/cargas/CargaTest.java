/*
 * package com.ade.cargas;
 * 
 * import static org.junit.jupiter.api.Assertions.assertEquals;
 * 
 * import org.junit.jupiter.api.Test;
 * import org.junit.jupiter.api.extension.ExtendWith;
 * import org.junit.jupiter.params.ParameterizedTest;
 * import org.junit.jupiter.params.provider.CsvSource;
 * import org.mockito.Mock;
 * import org.mockito.junit.jupiter.MockitoExtension;
 * 
 * import com.ade.cargas.Carga.Status;
 * import com.ade.entidades.Caminhao;
 * import com.ade.entidades.Local;
 * 
 * @ExtendWith(MockitoExtension.class)
 * public class CargaTest {
 * 
 * @Mock
 * private Caminhao caminhao;
 * 
 * @Mock
 * private Local origem;
 * 
 * @Mock
 * private Local destino;
 * 
 * @Mock
 * private TipoCarga tipoCarga;
 * 
 * @Mock
 * private Cliente cliente;
 * 
 * // Teste unitário para o método cancelar
 * 
 * @Test
 * void testCancelamento() {
 * Carga carga = new Carga(1, 100, 100, 100, null, null, null, null);
 * carga.cancelar();
 * assertEquals(Status.CANCELADA, carga.getStatus());
 * }
 * 
 * // Teste unitário para o método finalizar
 * 
 * @Test
 * void testFinalizacao() {
 * Carga carga = new Carga(1, 100, 100, 100, null, null, null, null);
 * carga.finalizar();
 * assertEquals(Status.FINALIZADA, carga.getStatus());
 * }
 * 
 * // Teste unitário para o método locar
 * 
 * @Test
 * void testLocacao() {
 * Carga carga = new Carga(1, 100, 100, 100, null, null, null, null);
 * carga.locar();
 * assertEquals(Status.LOCADA, carga.getStatus());
 * }
 * 
 * // Teste unitário para o cálculo da distância
 * 
 * @CsvSource({ "0, 0, 0, 0, 0", "0, 0, 1, 1, 1.4", "1, 1, 0, 0, 1.4",
 * "1, 1, 1, 1, 0" })
 * 
 * @ParameterizedTest
 * void testDistancia(int lat1, int long1, int lat2, int long2, double expected)
 * {
 * Local origem = new Local("cidade1", 1, "nome1", lat1, long1);
 * Local destino = new Local("cidade2", 2, "nome2", lat2, long2);
 * 
 * Carga carga = new Carga(1, 100, 100, 100, destino, origem, null, null);
 * 
 * float delta = 0.1f;
 * assertEquals(expected, carga.distancia(), delta);
 * }
 * 
 * // Teste unitário para setar caminhao
 * 
 * @Test
 * void testeCaminhaoSet() {
 * Carga carga = new Carga(1, 100, 100, 100, null, null, null, null);
 * Caminhao caminhao = new Caminhao("nome", 10, 100, 20);
 * 
 * carga.setCaminhaoDesignado(caminhao);
 * 
 * assertEquals(caminhao, carga.getCaminhaoDesignado());
 * assertEquals(Status.LOCADA, carga.getStatus());
 * }
 * 
 * @ParameterizedTest
 * void precoDistTest(int lat1, int long1, int lat2, int long2, double expected)
 * {
 * Local origem = new Local("cidade1", 1, "nome1", lat1, long1);
 * Local destino = new Local("cidade2", 2, "nome2", lat2, long2);
 * 
 * Carga carga = new Carga(1, 100, 100, 100, destino, origem, null, null);
 * carga.setCaminhaoDesignado(new Caminhao("nome", 10, 100, 20));
 * 
 * assertEquals(expected, carga.precoPorDistancia());
 * }
 * 
 * // @Test
 * /*
 * /
 * 
 * @ParameterizedTest
 * void testPrecoDistancia() {
 * Local origem = new Local("cidade1", 1, "nome1", 0, 0);
 * Local destino = new Local("cidade2", 2, "nome2", 1, 1);
 * 
 * Carga carga = new Carga(1, 100, 100, 100, destino, origem, null, null);
 * 
 * carga.setCaminhaoDesignado(new CaminhaoMock());
 * assertEquals(1.4, carga.precoPorDistancia());
 * }
 * 
 * }
 */

package com.ade.entidades;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.ade.cargas.Carga;

public class LocalTest {
    // Testes unitários para métodos básicos da classe Local

    @CsvSource({ "0, 0, 0, 0, 0", "0, 0, 1, 1, 1.4", "1, 1, 0, 0, 1.4", "1, 1, 1, 1, 0" })
    @ParameterizedTest
    void testDistancia(int lat1, int long1, int lat2, int long2, double expected) {
        Local origem = new Local("cidade1", 1, "nome1", lat1, long1);
        Local destino = new Local("cidade2", 2, "nome2", lat2, long2);

        float delta = 0.1f;
        assertEquals(expected, Local.distancia(origem, destino), delta);
    }

    @Test
    void testCsvString() {
        Local local = new Local("cidade1", 1, "nome1", 0, 0);
        assertEquals("cidade1;1;nome1;0;0", local.csvString());
    }

    @CsvSource({ "nome1, nome1, 1, 1", "nome2, nome2, 2, 2", "nome0, nome0, 0, 0", "nome10, nome10, 10, 10" })
    @ParameterizedTest
    void testGetters(String nome, String expectedNome, int cod, int expectedCod) {
        Local local = new Local("cidadeX", cod, nome, 0, 0);
        assertEquals(expectedCod, local.getCodigo());
        assertEquals(expectedNome, local.getNome());
    }

}

package com.ade.aplicacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import com.ade.entidades.Local;

public class controleTest {

    // Testes de integração para métodos envolvendo criação e consulta de Locais na
    // classe Controle
    private Controle controle;
    // private Local local1;

    @BeforeEach
    void setUp() {
        controle = new Controle();
    }

    @Test
    void testNovoLocal() {
        int tamanhoAntes = controle.getLocais().size();
        controle.novoLocal("cidade1", "nome1", 0, 0);
        assertEquals(tamanhoAntes + 1, controle.getLocais().size());
    }

    @Test
    void testOrdenaLocais() {

        controle.novoLocal("cidade1", "nome1", 0, 0); // Código 1
        controle.novoLocal("cidade2", "nome2", 1, 1); // Código 2
        controle.novoLocal("cidade3", "nome3", 2, 2); // Código 3
        controle.novoLocal("cidade4", "nome4", 2, 2); // Código 4

        controle.ordenaLocaisPorCodigo();
        List<Local> locais = controle.getLocais();

        int previousCod = Integer.MIN_VALUE;
        for (Local local : locais) {
            assertTrue(local.getCodigo() > previousCod);
            previousCod = local.getCodigo();
        }
    }

    @CsvSource({ "2, false", "3, false", "4, false", "5, true", "8, true" })
    @ParameterizedTest
    void testVerificaCodigoUnicoLocal(int codigo, boolean expected) {

        controle.novoLocal("cidade1", "nome1", 0, 0); // Código 1
        controle.novoLocal("cidade2", "nome2", 1, 1); // Código 2
        controle.novoLocal("cidade3", "nome3", 2, 2); // Código 3
        controle.novoLocal("cidade4", "nome4", 3, 3); // Código 4
        controle.novoLocal("cidade5", "nome5", 5, 5); // Código 5

        assertEquals(expected, controle.verificaCodigoUnicoLocal(codigo));
    }

    @Test
    void testCodigoUnicoVazio() {
        assertTrue(controle.verificaCodigoUnicoLocal(15));
    }

    @Test
    void testAlgumDestinoCadastrado() {
        assertFalse(controle.verificaAlgumDestinoJaCadastrado());

        controle.novoLocal("cidade1", "nome1", 0, 0); // Código 1

        assertTrue(controle.verificaAlgumDestinoJaCadastrado());
    }

    @Test
    void testLocalPorCodigoNull() {

        controle.novoLocal("cidade1", "nome1", 0, 0); // Código 1
        controle.novoLocal("cidade2", "nome2", 1, 1); // Código 2
        controle.novoLocal("cidade3", "nome3", 2, 2); // Código 3
        controle.novoLocal("cidade4", "nome4", 3, 3); // Código 4
        controle.novoLocal("cidade5", "nome5", 5, 5); // Código 5

        List<Local> locais = controle.getLocais();

    }

    @Test
    void testLocalPorCodigoNaoNull1() {

        controle.novoLocal("cidade1", "nome1", 0, 0); // Código 1
        controle.novoLocal("cidade2", "nome2", 1, 1); // Código 2
        controle.novoLocal("cidade3", "nome3", 2, 2); // Código 3
        controle.novoLocal("cidade4", "nome4", 3, 3); // Código 4
        controle.novoLocal("cidade5", "nome5", 5, 5); // Código 5

        List<Local> locais = controle.getLocais();

        assertEquals(locais.get(0), controle.localPorCodigo(1));
        assertEquals(locais.get(1), controle.localPorCodigo(2));
        assertEquals(locais.get(2), controle.localPorCodigo(3));
        assertEquals(locais.get(3), controle.localPorCodigo(4));
        assertEquals(locais.get(4), controle.localPorCodigo(5));
        assertEquals(null, controle.localPorCodigo(6));
    }
}

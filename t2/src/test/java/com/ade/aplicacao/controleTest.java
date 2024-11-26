package com.ade.aplicacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import com.ade.entidades.Local;

public class controleTest {

    // Testes de integração para métodos envolvendo criação e consulta de Locais na
    // classe Controle
    private static Controle controle;
    private Local local1;

    @BeforeAll
    static void setUpAll() {
        controle = new Controle();
    }

    @BeforeEach
    void setUp() {
        controle.resetData();
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

    @ParameterizedTest
    @CsvSource({ "0, 1", "1, 2", "2, 3", "3, 4", "4, 5" })
    void testLocalPorCodigo(int index, int codigo) {

        controle.novoLocal("cidade1", "nome1", 0, 0); // Código 1
        controle.novoLocal("cidade2", "nome2", 1, 1); // Código 2
        controle.novoLocal("cidade3", "nome3", 2, 2); // Código 3
        controle.novoLocal("cidade4", "nome4", 3, 3); // Código 4
        controle.novoLocal("cidade5", "nome5", 5, 5); // Código 5

        List<Local> locais = controle.getLocais();

        assertEquals(locais.get(index), controle.localPorCodigo(codigo));
        assertEquals(null, controle.localPorCodigo(6));
    }

    @Test
    void testInicializaLocais() {
        String csvContent = "cidade;codigo;nome;latitude;longitude\n" +
                "Cidade1;1;Nome1;10;20\n" +
                "Cidade2;2;Nome2;30;40\n";

        Path mockFilePath = Paths.get("src\\main\\java\\com\\ade\\entidades\\locais.csv");
        try {
            Files.writeString(mockFilePath, csvContent);
        } catch (IOException e) {
            fail("Erro ao criar arquivo de teste");
        }

        String resultado = controle.inicializaLocais();

        assertEquals(2, controle.getLocais().size());
        assertEquals(1, controle.getLocais().get(0).getCodigo());
        assertEquals(2, controle.getLocais().get(1).getCodigo());
        assertEquals("Nome1", controle.getLocais().get(0).getNome());
        assertEquals("Nome2", controle.getLocais().get(1).getNome());
        assertTrue(resultado.contains("Locais carregados com sucesso"));

        try {
            Files.newBufferedWriter(mockFilePath, StandardOpenOption.TRUNCATE_EXISTING).close();
        } catch (IOException e) {
            fail("Erro ao limpar arquivo de teste");
        }
    }

    @Test
    void testInicializaLocaisVazio() {
        String csvContent = "cidade;codigo;nome;latitude;longitude\n";
        Path mockFilePath = Paths.get("src/main/java/com/ade/entidades/locais.csv");
        try {
            Files.writeString(mockFilePath, csvContent);
        } catch (IOException e) {
            fail("Erro ao criar arquivo de teste");
        }

        String resultado = controle.inicializaLocais();

        assertTrue(resultado.contains("Locais carregados com sucesso"));
        assertEquals(0, controle.getLocais().size());

        try {
            Files.newBufferedWriter(mockFilePath, StandardOpenOption.TRUNCATE_EXISTING).close();
        } catch (IOException e) {
            fail("Erro ao limpar arquivo: " + e.getMessage());
        }
    }

    @Test
    void testSalvaLocais() {

        Path mockFilePath = Paths.get("src\\main\\java\\com\\ade\\entidades\\locais.csv");
        try {
            Files.createDirectories(mockFilePath.getParent());
            if (!Files.exists(mockFilePath)) {
                Files.createFile(mockFilePath);
            }
        } catch (IOException e) {
            fail("Erro ao preparar arquivo de teste: " + e.getMessage());
        }

        controle.novoLocal("Cidade1", "Nome1", 10, 20);
        controle.novoLocal("Cidade2", "Nome2", 30, 40);

        String resultado = controle.salvaLocais();

        assertTrue(resultado.contains("Locais salvos com sucesso"));

        try {
            List<String> lines = Files.readAllLines(mockFilePath);

            assertEquals(3, lines.size());
            assertEquals("cidade;codigo;nome;latitude;longitude", lines.get(0));
            assertEquals("Cidade1;1;Nome1;10;20", lines.get(1));
            assertEquals("Cidade2;2;Nome2;30;40", lines.get(2));
        } catch (IOException e) {
            fail("Erro ao ler arquivo salvo: " + e.getMessage());
        }

        try {
            Files.newBufferedWriter(mockFilePath, StandardOpenOption.TRUNCATE_EXISTING).close();
        } catch (IOException e) {
            fail("Erro ao limpar arquivo: " + e.getMessage());
        }
    }

    @Test
    void testSalvaLocaisVazio() {
        Path mockFilePath = Paths.get("src/main/java/com/ade/entidades/locais.csv");

        try {
            Files.createDirectories(mockFilePath.getParent());
            if (!Files.exists(mockFilePath)) {
                Files.createFile(mockFilePath);
            }
        } catch (IOException e) {
            fail("Erro ao preparar arquivo de teste: " + e.getMessage());
        }

        String resultado = controle.salvaLocais();

        assertTrue(resultado.contains("Locais salvos com sucesso"));
        try {
            List<String> lines = Files.readAllLines(mockFilePath);

            // Verifica que o arquivo contém apenas o cabeçalho
            assertEquals(1, lines.size());
            assertEquals("cidade;codigo;nome;latitude;longitude", lines.get(0));
        } catch (IOException e) {
            fail("Erro ao ler arquivo salvo: " + e.getMessage());
        }

        try {
            Files.newBufferedWriter(mockFilePath, StandardOpenOption.TRUNCATE_EXISTING).close();
        } catch (IOException e) {
            fail("Erro ao limpar arquivo: " + e.getMessage());
        }
    }

}

package com.ade.aplicacao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.ade.entidades.Local;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    private Controle controle;

    // Combina as inicializações em um único método
    @BeforeEach
    void setUp() {
        controle = new Controle(); // Inicializa o controle
        controle.resetData(); // Reseta os dados para evitar interferências entre testes
    }

    private List<String> loadCsv(Path path) throws Exception {
        try (Reader reader = Files.newBufferedReader(path)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            return StreamSupport.stream(records.spliterator(), false)
                    .map(record -> String.join(",", record))
                    .collect(Collectors.toList());
        }
    }

    private void copyCsv(Path source, Path target) throws Exception {
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    private void assertCsvDelta(Path csv1, Path csv2, String expectedDelta) throws Exception {
        List<String> file1Lines = Files.readAllLines(csv1);
        List<String> file2Lines = Files.readAllLines(csv2);

        // Calcula o delta
        StringBuilder actualDelta = new StringBuilder();
        int maxLines = Math.max(file1Lines.size(), file2Lines.size());

        for (int i = 0; i < maxLines; i++) {
            String line1 = i < file1Lines.size() ? file1Lines.get(i) : "<Linha ausente no CSV1>";
            String line2 = i < file2Lines.size() ? file2Lines.get(i) : "<Linha ausente no CSV2>";

            if (!line1.equals(line2)) {
                actualDelta.append(line2).append("\n");
            }
        }

        // Compara o delta gerado com o delta esperado
        String deltaResult = actualDelta.toString().trim();
        if (!deltaResult.equals(expectedDelta.trim())) {
            throw new AssertionError(
                    "Delta não corresponde:\nEsperado:\n" + expectedDelta + "\n\nObtido:\n" + deltaResult);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Porto Alegre, Centro, -30, -51",
            "São Paulo, Avenida Paulista, -23, -46"
    })
    public void testAdicionarNovoLocalESair(String cidade, String nome, String latitude, String longitude)
            throws Exception {

        // Caminhos dos arquivos
        Path originalCsv = Path.of("src/main/java/com/ade/entidades/locais.csv");
        Path copiaCsv = Path.of("src/test/java/com/ade/aplicacao/copiaLocais.csv");

        // Copia o arquivo original
        copyCsv(originalCsv, copiaCsv);

        // Simula entradas do usuário
        String simulatedInput = String.format("1\n%s\n%s\n%s\n%s\n0\n", cidade, nome, latitude, longitude);

        // Redireciona System.in para o fluxo de entrada simulado
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Redireciona System.out para capturar a saída
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            // Executa a aplicação
            App app = new App();
            app.run();

            // Verifica se as mensagens esperadas aparecem na saída
            String output = outputStream.toString();
            assertTrue(output.contains("---- CADASTRAR NOVO DESTINO ----"),
                    "O menu para cadastro de novo destino deve ser exibido.");
            assertTrue(output.contains("Destino cadastrado"),
                    "Mensagem de confirmação de cadastro do destino deve ser exibida.");

            int codigo = Local.getCodigoAUX() - 1;
            String cod = String.valueOf(codigo);
            // Carrega o arquivo atualizado
            List<String> updatedLines = loadCsv(originalCsv);

            // Delta esperado com base no local adicionado
            String expectedDelta = String.join(";", cidade, cod, nome, latitude, longitude) + "\n";

            // Verifica se o delta corresponde ao local adicionado
            assertCsvDelta(copiaCsv, originalCsv, expectedDelta);

        } finally {
            // Restaura System.in e System.out
            System.setIn(System.in);
            System.setOut(System.out);
        }
    }
}
package com.ade.aplicacao;

import com.ade.cargas.TipoCarga;
import com.ade.entidades.Cliente;
import com.ade.entidades.Local;
import java.util.Objects;
import java.util.Scanner;

public class App {
    Controle controle = new Controle();
    Scanner in = new Scanner(System.in);

    public void run() {
        int menu;
        do {
            System.out.println("MENU" +

                    "\n\nESCOLHA UMA OPÇÃO:" +

                    "\n\n[1]CADASTRAR NOVO DESTINO" +
                    "\n[2]CADASTRAR NOVO CAMINHAO" +
                    "\n[3]CADASTRAR NOVO CLIENTE" +
                    "\n[4]CADASTRAR NOVA TIPO DE CARGA" +
                    "\n[5]CADASTRAR NOVA CARGA" +
                    "\n[6]CONSULTAR TODAS CARGAS" +
                    "\n[7]ALTERAR SITUAÇÃO DE UMA CARGA" +
                    "\n[8]FRETAR CARGAS" +

                    "\n\n[0]SAIR");

            menu = in.nextInt();
            in.nextLine();

            switch (menu) {
                case 0 -> controle.salvaDados();
                case 1 -> novoDestino();
                case 2 -> novoCaminhao();
                case 3 -> novoCliente();
                case 4 -> novoTipoCarga();
                case 5 -> novaCarga();
                case 6 -> consultarTodasCargas();
                case 7 -> alterarCarga();
                case 8 -> fretarCargas();
                default -> System.out.println("Opção Inválida ou não implementada");
            }

        } while (menu != 0);
    }

    public void fretarCargas() {
        System.out.println(controle.fretar());
    }

    public void alterarCarga() {
        int codigo = 0;

        boolean wenttocatch = false;
        do {
            System.out.println("---- CADASTRAR NOVA CARGA ----\n\nInforme o código da carga: ");
            if (in.hasNextInt()) {
                codigo = in.nextInt();
                wenttocatch = true;
            } else {
                in.nextLine();
                System.out.println("Erro: Insira um valor válido");
            }
        } while (!wenttocatch);

        String s = controle.consultaCarga(codigo);
        System.out.println(s);
        if (Objects.equals(s, "ERR0: Carga não encontrada"))
            return;

        System.out.println("Informe a nova situação da carga: " +
                "\n[1]CANCELADA" +
                "\n[2]FINALIZADA" +
                "\n[3]LOCADA");

        int status = in.nextInt();
        System.out.println(controle.alteraStatus(codigo, status));

    }

    public void consultarTodasCargas() {
        System.out.println(controle.consultaTodasCargas());
    }

    public void novaCarga() {
        int codigo = 0;
        boolean wenttocatch = false;
        do {
            System.out.println("---- CADASTRAR NOVA CARGA ----\n\nInsira o código da carga: ");
            if (in.hasNextInt()) {
                codigo = in.nextInt();
                wenttocatch = true;
            } else {
                in.nextLine();
                System.out.println("Erro: Insira um valor válido");
            }
        } while (!wenttocatch);

        if (!controle.verificaCodigoUnicoCarga(codigo)) {
            System.out.println("Número Já Cadastrado, retornando ao menu.");
            return;
        }

        wenttocatch = false;
        int peso = 0;
        do {
            System.out.println("Insira o peso em toneladas: ");
            if (in.hasNextInt()) {
                peso = in.nextInt();
                wenttocatch = true;
            } else {
                in.nextLine();
                System.out.println("Erro: Insira um valor válido");
            }
        } while (!wenttocatch);

        int tempoMaximo = 0;
        wenttocatch = false;
        do {
            System.out.println("Insira o tempo máximo de entrega em dias: ");
            if (in.hasNextInt()) {
                tempoMaximo = in.nextInt();
                wenttocatch = true;
            } else {
                in.nextLine();
                System.out.println("Erro: Insira um valor válido");
            }
        } while (!wenttocatch);

        System.out.println("Insira o o valor declarado da carga em reais: ");
        double valorDeclarado = in.nextDouble();

        controle.ordenaLocaisPorCodigo();
        System.out.println("\n\n" + controle.exibirLocaisPorCodigo());

        System.out.println("\n\nInforme o código do local de destino: ");
        Local destino = controle.localPorCodigo(in.nextInt());
        System.out.println("\n\nInforme o código do local de origem: ");
        Local origem = controle.localPorCodigo(in.nextInt());

        System.out.println("Informe o código do cliente: ");
        Cliente cliente = controle.clientePorCodigo(in.nextInt());

        System.out.println("Informe o número do tipo de Carga: ");
        TipoCarga tipoCarga = controle.tipoPorNumero(in.nextInt());

        controle.novaCarga(codigo, peso, tempoMaximo, valorDeclarado, destino, origem, cliente, tipoCarga);
        System.out.println("Carga cadastrada");
    }

    public void novoTipoCarga() {
        int codigo = 0;
        boolean wenttocatch = false;
        do {
            System.out.println("---- CADASTRAR NOVO TIPO DE CARGA ----\n\nInsira o número do novo tipo de carga: ");
            if (in.hasNextInt()) {
                codigo = in.nextInt();
                wenttocatch = true;
            } else {
                in.nextLine();
                System.out.println("Erro: Insira um valor válido");
            }
        } while (!wenttocatch);

        if (!controle.verificaNumeroUnicoTipoCarga(codigo)) {
            System.out.println("Número Já Cadastrado, retornando ao menu.");
            return;
        }
        in.nextLine();
        System.out.println("Informe a descrição: ");
        String descricao = in.nextLine();

        System.out.println("Escolha uma opção: " +
                "\n[1]PERECÍVEL" +
                "\n[2]DURÁVEL");
        if (in.nextInt() == 1) {
            in.nextLine();
            System.out.println("Informe a origem: ");
            String origem = in.nextLine();

            System.out.println("Informe a validade em dias: ");
            int validade = in.nextInt();
            controle.novoPerecivel(descricao, codigo, origem, validade);
            System.out.println("Cadastrado com sucesso");
        } else {
            in.nextLine();
            System.out.println("Informe o setor: ");
            String setor = in.nextLine();

            System.out.println("Informe o material principal: ");
            String material = in.nextLine();
            controle.novoDuravel(descricao, codigo, material, setor);
            System.out.println("Cadastrado com sucesso");

        }
    }

    public void novoCliente() {
        int codigo = 0;
        boolean wenttocatch = false;
        do {
            System.out.println("---- CADASTRAR NOVO CLIENTE ----\n\nInsira o código do cliente: ");
            if (in.hasNextInt()) {
                codigo = in.nextInt();
                wenttocatch = true;
            } else {
                in.nextLine();
                System.out.println("Erro: Insira um valor válido");
            }
        } while (!wenttocatch);

        if (!controle.verificaCodigoUnicoCliente(codigo)) {
            System.out.println("ERRO: CÓDIGO JÁ CADASTRADO");
            return;
        }
        in.nextLine();
        System.out.println("Insira o nome do cliente: ");
        String nome = in.nextLine();

        System.out.println("Insira o telefone do cliente: ");
        String telefone = in.nextLine();

        controle.novoCliente(codigo, nome, telefone);
        System.out.println("Cliente cadastrado");
    }

    public void novoDestino() {

        System.out.println("---- CADASTRAR NOVO DESTINO ----\n\nInsira o nome da cidade: ");
        String cidade = in.nextLine();

        System.out.println("\nInsira um nome para o destino: ");
        String nome = in.nextLine();

        int latitude = 0;
        boolean wenttocatch = false;
        do {
            System.out.println("\nInsira a latitude: ");
            if (in.hasNextInt()) {
                latitude = in.nextInt();
                wenttocatch = true;
            } else {
                in.nextLine();
                System.out.println("Erro: Insira um valor válido");
            }
        } while (!wenttocatch);

        int longitude = 0;
        wenttocatch = false;
        do {
            System.out.println("\nInsira a longitude: ");
            if (in.hasNextInt()) {
                longitude = in.nextInt();
                wenttocatch = true;
            } else {
                in.nextLine();
                System.out.println("Erro: Insira um valor válido");
            }
        } while (!wenttocatch);

        controle.novoLocal(cidade, nome, latitude, longitude);
        System.out.println("Destino cadastrado");
    }

    public void novoCaminhao() {
        System.out.println("---- CADASTRAR NOVO CAMINHÃO ----\n\nInsira o nome do caminhão: ");
        String nome = in.nextLine();

        if ((!controle.verificaNomeUnicoCaminhao(nome))) {
            int menu1;
            do {
                System.out.println(
                        "ERRO: CAMINHÃO JÁ CADASTRADO\nEscolha uma opção: \n\n[1] Continuar Cadastrando Outro Caminhão\n[2] Sair");
                menu1 = in.nextInt();
                switch (menu1) {
                    case 1 -> {
                        System.out.println("Insira o nome do caminhão: ");
                        nome = in.nextLine();
                    }
                    case 2 -> System.out.println("Cadastro cancelado.");
                    default -> {
                        System.out.println("Opção inválida, cadastro cancelado.");
                        menu1 = 2;
                    }
                }
            } while (!controle.verificaNomeUnicoCaminhao(nome) || menu1 != 1);
        }
        double autonomia = 0;
        boolean wenttocatch = false;
        do {
            System.out.println("\nInsira a autonomia do caminhão (Km/L): ");
            if (in.hasNextDouble()) {
                autonomia = in.nextDouble();
                wenttocatch = true;
            } else {
                in.nextLine();
                System.out.println("Erro: Insira um valor válido");
            }
        } while (!wenttocatch);

        double velocidade = 0;
        wenttocatch = false;
        do {
            System.out.println("\nInsira a velocidade do caminhão: ");
            if (in.hasNextDouble()) {
                velocidade = in.nextDouble();
                wenttocatch = true;
            } else {
                in.nextLine();
                System.out.println("Erro: Insira um valor válido");
            }
        } while (!wenttocatch);

        wenttocatch = false;
        int capacidade = 0;
        do {
            System.out.println("\nInsira a capacidade em toneladas do caminhao: ");
            if (in.hasNextInt()) {
                capacidade = in.nextInt();
                wenttocatch = true;
            } else {
                in.nextLine();
                System.out.println("Erro: Insira um valor válido");
            }
        } while (!wenttocatch);

        controle.novoCaminhao(nome, autonomia, velocidade, capacidade);
        System.out.println("Caminhão cadastrado na frota");
    }

}

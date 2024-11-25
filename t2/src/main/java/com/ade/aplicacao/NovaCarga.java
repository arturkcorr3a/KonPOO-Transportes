package com.ade.aplicacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NovaCarga extends JFrame {

    private JTextField campoCodigo;
    private JTextField campoPeso;
    private JTextField campoTempo;
    private JTextField campoValor;
    private JTextField campoOrigem;
    private JTextField campoDestino;
    private JTextField campoCliente;
    private JTextField campoCarga;
    private JButton cadastrar;
    private JLabel labelCodigo;
    private JLabel labelPeso;
    private JPanel novaCarga;
    private JLabel labelValor;
    private JLabel labelCliente;
    private JLabel labelTempo;
    private JLabel labelOrigem;
    private JLabel labelDestino;
    private JLabel labelTipo;
    private JLabel title;

    public NovaCarga(Controle controle) {
        setContentPane(novaCarga);
        setTitle("NOVA CARGA");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        cadastrar.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(campoCodigo.getText());
                    int peso = Integer.parseInt(campoPeso.getText());
                    int tempoMaximo = Integer.parseInt(campoTempo.getText());
                    double valorDeclarado = Double.parseDouble(campoValor.getText());
                    int codigoDestino = Integer.parseInt(campoDestino.getText());
                    if (controle.verificaCodigoUnicoLocal(codigoDestino)) {
                        JOptionPane.showMessageDialog(null, "ERRO: CÓDIGO DE DESTINO NÃO EXISTE");
                        return;
                    }
                    int codigoOrigem = Integer.parseInt(campoOrigem.getText());
                    if (controle.verificaCodigoUnicoLocal(codigoOrigem)) {
                        JOptionPane.showMessageDialog(null, "ERRO: CÓDIGO DE ORIGEM NÃO EXISTE");
                        return;
                    }

                    int codigoCliente = Integer.parseInt(campoCliente.getText());
                    if (controle.verificaCodigoUnicoCliente(codigoCliente)) {
                        JOptionPane.showMessageDialog(null, "ERRO: CÓDIGO DE CLIENTE NÃO EXISTE");
                        return;
                    }

                    int codigoTipoCarga = Integer.parseInt(campoCarga.getText());
                    if (controle.verificaNumeroUnicoTipoCarga(codigoTipoCarga)) {
                        JOptionPane.showMessageDialog(null, "ERRO: NÚMERO DO TIPO DA CARGA NÃO EXISTE");
                        return;
                    }

                    if (!controle.verificaCodigoUnicoCarga(codigo)) {
                        JOptionPane.showMessageDialog(null, "ERRO: CÓDIGO DA CARGA DEVE SER ÚNICO");
                        return;
                    }
                    controle.novaCarga(codigo, peso, tempoMaximo, valorDeclarado,
                            controle.localPorCodigo(codigoDestino),
                            controle.localPorCodigo(codigoOrigem), controle.clientePorCodigo(codigoCliente),
                            controle.tipoPorNumero(codigoTipoCarga));

                    JOptionPane.showMessageDialog(null, "Carga cadastrado com sucesso");
                    dispose();

                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "ERRO: DADOS INSERIDOS INCORRETAMENTE");
                }

            }
        });
    }

}

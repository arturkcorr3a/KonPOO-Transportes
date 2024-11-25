package com.ade.aplicacao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NovoTipo extends JFrame {

    private JPanel novoTipo;
    private JTextField campoNumero;
    private JTextField campoDescricao;
    private JRadioButton radioPerecivel;
    private JRadioButton radioDuravel;
    private JTextField campoOrigem;
    private JTextField campoMaterial;
    private JLabel labelNumero;
    private JLabel labelDescricao;
    private JTextField campoValidade;
    private JTextField campoSetor;
    private JLabel labelOrigem;
    private JLabel labelMaterial;
    private JLabel labelValidade;
    private JLabel labelSetor;
    private JButton cadastrar;
    private JLabel title;

    public NovoTipo(Controle controle) {
        setContentPane(novoTipo);
        setTitle("NOVO TIPO DE CARGA");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        ButtonGroup tipoDeCarga = new ButtonGroup();
        tipoDeCarga.add(radioDuravel);
        tipoDeCarga.add(radioPerecivel);
        radioPerecivel.setSelected(true);
        radioPerecivel.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                labelOrigem.setEnabled(true);
                labelValidade.setEnabled(true);
                labelMaterial.setEnabled(false);
                labelSetor.setEnabled(false);

                campoOrigem.setEnabled(true);
                campoValidade.setEnabled(true);
                campoMaterial.setEnabled(false);
                campoSetor.setEnabled(false);

                campoOrigem.setEditable(true);
                campoValidade.setEditable(true);
                campoMaterial.setEditable(false);
                campoSetor.setEditable(false);
            }
        });
        radioDuravel.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                labelOrigem.setEnabled(false);
                labelValidade.setEnabled(false);
                labelMaterial.setEnabled(true);
                labelSetor.setEnabled(true);

                campoOrigem.setEnabled(false);
                campoValidade.setEnabled(false);
                campoMaterial.setEnabled(true);
                campoSetor.setEnabled(true);

                campoOrigem.setEditable(false);
                campoValidade.setEditable(false);
                campoMaterial.setEditable(true);
                campoSetor.setEditable(true);
            }
        });
        cadastrar.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String descricao = campoDescricao.getText();
                    int numero = Integer.parseInt(campoNumero.getText());

                    if (radioDuravel.isSelected()) {
                        String material = campoMaterial.getText();
                        String setor = campoSetor.getText();

                        if (controle.verificaNumeroUnicoTipoCarga(numero)) {
                            controle.novoDuravel(descricao, numero, material, setor);
                            JOptionPane.showMessageDialog(null, "Carga durável cadastrada com sucesso!");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "ERRO: Numero já existente.");
                        }
                    } else if (radioPerecivel.isSelected()) {
                        String origem = campoOrigem.getText();
                        int validade = Integer.parseInt(campoValidade.getText());

                        if (controle.verificaNumeroUnicoTipoCarga(numero)) {
                            controle.novoPerecivel(descricao, numero, origem, validade);
                            JOptionPane.showMessageDialog(null, "Carga perecível cadastrada com sucesso!");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "ERRO: Numero já existente.");
                        }
                    }

                    dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "ERRO: Há campos invalidos.");
                }
            }
        });
    }
}

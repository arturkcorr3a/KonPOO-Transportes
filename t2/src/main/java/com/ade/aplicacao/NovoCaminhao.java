package com.ade.aplicacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NovoCaminhao extends JFrame {
    private JLabel labelNovoCaminhao;
    private JButton cadastrar;
    private JTextField campoCapacidade;
    private JTextField campoVelocidade;
    private JTextField campoAutonomia;
    private JTextField campoNome;
    private JPanel novoCaminhao;
    private JLabel labelNome;
    private JLabel labelAutonomia;
    private JLabel labelVelocidade;
    private JLabel labelCapacidade;

    public NovoCaminhao(Controle controle) {
        setContentPane(novoCaminhao);
        setTitle("NOVO CAMINHAO");
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
                    String nome = campoNome.getText();
                    int capacidade = Integer.parseInt(campoCapacidade.getText());
                    double velocidade = Double.parseDouble(campoVelocidade.getText());
                    double autonomia = Double.parseDouble(campoAutonomia.getText());

                    if (controle.verificaNomeUnicoCaminhao(nome)) {
                        controle.novoCaminhao(nome, autonomia, velocidade, capacidade);
                        JOptionPane.showMessageDialog(null, "Caminhao adicionado com sucesso!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "ERRO: Nome já existente.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "ERRO: Há campos invalidos.");
                }
            }
        });
    }

}

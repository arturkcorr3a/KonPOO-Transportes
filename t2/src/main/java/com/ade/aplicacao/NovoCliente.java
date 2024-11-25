package com.ade.aplicacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NovoCliente extends JFrame {
    private JLabel title;
    private JButton cadastrar;
    private JTextField campoNome;
    private JTextField campoCodigo;
    private JTextField campoTelefone;
    private JLabel labelNome;
    private JLabel labelCodigo;
    private JLabel labelTelefone;
    private JPanel novoCliente;

    public NovoCliente(Controle controle) {
        setContentPane(novoCliente);
        setTitle("NOVO CLIENTE");
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
                    String telefone = campoTelefone.getText();
                    int codigo = Integer.parseInt(campoCodigo.getText());
                    if (!controle.verificaCodigoUnicoCliente(codigo)) {
                        JOptionPane.showMessageDialog(null, "ERRO: CÓDIGO DO CLIENTE DEVE SER ÚNICO");
                        return;
                    }
                    controle.novoCliente(codigo, nome, telefone);
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso");
                    dispose();
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "ERRO: DADOS INSERIDOS INCORRETAMENTE");
                }

            }
        });
    }

}

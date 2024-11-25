package com.ade.aplicacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {

    private JButton novoCliente;
    private JPanel mainMenu;
    private JButton novoCaminhao;
    private JButton novoLocal;
    private JButton novoTipo;

    private JLabel mainTitle;
    private JButton consultarCargas;
    private JButton novaCarga;
    private JButton finalizarCarga;
    private JButton fretarCargas;
    private JButton finalizar;

    public MenuPrincipal(Controle controle) {
        setContentPane(mainMenu);
        setTitle("MENU PRINCIPAL");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        String iniciou = controle.inicializaDados();
        JOptionPane.showMessageDialog(null, iniciou);

        // FEITO
        novoCaminhao.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new NovoCaminhao(controle);
            }
        });
        // FEITO
        novoCliente.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new NovoCliente(controle);
            }
        });
        // FEITO
        finalizarCarga.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer
                            .parseInt(JOptionPane.showInputDialog("Insira o código da carga a ser finalizada"));
                    JOptionPane.showMessageDialog(null, controle.finalizarEntrega(codigo));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Código inválido");
                }
            }
        });
        // FEITO
        novoLocal.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new NovoLocal(controle);
            }
        });
        // FEITO
        consultarCargas.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, controle.consultaTodasCargas());
            }
        });
        // FEITO
        fretarCargas.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, controle.fretar());
            }
        });

        novaCarga.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new NovaCarga(controle);
            }
        });
        novoTipo.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new NovoTipo(controle);
            }
        });
        finalizar.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, controle.salvaDados());
                JOptionPane.showMessageDialog(null, "Obrigado por utilizar o sistema!");
                System.exit(0);
            }
        });
    }

}

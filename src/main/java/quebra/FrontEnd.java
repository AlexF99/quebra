package quebra;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrontEnd extends JFrame implements ActionListener {

    private DefaultTableModel modelCursadas = new DefaultTableModel();

    ListaCursadas listaCursadas = ListaCursadas.getInstance();
    ListaOfertadas listaOfertadas = ListaOfertadas.getInstance();

    public FrontEnd() {
        // JFrame f = new JFrame();

        JTable cursadasView = cursadas();
        JLabel aprovacao = aprovacaoUltimo();

        this.getContentPane().add(cursadasView);
        this.getContentPane().add(aprovacao);

        this.setSize(600, 450);
    }

    private JTable cursadas() {
        JTable cursadasTable = new JTable(modelCursadas);
        modelCursadas.addColumn("codigo");
        modelCursadas.addColumn("disciplina");
        modelCursadas.addColumn("periodo");
        modelCursadas.addColumn("media");
        modelCursadas.addColumn("situação");

        for (Cursada cursada : listaCursadas.lista) {
            if (cursada.getSituacao() != 10) {
                modelCursadas.addRow(
                        new Object[] { cursada.getCodDisciplina(),
                                cursada.getNomeDisciplina(),
                                cursada.getPeriodo().toString(),
                                cursada.getMedia().toString(),
                                cursada.getStrSituacao() });
            }
        }

        cursadasTable.setBounds(0, 0, 600, 200);
        return cursadasTable;
    }

    private JLabel aprovacaoUltimo() {
        JLabel label1 = new JLabel("aprovacao");
        double ultimasAprovadas = 0;
        double ultimasCursadas = 0;
        for (Cursada cursada : listaCursadas.lista) {
            if (cursada.getPeriodo() == 3) {
                ultimasCursadas++;
                if (cursada.getSituacao() == 1)
                    ultimasAprovadas++;
            }
        }
        double porcentagem = (double) (ultimasAprovadas / ultimasCursadas) * 100;
        if (ultimasCursadas != 0) {
            label1.setText("Aprovação no ultimo periodo: " + porcentagem + "%");
        }
        return label1;
    }

    public void actionPerformed(ActionEvent e) {
        // if (e.getSource() == B1) {
        // String nome = T1.getText();
        // }
    }

}
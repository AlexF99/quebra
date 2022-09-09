package quebra;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrontEnd extends JFrame implements ActionListener {

    private DefaultTableModel modelCursadas = new DefaultTableModel();
    private DefaultTableModel modelFaltantes = new DefaultTableModel();

    ListaCursadas listaCursadas = ListaCursadas.getInstance();
    ListaOfertadas listaOfertadas = ListaOfertadas.getInstance();

    public FrontEnd() {
        JTable cursadasView = cursadas();
        JLabel aprovacao = extraInfo();
        JTable faltantes = faltantes();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(cursadasView);
        panel.add(aprovacao);
        panel.add(faltantes);

        this.add(panel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("pedido de quebra");
        this.pack();
        this.setVisible(true);

        this.setSize(600, 600);
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

        cursadasTable.setBounds(100, 100, 500, 200);
        return cursadasTable;
    }

    private JLabel extraInfo() {
        JLabel label1 = new JLabel("aprovacao");
        double ultimasAprovadas = 0;
        double ultimasCursadas = 0;
        int reprovacoesFalta = 0;

        for (Cursada cursada : listaCursadas.lista) {
            if (cursada.getPeriodo() == 3) {
                ultimasCursadas++;
                if (cursada.getSituacao() == 1)
                    ultimasAprovadas++;
            }

            if (cursada.getSituacao() == 3)
                reprovacoesFalta++;
        }
        double porcentagem = (double) (ultimasAprovadas / ultimasCursadas) * 100;
        if (ultimasCursadas != 0) {
            label1.setText("Aprovação no ultimo periodo: " + porcentagem + "%" + " Reprovações por falta: " + reprovacoesFalta );
        }
        return label1;
    }

    private JTable faltantes() {
        JTable faltantesTable = new JTable(modelFaltantes);
        modelFaltantes.addColumn("codigo");
        modelFaltantes.addColumn("disciplina");

        // pega barreira
        List<String> barreira = new ArrayList<>();
        for (Ofertada ofertada : listaOfertadas.lista) {
            if (ofertada.getperiodoIdeal() != 0 && ofertada.getperiodoIdeal() < 4)
                barreira.add(ofertada.getCodDisciplina());
        }

        // pega aprovadas
        List<String> aprovadas = new ArrayList<>();
        for (Cursada cursada : listaCursadas.lista) {
            if (cursada.getSituacao() == 1)
                aprovadas.add(cursada.getCodDisciplina());
        }

        // pega faltantes
        List<String> faltantes = new ArrayList<>();
        for (String b : barreira)
            if (!aprovadas.contains(b))
                faltantes.add(b);

        for (Ofertada ofertada : listaOfertadas.lista) {
            if (faltantes.contains(ofertada.getCodDisciplina())) {
                modelFaltantes.addRow(
                        new Object[] { ofertada.getCodDisciplina(),
                                ofertada.getNomeDisciplina() });
            }
        }

        faltantesTable.setBounds(100, 100, 300, 180);
        return faltantesTable;
    }

    public void actionPerformed(ActionEvent e) {
        // if (e.getSource() == B1) {
        // String nome = T1.getText();
        // }
    }

}
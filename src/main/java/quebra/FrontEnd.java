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
        JFrame f = new JFrame();

        JScrollPane cursadasView = cursadas();
        f.add(cursadasView);
        f.setSize(600, 450);
        f.setVisible(true);
    }

    private JScrollPane cursadas() {

        List<String[]> data = new ArrayList<String[]>();

        for (Cursada cursada : listaCursadas.lista) {
            String disciplina[] = new String[3];
            disciplina[0] = cursada.getCodCurso();
            disciplina[1] = cursada.getNomeDisciplina();
            disciplina[2] = cursada.getMedia().toString();
            data.add(disciplina);
        }

        JTable cursadasTable = new JTable(modelCursadas);
        modelCursadas.addColumn("codigo");
        modelCursadas.addColumn("disciplina");
        modelCursadas.addColumn("periodo");
        modelCursadas.addColumn("media");
        modelCursadas.addColumn("situação");

        for (Cursada cursada : listaCursadas.lista) {
            modelCursadas.addRow(
                    new Object[] { cursada.getCodDisciplina(),
                            cursada.getNomeDisciplina(),
                            cursada.getPeriodo().toString(),
                            cursada.getMedia().toString(),
                            cursada.getStrSituacao() });
        }

        cursadasTable.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(cursadasTable);
        return sp;
    }

    public void actionPerformed(ActionEvent e) {
        // if (e.getSource() == B1) {
        // String nome = T1.getText();
        // }
    }

}
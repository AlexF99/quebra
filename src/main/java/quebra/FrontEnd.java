package quebra;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class FrontEnd extends JFrame implements ActionListener {

    private DefaultTableModel modelCursadas = new DefaultTableModel();
    private DefaultTableModel modelFaltantes = new DefaultTableModel();
    private DefaultTableModel modelOfertadas = new DefaultTableModel();
    private DefaultTableModel modelSelecionadas = new DefaultTableModel();
    private String desempenho = "";
    ListaCursadas listaCursadas = ListaCursadas.getInstance();
    ListaOfertadas listaOfertadas = ListaOfertadas.getInstance();

    public FrontEnd() {
        JTable cursadas = cursadas();
        JLabel aprovacao = extraInfo();
        JTable faltantes = faltantes();
        final JTable selecionadas = selecionadas();
        final JTable ofertadas = ofertadas();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, 1));
        JButton button = new JButton("Enviar");
        button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        JScrollPane aprovacaoPane = new JScrollPane(aprovacao);
        aprovacaoPane.setBorder(BorderFactory.createTitledBorder("Aprovação"));
        JScrollPane cursadasPane = new JScrollPane(cursadas);
        cursadasPane.setBorder(BorderFactory.createTitledBorder("Cursadas"));
        JScrollPane ofertadasPane = new JScrollPane(ofertadas);
        ofertadasPane.setBorder(BorderFactory.createTitledBorder("Ofertadas"));
        JScrollPane faltantesPane = new JScrollPane(faltantes);
        faltantesPane.setBorder(BorderFactory.createTitledBorder("Faltantes"));
        JScrollPane selecionadasPane = new JScrollPane(selecionadas);
        selecionadasPane.setBorder(BorderFactory.createTitledBorder("Selecionadas"));
        panel.add(aprovacaoPane);
        panel.add(cursadasPane);
        panel.add(faltantesPane);
        panel.add(ofertadasPane);
        panel.add(selecionadasPane);
        panel.add(button);
        this.add(panel, BorderLayout.CENTER);
    
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("pedido de quebra");
        this.pack();
        this.setVisible(true);

        final ListSelectionModel ofertadasSelectionModel = ofertadas.getSelectionModel();
        ofertadasSelectionModel.addListSelectionListener(new ListSelectionListener() {
            int clickCount = 0;
            boolean exists = false;

            public void valueChanged(ListSelectionEvent event) {
                // verifica se já existe na tabela
                for (int i = 0; i < modelSelecionadas.getDataVector().size(); i++) {
                    if (ofertadas.getValueAt(ofertadas.getSelectedRow(), 0).toString() == modelSelecionadas
                            .getDataVector().get(i).get(0)) {
                        exists = true;
                    }
                }
                // adiciona o elemento clicado só se não existir na tabela e o clickCount for
                // par (para corrigir o bug da tabela que clica duas vezes)
                if (!ofertadasSelectionModel.isSelectionEmpty() && clickCount % 2 == 0 && !exists) {
                    modelSelecionadas.addRow(
                            new Object[] { ofertadas.getValueAt(ofertadas.getSelectedRow(), 0).toString(),
                            });
                }
                exists = false;
                clickCount++;
            }
        });

        final ListSelectionModel selecionadasSelectionModel = selecionadas.getSelectionModel();
        selecionadasSelectionModel.addListSelectionListener(new ListSelectionListener() {
            int clickCount = 0;

            public void valueChanged(ListSelectionEvent event) {
                // remove o elemento clicado da tabela quando o clickCount for
                // par (para corrigir o bug da tabela que clica duas vezes)
                if (!selecionadasSelectionModel.isSelectionEmpty() && clickCount % 2 == 0) {
                    modelSelecionadas.removeRow(selecionadas.getSelectedRow());
                }
                clickCount++;
            }
        });

        this.setSize(700, 800);
    }

    private JTable selecionadas() {
        JTable selecionadasTable = new JTable(modelSelecionadas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // habilita o sort ao clicar no header
        selecionadasTable.setAutoCreateRowSorter(true);
        modelSelecionadas.addColumn("codigo");
        selecionadasTable.setBounds(100, 100, 500, 500);
        return selecionadasTable;
    }

    private JTable cursadas() {
        JTable cursadasTable = new JTable(modelCursadas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // habilita o sort ao clicar no header
        cursadasTable.setAutoCreateRowSorter(true);
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

        cursadasTable.setBounds(100, 100, 500, 500);
        return cursadasTable;
    }

    private JTable ofertadas() {
        JTable ofertadasTable = new JTable(modelOfertadas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelOfertadas.addColumn("codigo");
        modelOfertadas.addColumn("nome");
        modelOfertadas.addColumn("periodo ideal");
        // habilita o sort ao clicar no header
        ofertadasTable.setAutoCreateRowSorter(true);
        Boolean exists;
        // percorre a lista de ofertadas e de cursadas e adiciona na tabela de ofertadas
        // apenas as que forem acima do terceiro período e que não foram cursadas
        for (Ofertada ofertada : listaOfertadas.lista) {
            exists = false;
            for (Cursada cursada : listaCursadas.lista) {
                if (ofertada.getCodDisciplina().equals(cursada.getCodDisciplina())
                        && cursada.getStrSituacao().equals("aprovado")) {
                    exists = true;
                }
            }
            if (!exists && ofertada.getperiodoIdeal() > 3) {
                modelOfertadas.addRow(
                        new Object[] { ofertada.getCodDisciplina(),
                                ofertada.getNomeDisciplina(),
                                ofertada.getperiodoIdeal(),
                        });
            }
        }

        ofertadasTable.setBounds(100, 100, 500, 500);
        return ofertadasTable;
    }

    private JLabel extraInfo() {
        JLabel label1 = new JLabel("aprovacao");
        double ultimasAprovadas = 0;
        double ultimasCursadas = 0;
        double ira = 0;
        double soma = 0;
        double chTotal = 0;
        int reprovacoesFalta = 0;

        for (Cursada cursada : listaCursadas.lista) {
            soma = soma + cursada.getMedia() * cursada.getCargaHoraria();
            chTotal += cursada.getCargaHoraria();

            if (cursada.getPeriodo() == 3) {
                ultimasCursadas++;
                if (cursada.getSituacao() == 1)
                    ultimasAprovadas++;
            }

            if (cursada.getSituacao() == 3)
                reprovacoesFalta++;
        }
        ira = soma / (chTotal * 100);
        double fracao = ultimasAprovadas / ultimasCursadas;
        String porcentagem = (String) String.format("%.2f", ((fracao) * 100));

        if (fracao > (2.0 / 3.0))
            desempenho = "Bom";
        else if (fracao <= 2.0 / 3.0 && fracao > 1.0 / 2.0)
            desempenho = "Médio";
        else if (fracao < 1.0 / 2.0)
            desempenho = "Ruim";

        if (ultimasCursadas != 0) {
            label1.setText("Aprovação no ultimo periodo: " + porcentagem + "% " + " \nReprovações por falta: "
                    + reprovacoesFalta + " \nIRA: " + ira + " Desempenho: " + desempenho);
        }
        return label1;
    }

    private JTable faltantes() {
        JTable faltantesTable = new JTable(modelFaltantes) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // habilita o sort ao clicar no header
        faltantesTable.setAutoCreateRowSorter(true);
        modelFaltantes.addColumn("codigo");
        modelFaltantes.addColumn("disciplina");
        modelFaltantes.addColumn("periodo");

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
                                ofertada.getNomeDisciplina(), ofertada.getperiodoIdeal() });
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
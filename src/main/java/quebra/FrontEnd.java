package quebra;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.opencsv.CSVReader;

public class FrontEnd extends JFrame implements ActionListener {
    private DefaultTableModel modelCursadas = new DefaultTableModel();
    private DefaultTableModel modelFaltantes = new DefaultTableModel();
    private DefaultTableModel modelOfertadas = new DefaultTableModel();
    private DefaultTableModel modelSelecionadas = new DefaultTableModel();

    private JLabel status = new JLabel();
    private String desempenho = "";
    private double ira = 0.0;
    ListaCursadas listaCursadas = ListaCursadas.getInstance();
    ListaOfertadas listaOfertadas = ListaOfertadas.getInstance();
    Aluno aluno = new Aluno();

    public FrontEnd() {
        JTable cursadas = cursadas();
        final JLabel aprovacao = new JLabel("aprovacao");
        JTable faltantes = faltantes();

        final JTable selecionadas = selecionadas();
        final JTable ofertadas = ofertadas();
        JPanel panel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel importsPanel = new JPanel();
        importsPanel.setLayout(new BoxLayout(importsPanel, 0));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));
        panel.setLayout(new BoxLayout(panel, 1));
        final JButton buttonImportarHist = new JButton("Importar histórico");
        final JButton buttonImportarGrade = new JButton("Importar grade");
        final JButton buttonEnviar = new JButton("Enviar");
        JButton buttonSalvar = new JButton("Salvar");
        buttonEnviar.setEnabled(verificaRegras());
        // ação do botão enviar - Salva as disciplinas em um CSV
        buttonEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    FileWriter csv = new FileWriter(new File("selecionadasEnvio.csv"));
                    for (int i = 0; i < modelSelecionadas.getColumnCount(); i++) {
                        csv.write(modelSelecionadas.getColumnName(i) + ",");
                    }
                    csv.write("\n");
                    for (int i = 0; i < modelSelecionadas.getRowCount(); i++) {
                        for (int j = 0; j < modelSelecionadas.getColumnCount(); j++) {
                            csv.write(modelSelecionadas.getValueAt(i, j).toString() + ",");
                        }
                        csv.write("\n");
                    }
                    csv.close();
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        });
        buttonSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    FileWriter csv = new FileWriter(new File("selecionadasSalvo.csv"));
                    for (int i = 0; i < modelSelecionadas.getColumnCount(); i++) {
                        csv.write(modelSelecionadas.getColumnName(i) + ",");
                    }
                    csv.write("\n");
                    for (int i = 0; i < modelSelecionadas.getRowCount(); i++) {
                        for (int j = 0; j < modelSelecionadas.getColumnCount(); j++) {
                            csv.write(modelSelecionadas.getValueAt(i, j).toString() + ",");
                        }
                        csv.write("\n");
                    }
                    csv.close();
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        });
        buttonImportarHist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser chooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Selecione um arquivo csv", "csv");

                    chooser.setFileFilter(filter);
                    int resp = chooser.showOpenDialog(null);

                    if (resp == JFileChooser.APPROVE_OPTION) {
                        final String path = chooser.getSelectedFile().getAbsolutePath();

                        listaCursadas.leCursadas(path);

                        aluno.leAluno(path);

                        selecionadas();

                        // Le disciplinas cursadas e as adiciona a tabela
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

                        Boolean exists;
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

                        // Calcula e exibe o desempenho do aluno
                        double ultimasAprovadas = 0;
                        double ultimasCursadas = 0;
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
                        // classifica o desempenho do aluno
                        if (fracao > (2.0 / 3.0))
                            desempenho = "Bom";
                        else if (fracao <= 2.0 / 3.0 && fracao > 1.0 / 2.0)
                            desempenho = "Médio";
                        else if (fracao < 1.0 / 2.0)
                            desempenho = "Ruim";

                        if (ultimasCursadas != 0) {
                            aprovacao.setText(
                                    "Aprovação no ultimo periodo: " + porcentagem + "% " + " \nReprovações por falta: "
                                            + reprovacoesFalta + " \nIRA: " + ira + " Desempenho: " + desempenho);
                        }
                    }
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        });

        buttonImportarGrade.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser chooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Selecione um arquivo csv", "csv");

                    chooser.setFileFilter(filter);
                    int resp = chooser.showOpenDialog(null);

                    if (resp == JFileChooser.APPROVE_OPTION) {
                        final String path = chooser.getSelectedFile().getAbsolutePath();
                        listaOfertadas.leOfertadas(path);

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

                        Boolean exists;

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
                    }

                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        });

        JScrollPane aprovacaoPane = new JScrollPane(aprovacao);
        aprovacaoPane.setBorder(BorderFactory.createTitledBorder("Estatísticas do Aluno"));
        JScrollPane cursadasPane = new JScrollPane(cursadas);
        cursadasPane.setBorder(BorderFactory.createTitledBorder("Disciplinas Cursadas"));
        JScrollPane ofertadasPane = new JScrollPane(ofertadas);
        ofertadasPane.setBorder(BorderFactory.createTitledBorder("Disciplinas Ofertadas"));
        JScrollPane faltantesPane = new JScrollPane(faltantes);
        faltantesPane.setBorder(BorderFactory.createTitledBorder("Disciplinas Faltantes"));
        final JScrollPane selecionadasPane = new JScrollPane(selecionadas);
        selecionadasPane
                .setBorder(BorderFactory.createTitledBorder("Disciplinas Selecionadas" + " - " + status.getText()));
        importsPanel.add(buttonImportarHist);
        importsPanel.add(buttonImportarGrade);
        panel.add(importsPanel);
        panel.add(aprovacaoPane);
        panel.add(cursadasPane);
        panel.add(faltantesPane);
        panel.add(ofertadasPane);
        panel.add(selecionadasPane);
        buttonPanel.add(buttonSalvar);
        buttonPanel.add(buttonEnviar);
        panel.add(buttonPanel);
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
                    if (ofertadas.getValueAt(ofertadas.getSelectedRow(), 0).toString().equals(modelSelecionadas
                            .getDataVector().get(i).get(0))) {
                        exists = true;
                    }
                }
                // adiciona o elemento clicado só se não existir na tabela e o clickCount for
                // par (para corrigir o bug da tabela que clica duas vezes)
                if (!ofertadasSelectionModel.isSelectionEmpty() && clickCount % 2 == 0 && !exists) {
                    modelSelecionadas.addRow(
                            new Object[] { ofertadas.getValueAt(ofertadas.getSelectedRow(), 0).toString(),
                                    ofertadas.getValueAt(ofertadas.getSelectedRow(), 1).toString(),
                                    ofertadas.getValueAt(ofertadas.getSelectedRow(), 2).toString(),
                            });
                    buttonEnviar.setEnabled(verificaRegras());
                    selecionadasPane
                            .setBorder(BorderFactory
                                    .createTitledBorder("Disciplinas Selecionadas" + " - " + status.getText()));
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
                    buttonEnviar.setEnabled(verificaRegras());
                    selecionadasPane
                            .setBorder(BorderFactory
                                    .createTitledBorder("Disciplinas Selecionadas" + " - " + status.getText()));
                }
                clickCount++;
            }
        });
        this.setSize(700, 800);
    }

    public boolean verificaRegras() {
        int tamanhoLista = modelSelecionadas.getDataVector().size();
        Vector<Vector> disciplinasFaltantes = modelFaltantes.getDataVector();
        Vector<Vector> disciplinasSelecionadas = modelSelecionadas.getDataVector();

        Boolean cursouArq = true;
        Boolean selecionouSO = false;

        for (Vector vectorData : disciplinasFaltantes)
            for (Object data : vectorData) {
                if (data.equals("CI1237"))
                    cursouArq = false;
                break;
            }

        for (Vector vectorData : disciplinasSelecionadas)
            for (Object data : vectorData) {
                if (data.equals("CI1215"))
                    selecionouSO = true;
                break;
            }

        if (tamanhoLista == 0) {
            status.setText("Nenhuma disciplina foi selecionada");
            return false;
        }

        if (!cursouArq && selecionouSO) {
            status.setText("Você não pode fazer Sistemas Operacionais sem ter feito Arquitetura de Computadores");
            return false;
        }

        if (ira >= 8.0 || tamanhoLista <= 3) {
            status.setText("As Disciplinas serão aprovadas");
            return true;
        }

        if (desempenho.equals("Ruim") && tamanhoLista > 3) {
            // máximo de 3 matérias
            status.setText("Escolha no máximo 3 disciplinas");
            return false;
        } else if (desempenho.equals("Médio") && tamanhoLista > 4) {
            // máximo de 4 matérias
            status.setText("Escolha no máximo 4 disciplinas");
            status.setForeground(Color.RED);
            return false;
        } else if (desempenho.equals("Bom") && tamanhoLista > 5) {
            // máximo de 5 matérias
            status.setText("Escolha no máximo 5 disciplinas");
            status.setForeground(Color.RED);
            return false;
        }

        // maximicar a matricula nos periodos mais próximos ao início do curso
        // matricular o maximo dentro da barreira

        return false;
    }

    private JTable selecionadas() {
        JTable selecionadasTable = new JTable(modelSelecionadas) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        selecionadasTable.setAutoCreateRowSorter(true);

        if (modelSelecionadas.getColumnCount() == 0) {
            modelSelecionadas.addColumn("codigo");
            modelSelecionadas.addColumn("nome");
            modelSelecionadas.addColumn("periodo");
            selecionadasTable.setBounds(100, 100, 500, 500);
        }

        // verifica se existe o arquivo com as disciplinas salvas
        if (Files.exists(Paths.get("selecionadasSalvo.csv")) && listaCursadas.lista.size() > 0) {
            try {
                // adiciona as disciplinas salvas na tabela
                FileReader filereader = new FileReader("selecionadasSalvo.csv");
                CSVReader csvReader = new CSVReader(filereader);
                String[] nextRecord;
                nextRecord = csvReader.readNext();
                while ((nextRecord = csvReader.readNext()) != null) {
                    if (nextRecord.length > 0) {
                    }
                    modelSelecionadas.addRow(
                            new Object[] { nextRecord[0].toString(), nextRecord[1].toString(), nextRecord[2].toString(),
                            });
                }
                csvReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        ofertadasTable.setBounds(100, 100, 500, 500);
        return ofertadasTable;
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

        faltantesTable.setBounds(100, 100, 300, 180);
        return faltantesTable;
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("aqui");
    }

}
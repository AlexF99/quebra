package quebra;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FrontEnd extends JFrame implements ActionListener {
    private JButton B1, B2;
    private JLabel L1, L2, L3;
    private JTextField T1, T2, T3;

    public FrontEnd() {
        B1 = new JButton("Insere aluno");
        B1.addActionListener(this);
        L1 = new JLabel("Nome");
        L2 = new JLabel("email");
        L3 = new JLabel("grr");
        T1 = new JTextField("Nome");
        T2 = new JTextField("email");
        T3 = new JTextField("GRR_____");
        this.getContentPane().setLayout(new FlowLayout());
        this.getContentPane().add(L1);
        this.getContentPane().add(T1);
        this.getContentPane().add(L2);
        this.getContentPane().add(T2);
        this.getContentPane().add(L3);
        this.getContentPane().add(T3);
        this.getContentPane().add(B1);
        this.setLocation(200, 200);
        this.setSize(500, 350);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == B1) {
            String nome = T1.getText();
            String email = T2.getText();
            String grr = T3.getText();
        }
    }

}
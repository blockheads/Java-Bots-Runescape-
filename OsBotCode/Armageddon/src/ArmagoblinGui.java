import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Wed Jul 27 17:28:05 EDT 2016
 */

/**
 * @author nick asdf
 */
public class ArmagoblinGui extends JFrame {

    private boolean started = false;

    public ArmagoblinGui() {

        initComponents();
    }

    private void button1ActionPerformed(ActionEvent e) {
        // TODO add your code here
        started = true;
        this.setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - nick asdf
        button1 = new JButton();
        textField1 = new JTextField();
        label1 = new JLabel();
        checkBox1 = new JCheckBox();

        //======== this ========
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button1 ----
        button1.setText("Start");
        button1.addActionListener(e -> button1ActionPerformed(e));
        contentPane.add(button1);
        button1.setBounds(55, 85, 85, 55);

        //---- textField1 ----
        textField1.setText("Enter food name");
        contentPane.add(textField1);
        textField1.setBounds(40, 35, 105, 24);

        //---- label1 ----
        label1.setText("ARMAGOBLIN");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 6f));
        contentPane.add(label1);
        label1.setBounds(25, 0, 130, 36);

        //---- checkBox1 ----
        checkBox1.setText("Bury Bones");
        contentPane.add(checkBox1);
        checkBox1.setBounds(new Rectangle(new Point(0, 60), checkBox1.getPreferredSize()));

        contentPane.setPreferredSize(new Dimension(190, 155));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - nick asdf
    private JButton button1;
    private JTextField textField1;
    private JLabel label1;
    private JCheckBox checkBox1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}

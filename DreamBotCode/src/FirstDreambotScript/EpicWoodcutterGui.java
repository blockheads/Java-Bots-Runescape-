/*
 * Created by JFormDesigner on Thu Jul 21 19:55:18 EDT 2016
 */

package FirstDreambotScript;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * @author unknown
 */
public class EpicWoodcutterGui extends JFrame {
    private Main ctx;

    public EpicWoodcutterGui(Main main) {
        this.ctx = main;
        initComponents();
    }

    private void button1ActionPerformed(ActionEvent e) {
        ctx.createChop();
        ctx.getChop().StartGeneration();
        ctx.setStartScript(true);
        setVisible(false);
        ctx.setStartTimer();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - nick asdf
        button1 = new JButton();
        TreeSelector = new JComboBox<>();
        label1 = new JLabel();
        label3 = new JLabel();
        label2 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        BoundingBoxHeight = new JSpinner();
        BoundingBoxWidth = new JSpinner();
        RangeSize = new JSpinner();
        label6 = new JLabel();
        ThresholdRange = new JSpinner();
        label7 = new JLabel();
        scrollPane2 = new JScrollPane();
        textPane1 = new JTextPane();
        CoryBox = new JCheckBox();

        //======== this ========
        setTitle("AIO WoodCutter");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button1 ----
        button1.setText("Start");
        button1.addActionListener(e -> button1ActionPerformed(e));
        contentPane.add(button1);
        button1.setBounds(245, 195, 105, 60);

        //---- TreeSelector ----
        TreeSelector.setModel(new DefaultComboBoxModel<>(new String[] {
            "Tree",
            "Oak",
            "Willow",
            "Maple",
            "Achey",
            "Yew",
            "Magic"
        }));
        contentPane.add(TreeSelector);
        TreeSelector.setBounds(new Rectangle(new Point(20, 40), TreeSelector.getPreferredSize()));

        //---- label1 ----
        label1.setText("Tree Type Select");
        contentPane.add(label1);
        label1.setBounds(20, 10, 90, 25);

        //---- label3 ----
        label3.setText("Bounding Box Height");
        label3.setToolTipText("The bounding box tells the script how high and low you want the bot to go to look for trees. IE a value of 20 would look 10 high and 10 below the bot for trees");
        contentPane.add(label3);
        label3.setBounds(190, 45, 120, 16);

        //---- label2 ----
        label2.setText("Range Size");
        label2.setToolTipText("How far away from the player distance wise should the bot seach for trees within the bounded area");
        contentPane.add(label2);
        label2.setBounds(245, 105, label2.getPreferredSize().width, 16);

        //---- label4 ----
        label4.setText("Bounding Box Width");
        label4.setToolTipText("The bounding box width tells the script how far left and right you want the bot to go to look for trees. IE a value of 20 would look 10 to the right and 10 to the left");
        contentPane.add(label4);
        label4.setBounds(195, 75, 120, 16);

        //---- label5 ----
        label5.setText("Advanced options (hover option for tooltip)");
        contentPane.add(label5);
        label5.setBounds(new Rectangle(new Point(145, 10), label5.getPreferredSize()));

        //---- BoundingBoxHeight ----
        BoundingBoxHeight.setModel(new SpinnerNumberModel(20, 4, 40, 2));
        contentPane.add(BoundingBoxHeight);
        BoundingBoxHeight.setBounds(new Rectangle(new Point(320, 40), BoundingBoxHeight.getPreferredSize()));

        //---- BoundingBoxWidth ----
        BoundingBoxWidth.setModel(new SpinnerNumberModel(20, 4, 40, 2));
        contentPane.add(BoundingBoxWidth);
        BoundingBoxWidth.setBounds(320, 70, 52, 26);

        //---- RangeSize ----
        RangeSize.setModel(new SpinnerNumberModel(20, 4, 40, 1));
        contentPane.add(RangeSize);
        RangeSize.setBounds(320, 100, 52, 26);

        //---- label6 ----
        label6.setText("Threshold Range");
        label6.setToolTipText("The threshold range tells the script what the largest possible value of the distance from the player to a tree is to be possibly selected by a random generation. I can't explain this better just leave it at 3 if you are confused");
        contentPane.add(label6);
        label6.setBounds(220, 135, 95, 16);

        //---- ThresholdRange ----
        ThresholdRange.setModel(new SpinnerNumberModel(3, 1, 10, 1));
        contentPane.add(ThresholdRange);
        ThresholdRange.setBounds(320, 130, 52, 26);

        //---- label7 ----
        label7.setText("How to use this bot");
        contentPane.add(label7);
        label7.setBounds(55, 90, 110, label7.getPreferredSize().height);

        //======== scrollPane2 ========
        {

            //---- textPane1 ----
            textPane1.setText("In order to use this bot, first equip a axe and simply move to a area with the type of trees you want to cut and go to the center of the location. Once done you can change the advance settings to better fit the area(hover over them for tooltips on what they do), or just leave them as is and simply start up the bot. The bot will automatically collect wood based on the ranges you set up and bank automatically.");
            textPane1.setEditable(false);
            scrollPane2.setViewportView(textPane1);
        }
        contentPane.add(scrollPane2);
        scrollPane2.setBounds(15, 115, 200, 135);

        //---- CoryBox ----
        CoryBox.setText("Cory");
        CoryBox.setSelected(true);
        contentPane.add(CoryBox);
        CoryBox.setBounds(105, 40, 60, CoryBox.getPreferredSize().height);

        contentPane.setPreferredSize(new Dimension(400, 300));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - nick asdf
    private JButton button1;
    private JComboBox<String> TreeSelector;
    private JLabel label1;
    private JLabel label3;
    private JLabel label2;
    private JLabel label4;
    private JLabel label5;
    private JSpinner BoundingBoxHeight;
    private JSpinner BoundingBoxWidth;
    private JSpinner RangeSize;
    private JLabel label6;
    private JSpinner ThresholdRange;
    private JLabel label7;
    private JScrollPane scrollPane2;
    private JTextPane textPane1;
    private JCheckBox CoryBox;

    public String getTreeSelector() {
        return TreeSelector.getSelectedItem().toString();
    }

    public int getBoundingBoxHeight() {
        return (int)(BoundingBoxHeight.getValue());
    }

    public int getBoundingBoxWidth() {
        return (int)(BoundingBoxWidth.getValue());
    }

    public int getRangeSize() {
        return (int)RangeSize.getValue();
    }

    public int getThresholdRange() {
        return (int)ThresholdRange.getValue();
    }

    public boolean getCoryBox() {
        return CoryBox.isSelected();
    }

    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

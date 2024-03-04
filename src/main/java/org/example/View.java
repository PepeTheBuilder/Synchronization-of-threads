package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JPanel panel;
    private JTextField simTimeField;
    private JTextField nrClientsField;
    private JTextField nrQueueField;
    private JTextField minArrField;
    private JTextField minServField;
    private JTextField maxArrField;
    private JTextField maxServField;
    private JTable table1;
    private JButton startButton;
    private JLabel actualTime;
    private int simTime;
    private int nrClients;
    private int nrQueue;
    private int minArr;
    private int minServ;
    private int maxArr;
    private int maxServ;
    public View(){
        setTitle("My thread thing");
        setSize(600, 500);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setActualTime("0");

    }
    public void setActualTime(String actualTime) {
        this.actualTime.setText(actualTime);
        this.actualTime.repaint();

    }
    public void setZeroTime(){
        this.actualTime.setText("0");
    }
    public JTextField getSimTimeField() {
        return simTimeField;
    }

    public void setSimTimeField(JTextField simTimeField) {
        this.simTimeField = simTimeField;
    }

    public JTextField getNrClientsField() {
        return nrClientsField;
    }

    public void setNrClientsField(JTextField nrClientsField) {
        this.nrClientsField = nrClientsField;
    }

    public JTextField getNrQueueField() {
        return nrQueueField;
    }

    public void setNrQueueField(JTextField nrQueueField) {
        this.nrQueueField = nrQueueField;
    }

    public JTextField getMinArrField() {
        return minArrField;
    }

    public void setMinArrField(JTextField minArrField) {
        this.minArrField = minArrField;
    }

    public JTextField getMinServField() {
        return minServField;
    }

    public void setMinServField(JTextField minServField) {
        this.minServField = minServField;
    }

    public JTextField getMaxArrField() {
        return maxArrField;
    }

    public void setMaxArrField(JTextField maxArrField) {
        this.maxArrField = maxArrField;
    }

    public JTextField getMaxServField() {
        return maxServField;
    }

    public void setMaxServField(JTextField maxServField) {
        this.maxServField = maxServField;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public void setStartButton(JButton startButton) {
        this.startButton = startButton;
    }

    public void setActualTime(JLabel actualTime) {
        this.actualTime = actualTime;
    }

    public JLabel getActualTime() {
        return actualTime;
    }

    public JTable getTable1() {
        return table1;
    }
    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public void addSimTimeListener(ActionListener listener) {
        simTimeField.addActionListener(listener);
    }

    public void addNrClientsListener(ActionListener listener) {
        nrClientsField.addActionListener(listener);
    }

    public void addNrQueueListener(ActionListener listener) {
        nrQueueField.addActionListener(listener);
    }

    public void addMinArrListener(ActionListener listener) {
        minArrField.addActionListener(listener);
    }

    public void addMinServListener(ActionListener listener) {
        minServField.addActionListener(listener);
    }

    public void addMaxArrListener(ActionListener listener) {
        maxArrField.addActionListener(listener);
    }

    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void addMaxServListener(ActionListener actionListener) {
        maxServField.addActionListener(actionListener);
    }
}

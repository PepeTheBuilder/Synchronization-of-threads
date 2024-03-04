package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Control {
    private View view;
    private int nrQueue;
    private int nrClient;
    private int simTime;
    private int minArr;
    private int maxArr;
    private int minServ;
    private int maxServ;

    public Control() {
        view = new View();
        view.setVisible(true);

        view.addStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getAllFiled();
                if (minArr > maxArr && minServ > maxServ) {
                    JOptionPane.showMessageDialog(view.getPanel(), "Minimum values are more than maximum values.");
                } else {
                   //TODO baaaaaaaaaaaaaaaaaaaaaaaa
                    view.setZeroTime();
                    System.out.println("SimTime:"+simTime+" nrQueues:"+nrQueue+" nrClients:"+nrClient+" Arrival:"+minArr+" to "+maxArr+" Service:"+minServ+" to "+maxServ);
                    Server server = new Server(simTime,nrQueue,nrClient,minArr,maxArr,minServ,maxServ,view);
                    try {
                        server.stopAllThreads();
                    } catch (InterruptedException ex) {
                    }
                }
            }
        });
    }

        public void getAllFiled(){
            getMaxArr();
            getMaxServ();
            getSimTimeField();
            getMinArrField();
            getNrQueueField();
            getMinServField();
            getNrClientsField();
        }
        public void getSimTimeField() {
            String input = view.getSimTimeField().getText();
            try {
                simTime = Integer.parseInt(input);
                // Do something with the simTime
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view.getPanel(), "Please enter a valid integer for simulation time.");
            }
        }
        public void getNrClientsField() {
            String input = view.getNrClientsField().getText();
            try {
                if(!input.isEmpty())
                    nrClient = Integer.parseInt(input);
                else nrClient=0;

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view.getPanel(), "Please enter a valid integer for number of clients.");
            }
        }
        public void getNrQueueField() {
            String input = view.getNrQueueField().getText();
            try {
                if(!input.isEmpty())
                    nrQueue = Integer.parseInt(input);
                else nrQueue=1;
                // Do something with the nrQueue
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view.getPanel(), "Please enter a valid integer for number of queues.");
            }
        }
        public void getMinArrField() {
            String input = view.getMinArrField().getText();
            try {
                if(!input.isEmpty())
                    minArr = Integer.parseInt(input);
                else minArr=0;
                // Do something with the minArr
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view.getPanel(), "Please enter a valid integer for minimum arrival time.");
            }
        }
        public void getMinServField(){
            String input = view.getMinServField().getText();
            try {
                if(!input.isEmpty())
                    minServ = Integer.parseInt(input);
                else minServ=0;
                // Do something with the minServ
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view.getPanel(), "Please enter a valid integer for minimum service time.");
            }
        }
        public void getMaxArr() {
            String input = view.getMaxArrField().getText();
            try {
                if(!input.isEmpty())
                    maxArr = Integer.parseInt(input);
                else maxArr=1;
                // Do something with the maxArr
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view.getPanel(), "Please enter a valid integer for maximum arrival time.");
            }
        }
        public void getMaxServ() {
        String input = view.getMaxServField().getText();
        try {
            if(!input.isEmpty())
                maxServ = Integer.parseInt(input);
            else maxServ=1;
            // Do something with the maxArr
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view.getPanel(), "Please enter a valid integer for maximum arrival time.");
        }
    }
}

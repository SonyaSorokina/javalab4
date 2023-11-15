import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Font;
import java.util.Random;
import java.io.*;

public class Main {
    static volatile boolean flag = false;
    static String item = "Arial";
    static String stroka = "";
    static Random r = new Random();
    static Font font = new Font("Monospace", Font.PLAIN, 40);

    public static void main(String[] args) {

        JFrame fr = new JFrame("Диагональ");
        fr.setPreferredSize( new Dimension(500,500));

        String[] items = {
                "Arial",
                "SansSerif",
                "Serif"
        };

        final JPanel pan = new JPanel();
        fr.add(pan);
        fr.setVisible(true);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                item = (String)box.getSelectedItem();
            }
        };
        ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                flag = true;
            }
        };

        JLabel label1 = new JLabel("Введите строку");
        JTextField textbox1 = new JTextField();
        textbox1.setPreferredSize(new Dimension(100, 24));
        JComboBox comboBox = new JComboBox(items);
        comboBox.addActionListener(actionListener);
        JButton button1 = new JButton();
        button1.setText("Старт!");
        button1.addActionListener(listener);
        pan.add(label1);
        pan.add(textbox1);
        pan.add(comboBox);
        pan.add(button1);

        fr.pack();
        while (!flag) {
            Thread.onSpinWait();
            ;
        }
        stroka = textbox1.getText();
        int l = stroka.length();
        Timer tm = new Timer(20, new ActionListener(){
            int x = 0, y = 0;
            @Override
            public void actionPerformed(ActionEvent arg0) {

                pan.repaint();
                Graphics2D gr = (Graphics2D)pan.getRootPane().getGraphics();
                gr.setFont(font);
                gr.drawString(stroka, x, y);
                gr.dispose();
                pan.update(gr);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                x += 2;
                y += 2;

                if(x > 500 && y > 500){
                    x = 0;
                    y = 0;
                    font = new Font(item, Font.PLAIN, 30);
                    String newstroka = "";
                    for(char i : stroka.toCharArray()){
                        String avyrpa = String.valueOf(i);
                        if (r.nextBoolean()){
                            newstroka = newstroka + avyrpa.toUpperCase();
                        }
                        else{
                            newstroka = newstroka + avyrpa.toLowerCase();
                        }
                    }
                    stroka = newstroka;
                }
            }}  );
        tm.start();
    }
}
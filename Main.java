package Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private static int SIDE;
    private static int HARDNESS;
    private static int SIZE_V;
    private static int SIZE_H;
    private static JFrame startMenu;
    public static GameField gameField;

    public static void main(String[] args) {

        SIDE = 15;
        HARDNESS = 18;
        SIZE_V = 680;
        SIZE_H = 680;

        GridLayout layout = new GridLayout(4, 1, 0, 10);
        JPanel startPanel = new JPanel(layout);
        startPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JRadioButton min = new JRadioButton("Легко (10 x 10)");
        min.setFont(Constants.ARIAL_BOLD_12);
        JRadioButton mid = new JRadioButton("Средне (15 x 15)", true);
        mid.setFont(Constants.ARIAL_BOLD_12);
        JRadioButton max = new JRadioButton("Сложно (20 x 20)");
        max.setFont(Constants.ARIAL_BOLD_12);
        ButtonGroup level = new ButtonGroup();

        level.add(min);
        level.add(mid);
        level.add(max);

        JButton ok = new JButton("ПОЕХАЛИ!");                                   // Кнопка OK
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameField.totalMinesCount = 0;
                GameField.checkedMinesCount = 0;
                GameField.flagsCount = 0;
                startMenu.dispose();
                gameField = new GameField(SIDE, HARDNESS, SIZE_V, SIZE_H);
            }
        });

        min.addActionListener(new ActionListener() {                                // Минимальная сложность
            @Override
            public void actionPerformed(ActionEvent e) {
                SIDE = 10;
                HARDNESS = 10;
                SIZE_V = 460;
                SIZE_H = 457;
            }
        });
        mid.addActionListener(new ActionListener() {                                // Средняя сложность (ЗАДАТЬ ТАКЖЕ ПРИ ИНИЦИАЛИЗАЦИИ ПЕРЕМЕННЫХ)
            @Override
            public void actionPerformed(ActionEvent e) {
                SIDE = 15;
                HARDNESS = 18;
                SIZE_V = 680;
                SIZE_H = 680;
            }
        });
        max.addActionListener(new ActionListener() {                                // Максимальная сложность
            @Override
            public void actionPerformed(ActionEvent e) {
                SIDE = 20;
                HARDNESS = 30;
                SIZE_V = 897;
                SIZE_H = 900;
            }
        });

        startPanel.add(min);
        startPanel.add(mid);
        startPanel.add(max);
        startPanel.add(ok);


        startMenu = new JFrame("Выберите сложность");
        startMenu.setIconImage(Constants.BEAR.getImage());
        startMenu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        startMenu.setSize(350, 200);
        startMenu.setResizable(false);
        startMenu.setLocationByPlatform(true);
        startMenu.add(startPanel);
        startMenu.setVisible(true);
    }
}

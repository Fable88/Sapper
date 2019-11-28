package Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameField extends JFrame {

    private static GameObject[][] cells;
    public static GameObject getCell (int i, int j) {
        return cells[i][j];
    }

    private static int SIDE;
    public static int getSIDE() {
        return SIDE;
    }

    private final int SIZE_V;
    private final int SIZE_H;
    private final int HARDNESS;

    private JPanel rootPanel;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem restartItem;
    private JMenuItem exitItem;

    public static int totalMinesCount;
    public static int checkedMinesCount;
    public static int flagsCount;

    GameField(int SIDE, int HARDNESS, int SIZE_V, int SIZE_H) {
        this.SIDE = SIDE;
        this.HARDNESS = HARDNESS;
        this.SIZE_V = SIZE_V;
        this.SIZE_H = SIZE_H;

        setIconImage(Constants.BEAR.getImage());
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridLayout gridLayout = new GridLayout(SIDE, SIDE, 3, 3);

        setTitle("Однажды в лесу затаился медведь...");

        // МЕНЮ

        restartItem = new JMenuItem("Начать сначала", Constants.RESTART);          // Кнопка меню РЕСТАРТ
        restartItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Main.main(null);
            }
        });

        exitItem = new JMenuItem("Выход", Constants.EXIT);                      // Кнопка меню ВЫХОД
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu = new JMenu("Меню");                             // Меню
        fileMenu.add(restartItem);
        fileMenu.add(exitItem);

        menuBar = new JMenuBar();                                    // Меню бар
        menuBar.add(fileMenu);

        rootPanel = new JPanel();                                    // ROOT PANEL
        rootPanel.setLayout(gridLayout);
        add(rootPanel);


        // СОЗДАНИЕ ПОЛЯ

        cells = new GameObject[SIDE][SIDE];

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                cells[i][j] = new GameObject(i, j, HARDNESS);
            }
        }                   // Создание кнопок-клеток + применение сложности

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                cells[i][j].neighbors = cells[i][j].getNeighbors(cells, cells[i][j]);
            }
        }                   // Поиск соседей каждой клетки

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                cells[i][j].minesCount = cells[i][j].getMineCount(cells[i][j]);
            }
        }                   // Получение количества мин вокруг для каждой клетки

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (cells[i][j].isMine()) {
                    totalMinesCount++;
                }
            }
        }                   // Подсчёт количества мин на поле

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                rootPanel.add(cells[i][j]);
            }
        }                   // Добавление клеток на поле

        setSize(SIZE_V, SIZE_H);                               // Размер поля: зависит от сложности (кол-ва клеток)

        // ДОБАВЛЕНИЕ МЕНЮ И ТЕКСТА

        JLabel totalBears = new JLabel("    * Медвежьих засад в лесу: " + totalMinesCount);  // Строка с количеством медведей
        totalBears.setFont(Constants.ARIAL_ITALIC_12);
        menuBar.add(totalBears);

        setJMenuBar(menuBar);
    }

}
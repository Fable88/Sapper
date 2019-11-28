package Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;


public class GameObject extends JButton {

    private boolean mine;
    private final int HARDNESS;
    public int minesCount;
    public int x;
    private int y;
    private boolean isOpened;
    private boolean isChecked;
    public ArrayList<GameObject> neighbors;
    private JFrame endMenu;


    private Font font = new Font(null, 0, 12);


    public GameObject(int x, int y, int HARDNESS) {
        super();
        this.HARDNESS = HARDNESS;

        setFont(font);
        this.x = x;
        this.y = y;
        setPreferredSize(new Dimension(15, 15));
        mine = setMine();

        setBackground(Constants.FOREST);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isMine() && !isChecked) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (minesCount != -1 && minesCount != 0) {
                            setText(Integer.toString(minesCount));
                            setBackground(Color.ORANGE);
                        }
                        if (minesCount == 0) {
                            setBackground(Constants.WOOD);
                            for (GameObject neighbor : neighbors) {
                                if (!neighbor.isOpened && !neighbor.isChecked && neighbor.minesCount == 0) {
                                    neighbor.setBackground(Constants.WOOD);
                                    neighbor.isOpened = true;
                                    openNeighbor(neighbor);
                                }
                            }
                        }

                        for (GameObject neighbor : neighbors) {
                            if (!neighbor.isOpened && !neighbor.isChecked && !neighbor.isMine() && neighbor.minesCount > 0) {
                                neighbor.setBackground(Color.ORANGE);
                                neighbor.setText(Integer.toString(neighbor.minesCount));
                                neighbor.isOpened = true;
                            }
                        }

                    }
                } else if (!isChecked && e.getButton() == MouseEvent.BUTTON1) {
                    showAllMines();                                                                  // Показать все мины
                    showEndMenu("Тебя покусали!", Constants.BEAR);                              // ПОРАЖЕНИЕ
                }

                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (!isChecked) {
                        isChecked = true;
                        if (isMine()) {
                            GameField.checkedMinesCount++;
                            GameField.flagsCount++;
                        } else {
                            GameField.flagsCount++;
                        }
                        setBackground(Constants.GRASS);
                        setIcon(Constants.HONEY);
                        if (GameField.checkedMinesCount == GameField.totalMinesCount && GameField.checkedMinesCount == GameField.flagsCount) {               // ПОБЕДА - ставим флаг
                            setBackground(Constants.GRASS);
                            showEndMenu("Медведь наелся! Победа!", Constants.BEAR_WIN);
                        }
                    } else {
                        isChecked = false;
                        setBackground(Constants.FOREST);
                        setIcon(null);
                        if (isMine()) {
                            GameField.checkedMinesCount--;
                            GameField.flagsCount--;
                        } else {
                            GameField.flagsCount--;
                            if (GameField.checkedMinesCount == GameField.totalMinesCount && GameField.checkedMinesCount == GameField.flagsCount) {               // ПОБЕДА - убираем флаг
                                setBackground(Constants.GRASS);
                                showEndMenu("Медведь наелся! Победа!", Constants.BEAR_WIN);
                            }
                        }
                    }
                }
            }
        });

    }

    private void openNeighbor(GameObject gameObject) {
        for (GameObject neighbor : gameObject.neighbors) {
            if (!neighbor.isOpened && !neighbor.isChecked && neighbor.minesCount == 0) {
                neighbor.setBackground(Constants.WOOD);
                neighbor.isOpened = true;
                openNeighbor(neighbor);
            }
            if (!neighbor.isOpened && !neighbor.isChecked && !neighbor.isMine() && neighbor.minesCount > 0) {
                neighbor.setBackground(Color.ORANGE);
                neighbor.setText(Integer.toString(neighbor.minesCount));
                neighbor.isOpened = true;
            }
        }
    }                                               // Открывает соседню клетку, если там нет мин

    private boolean setMine() {
        Random random = new Random();
        int i = random.nextInt(100);
        if (i < HARDNESS) {
            return true;
        } else return false;
    }                                                                      // Рандомно ставит мину (или не ставит)

    public boolean isMine() {
        return mine;
    }                                                             // Есть мина?

    public ArrayList<GameObject> getNeighbors(GameObject[][] gameObjects, GameObject gameObject) {
        ArrayList<GameObject> neighbors = new ArrayList<>();

        if (gameObject.x != 0 && gameObject.y != 0 && gameObject.x != gameObjects.length - 1 && gameObject.y != gameObjects.length - 1) {
            neighbors.add(gameObjects[x - 1][y - 1]);
            neighbors.add(gameObjects[x][y - 1]);
            neighbors.add(gameObjects[x + 1][y - 1]);
            neighbors.add(gameObjects[x - 1][y]);
            neighbors.add(gameObjects[x + 1][y]);
            neighbors.add(gameObjects[x - 1][y + 1]);
            neighbors.add(gameObjects[x][y + 1]);
            neighbors.add(gameObjects[x + 1][y + 1]);
            return neighbors;
        } else if (gameObject.x == 0 && gameObject.y != 0 && gameObject.y != gameObjects.length - 1) {
            neighbors.add(gameObjects[x][y - 1]);
            neighbors.add(gameObjects[x + 1][y - 1]);
            neighbors.add(gameObjects[x + 1][y]);
            neighbors.add(gameObjects[x][y + 1]);
            neighbors.add(gameObjects[x + 1][y + 1]);
            return neighbors;
        } else if (gameObject.y == 0 && gameObject.x != 0 && gameObject.x != gameObjects.length - 1) {
            neighbors.add(gameObjects[x - 1][y]);
            neighbors.add(gameObjects[x - 1][y + 1]);
            neighbors.add(gameObjects[x][y + 1]);
            neighbors.add(gameObjects[x + 1][y]);
            neighbors.add(gameObjects[x + 1][y + 1]);
            return neighbors;
        } else if (gameObject.x == gameObjects.length - 1 && gameObject.y != gameObjects.length - 1 && gameObject.y != 0) {
            neighbors.add(gameObjects[x - 1][y - 1]);
            neighbors.add(gameObjects[x][y - 1]);
            neighbors.add(gameObjects[x - 1][y]);
            neighbors.add(gameObjects[x - 1][y + 1]);
            neighbors.add(gameObjects[x][y + 1]);
            return neighbors;
        } else if (gameObject.y == gameObjects.length - 1 && gameObject.x != gameObjects.length - 1 && gameObject.x != 0) {
            neighbors.add(gameObjects[x - 1][y]);
            neighbors.add(gameObjects[x - 1][y - 1]);
            neighbors.add(gameObjects[x][y - 1]);
            neighbors.add(gameObjects[x + 1][y - 1]);
            neighbors.add(gameObjects[x + 1][y]);
            return neighbors;
        } else if (gameObject.x == 0 && gameObject.y == gameObjects.length - 1) {
            neighbors.add(gameObjects[x + 1][y]);
            neighbors.add(gameObjects[x + 1][y - 1]);
            neighbors.add(gameObjects[x][y - 1]);
            return neighbors;
        } else if (gameObject.x == 0 && gameObject.y == 0) {
            neighbors.add(gameObjects[x + 1][y]);
            neighbors.add(gameObjects[x + 1][y + 1]);
            neighbors.add(gameObjects[x][y + 1]);
            return neighbors;
        } else if (gameObject.x == gameObjects.length - 1 && gameObject.y == 0) {
            neighbors.add(gameObjects[x - 1][y]);
            neighbors.add(gameObjects[x - 1][y + 1]);
            neighbors.add(gameObjects[x][y + 1]);
            return neighbors;
        } else {
            neighbors.add(gameObjects[x][y - 1]);
            neighbors.add(gameObjects[x - 1][y - 1]);
            neighbors.add(gameObjects[x - 1][y]);
            return neighbors;
        }
    }   // Список соседних клеток

    public int getMineCount(GameObject gameObject) {
        int count = 0;
        if (!gameObject.isMine()) {
            for (GameObject neighbor : gameObject.neighbors) {
                if (neighbor.isMine()) {
                    count++;
                }
            }
            return count;
        } else {
            return -1;
        }
    }                    // Узнать коичество мин вокруг клетки

    private void showEndMenu(String text, ImageIcon image) {
        JPanel end = new JPanel();
        JButton endButton = new JButton("Выйти");
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JButton restart = new JButton("Сначала");
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.gameField.dispose();
                endMenu.dispose();
                Main.main(null);
            }
        });
        end.add(new JLabel(text));
        JLabel picture = new JLabel(image);
        end.add(picture);
        end.add(endButton);
        end.add(restart);
        endMenu = new JFrame();
        endMenu.setIconImage(Constants.BEAR.getImage());
        endMenu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        endMenu.setSize(300, 360);
        endMenu.setResizable(false);
        endMenu.setLocationRelativeTo(this);
        endMenu.add(end);
        endMenu.setVisible(true);
    }            // Меню окончания игры

    public static void showAllMines() {
        for (int i = 0; i < GameField.getSIDE(); i++) {
            for (int j = 0; j < GameField.getSIDE(); j++) {
                GameObject cell = GameField.getCell(i, j);
                if (cell.isMine()) {
                    cell.setBackground(Constants.GRASS);
                    cell.setIcon(Constants.BEAR_BUTTON);
                }
            }
        }
    }                                 // Показывает все мины на поле

}

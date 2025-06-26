import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoaderPanel extends JPanel {

    private BufferedImage backgroundImage;

    private JTable datFilesTable;
    private DefaultTableModel tableModel;
    private JPanel dataPanel;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    public static MyButton deleteButton;
    public static MyButton loadButton;
    public static MyButton mainMenuButtonLoader = new MyButton("Return to main menu");

    public LoaderPanel() {
        // méret, háttér beállítása
        setPanelProperties();
        // tábla létrehozása
        makeTable();
    }

    private void setPanelProperties() {

        // méret
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);

        // háttér
        try {
            File im = new File("images/background.jpg");
            backgroundImage = ImageIO.read(im);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void makeTable() {

        // oszlop(ok)
        String[] columnNames = {"File Name", "Date of last modification"};

        // adatmodell alapállapota
        tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // tábla létrehozása az elkészített (üres) adatmodellel
        datFilesTable = new JTable(tableModel);
        datFilesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        datFilesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        datFilesTable.setFillsViewportHeight(true);
        datFilesTable.getTableHeader().setReorderingAllowed(false);

    }

    public void makeModel(){

        // töröljünk mindent a panelban
        removeAll();

        // új tábla és adatmodell létrehozása, majd feltöltés az éppen aktuális adatokkal
        loadDatFiles();

        // gridbaglayoutot használunk
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // új panel létrehozása, amibe a scrollpane-t és a gombokat tároló sávot fogjuk tenni
        makeDataPanel();

        // datapanel hozzáadása
        add(dataPanel, gbc);
        // Button hozzáadása alatta
        gbc.gridy = 1;
        add(mainMenuButtonLoader, gbc);
    }

    private void loadDatFiles() {

        // töröljük a modell tartalmát
        tableModel.setRowCount(0);

        // mappánk tárolása File-ként
        File directory = new File("games");

        // .dat fájlok hozzáadása a modellhez
        if (directory.isDirectory()) {
            
            File[] datFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".dat"));

            if (datFiles != null) {
                for (File datFile : datFiles) {
                    
                    long lastModified = datFile.lastModified();
                    String lastModifiedDate = formatLastModifiedDate(lastModified);
                    tableModel.addRow(new Object[]{datFile.getName(), lastModifiedDate});

                }
            }
        }
    }

    private void makeDataPanel() {

        dataPanel = new JPanel( new BorderLayout());

        scrollPane = new JScrollPane(datFilesTable);
        scrollPane.setPreferredSize(datFilesTable.getPreferredScrollableViewportSize());

        buttonPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(53, 60, 74));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        deleteButton = new MyButton("Delete game");
        loadButton = new MyButton("Load game");

        setButtons();

        buttonPanel.add(deleteButton);
        buttonPanel.add(loadButton);

        dataPanel.add(scrollPane, BorderLayout.CENTER);
        dataPanel.add(buttonPanel, BorderLayout.SOUTH);
        
    }

    private void setButtons() {

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = datFilesTable.getSelectedRow();

                if (selectedRow != -1) {

                    String fileName = (String) datFilesTable.getValueAt(selectedRow, 0);
                    tableModel.removeRow(selectedRow);
                    deleteFile(fileName);

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete!");
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = datFilesTable.getSelectedRow();
                if (selectedRow != -1) {
                    
                    String fileName = (String) datFilesTable.getValueAt(selectedRow, 0);

                    try {

                        Model loadedModel = readDatFile(fileName);
                        RenjuGame.layout.show(RenjuGame.mainPanel, "GAME");
                        RenjuGame.gamePanel.startGame(loadedModel);

                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to load!");
                }
                
            }
        });

        mainMenuButtonLoader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                RenjuGame.layout.show( RenjuGame.mainPanel, "MENU" );
            }
        });
    }

    private void deleteFile(String fileName) {

        // csináljunk egy fájl változót, ami a kívánt nevű fájlra mutat
        File fileToDelete = new File("./games/" + fileName);
        // töröljük
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        } else JOptionPane.showMessageDialog(null, "The file does not exist!");

    }

    private String formatLastModifiedDate(long lastModified) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(lastModified));

    }

    private Model readDatFile(String fileName) throws IOException, ClassNotFoundException {

        File fileToRead = new File("./games/" + fileName);

        InputStream is = new FileInputStream(fileToRead);
        ObjectInputStream ois = new ObjectInputStream( is );
        Model inputModel = (Model)ois.readObject();

        return inputModel;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

}

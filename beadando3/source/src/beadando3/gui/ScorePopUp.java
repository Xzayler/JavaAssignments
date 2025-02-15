package beadando3.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A dialog window displaying high scores in a table
 */
public class ScorePopUp extends JDialog {

    /**
     * Creates and displays the dialog window from the data
     * @param frame The parent component to display on to
     * @param names The names of the players
     * @param scores The scores of the players
     */
    public ScorePopUp(Frame frame, List<String> names, List<Integer> scores) {
        super(frame);

        String[] cols = {"Name", "Score"};
        Object[][] data = convertData(names, scores);
        JTable table = new JTable(data, cols);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setBackground(Color.RED);
        scrollPane.setBackground(Color.RED);

        add(scrollPane, BorderLayout.CENTER);
        setTitle("Highscores");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        pack();
        revalidate();
        setVisible(true);
    }

    private Object[][] convertData(List<String> names, List<Integer> scores) {
        int rows = names.size();
        Object[][] data = new Object[rows][2];
        for (int i = 0; i < rows; i++) {
            data[i][0] = names.get(i);
            data[i][1] = scores.get(i);
        }
        return data;
    }
}

package phoneticinventory;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * The GUI for the application. Accesses a QueryMaker object to perform queries
 * on the DB.
 * 
 * @author Kim Steffens
 */
public class QueryPanel extends JPanel {

	private QueryMakerImpl myQueryMaker;

	private JButton quitButton;
	private JButton queryButton;
	private JButton clearButton;

	private JTextArea resultArea;
	private JTextField queryArea;

	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;

	private JScrollPane scroll;

	private ImageIcon redx;
	private ImageIcon greencheck;

	private JComboBox querySelector;

	private String[] selections;

	/**
	 * Constructor: Sets up the GUI
	 */
	@SuppressWarnings("unchecked")
	public QueryPanel() {

		myQueryMaker = new QueryMaker();

		MyListener listener = new MyListener();

		clearButton = new JButton("Clear All");
		quitButton = new JButton("Quit");
		queryButton = new JButton("Query!");

		resultArea = new JTextArea(30, 20);
		resultArea.setEditable(false);

		queryArea = new JTextField(8);
		queryArea.setEditable(true);

		selections = new String[] { "contains phoneme",
				"does not contain phoneme", "find language by iso639",
				"find languages in family" };
		
		querySelector = new JComboBox(selections);

		clearButton.addMouseListener(listener);
		quitButton.addMouseListener(listener);
		queryButton.addMouseListener(listener);

		topPanel = new JPanel();
		centerPanel = new JPanel();
		bottomPanel = new JPanel();
		leftPanel = new JPanel();
		rightPanel = new JPanel();

		topPanel.setLayout(new GridLayout());
		centerPanel.setLayout(new GridLayout());
		bottomPanel.setLayout(new GridLayout());

		// set the correct image for whether the database is up or down
		redx = new ImageIcon("redx.png");
		greencheck = new ImageIcon("greencheckmark.png");
		JLabel isUpImage;
		if (myQueryMaker.isDbUp()) {
			isUpImage = new JLabel("Database is up!", greencheck, JLabel.CENTER);

		} else {
			isUpImage = new JLabel("Database not up.", redx, JLabel.CENTER);

		}
		topPanel.add(isUpImage);

		bottomPanel.add(clearButton);
		bottomPanel.add(quitButton);

		leftPanel.add(querySelector);
		leftPanel.add(queryArea);
		leftPanel.add(queryButton);

		scroll = new JScrollPane(resultArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		rightPanel.add(scroll);

		// initial text for resultArea
		resultArea.setText("Your results will display here.");

		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.EAST);
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	/**
	 * Used if the User clicks a button to perform various actions.
	 * 
	 * @author kimberlin
	 */
	private class MyListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// leave blank- unneeded
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// leave blank- unneeded
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// leave blank- unneeded
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// leave blank- unneeded
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			// if left-click
			if (e.getButton() == MouseEvent.BUTTON1) {

				// if the quick button is clicked
				if (quitButton == e.getSource()) {

					// exit if user confirms, or else do nothing
					if (JOptionPane
							.showConfirmDialog(
									null,
									"Are you sure you want to quit? You will lose all data.",
									"WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				}

				// if the user wants to perform a query
				if (queryButton == e.getSource()) {

					// find languages with a phoneme
					if (querySelector.getSelectedItem().equals(selections[0])) {
						resultArea.setText(myQueryMaker.queryPhoneme(queryArea
								.getText()));
					}
					// find languages without a phoneme
					else if (querySelector.getSelectedItem().equals(
							selections[1])) {
						resultArea.setText(myQueryMaker
								.queryNoPhoneme(queryArea.getText()));
					}
					// find languages by iso639
					else if (querySelector.getSelectedItem().equals(
							selections[2])) {
						resultArea.setText(myQueryMaker.queryByIso(queryArea
								.getText()));
					}
					// find languages by family
					else if (querySelector.getSelectedItem().equals(
							selections[3])) {
						resultArea.setText(myQueryMaker
								.queryLanguageByFamily(queryArea.getText()));
					}

					// if no results
					if (resultArea.getText().trim().length() <= 0) {
						resultArea.setText("No results found.");
					}
				}

				// clear queryArea and set default text for resultsArea
				if (clearButton == e.getSource()) {
					resultArea.setText("Your results will display here." + "");
					queryArea.setText("");
				}

			}
		}

	}
}

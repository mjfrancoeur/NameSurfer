
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {

		// initialize database //
		db = new NameSurferDataBase(NAMES_DATA_FILE);
		graph = new NameSurferGraph();
		add(graph);

		// initialize interactors //
		nameField = new JTextField(MAX_NAME);
		nameField.addActionListener(this);

		graphButton = new JButton("Graph");
		clearButton = new JButton("Clear");

		// add interactors to canvas //
		add(new JLabel("Name:"), SOUTH);
		add(nameField, SOUTH);
		add(graphButton, SOUTH);
		add(clearButton, SOUTH);

		addActionListeners();

	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked.
	 */
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();
		if (source == nameField || source == graphButton) {
			String currentName = nameField.getText();
			NameSurferEntry entry = db.findEntry(currentName);
			if (entry != null) {
				graph.addEntry(entry);
				graph.update();
			}

		} else if (source == clearButton) {
			graph.clear();
		}

	}

	/* PRIVATE INSTANCE VARIABLES */
	private JTextField nameField;
	private JButton graphButton;
	private JButton clearButton;
	private NameSurferDataBase db;
	private NameSurferGraph graph;

	/* CONSTANTS */
	private final int MAX_NAME = 10;

}

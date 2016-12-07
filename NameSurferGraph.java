
/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);

		// initialize canvasHeight and canvasWidth based on NameSurferConstants.
		// //
		// GObject coordinates dependent upon these variables //
		canvasHeight = APPLICATION_HEIGHT;
		canvasWidth = APPLICATION_WIDTH;
		graphHeight = APPLICATION_HEIGHT - GRAPH_MARGIN_SIZE * 2;

		// set private instance variables //
		keyOrder = new ArrayList<String>();
		grid = new GCompound(); // create grid GCompound
		entryCompound = new GCompound(); // create entryCompound GCompound,
											// containing entry GLines and
											// GLabels
		entryHashMap = new HashMap<String, int[]>();

		// call the method addGrid() //
		addGrid();
	}

	private void addGrid() {

		String[] decades = { "1900", "1910", "1920", "1930", "1940", "1950", "1960", "1970", "1980", "1990", "2000" };
		widthBetweenLines = canvasWidth / NDECADES;

		// add vertical lines to GCompound//

		for (int i = 0; i < NDECADES; i++) {
			int xCoord = widthBetweenLines * i;
			int yCoord = canvasHeight;
			grid.add(new GLine(xCoord, 0, xCoord, yCoord));

			// add labels to GCompound //

			GLabel decadeLabel = new GLabel(decades[i]);
			grid.add(decadeLabel, xCoord + 5, yCoord);
		}

		// add horizontal lines to GCompound //
		grid.add(new GLine(0, canvasHeight - GRAPH_MARGIN_SIZE, canvasWidth, canvasHeight - GRAPH_MARGIN_SIZE));
		grid.add(new GLine(0, GRAPH_MARGIN_SIZE, canvasWidth, GRAPH_MARGIN_SIZE));

		// add GCompound to the canvas //
		add(grid);
	}

	/* Method: clear() */
	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		removeAll(); // remove all objects from canvas
		entryCompound.removeAll(); // remove all GObjects from GCompound
									// entryCompound
		grid.removeAll(); // remove all GObjects from GCompound grid
		entryHashMap.clear(); // empty HashMap of prior entries
		keyOrder.clear(); // empty ArrayList of prior entries
		addGrid(); // re-add the grid
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note
	 * that this method does not actually draw the graph, but simply stores the
	 * entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {

		// assign variables //
		int[] rankArray = entry.getRankArray();
		entryName = entry.getName();
		entryHashMap.put(entryName, rankArray);
		keyOrder.add(entryName);
	}

	/* Method: graphEntry() */
	/**
	 * 
	 */

	private void graphEntry() {

		int[] rankArray;
		String key;

		for (int j = 0; j < keyOrder.size(); j++) {
			// Loop through names that have been input by the user //
			// In the order input by user, getting the name and array of graph
			// points //

			key = keyOrder.get(j);
			rankArray = entryHashMap.get(key);

			for (int i = 1; i < rankArray.length; i++) {
				// Loop through rankArray (points to graph each name) plotting
				// on the grid //
				// add a GLine to connect these points and a name label //

				// plot X and Y Coordinates for GLines //

				// set Y coordinate default 0 to the top margin //
				double y1 = graphHeight + GRAPH_MARGIN_SIZE;
				double y2 = graphHeight + GRAPH_MARGIN_SIZE;

				// create a GLabel with the name as the String //

				// set Y coordinates //
				if (rankArray[i - 1] != 0 && rankArray[i] != 0) {
					y1 = ((double) graphHeight / 1000 * rankArray[i - 1]) + GRAPH_MARGIN_SIZE;
					y2 = ((double) graphHeight / 1000 * rankArray[i]) + GRAPH_MARGIN_SIZE;
				} else if (rankArray[i] == 0 && rankArray[i - 1] != 0) {
					y1 = ((double) graphHeight / 1000 * rankArray[i - 1]) + GRAPH_MARGIN_SIZE;
				} else if (rankArray[i - 1] == 0 && rankArray[i] != 0) {
					y2 = ((double) graphHeight / 1000 * rankArray[i]) + GRAPH_MARGIN_SIZE;
				}

				double x1 = (i - 1) * widthBetweenLines;
				double x2 = i * widthBetweenLines;

				GLine line = new GLine(x1, y1, x2, y2);
				GLabel label = new GLabel("");
				if (rankArray[i - 1] == 0) {
					label.setLabel(key + "*");
				} else {
					label.setLabel(key);
				}

				entryCompound.add(line);
				setColor(j, line);
				entryCompound.add(label, x1, y1);
				setColor(j, label);

				// add final GLabel //
				if (i == (rankArray.length - 1)) {
					GLabel finalLabel = new GLabel(key);
					if (rankArray[i] == 0) {
						finalLabel.setLabel(key + "*");
					}
					entryCompound.add(finalLabel, x2, y2);
					setColor(j, finalLabel);
				}
			}
		}
		grid.add(entryCompound);
	}

	/* Method: setColor */
	/*
	 * Takes in two parameters, an int j and a GObject object and sets the
	 * object color alternating between four colors, based upon the value of the
	 * int j
	 */

	private void setColor(int j, GObject object) {
		int colorCode = (j + 1) % 4;
		switch (colorCode) {
		case 1:
			object.setColor(Color.black);
			break;
		case 2:
			object.setColor(Color.red);
			break;
		case 3:
			object.setColor(Color.blue);
			break;
		case 0:
			object.setColor(Color.magenta);
			break;
		}

	}

	/*
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of
	 * entries. Your application must call update after calling either clear or
	 * addEntry; update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		grid.removeAll();
		entryCompound.removeAll();

		// reset canvas height and width to be dependent upon current size of
		// canvas //
		// previously set to constants //

		canvasWidth = getWidth();
		canvasHeight = getHeight();
		graphHeight = canvasHeight - GRAPH_MARGIN_SIZE * 2;

		// add GCompound grid //
		addGrid();
		graphEntry();

	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}

	/* private instance variables */
	private GCompound grid;
	private GCompound entryCompound;
	private int widthBetweenLines;
	private int canvasHeight;
	private int canvasWidth;
	private int graphHeight; // canvasHeight minus padding on top and bottom
	private String entryName;
	private HashMap<String, int[]> entryHashMap;
	private ArrayList<String> keyOrder;

}

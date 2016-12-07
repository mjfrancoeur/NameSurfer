
/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

	/* Constructor: NameSurferEntry(line) */
	/**
	 * Creates a new NameSurferEntry from a data line as it appears in the data
	 * file. Each line begins with the name, which is followed by integers
	 * giving the rank of that name for each decade.
	 */
	public NameSurferEntry(String line) {

		rankArray = new int[NDECADES];

		int nameStart = 0;
		int nameEnd = line.indexOf(" ");
		name = line.substring(nameStart, nameEnd);

		int rankStart = nameEnd + 1;
		int rankEnd;

		for (int i = 0; i < NDECADES; i++) {

			rankEnd = line.indexOf(" ", rankStart);
			if (rankEnd == -1) {
				rank = Integer.parseInt(line.substring(rankStart));
			} else {
				rank = Integer.parseInt(line.substring(rankStart, rankEnd));
			}
			rankArray[i] = rank;
			rankStart = rankEnd + 1;
		}

	}

	/* Method: getName() */
	/**
	 * Returns the name associated with this entry.
	 */
	public String getName() {

		return name;
	}

	/* Method: getRank(decade) */
	/**
	 * Returns the rank associated with an entry for a particular decade. The
	 * decade value is an integer indicating how many decades have passed since
	 * the first year in the database, which is given by the constant
	 * START_DECADE. If a name does not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {

		switch (decade) {
		case 1900:
			decade = 0;
			break;
		case 1910:
			decade = 1;
			break;
		case 1920:
			decade = 2;
			break;
		case 1930:
			decade = 3;
			break;
		case 1940:
			decade = 4;
			break;
		case 1950:
			decade = 5;
			break;
		case 1960:
			decade = 6;
			break;
		case 1970:
			decade = 7;
			break;
		case 1980:
			decade = 8;
			break;
		case 1990:
			decade = 9;
			break;
		case 2000:
			decade = 10;
			break;
		}

		return rankArray[decade];
	}

	public int[] getRankArray() {
		return rankArray;
	}

	/* Method: toString() */
	/**
	 * Returns a string that makes it easy to see the value of a
	 * NameSurferEntry.
	 */
	public String toString() {
		return name + " " + Arrays.toString(rankArray);
	}

	/* private instance variables */
	private String name;
	private int rank;
	private int[] rankArray;

}

/**
 ** SchellingGeometry.java
 **
 ** Copyright 2011 by Sarah Wise, Mark Coletti, Andrew Crooks, and
 ** George Mason University.
 **
 ** Licensed under the Academic Free License version 3.0
 **
 ** See the file "LICENSE" for more information
 **
 ** $Id$
 **/
package sim.app.geo.dschellingspace;

import java.util.ArrayList;
import sim.util.geo.MasonGeometry;

public class SchellingGeometry extends MasonGeometry {

	private static final long serialVersionUID = 1L;

	public int initRed = 0, initBlue = 0;

	public ArrayList<DPerson> residents;
	public ArrayList<SchellingGeometry> neighbors;

	public SchellingGeometry() {
		super();
		residents = new ArrayList<DPerson>();
		neighbors = new ArrayList<SchellingGeometry>();
	}

	public void init() {
		initRed = getIntegerAttribute("RED");
		initBlue = getIntegerAttribute("BLUE");
	}

	int getID() {
		return getDoubleAttribute("ID_ID").intValue();
	}

	String getSoc() {
		return getStringAttribute("SOC");
	}

}
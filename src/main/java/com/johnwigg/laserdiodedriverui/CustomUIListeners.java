// Based on the EMU source code https://github.com/jdeschamps/EMU/blob/master/src/main/java/de/embl/rieslab/emu/ui/swinglisteners/SwingUIListeners.java

package com.johnwigg.laserdiodedriverui;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.embl.rieslab.emu.ui.ConfigurablePanel;
import de.embl.rieslab.emu.utils.EmuUtils;

public class CustomUIListeners {
	public static void addActionListenerOnDoubleValue(final ConfigurablePanel cp, final String propertyKey, final JSpinner spnr) {
		if(cp == null) {
			throw new NullPointerException("The ConfigurablePanel cannot be null.");
		}
		if(propertyKey == null) {
			throw new NullPointerException("The UIProperty's label cannot be null.");
		}
		if(spnr == null) {
			throw new NullPointerException("The JSpinner cannot be null.");
		}
		
		spnr.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {	
				double val = (double) spnr.getValue();
				cp.setUIPropertyValue(propertyKey, String.valueOf(val));
			}
	    });
	}
}
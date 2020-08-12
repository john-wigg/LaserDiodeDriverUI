package com.johnwigg.laserdiodedriverui;

import java.util.TreeMap;

import de.embl.rieslab.emu.controller.SystemController;
import de.embl.rieslab.emu.plugin.UIPlugin;
import de.embl.rieslab.emu.ui.ConfigurableMainFrame;

public class LaserDiodeDriverPlugin implements UIPlugin {

	@Override
	public ConfigurableMainFrame getMainFrame(SystemController arg0, TreeMap<String, String> arg1) {
		return new MainFrame("Laser Diode Driver UI", arg0, arg1);
	}

	@Override
	public String getName() {
		return "Laser Diode Driver UI";
	}

}
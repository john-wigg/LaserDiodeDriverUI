package com.johnwigg.laserdiodedriverui;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import de.embl.rieslab.emu.controller.SystemController;
import de.embl.rieslab.emu.ui.ConfigurableMainFrame;
import de.embl.rieslab.emu.utils.settings.IntSetting;
import de.embl.rieslab.emu.utils.settings.Setting;
import de.embl.rieslab.emu.utils.settings.StringSetting;

public class MainFrame extends ConfigurableMainFrame {
	private static final String default_path = System.getProperty("user.home") + "/LaserDriverUI"; // path of settings file (without extension)

	public final String SETTING_NUM_LASERS = "Number of Laser Diodes";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws JSONException 
	 */
	public MainFrame() {
		super("", null, null);
		initComponents();
	}
	
	public MainFrame(String arg0, SystemController arg1, TreeMap<String, String> arg2) {
		super(arg0, arg1, arg2);
	}

	@Override
	public HashMap<String, Setting> getDefaultPluginSettings() {
		HashMap<String, Setting>  settgs = new HashMap<String, Setting>();
		settgs.put(SETTING_NUM_LASERS, new IntSetting(SETTING_NUM_LASERS, "Specify the number of laser diodes (between 1 and 8).", 1));
		return settgs;
	}

	@Override
	protected String getPluginInfo() {
		return "Description of the plugin and mention of the author.";
	}

	@Override
	protected void initComponents() {
		int num_lasers = ((IntSetting) this.getCurrentPluginSettings().get(SETTING_NUM_LASERS)).getValue();
		
		getContentPane().setLayout(null);
		
		setBounds(0, 0, 1200, 300);
		
		for (int i = 0; i < num_lasers; ++i) {
			LaserPanel laserPanel = new LaserPanel(String.format("Laser Diode %d", i+1));
			laserPanel.setBounds(285 * (i % 4), 120 * (int) (i / 4), 285, 120);
			laserPanel.index = i;
			getContentPane().add(laserPanel);
		}
		
	}
}

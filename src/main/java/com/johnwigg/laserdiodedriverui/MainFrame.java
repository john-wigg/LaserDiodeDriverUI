package com.johnwigg.laserdiodedriverui;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.TreeMap;

import de.embl.rieslab.emu.controller.SystemController;
import de.embl.rieslab.emu.ui.ConfigurableMainFrame;
import de.embl.rieslab.emu.utils.settings.IntSetting;
import de.embl.rieslab.emu.utils.settings.Setting;

public class MainFrame extends ConfigurableMainFrame {

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
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		int num_lasers = ((IntSetting) this.getCurrentPluginSettings().get(SETTING_NUM_LASERS)).getValue();
		
		setBounds(0, 0, 1200, 500);
		
		for (int i = 0; i < num_lasers; ++i) {
			LaserPanel laserPanel = new LaserPanel(String.format("Laser Diode %d", i));
			laserPanel.setBounds(285 * (i % 4), 215 * (int) (i / 4), 285, 215);
			getContentPane().add(laserPanel);
		}
	}
}

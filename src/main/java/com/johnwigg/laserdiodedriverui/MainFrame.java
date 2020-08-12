package com.johnwigg.laserdiodedriverui;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.TreeMap;

import de.embl.rieslab.emu.controller.SystemController;
import de.embl.rieslab.emu.ui.ConfigurableMainFrame;
import de.embl.rieslab.emu.utils.settings.Setting;

public class MainFrame extends ConfigurableMainFrame {

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
		return settgs;
	}

	@Override
	protected String getPluginInfo() {
		return "Description of the plugin and mention of the author.";
	}

	@Override
	protected void initComponents() {
		setBounds(100, 100, 1200, 500);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		LaserPanel laserPanel = new LaserPanel("Laser Diode 1");
		laserPanel.setBounds(12, 0, 285, 215);
		getContentPane().add(laserPanel);
		
		LaserPanel laserPanel_1 = new LaserPanel("Laser Diode 2");
		laserPanel_1.setBounds(309, 0, 285, 215);
		getContentPane().add(laserPanel_1);
		
		LaserPanel laserPanel_2 = new LaserPanel("Laser Diode 3");
		laserPanel_2.setBounds(606, 0, 285, 215);
		getContentPane().add(laserPanel_2);
		
		LaserPanel laserPanel_3 = new LaserPanel("Laser Diode 4");
		laserPanel_3.setBounds(903, 0, 285, 215);
		getContentPane().add(laserPanel_3);
		
		LaserPanel laserPanel_4 = new LaserPanel("Laser Diode 5");
		laserPanel_4.setBounds(12, 214, 285, 215);
		getContentPane().add(laserPanel_4);
		
		LaserPanel laserPanel_5 = new LaserPanel("Laser Diode 6");
		laserPanel_5.setBounds(309, 214, 285, 215);
		getContentPane().add(laserPanel_5);
		
		LaserPanel laserPanel_6 = new LaserPanel("Laser Diode 7");
		laserPanel_6.setBounds(606, 214, 285, 215);
		getContentPane().add(laserPanel_6);
		
		LaserPanel laserPanel_7 = new LaserPanel("Laser Diode 8");
		laserPanel_7.setBounds(903, 214, 285, 215);
		getContentPane().add(laserPanel_7);
	}
}

package com.johnwigg.laserdiodedriverui;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.embl.rieslab.emu.controller.SystemController;
import de.embl.rieslab.emu.ui.ConfigurableMainFrame;
import de.embl.rieslab.emu.utils.settings.IntSetting;
import de.embl.rieslab.emu.utils.settings.Setting;
import de.embl.rieslab.emu.utils.settings.StringSetting;

public class MainFrame extends ConfigurableMainFrame implements RangeSettingListener {
	private static final String default_path = System.getProperty("user.home") + "/LaserDriverUI"; // path of settings file (without extension)
	
	private JSONObject settings;
	private String settings_path;

	public final String SETTING_NUM_LASERS = "Number of Laser Diodes";
	public final String SETTING_CONFIG_PATH = "Config directory path";
	
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
	
	// TODO
	private void readSettings()  {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        Object obj = null;
        
        String path = new File(settings_path + "/UISettings.json").getAbsolutePath();
         
        try (FileReader reader = new FileReader(path))
        {
            //Read JSON file
			try {
				obj = jsonParser.parse(reader);
			} catch (java.io.IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            settings = (JSONObject) obj;
 
            System.err.println(settings);
 
        } catch (FileNotFoundException e) {
            settings = new JSONObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            settings = new JSONObject();
        }
        System.err.println("Read settings:");
        System.err.println(settings);
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
		settgs.put(SETTING_CONFIG_PATH, new StringSetting(SETTING_CONFIG_PATH, "Specify the path of the config file directory.", default_path));
		return settgs;
	}

	@Override
	protected String getPluginInfo() {
		return "Description of the plugin and mention of the author.";
	}

	@Override
	protected void initComponents() {
		int num_lasers = ((IntSetting) this.getCurrentPluginSettings().get(SETTING_NUM_LASERS)).getValue();
		settings_path = ((StringSetting) this.getCurrentPluginSettings().get(SETTING_CONFIG_PATH)).getValue();
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.err.println(settings_path);
		File dir = new File(settings_path);
		boolean success = dir.mkdirs();
		System.err.println(success);
		
		readSettings();
		System.err.println("Init components...");
		getContentPane().setLayout(null);
		
		
		setBounds(0, 0, 1200, 700);
		
		for (int i = 0; i < num_lasers; ++i) {
			LaserPanel laserPanel = new LaserPanel(String.format("Laser Diode %d", i));
			laserPanel.setBounds(285 * (i % 4), 215 * (int) (i / 4), 285, 215);
			laserPanel.addRangeSettingListener(this);
			laserPanel.index = i;
			JSONObject diode_settings = new JSONObject();
			diode_settings = (JSONObject) settings.get(String.format("diode%d", i));
			if (diode_settings != null) {
				laserPanel.setRange(((Long)diode_settings.get("min")).intValue(), ((Long)diode_settings.get("max")).intValue());
			}
			getContentPane().add(laserPanel);
		}
		
	}
	
	@Override
	public void onRangeSetting(int index, boolean is_max, int value) {
		System.err.println(settings);
		System.err.printf("Hello fom panel %d. Is max? %b New value: %d%n", index, is_max, value);
		JSONObject diode_settings = new JSONObject();
		diode_settings = (JSONObject) settings.get(String.format("diode%d", index));
		if (diode_settings == null) { // Create a new key if there is no key for the diode yet
			diode_settings = new JSONObject();
			diode_settings.put("min", 0);
			diode_settings.put("max", 100);
		}
		if (is_max) {
			diode_settings.put("max", value);
		} else {
			diode_settings.put("min", value);
		}
		settings.put(String.format("diode%d", index), diode_settings);
		System.err.println(settings);
		File temp_file = new File(settings_path + "/UISettings.tmp");

		try {
			FileWriter temp_writer = new FileWriter(temp_file);
			temp_writer.write(settings.toString());
			temp_writer.flush();
			temp_writer.close();
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			File realName = new File(settings_path + "/UISettings.json");
			realName.delete();
			temp_file.renameTo(realName);
		}
	}
}

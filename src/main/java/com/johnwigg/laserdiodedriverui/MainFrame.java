package com.johnwigg.laserdiodedriverui;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.TreeMap;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.embl.rieslab.emu.controller.SystemController;
import de.embl.rieslab.emu.ui.ConfigurableMainFrame;
import de.embl.rieslab.emu.utils.settings.IntSetting;
import de.embl.rieslab.emu.utils.settings.Setting;
import mmcorej.org.json.JSONException;
import mmcorej.org.json.JSONObject;
import zmq.ZError.IOException;

public class MainFrame extends ConfigurableMainFrame implements RangeSettingListener {
	
	private JSONObject settings = new JSONObject();

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
	
	// TODO
	/*private void readSettings()  {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        Object obj = null;
         
        try (FileReader reader = new FileReader("/home/iscat/UISettings.json"))
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.io.IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}*/

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
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		int num_lasers = ((IntSetting) this.getCurrentPluginSettings().get(SETTING_NUM_LASERS)).getValue();
		
		setBounds(0, 0, 1200, 700);
		
		for (int i = 0; i < num_lasers; ++i) {
			LaserPanel laserPanel = new LaserPanel(String.format("Laser Diode %d", i));
			laserPanel.setBounds(285 * (i % 4), 215 * (int) (i / 4), 285, 215);
			laserPanel.addRangeSettingListener(this);
			laserPanel.index = i;
			getContentPane().add(laserPanel);
		}
	}
	
	@Override
	public void onRangeSetting(int index, boolean is_max, float value) {
		System.err.printf("Hello fom panel %d. Is max? %b New value: %f%n", index, is_max, value);
		JSONObject diode_settings = new JSONObject();
		try {
			diode_settings = (JSONObject) settings.get(String.format("diode%d", index));
		} catch (JSONException e) {
			diode_settings = new JSONObject();
			try {
			diode_settings.put("min", 0.0);
			diode_settings.put("max", 100.0);
			} catch (JSONException f) {
				f.printStackTrace();
			}
		}
		try {
			if (is_max) {
				diode_settings.put("max", value);
			} else {
				diode_settings.put("min", value);
			}
			settings.put(String.format("diode%d", index), diode_settings);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println(settings);
		try {
			FileWriter temp_writer = new FileWriter("/home/iscat/UISettings.tmp");
			temp_writer.write(settings.toString());
			temp_writer.flush();
			temp_writer.close();
			System.err.println(settings.toString());
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			File realName = new File("/home/iscat/UISettings.json");
			realName.delete();
			new File("/home/iscat/UISettings.tmp").renameTo(realName);
		}
	}
}

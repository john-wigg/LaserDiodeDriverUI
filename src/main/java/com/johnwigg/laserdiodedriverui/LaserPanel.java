package com.johnwigg.laserdiodedriverui;

import de.embl.rieslab.emu.ui.ConfigurablePanel;
import de.embl.rieslab.emu.ui.swinglisteners.SwingUIListeners;
import de.embl.rieslab.emu.ui.uiparameters.ColorUIParameter;
import de.embl.rieslab.emu.ui.uiparameters.StringUIParameter;
import de.embl.rieslab.emu.ui.uiproperties.TwoStateUIProperty;
import de.embl.rieslab.emu.ui.uiproperties.UIProperty;
import de.embl.rieslab.emu.ui.uiproperties.flag.NoFlag;
import de.embl.rieslab.emu.utils.EmuUtils;
import de.embl.rieslab.emu.utils.exceptions.IncorrectUIParameterTypeException;
import de.embl.rieslab.emu.utils.exceptions.IncorrectUIPropertyTypeException;
import de.embl.rieslab.emu.utils.exceptions.UnknownUIParameterException;
import de.embl.rieslab.emu.utils.exceptions.UnknownUIPropertyException;

import javax.swing.border.TitledBorder;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JPanel;

public class LaserPanel extends ConfigurablePanel {
	private JSlider slider;
	private JSlider slider_1;
	private JSlider slider_2;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JToggleButton tglbtnOnOff;
	
	public final String LASER_POWER = "power percentage";
	public final String LASER_MIN_POWER = "minimum power percentage";
	public final String LASER_MAX_POWER = "maximum power percentage";
	public final String LASER_OPERATION = "enable";
	
	public final String PARAM_TITLE = "Name";
	public final String PARAM_COLOR = "Color";
	
	public int index = 0;


	/**
	 * Create the panel.
	 * @throws JSONException 
	 */
	public LaserPanel(String title) {
		super(title);

		initComponents();

	}
	
	public void setRange(int min, int max) {
		slider.setValue(min);
		slider_2.setValue(max);
		label.setText(String.valueOf(slider.getValue()) + " %");
		label_2.setText(String.valueOf(slider_2.getValue()) + " %");
	}

	
	private void initComponents() {
		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Laser Diode", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		setLayout(null);
		
		slider = new JSlider();
		slider.setBounds(20, 23, 200, 38);
		slider.setValue(0);
		slider.setBorder(new TitledBorder(null, "Min. Laser Power", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(slider);
		
		label = new JLabel("0 %");
		label.setBounds(238, 34, 37, 15);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label);
		
		slider_1 = new JSlider();
		slider_1.setBounds(20, 66, 200, 38);
		slider_1.setValue(0);
		slider_1.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Laser Power", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		add(slider_1);
		
		label_1 = new JLabel("0 %");
		label_1.setBounds(235, 77, 40, 15);
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label_1);
		
		slider_2 = new JSlider();
		slider_2.setBounds(20, 109, 200, 38);
		slider_2.setValue(100);
		slider_2.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Max. Laser Power", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		add(slider_2);
		
		label_2 = new JLabel("100 %");
		label_2.setBounds(235, 121, 40, 15);
		add(label_2);
		
		tglbtnOnOff = new JToggleButton("On/Off");
		tglbtnOnOff.setBounds(20, 159, 79, 50);
		add(tglbtnOnOff);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Fine Tune", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel.setBounds(125, 159, 95, 50);
		add(panel);
		panel.setLayout(null);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		spinner.setBounds(29, 20, 54, 25);
		panel.add(spinner);
		
		addSliderListeners();

	}
	
	private List<RangeSettingListener> listeners = new ArrayList<RangeSettingListener>();
	private JSpinner spinner;
	public void addRangeSettingListener(RangeSettingListener toAdd) {
		listeners.add(toAdd);
	}
	
	private void notifyListeners(boolean is_max, int value) {
		for (RangeSettingListener l : listeners) {
			l.onRangeSetting(index, is_max, value);
		}
	}
	
	// add listeners to keep the sliders within their sensible boundaries
	protected void addSliderListeners() {
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (slider.getValue() > slider_2.getValue()) {
					slider.setValue(slider_2.getValue());
					label.setText(String.valueOf(slider_2.getValue()) + " %");
				}
				if (slider.getValue() > slider_1.getValue()) {
					slider_1.setValue(slider.getValue());
					label_1.setText(String.valueOf(slider_1.getValue()) + " %");
					
				}
				
				if (!slider.getValueIsAdjusting()) { // only notify listeners when the slider is let go
					notifyListeners(false, slider.getValue());
				}
			}
		});
		
		slider_2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (slider_2.getValue() < slider.getValue()) {
					slider_2.setValue(slider.getValue());
					label_2.setText(String.valueOf(slider.getValue()) + " %");
				}
				if (slider_2.getValue() < slider_1.getValue()) {
					slider_1.setValue(slider_2.getValue());
					label_1.setText(String.valueOf(slider_1.getValue()) + " %");
				}
				
				if (!slider.getValueIsAdjusting()) { // only notify listeners when the slider is let go
					notifyListeners(true, slider_2.getValue());
				}
			}
		});	
		
		slider_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (slider_1.getValue() > slider_2.getValue()) {
					slider_1.setValue(slider_2.getValue());
					label_1.setText(String.valueOf(slider_2.getValue()) + " %");
				}
				if (slider_1.getValue() < slider.getValue()) {
					slider_1.setValue(slider.getValue());
					label_1.setText(String.valueOf(slider.getValue()) + " %");
				}
				
				// Update spinner
				spinner.setValue(slider_1.getValue());
			}
		});
		
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				String propertyPower = getPanelLabel() + " " + LASER_POWER;
				slider_1.setValue((int)spinner.getValue());
				setUIPropertyValue(propertyPower, String.valueOf(slider_1.getValue()));
				label_1.setText(String.valueOf(slider_1.getValue()) + " %");
			}
		});
	}
	
	@Override
	protected void addComponentListeners() {
		String propertyMinPower = getPanelLabel() + " " + LASER_MIN_POWER;
		String propertyMaxPower = getPanelLabel() + " " + LASER_MAX_POWER;
		String propertyPower = getPanelLabel() + " " + LASER_POWER;
		String propertyOperation = getPanelLabel() + " " + LASER_OPERATION;
		
		SwingUIListeners.addActionListenerOnIntegerValue(this, propertyMinPower,  slider, label, "", " %");
		SwingUIListeners.addActionListenerOnIntegerValue(this, propertyMaxPower,  slider_2, label_2, "", " %");
		SwingUIListeners.addActionListenerOnIntegerValue(this, propertyPower,  slider_1, label_1, "", " %");
		
		try {
			SwingUIListeners.addActionListenerToTwoState(this, propertyOperation, tglbtnOnOff);
		} catch (IncorrectUIPropertyTypeException e) {
			e.printStackTrace();
		}
	}	

	@Override
	public String getDescription() {
		return "Panel controlling a signel Laser laser diode.";
	}

	@Override
	protected void initializeInternalProperties() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initializeParameters() {
		addUIParameter(new StringUIParameter(this, PARAM_TITLE, "Panel title.", getPanelLabel()));
		addUIParameter(new ColorUIParameter(this, PARAM_COLOR, "laser diode color.", Color.black));
	}

	@Override
	protected void initializeProperties() {
		String text1 = "Property changing the minimum settable power percentage of the laser diode.";
		String text2 = "Property changing the maximum settable power percentage of the laser diode.";
		String text3 = "Property changing the power percentage of the laser diode.";
		String text4 = "Property turning the laser diode on/off";
		
		String propertyMinPower = getPanelLabel() + " " + LASER_MIN_POWER;
		String propertyMaxPower = getPanelLabel() + " " + LASER_MAX_POWER;
		String propertyPower = getPanelLabel() + " " + LASER_POWER;
		String propertyOperation = getPanelLabel() + " " + LASER_OPERATION;
		
		addUIProperty(new UIProperty(this, propertyMinPower, text1));
		addUIProperty(new UIProperty(this, propertyMaxPower, text2));
		addUIProperty(new UIProperty(this, propertyPower, text3));
		addUIProperty(new TwoStateUIProperty(this, propertyOperation, text4));

	}

	@Override
	public void internalpropertyhasChanged(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void parameterhasChanged(String parameterName) {
		if (PARAM_TITLE.equals(parameterName)) {
			try {
				((TitledBorder) this.getBorder())
	                	.setTitle(getStringUIParameterValue(PARAM_TITLE));
				this.repaint();
			} catch (UnknownUIParameterException e) {
				e.printStackTrace();			
	        }
	    } else if (PARAM_COLOR.equals(parameterName)) {
			try {
				((TitledBorder) this.getBorder())
	               	.setTitleColor(getColorUIParameterValue(PARAM_COLOR));
				this.repaint();
			} catch (IncorrectUIParameterTypeException | UnknownUIParameterException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void propertyhasChanged(String propertyName, String newValue) {
		String propertyMinPower = getPanelLabel() + " " + LASER_MIN_POWER;
		String propertyMaxPower = getPanelLabel() + " " + LASER_MAX_POWER;
		String propertyPower = getPanelLabel() + " " + LASER_POWER;
		String propertyOperation = getPanelLabel() + " " + LASER_OPERATION;
		
		if(propertyMinPower.equals(propertyName)) {
			if (EmuUtils.isNumeric(newValue)) {
				int val = (int) Double.parseDouble(newValue);
				
				if (val >= 0 && val <= 100) {
					slider.setValue(val);
					label.setText(String.valueOf(val) + " %");
				}
			}
		} else if(propertyMaxPower.equals(propertyName)) {
			if (EmuUtils.isNumeric(newValue)) {
				int val = (int) Double.parseDouble(newValue);
				
				if (val >= 0 && val <= 100) {			
					slider_2.setValue(val);
					label_2.setText(String.valueOf(val) + " %");
				}
			}
		} else if(propertyPower.equals(propertyName)) {
			if (EmuUtils.isNumeric(newValue)) {
				int val = (int) Double.parseDouble(newValue);
				
				if (val >= 0 && val <= 100) {
					slider_1.setValue(val);
					label_1.setText(String.valueOf(val) + " %");
				}
			}
		} else if (propertyOperation.equals(propertyName)) {
			try {
				String onValue = ((TwoStateUIProperty) getUIProperty(propertyOperation)).getOnStateValue();
				tglbtnOnOff.setSelected(newValue.equals(onValue));
			} catch (UnknownUIPropertyException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
		
	}

	protected JSlider getSlider() {
		return slider;
	}
	protected JSlider getSlider_2() {
		return slider_1;
	}
	protected JSlider getSlider_1() {
		return slider_2;
	}
	protected JLabel getLabel() {
		return label;
	}
	protected JLabel getLabel_1() {
		return label_1;
	}
	protected JLabel getLabel_2() {
		return label_2;
	}
	protected JToggleButton getTglbtnNewToggleButton() {
		return tglbtnOnOff;
	}
}

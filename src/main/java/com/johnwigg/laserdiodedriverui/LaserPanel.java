package com.johnwigg.laserdiodedriverui;

import de.embl.rieslab.emu.ui.ConfigurablePanel;
import de.embl.rieslab.emu.ui.swinglisteners.SwingUIListeners;
import de.embl.rieslab.emu.ui.uiparameters.ColorUIParameter;
import de.embl.rieslab.emu.ui.uiparameters.StringUIParameter;
import de.embl.rieslab.emu.ui.uiproperties.TwoStateUIProperty;
import de.embl.rieslab.emu.ui.uiproperties.UIProperty;
import de.embl.rieslab.emu.utils.EmuUtils;
import de.embl.rieslab.emu.utils.exceptions.IncorrectUIParameterTypeException;
import de.embl.rieslab.emu.utils.exceptions.IncorrectUIPropertyTypeException;
import de.embl.rieslab.emu.utils.exceptions.UnknownUIParameterException;
import de.embl.rieslab.emu.utils.exceptions.UnknownUIPropertyException;

import javax.swing.border.TitledBorder;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JPanel;
import java.awt.Font;

public class LaserPanel extends ConfigurablePanel {
	private JLabel label;
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
	
	private void initComponents() {
		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Laser Diode", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		setLayout(null);
		
		label = new JLabel("0 %");
		label.setBounds(251, 167, 37, 15);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label);
		
		label_2 = new JLabel("100 %");
		label_2.setBounds(248, 194, 40, 15);
		add(label_2);
		
		tglbtnOnOff = new JToggleButton("On/Off");
		tglbtnOnOff.setBounds(187, 44, 79, 81);
		add(tglbtnOnOff);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Fine Tune", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel.setBounds(12, 23, 139, 113);
		add(panel);
		panel.setLayout(null);
		
		spinner = new JSpinner();
		spinner.setFont(new Font("Dialog", Font.PLAIN, 24));
		spinner.setModel(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.01));
		spinner.setBounds(29, 20, 98, 34);
		panel.add(spinner);

	}
	
	private JSpinner spinner;
	
	@Override
	protected void addComponentListeners() {
		String propertyMinPower = getPanelLabel() + " " + LASER_MIN_POWER;
		String propertyMaxPower = getPanelLabel() + " " + LASER_MAX_POWER;
		String propertyPower = getPanelLabel() + " " + LASER_POWER;
		String propertyOperation = getPanelLabel() + " " + LASER_OPERATION;
		
		CustomUIListeners.addActionListenerOnDoubleValue(this, propertyMinPower,  spinner);
		
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
				double val = Double.parseDouble(newValue);
				
				if (val >= 0 && val <= 100) {
					label.setText(String.valueOf(val) + " %");
					((SpinnerNumberModel)spinner.getModel()).setMinimum(val);
				}
			}
		} else if(propertyMaxPower.equals(propertyName)) {
			if (EmuUtils.isNumeric(newValue)) {
				double val = Double.parseDouble(newValue);
				
				if (val >= 0 && val <= 100) {			
					label_2.setText(String.valueOf(val) + " %");
					((SpinnerNumberModel)spinner.getModel()).setMaximum(val);
				}
			}
		} else if(propertyPower.equals(propertyName)) {
			if (EmuUtils.isNumeric(newValue)) {
				double val = Double.parseDouble(newValue);
				
				if (val >= 0 && val <= 100) {
					spinner.setValue(val);
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

	protected JLabel getLabel() {
		return label;
	}
	protected JLabel getLabel_2() {
		return label_2;
	}
	protected JToggleButton getTglbtnNewToggleButton() {
		return tglbtnOnOff;
	}
}

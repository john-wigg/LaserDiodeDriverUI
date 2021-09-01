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
	private JSpinner spinnerPower;
	private JToggleButton tglbtnOnOff;
	
	public final String LASER_LABEL = "label";
	public final String LASER_POWER = "power percentage";
	public final String LASER_OPERATION = "enable";
	
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
		
		tglbtnOnOff = new JToggleButton("On");
		tglbtnOnOff.setBounds(167, 31, 79, 68);
		add(tglbtnOnOff);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Laser Power", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel_1.setBounds(20, 31, 135, 68);
		add(panel_1);
		
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0.0, 0.0, 100.0, 0.1);  
		spinnerPower = new JSpinner(spinnerModel);
		spinnerPower.setFont(new Font("Dialog", Font.PLAIN, 22));
		spinnerPower.setBounds(12, 20, 111, 36);
		panel_1.add(spinnerPower);
		
		addSliderListeners();
	}
	
	// add listeners to keep the sliders within their sensible boundaries
	protected void addSliderListeners() {
	}
	
	@Override
	protected void addComponentListeners() {
		String propertyPower = getPanelLabel() + " " + LASER_POWER;
		String propertyOperation = getPanelLabel() + " " + LASER_OPERATION;
		
		SwingUIListeners.addChangeListenerOnNumericalValue(this, propertyPower, spinnerPower);
		
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
		addUIParameter(new ColorUIParameter(this, PARAM_COLOR, "laser diode color.", Color.black));
	}

	@Override
	protected void initializeProperties() {
		String text1 = "Property containing the label/name of the laser duide.";
		String text2 = "Property changing the power percentage of the laser diode.";
		String text3 = "Property turning the laser diode on/off";
		
		String propertyLabel = getPanelLabel() + " " + LASER_LABEL;
		String propertyPower = getPanelLabel() + " " + LASER_POWER;
		String propertyOperation = getPanelLabel() + " " + LASER_OPERATION;
		
		addUIProperty(new UIProperty(this, propertyLabel, text1));
		addUIProperty(new UIProperty(this, propertyPower, text2));
		addUIProperty(new TwoStateUIProperty(this, propertyOperation, text3));
	}

	@Override
	public void internalpropertyhasChanged(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void parameterhasChanged(String parameterName) {
		if (PARAM_COLOR.equals(parameterName)) {
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
		String propertyPower = getPanelLabel() + " " + LASER_POWER;
		String propertyOperation = getPanelLabel() + " " + LASER_OPERATION;
		String propertyLabel = getPanelLabel() + " " + LASER_LABEL;
		
		if(propertyPower.equals(propertyName)) {
			if (EmuUtils.isNumeric(newValue)) {
				double val = Double.parseDouble(newValue.replace(",", "."));
				
				if (val >= 0 && val <= 100) {
					spinnerPower.setValue(val);
				}
			}
		} else if (propertyOperation.equals(propertyName)) {
			try {
				String onValue = ((TwoStateUIProperty) getUIProperty(propertyOperation)).getOnStateValue();
				tglbtnOnOff.setSelected(newValue.equals(onValue));
			} catch (UnknownUIPropertyException e) {
				e.printStackTrace();
			}
		} else if (propertyLabel.equals(propertyName)) {
			((TitledBorder) this.getBorder()).setTitle(newValue);
			this.repaint();
			// TODO: Change border label.
		}
	}
	
	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
		
	}

	protected JSpinner getSpinnerPower() {
		return spinnerPower;
	}
	protected JToggleButton getTglbtnNewToggleButton() {
		return tglbtnOnOff;
	}
}

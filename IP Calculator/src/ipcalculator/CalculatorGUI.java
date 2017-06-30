package ipcalculator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalculatorGUI {	
	private JFrame frame;
	private JLabel hostip, subnet, net, broad, range, netBit, useHost, bits;
	private JTextField hostF, subnetF, netF, broadF, rangeF, useHostF;
	private JSlider subnetS;
	private GridBagLayout gridBagLayout;
	private IPCalculator ipc = new IPCalculator();
	
	public CalculatorGUI() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e){}
		
		frame = new JFrame("IP Calculator");
		frame.setSize(390, 270);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gridBagLayout = new GridBagLayout();
		frame.setLayout(gridBagLayout);
		gridBagLayout.columnWidths = new int[]{0, 180, 80};
		
		GridBagConstraints host_gbc = new GridBagConstraints();
		
		hostip = new JLabel("Host IP: ", SwingConstants.LEFT);
		host_gbc.insets = new Insets(0, 0, 5, 5);
		host_gbc.gridx = 0;
		host_gbc.gridy = 1;
		frame.add(hostip, host_gbc);
		
		hostF = new JTextField();
		host_gbc.insets = new Insets(0, 0, 5, 5);
		host_gbc.gridx = 1;
		host_gbc.gridy = 1;
		host_gbc.gridwidth = 3;
		host_gbc.fill = GridBagConstraints.HORIZONTAL;
		frame.add(hostF, host_gbc);
		hostF.setText("192.168.1.1");
		hostF.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent event) {
				Thread thread = new Thread() {
					public void run() {
						ipCal();
					}
				}; thread.start();
			}
		});
		
		GridBagConstraints sub_gbc = new GridBagConstraints();
		
		subnet = new JLabel("Subnet: ", SwingConstants.LEFT);
		sub_gbc.insets = new Insets(0, 0, 5, 5);
		sub_gbc.gridx = 0;
		sub_gbc.gridy = 2;
		frame.add(subnet, sub_gbc);
		
		subnetF = new JTextField();
		sub_gbc.insets = new Insets(0, 0, 5, 5);
		sub_gbc.gridx = 1;
		sub_gbc.gridy = 2;
		sub_gbc.gridwidth = 3;
		sub_gbc.fill = GridBagConstraints.HORIZONTAL;
		frame.add(subnetF, sub_gbc);
		subnetF.setEditable(false);
		subnetF.setText("255.0.0.0");
		
		GridBagConstraints net_gbc = new GridBagConstraints();
		
		net = new JLabel("Network: ", SwingConstants.LEFT);
		net_gbc.insets = new Insets(0, 0, 5, 5);
		net_gbc.gridx = 0;
		net_gbc.gridy = 3;
		frame.add(net, net_gbc);
		
		netF = new JTextField();
		net_gbc.insets = new Insets(0, 0, 5, 5);
		net_gbc.gridx = 1;
		net_gbc.gridy = 3;
		net_gbc.gridwidth = 3;
		net_gbc.fill = GridBagConstraints.HORIZONTAL;
		frame.add(netF, net_gbc);
		netF.setEditable(false);
		
		GridBagConstraints broad_gbc = new GridBagConstraints();
		
		broad = new JLabel("Broadcast: ", SwingConstants.LEFT);
		broad_gbc.insets = new Insets(0, 0, 5, 5);
		broad_gbc.gridx = 0;
		broad_gbc.gridy = 4;
		frame.add(broad, broad_gbc);
		
		broadF = new JTextField();
		broad_gbc.insets = new Insets(0, 0, 5, 5);
		broad_gbc.gridx = 1;
		broad_gbc.gridy = 4;
		broad_gbc.gridwidth = 3;
		broad_gbc.fill = GridBagConstraints.HORIZONTAL;
		frame.add(broadF, broad_gbc);
		broadF.setEditable(false);
		
		GridBagConstraints range_gbc = new GridBagConstraints();
		
		range = new JLabel("IP range: ", SwingConstants.LEFT);
		range_gbc.insets = new Insets(0, 0, 5, 5);
		range_gbc.gridx = 0;
		range_gbc.gridy = 5;
		frame.add(range, range_gbc);
		
		rangeF = new JTextField();
		range_gbc.insets = new Insets(0, 0, 5, 5);
		range_gbc.gridx = 1;
		range_gbc.gridy = 5;
		range_gbc.gridwidth = 3;
		range_gbc.fill = GridBagConstraints.HORIZONTAL;
		frame.add(rangeF, range_gbc);
		rangeF.setEditable(false);
		
		GridBagConstraints useHost_gbc = new GridBagConstraints();
		
		useHost = new JLabel("Usable hosts: ", SwingConstants.LEFT);
		useHost_gbc.insets = new Insets(0, 0, 5, 5);
		useHost_gbc.gridx = 0;
		useHost_gbc.gridy = 6;
		frame.add(useHost, useHost_gbc);
		
		useHostF = new JTextField();
		useHost_gbc.insets = new Insets(0, 0, 5, 5);
		useHost_gbc.gridx = 1;
		useHost_gbc.gridwidth = 3;
		useHost_gbc.fill = GridBagConstraints.HORIZONTAL;
		frame.add(useHostF, useHost_gbc);
		useHostF.setEditable(false);
		
		GridBagConstraints netBit_gbc = new GridBagConstraints();
		
		netBit = new JLabel("Network bits: ", SwingConstants.LEFT);
		netBit_gbc.insets = new Insets(0, 0, 5, 5);
		netBit_gbc.gridx = 0;
		netBit_gbc.gridy = 7;
		frame.add(netBit, netBit_gbc);
		
		subnetS = new JSlider(JSlider.HORIZONTAL, 0, 24, 1);
		netBit_gbc.insets = new Insets(0, 0, 5, 5);
		netBit_gbc.gridx = 1;
		netBit_gbc.gridy = 7;
		netBit_gbc.fill = GridBagConstraints.HORIZONTAL;
		frame.add(subnetS, netBit_gbc);
		subnetS.setValue(16);
		subnetS.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Thread thread = new Thread() {
					public void run() {
						ipCal();
					}
				}; thread.start();
			}
		});
		
		bits = new JLabel("", SwingConstants.LEFT);
		netBit_gbc.insets = new Insets(0, 0, 5, 5);
		netBit_gbc.gridx = 2;
		netBit_gbc.gridy = 7;
		frame.add(bits, netBit_gbc);
		bits.setText((subnetS.getValue() + 8) + " Bits");
				
		frame.setVisible(true);
	}
	
	private void ipCal() {
		int bit = subnetS.getValue() + 8;
		if(!ipc.checkIpAddress(hostF.getText()))
			return;
		bits.setText(bit + " Bits");
		subnetF.setText(ipc.getSubnet(bit));
		netF.setText(ipc.getNetworkAddress());
		broadF.setText(ipc.getBroadCast());
		rangeF.setText(ipc.getHostRange());
		useHostF.setText(ipc.getUsableHost(bit));		
	}
}

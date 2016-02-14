package com.arinerron.tools.spam;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import javax.swing.*;

    /////////////////////////////////////////////////
    //                                             //
    //  |=======================================|  //
    //  | Author: Arinerron                     |  //
    //  | Message: Enjoy spamming people! :P    |  //
    //  |=======================================|  //
    //                                             //
    /////////////////////////////////////////////////

public class GUI extends JFrame {

	private JButton jStart;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JSpinner jTimes;
	private JSpinner jDelay;
	private JSpinner jTime;
	private JTextField jmessage;

	public GUI() {
		this.init();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void init() {

		jLabel1 = new JLabel();
		jmessage = new JTextField();
		jTimes = new JSpinner();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
		jDelay = new JSpinner();
		jLabel4 = new JLabel();
		jTime = new JSpinner();
		jStart = new JButton();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Spammer by Arinerron");
		setAlwaysOnTop(true);
		setResizable(false);

		jLabel1.setText("Message:");

		jmessage.setText("");

		jTimes.setModel(new SpinnerNumberModel(30, 0, 9999, 5));

		jLabel2.setText("Times to spam it:");

		jLabel3.setText("Millisecond delay between key presses (0 recommended):");

		jDelay.setModel(new SpinnerNumberModel(7, 0, 100, 1));

		jLabel4.setText("Second delay before spamming starts:");

		jTime.setModel(new SpinnerNumberModel(0, 0, 1000, 10));

		jStart.setText("Begin annoying people >:)");

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jmessage)
								.addComponent(jDelay).addComponent(jTimes)
								.addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
						.addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jStart, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jTime)).addContainerGap()));
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addContainerGap().addComponent(jLabel1)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jmessage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel2)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jTimes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel4)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jDelay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE).addComponent(jStart)
				.addContainerGap()));

		jStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = jmessage.getText();
				int times = (int) jTimes.getValue();
				int delay = (int) jDelay.getValue();
				int time = (int) jTime.getValue();
				dispose();
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						JFrame frame = new JFrame("Click to cancel");
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.setSize(300, 100);
						JButton jn = new JButton("[ Cancel Spam ]");
						frame.setAlwaysOnTop(true);
						jn.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								System.exit(0);
							}
						});
						jn.setLayout(null);
						JPanel jk = new JPanel();
						jk.add(jn);
						frame.add(jk);
						frame.setLocationRelativeTo(null);
						frame.setVisible(true);
					}
				});
				t.start();
				spam(msg, times, delay, time);
			}
		});

		this.pack();
	}

	public static void spam(String message, int times, int delay, int time) {
		System.out.println("Starting spam...");
		Robot r = null;

		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(delay * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long i = 0;
		while (i < times) {
			String smessage = message;
			String[] cc = smessage.split("/!");
			int ii = 0;
			String append = "";
			for (String c : cc) {
				if (ii % 2 == 0) {
					append += c;
				} else {
					append += " " + eval(c.replaceAll("i", new Long(i).toString()));
				}
				ii++;
			}

			smessage = smessage.replaceAll("!i!", new Long(i).toString());
			type(append, r, time);

			enter(r);
			i++;
		}
		System.exit(0);
	}

	private static long eval(String i) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		try {
			return new Long(Integer.parseInt("" + engine.eval(i)));
		} catch (ScriptException e) {
			e.printStackTrace();
			try {
				return Long.parseLong(i);
			} catch (Exception ee) {
				ee.printStackTrace();
				return -1;
			}
		}
	}

	private static void type(String s, Robot r, int delay) {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isUpperCase(c)) {
				r.keyPress(KeyEvent.VK_SHIFT);
			}
			r.keyPress(Character.toUpperCase(c));
			r.keyRelease(Character.toUpperCase(c));

			if (Character.isUpperCase(c)) {
				r.keyRelease(KeyEvent.VK_SHIFT);
			}
		}
		System.out.println("delay=" + delay);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void enter(Robot r) {
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
	}

	public static void main(String args[]) {
		GUI gui = new GUI();
	}
}

package com.modsim;

import com.modsim.gui.GUI;
import com.modsim.simulator.Sim;
import com.modsim.operations.OperationStack;
import com.modsim.util.ModuleClipboard;
import com.modsim.util.Selection;
import com.modsim.util.XMLReader;

import java.io.File;

import javax.swing.*;

/**
 * Just does initialisation for the program
 * @author aw12700
 *
 */
public class Main {

	public static GUI ui = null;
	public static Sim sim = null;

	public static OperationStack opStack = null;
	public static ModuleClipboard clipboard = null;
	public static Selection selection = null;

	/**
	 * Program starting point
	 * @param args System argument
	 */
	public static void main(String[] args) {
		// Set up GUI thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// (In the new thread)
				ui = new GUI();
				ui.generateUI();
				ui.showUI(true);

				// Start sim ticking - sim is initialized below *before* this is called
				sim.newSim();
                sim.start();

                if (args.length > 0) {
                    try {
                        File file = new File(args[0]);
                        XMLReader.readFile(file);
                    }
                    catch (Exception e) {
                        System.err.print("Could not read file: \"" + args[0] + "\": " + e.getMessage());
                    }
                }
			}
		});

		// Set up operation stack & clipboard
		opStack = new OperationStack();
		clipboard = new ModuleClipboard();
		selection = new Selection(true);

		// Set up simulator
		sim = new Sim();
	}

}

import backend.Server;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

/*
  The setBounds(int xaxis, int yaxis, int width, int height) is used in 
  the above example that sets the position of the button.
*/

public class Client {
	JFrame f;
	JLabel l0, l1, l2, l3, l4, l5, l6;
	JFileChooser jfc;
	int n_sets = 4;
	int n_blocks_per_set = 2;
	String[] outputs = new String[5];
	String[] cols = { "Set", "Block", "Value" };
	// public static Object[][] data = { { 0, 0, 3 }, { 0, 1, 4 }, { 1, 0, 3 }, { 1,
	// 1, 3 }, { 2, 0, 3 }, { 2, 1, 3 },
	// { 3, 0, 3 }, { 3, 1, 3 } };
	public static ArrayList<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();
	public static String[][] data;
	public static DefaultTableModel model;
	public Server backend;

	public Client() {
		f = new JFrame(); // creating instance of JFrame
		model = new DefaultTableModel();
		model.setColumnIdentifiers(cols);

		// Output Side Componenets
		l0 = new JLabel("Statistics");
		l0.setBounds(50, 30, 200, 30);
		l0.setFont(new Font("Helvetica", Font.BOLD, 22));

		l1 = new JLabel("Cache Hits: ");
		l1.setBounds(50, 80, 200, 30);
		l1.setFont(new Font("Helvetica", Font.BOLD, 16));

		l2 = new JLabel("Cache Miss: ");
		l2.setBounds(50, 130, 200, 30);
		l2.setFont(new Font("Helvetica", Font.BOLD, 16));

		l3 = new JLabel("Miss Penalty: ");
		l3.setBounds(50, 180, 200, 30);
		l3.setFont(new Font("Helvetica", Font.BOLD, 16));

		l4 = new JLabel("Avg. Memory Access Time: ");
		l4.setBounds(50, 230, 400, 30);
		l4.setFont(new Font("Helvetica", Font.BOLD, 16));

		l5 = new JLabel("Total Memory Access Time: ");
		l5.setBounds(50, 280, 400, 30);
		l5.setFont(new Font("Helvetica", Font.BOLD, 16));

		l6 = new JLabel("Cache Table");
		l6.setBounds(50, 330, 200, 30);
		l6.setFont(new Font("Helvetica", Font.BOLD, 22));

		// save to text file button

		JButton btn1 = new JButton("Save to file");
		btn1.setBounds(330, 330, 120, 30);

		btn1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					saveToText();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// build JTable
		JScrollPane sp = Table();

		// Input Side Componenets
		JRadioButton blocks1 = new JRadioButton("blocks");
		blocks1.setBounds(385, 75, 80, 25);
		blocks1.setBackground(Color.lightGray);
		JRadioButton words1 = new JRadioButton("words");
		words1.setBounds(475, 75, 80, 25);
		words1.setBackground(Color.lightGray);

		JRadioButton blocks2 = new JRadioButton("blocks");
		blocks2.setBounds(385, 120, 80, 25);
		blocks2.setBackground(Color.lightGray);
		JRadioButton words2 = new JRadioButton("words");
		words2.setBounds(475, 120, 80, 25);
		words2.setBackground(Color.lightGray);

		JRadioButton block1 = new JRadioButton("block");
		block1.setBounds(385, 385, 80, 25);
		block1.setBackground(Color.lightGray);
		JRadioButton hex1 = new JRadioButton("hex");
		hex1.setBounds(475, 385, 80, 25);
		hex1.setBackground(Color.lightGray);

		JRadioButton notLT = new JRadioButton("non L/T");
		notLT.setBounds(20, 430, 80, 25);
		notLT.setBackground(Color.lightGray);
		JRadioButton isLT = new JRadioButton("LT");
		isLT.setBounds(110, 430, 80, 25);
		isLT.setBackground(Color.lightGray);

		ButtonGroup form0 = new ButtonGroup();
		blocks1.setSelected(true);
		form0.add(blocks1);
		form0.add(words1);

		ButtonGroup form1 = new ButtonGroup();
		blocks2.setSelected(true);
		form1.add(blocks2);
		form1.add(words2);

		ButtonGroup form6 = new ButtonGroup();
		block1.setSelected(true);
		form6.add(block1);
		form6.add(hex1);

		ButtonGroup form7 = new ButtonGroup();
		notLT.setSelected(true);
		form7.add(notLT);
		form7.add(isLT);

		JLabel labelTitle = new JLabel("Block Set Associative MRU");
		labelTitle.setBounds(20, 30, 300, 25);
		labelTitle.setFont(new Font("Helvetica", Font.BOLD, 22));

		JLabel labelZero = new JLabel("Main Memory Size");
		labelZero.setBounds(20, 75, 180, 25);
		labelZero.setFont(new Font("Helvetica", Font.BOLD, 16));
		JTextField inputZero = new JTextField("");
		inputZero.setBounds(210, 75, 165, 25);

		JLabel labelOne = new JLabel("Cache Size");
		labelOne.setBounds(20, 120, 180, 25);
		labelOne.setFont(new Font("Helvetica", Font.BOLD, 16));

		JTextField inputOne = new JTextField("");
		inputOne.setBounds(210, 120, 165, 25);

		JLabel labelTwo = new JLabel("Set Size");
		labelTwo.setBounds(20, 165, 170, 25);
		labelTwo.setFont(new Font("Helvetica", Font.BOLD, 16));

		JTextField inputTwo = new JTextField("");
		inputTwo.setBounds(210, 165, 165, 25);

		JLabel labelThree = new JLabel("Block Size (words)");
		labelThree.setBounds(20, 210, 170, 25);
		labelThree.setFont(new Font("Helvetica", Font.BOLD, 16));

		JTextField inputThree = new JTextField("");
		inputThree.setBounds(210, 210, 165, 25);

		JLabel labelFour = new JLabel("Cache Access Time");
		labelFour.setBounds(20, 255, 170, 25);
		labelFour.setFont(new Font("Helvetica", Font.BOLD, 16));

		JTextField inputFour = new JTextField("");
		inputFour.setBounds(210, 255, 165, 25);

		JLabel labelFive = new JLabel("Memory Access Time");
		labelFive.setBounds(20, 300, 170, 25);
		labelFive.setFont(new Font("Helvetica", Font.BOLD, 16));

		JTextField inputFive = new JTextField("");
		inputFive.setBounds(210, 300, 165, 25);

		JLabel labelSix = new JLabel("MM Block Sequence");
		labelSix.setFont(new Font("Helvetica", Font.BOLD, 16));
		labelSix.setBounds(20, 345, 200, 25);

		JTextField inputSix = new JTextField("");
		inputSix.setBounds(20, 385, 355, 25);

		JButton submitBtn = new JButton();
		submitBtn.setText("Submit");
		submitBtn.setBounds(20, 495, 100, 40);

		submitBtn.addActionListener(new java.awt.event.ActionListener() {

			// String mmSize = inputZero.getText();

			boolean mBlock;
			boolean cBlock;
			boolean seqBlock;
			boolean ltBlock;

			int mSize;
			int cSize;
			int sSize;
			int bSize;
			float cTime;
			float mTime;

			String gSeq = inputSix.getText();

			public void actionPerformed(java.awt.event.ActionEvent evt) {

				if (blocks1.isSelected()) {
					mBlock = true;

				} else {
					mBlock = false;

				}

				if (blocks2.isSelected()) {
					cBlock = true;

				} else {
					cBlock = false;

				}

				if (block1.isSelected()) {
					seqBlock = true;

				} else {
					seqBlock = false;

				}

				if (notLT.isSelected()) {
					ltBlock = false;

				} else {
					ltBlock = true;

				}

				if (evt.getSource() == submitBtn) {
					mSize = Integer.parseInt(inputZero.getText());

					cSize = Integer.parseInt(inputOne.getText());

					sSize = Integer.parseInt(inputTwo.getText());

					bSize = Integer.parseInt(inputThree.getText());

					cTime = Float.parseFloat(inputFour.getText());

					mTime = Float.parseFloat(inputFive.getText());

					gSeq = inputSix.getText();

				}
				System.out.printf("%d %d  %d %d %f %f %s %b %b %b %b", mSize, cSize, sSize, bSize, cTime, mTime, gSeq, mBlock,
						cBlock, seqBlock, ltBlock);

				try {
					// clear table
					model.setRowCount(0);

					// clear data list (for text file)
					dataLst.clear();

					// get output
					backend = new Server(seqBlock, gSeq, mBlock, mSize, cBlock, cSize, bSize, sSize, cTime, mTime, ltBlock);
					// backend = new Server(false, "200 204 208 20C 2F4 2F0 200 204 218 21C 24C
					// 2F4", true, 8, false, 32, 4, 4, 1,
					// 10, false);
					populateOutputsLst(); // stats
					populateDataLst(); // table
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		JPanel inPanel = new JPanel();
		inPanel.setBackground(Color.lightGray);
		inPanel.setBounds(0, 0, 600, 800);

		JPanel outPanel = new JPanel();
		outPanel.setBackground(Color.gray);
		outPanel.setBounds(600, 0, 600, 800);

		inPanel.add(labelTitle);
		inPanel.add(words1);
		inPanel.add(blocks1);
		inPanel.add(words2);
		inPanel.add(blocks2);
		inPanel.add(block1);
		inPanel.add(hex1);
		inPanel.add(labelZero);
		inPanel.add(inputZero);
		inPanel.add(labelOne);
		inPanel.add(inputOne);
		inPanel.add(labelTwo);
		inPanel.add(inputTwo);
		inPanel.add(labelThree);
		inPanel.add(inputThree);
		inPanel.add(labelFour);
		inPanel.add(inputFour);
		inPanel.add(labelFive);
		inPanel.add(inputFive);
		inPanel.add(labelSix);
		inPanel.add(inputSix);
		inPanel.add(notLT);
		inPanel.add(isLT);
		inPanel.add(submitBtn);
		inPanel.setLayout(null);

		outPanel.add(l0);
		outPanel.add(l1);
		outPanel.add(l2);
		outPanel.add(l3);
		outPanel.add(l4);
		outPanel.add(l5);
		outPanel.add(l6);
		outPanel.add(sp);
		outPanel.setLayout(null);
		outPanel.add(btn1);

		f.add(inPanel);
		f.add(outPanel);
		f.setSize(1200, 800); // 400 width and 500 height
		f.setLayout(null); // using no layout managers
		// handle close button
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true); // making the frame visible
	}

	public JScrollPane Table() {
		JTable jt = new JTable(model);

		// tableModel.addRow(data2);

		JScrollPane sp = new JScrollPane(jt);
		sp.setBounds(50, 380, 400, 300);

		return sp;
	}

	public void saveToText(/* byte[] data */) throws IOException {
		jfc = new JFileChooser();
		int returnVal = jfc.showSaveDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String dir = jfc.getSelectedFile().getAbsolutePath();
			String filepath = dir + ".txt";
			Path path = Paths.get(filepath);
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath), "utf-8"))) {
				writer.write("Cache Hits: " + outputs[0] + "\r\n");
				writer.write("Cache Miss: " + outputs[1] + "\r\n");
				writer.write("Miss Penalty: " + outputs[2] + "\r\n");
				writer.write("Avg. Memory Access Time: " + outputs[3] + "\r\n");
				writer.write("Total Memory Access Time: " + outputs[4] + "\r\n");

				writer.write("\r\n");
				writer.write("CACHE MEMORY" + "\r\n\r\n");
				// Write column names
				writer.write("|");
				for (int i = 0; i < cols.length; i++) {
					if (i == cols.length - 1) {
						writer.write("  " + cols[i]);
					} else {
						writer.write("  " + cols[i] + "  |");
					}
				}
				writer.write("\r\n");

				// Write rows
				for (int i = 0; i < dataLst.size(); i++) {
					writer.write("|");
					for (int j = 0; j < dataLst.get(i).size(); j++) {
						if (j == 2) { // if last col
							writer.write("   " + (dataLst.get(i)).get(j));
						} else if (j == 1) {
							writer.write("    " + (dataLst.get(i)).get(j) + "    |");
						} else {
							writer.write("   " + (dataLst.get(i)).get(j) + "   |");
						}
					}
					writer.write("\r\n");
				}

				writer.close();
			}
			// Files.write(path, data);
			System.out.println(dataLst);
			System.out.println("SAVIED TEXT FILE!");
		}
	}

	public void populateDataLst() {
		for (int i = 0; i < backend.setSize; i++) {
			for (int j = 0; j < backend.blockPerSet; j++) {
				ArrayList<String> row = new ArrayList<String>();
				row.add(Integer.toString(i)); // add set number
				row.add(Integer.toString(j)); // add block number
				if (backend.seqIsBlock == true) { // using different cache
					row.add(Integer.toString(backend.cache[i][j]));
				} else {
					row.add(backend.cachehex[i][j]);
				}
				System.out.print(row);
				dataLst.add(row);
				model.addRow(row.toArray()); // display to JTable
				// row.clear(); // empty list
			}
		}
	}

	public void populateOutputsLst() {
		outputs[0] = Integer.toString(backend.nHit);
		outputs[1] = Integer.toString(backend.nMiss);
		outputs[2] = Float.toString(backend.missPenalty);
		outputs[3] = Float.toString(backend.AvgMMtime);
		outputs[4] = Float.toString(backend.TotalMMtime);

		this.l1.setText("Cache Hits: " + outputs[0]);
		this.l2.setText("Cache Miss: " + outputs[1]);
		this.l3.setText("Miss Penalty: " + outputs[2]);
		this.l4.setText("Avg. Memory Access Time: " + outputs[3]);
		this.l5.setText("Total Memory Access Time: " + outputs[4]);
	}

	public static void main(String[] args) {
		new Client();
	}
}

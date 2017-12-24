package UserInterface;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import IO.LoadFile;
import Program.MainMethod;
import Values.BestChromosome;
import Values.DataSet;
import Values.Parameter;

public class MainView extends JFrame {
	
	/**
	 * M10456012
	 * Kevin Yen
	 * kelly10056040@gmail.com
	 */
	private static final long serialVersionUID = 1L;
	private boolean isLoad = false;
	
	//*****宣告介面*****//
	Container cp = this.getContentPane();
	
	//*****宣告元件*****//
	JLabel generationLabel = new JLabel("Generation:");
	JLabel chromosomeLabel = new JLabel("Chromosome:");
	JLabel crossoverRateLabel = new JLabel("Crossover Rate:");
	JLabel mutationRateCLabel = new JLabel("Mutation Rate 1:");
	JLabel mutationRateELabel = new JLabel("Mutation Rate 2:");
	JLabel runTimeLabel = new JLabel("Run Time: ");
	JLabel distanceLabel = new JLabel("Best Distance: ");
	JLabel fitnessLabel = new JLabel("Best Fitness: ");
	JLabel avgRunTimeLabel = new JLabel("Avg Time: ");
	JLabel avgDistanceLabel = new JLabel("Avg Distance: ");
	
	JTextField generationTextField = new JTextField("50");
	JTextField chromosomeTextField = new JTextField("40");
	JTextField crossoverRateTextField = new JTextField("0.6");
	JTextField mutationRateCTextField = new JTextField("0.05");
	JTextField mutationRateETextField = new JTextField("0.01");
	JTextField computerRunTextField = new JTextField("30");
	
	JButton loadFileButton = new JButton("Load File");
	JButton startButton = new JButton("Starts");
	
	JCheckBox isMacCheckBox = new JCheckBox("Using Mac");
	JCheckBox isInteger = new JCheckBox("Output Integer");
	JCheckBox isComputerRunCheckBox = new JCheckBox("Computer Run");
	JTextArea output = new JTextArea();
	JPanel show = new JPanel();
	JFileChooser open = new JFileChooser();
	JScrollPane sp = new JScrollPane(output,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	MainView()
	{
		//*****設定介面*****//
		this.setSize(1000, 800);
		this.setLayout(null);
		this.setTitle("MSGA(Auto)");
		
		//*****設定元件位置*****//
		generationLabel.setBounds(700, 30, 100, 30);
		generationTextField.setBounds(800, 30, 100, 30);
		chromosomeLabel.setBounds(700, 80, 100, 30);
		chromosomeTextField.setBounds(800, 80, 100, 30);
		crossoverRateLabel.setBounds(700, 130, 100, 30);
		crossoverRateTextField.setBounds(800, 130, 100, 30);
		mutationRateCLabel.setBounds(700, 180, 150, 30);
		mutationRateCTextField.setBounds(800, 180, 100, 30);
		mutationRateELabel.setBounds(700, 230, 150, 30);
		mutationRateETextField.setBounds(800, 230, 100, 30);
		
		isMacCheckBox.setBounds(700, 260, 120, 30);
		isInteger.setBounds(820, 260, 150, 30);
		loadFileButton.setBounds(700, 290, 200, 30);
		startButton.setBounds(700, 330, 200, 30);
		isComputerRunCheckBox.setBounds(700, 380, 150, 30);
		computerRunTextField.setBounds(850, 380, 40, 30);
		runTimeLabel.setBounds(700, 410, 250, 30);
		distanceLabel.setBounds(700, 430, 250, 30);
		fitnessLabel.setBounds(700, 450, 250, 30);
		avgRunTimeLabel.setBounds(700, 470, 250, 30);
		avgDistanceLabel.setBounds(700, 490, 250, 30);
		
		show.setBounds(20, 20, 650, 500);
		output.setBounds(20, 530, 950, 200);
		sp.setBounds(20, 530, 950, 200);
		
		//*****設定JPanel邊框*****//
		show.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		output.setEditable(false);//JTextArea �L�k���̭������
		output.setLineWrap(true);//JTextArea �۰ʴ���
		
		cp.add(generationLabel);
		cp.add(generationTextField);
		cp.add(chromosomeLabel);
		cp.add(chromosomeTextField);
		cp.add(crossoverRateLabel);
		cp.add(crossoverRateTextField);
		cp.add(mutationRateCLabel);
		cp.add(mutationRateCTextField);
		cp.add(mutationRateELabel);
		cp.add(mutationRateETextField);
		cp.add(startButton);
		cp.add(loadFileButton);
		cp.add(show);
		cp.add(sp);
		cp.add(runTimeLabel);
		cp.add(distanceLabel);
		cp.add(fitnessLabel);
		cp.add(isMacCheckBox);
		cp.add(isComputerRunCheckBox);
		cp.add(computerRunTextField);
		cp.add(avgRunTimeLabel);
		cp.add(avgDistanceLabel);
		cp.add(isInteger);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		loadFileButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				LoadFile loadFile = new LoadFile();
				isLoad = loadFile.loadfile(open, show, isMacCheckBox.isSelected(), -1);
			}});
		
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DataSet dataSet = DataSet.getInstance();
				if (isLoad) {
					initialOutPut();
					LoadFile loadFile = new LoadFile();
					for (int i=0; i<dataSet.getCount(); i++) {//delete
						loadFile.loadfile(open, show, isMacCheckBox.isSelected(), i);
						computerRunAlgorithm(isInteger.isSelected(), i);
					}
				}
			}});
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainView();
	}
	
	private void computerRunAlgorithm(boolean isInteger, int count) {
		int runTime = Integer.parseInt(computerRunTextField.getText());
		long startTime = 0;
		long endTime = 0;
		double totalDistance = 0;
		double totalTime = 0;
		ArrayList<Integer> bestPath = null;
		double bestDistance = -1;
		double bestTime = 0;
		BestChromosome bestChromosome;
		for (int i=0; i<runTime; i++) {
			// 記錄開始時間
			startTime = System.currentTimeMillis();
			// 設定參數
			setParameter(count);
			// 執行MMGA
			MainMethod main = new MainMethod();
			bestChromosome = main.mainProgram(isInteger);
			// 記錄結束時間
			endTime = System.currentTimeMillis();
			// 記錄最佳
			if (i==0) {
				bestPath = bestChromosome.chromosome;
				bestDistance = bestChromosome.distance;
				bestTime = getRunTime(startTime, endTime);
			} else {
				if (bestDistance > bestChromosome.distance) {
					bestPath = bestChromosome.chromosome;
					bestDistance = bestChromosome.distance;
					bestTime = getRunTime(startTime, endTime);
				}
			}
			totalDistance += bestChromosome.distance;
			totalTime += getRunTime(startTime, endTime);
		}
		bestChromosome = new BestChromosome();
		bestChromosome.chromosome = bestPath;
		bestChromosome.distance = bestDistance;
		bestChromosome.fitness = 1/bestDistance;
		System.out.println("Best Distance: " + bestChromosome.distance);//Delect
		System.out.println("Avg Distance: " + totalDistance/runTime);//Delect
		System.out.println("Avg Time: " + totalTime/runTime + " s");//Delect
		System.out.println();//Delect
		runTimeLabel.setText("Best Time: " + bestTime + " s");
		avgRunTimeLabel.setText("Avg Time: " + totalTime/runTime + " s");
		avgDistanceLabel.setText("Avg Distance: " + totalDistance/runTime);
	}
	
	private void setParameter(int count) {
		Parameter parameter = Parameter.getInstance();
		switch (count) {
		case 0:
			parameter.setGeneration(1);
			break;
		case 1:
			parameter.setGeneration(1);
			break;
		case 2:
			parameter.setGeneration(1);
			break;
		case 3:
			parameter.setGeneration(1);
			break;
		case 4:
			parameter.setGeneration(1);
			break;
		case 5:
			parameter.setGeneration(1);
			break;
		case 6:
			parameter.setGeneration(1);
			break;
		case 7:
			parameter.setGeneration(1);
			break;
		case 8:
			parameter.setGeneration(1);
			break;
		}
		parameter.setChromosome(40);
		parameter.setCossoverRate(0.6);
		parameter.setMutationRateC(0.05);
		parameter.setMutationRateE(0.01);
	}
	
	private void initialOutPut() {
		runTimeLabel.setText("Run Time: ");
		distanceLabel.setText("Best Distance: ");
		fitnessLabel.setText("Best Fitness: ");
		avgRunTimeLabel.setText("Avg Time: ");
		avgDistanceLabel.setText("Avg Distance: ");
	}

	private double getRunTime(long start, long end) {
		double startTime = start;
		double endTime = end;
		return (endTime - startTime)/1000;
	}
}

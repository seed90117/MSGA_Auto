package IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import Values.Data;
import Values.DataSet;
import Values.Parameter;

public class LoadFile {

static BufferedReader br;
	
	private static String WINDOWS = "D:\\Algorithm_Data\\TSP Data\\tsp";
	private static String MAC = "//Users//kevin//Documents//OneDrive//Algorithm_Data//TSP Data//tsp";
	private Data data = Data.getInstance();
	private DataSet dataSet = DataSet.getInstance();

	public boolean loadfile(JFileChooser open, JPanel show, boolean isMac)
	{
		String tmp =null;
		FileReader fr = null;
		String loadPath = LoadFile.MAC;
		String fileName = "";
		if (isMac)
			loadPath = LoadFile.MAC;
		else
			loadPath = LoadFile.WINDOWS;
		
		//*****預設路徑*****//
		open.setCurrentDirectory(new File(loadPath));
		
		//*****設定Title*****//
		open.setDialogTitle("Choose dataset");
		
		//*****是否按下Load*****//
		if(open.showDialog(open, "Load") == JFileChooser.APPROVE_OPTION)
		{
			//*****取得路徑*****//
			File filepath = open.getSelectedFile();
			fileName = filepath.getName();
			
			//*****路徑轉為String*****//
			tmp = filepath.getPath().toString();
			
			//*****讀取檔案*****//
			try
			{
				fr = new FileReader(tmp);
			}
			catch (FileNotFoundException ex)
			{
				Logger.getLogger(LoadFile.class.getName()).log(Level.SEVERE, null , ex);
			}
			
			LoadFile.br = new BufferedReader(fr);
			String[] tmpArray = fileName.split("\\.");
			if (tmpArray[1].equals("tsp"))
				getTspData(show);
			else
				getData(show);
		} 
		if (data.total > 0)
			return true;
		else
			return false;
	}
	
	
	public boolean loadfile(JFileChooser open, JPanel show, boolean isMac, int count) {
		String tmp =null;
		FileReader fr = null;
		String loadPath = LoadFile.MAC;
		if (isMac)
			loadPath = "//Users//kevin//Documents//OneDrive//Algorithm_Data";
		else
			loadPath = "D:\\Algorithm_Data";
		if (count == -1) {//delete
		//*****預設路徑*****//
		open.setCurrentDirectory(new File(loadPath));
		
		//*****設定Title*****//
		open.setDialogTitle("Choose dataset");
		
		//*****是否按下Load*****//
		if(open.showDialog(open, "Load") == JFileChooser.APPROVE_OPTION)
		{
			//*****取得路徑*****//
			File filepath = open.getSelectedFile();
			
			//*****路徑轉為String*****//
			tmp = filepath.getPath().toString();
			
			//*****讀取檔案*****//
			try
			{
				fr = new FileReader(tmp);
			}
			catch (FileNotFoundException ex)
			{
				Logger.getLogger(LoadFile.class.getName()).log(Level.SEVERE, null , ex);
			}
			
			LoadFile.br = new BufferedReader(fr);
			getDataSet();
		}
		return dataSet.isDataSet();
		}
		else {
			if (isMac)
				loadPath = LoadFile.MAC + "//";
			else
				loadPath = LoadFile.WINDOWS + "\\";
			loadPath += dataSet.getDataSet(count);
			System.out.println(dataSet.getDataSet(count));
			//*****讀取檔案*****//
			try
			{
				fr = new FileReader(loadPath);
			}
			catch (FileNotFoundException ex)
			{
				Logger.getLogger(LoadFile.class.getName()).log(Level.SEVERE, null , ex);
			}
			LoadFile.br = new BufferedReader(fr);
			getTspData(show);
			return true;
		}
	}
	
	private void getDataSet() {
		ArrayList<String> set = new ArrayList<>();
		String tmp;
		try {
			while ((tmp = br.readLine()) != null) {
				set.add(tmp);
			}
		} catch(Exception ex){
			Logger.getLogger(LoadFile.class.getName()).log(Level.SEVERE, null, ex);
		}
		dataSet.setDataSet(set);
	}//delete
	
	private void getData(JPanel show){
		String tmp;
		String[] tmparray;
		int i =0;
		double sizeX=0;
		double sizeY=0;
		double addNumber = 0;
		Parameter parameter = Parameter.getInstance();
		
		try
		{
			//*****讀取一行*****//
			tmp = br.readLine();
			tmparray = tmp.split(" ");
			
			//*****資料集大小*****//
			data.total = Integer.parseInt(tmparray[0]);
			data.x = new double[data.total];
			data.y = new double[data.total];
			
			//*****讀取資料點*****//
			while((tmp = br.readLine()) != null)
			{
				tmparray = tmp.split(" ");
				data.x[i] = Double.valueOf(tmparray[0]);
				data.y[i] = Double.valueOf(tmparray[1]);
				
				if(data.x[i] > sizeX)
				{
					sizeX = data.x[i];
				}
				if(data.y[i] > sizeY)
				{
					sizeY = data.y[i];
				}
				if (data.x[i] < 0 || data.y[i] < 0) {
					if(data.x[i] < addNumber)
					{
						addNumber = data.x[i];
					}
					if(data.y[i] < addNumber)
					{
						addNumber = data.y[i];
					}
				}
				i++;
			}
			LoadFile.br.close();
			//*****取得XY軸比率*****//
			DrawPanel drawPanel = new DrawPanel();
			addNumber *= -1;
			parameter.setPointParameter(show.getWidth()/(sizeX+addNumber+1), 
									show.getHeight()/(sizeY+addNumber+1), addNumber);
			drawPanel.drawpanel(show);
			
		}catch(Exception ex){
			Logger.getLogger(LoadFile.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private void getTspData(JPanel show){
		String tmp;
		String[] tmparray;
		int i =0;
		double sizeX=0;
		double sizeY=0;
		double addNumber = 0;
		Data data = Data.getInstance();
		Parameter parameter = Parameter.getInstance();
		
		try
		{
			//*****讀取一行*****//
			tmp = br.readLine();
			tmparray = tmp.split("	");
			
			//*****資料集大小*****//
			data.total = Integer.parseInt(tmparray[0]);
			data.x = new double[data.total];
			data.y = new double[data.total];
			
			//*****讀取資料點*****//
			while((tmp = br.readLine()) != null)
			{
				tmparray = tmp.split("	");
				data.x[i] = Double.valueOf(tmparray[1]);
				data.y[i] = Double.valueOf(tmparray[2]);
				
				if(data.x[i] > sizeX)
				{
					sizeX = data.x[i];
				}
				if(data.y[i] > sizeY)
				{
					sizeY = data.y[i];
				}
				if (data.x[i] < 0 || data.y[i] < 0) {
					if(data.x[i] < addNumber)
					{
						addNumber = data.x[i];
					}
					if(data.y[i] < addNumber)
					{
						addNumber = data.y[i];
					}
				}
				i++;
			}
			LoadFile.br.close();
			//*****取得XY軸比率*****//
			DrawPanel drawPanel = new DrawPanel();
			addNumber *= -1;
			parameter.setPointParameter(show.getWidth()/(sizeX+addNumber+1), 
					show.getHeight()/(sizeY+addNumber+1), addNumber);
			drawPanel.drawpanel(show);
			
		}catch(Exception ex){
			Logger.getLogger(LoadFile.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

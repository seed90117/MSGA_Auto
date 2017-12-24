package Values;

import java.util.ArrayList;

public class DataSet {
	
	private ArrayList<String> dataSet = new ArrayList<String>();
	private static DataSet instance = null;
	private DataSet(){}
	
	public static synchronized DataSet getInstance() {
		if (instance == null) {
			instance = new DataSet();
		}
		return instance;
	}
	
	public void setDataSet(ArrayList<String> dataSet) {
		this.dataSet = dataSet;
	}
	
	public ArrayList<String> getDataSet() {
		return this.dataSet;
	}
	
	public String getDataSet(int index) {
		return this.dataSet.get(index);
	}
	
	public int getCount() {
		return this.dataSet.size();
	}
	
	public boolean isDataSet() {
		return !dataSet.isEmpty();
	}
}

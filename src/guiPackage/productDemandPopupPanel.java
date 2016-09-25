package guiPackage;

import javax.swing.JPanel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class productDemandPopupPanel extends JPanel {

	public productDemandPopupPanel(ArrayList<String> data) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Calendar now = Calendar.getInstance();
		int currentMonthIndex = now.get((Calendar.MONTH)) ;
		String[] months = new String[] {"January","February","March","April","May","June",
				"July","August","September","October","November","December"};
		
		this.add(new JLabel("Predicted demand for " + data.get(0) + " for month " + months[(currentMonthIndex + 1) & 11]));	
		
		this.add(new JLabel("------------------------------------------------------------------------------"));
		this.add(new JLabel("Previous " + data.get(1) + " sales: "));		
		int n = 0;
		if(data.get(1) == "Quarterly"){
			n = 3;
		}else{
			n = 12;
		}
		int[] tempData = new int[] {1,2,3,4,5,6,7,8,9,10,11,12};
		for(int i = 0; i < n; i++){
			JLabel tempLabel = new JLabel("        " + months[(currentMonthIndex - (n-1) + i) & 11] + " : " + tempData[i]);
			this.add(tempLabel);
		}
		
		this.add(new JLabel("        " + months[(currentMonthIndex + 1) & 11] + " : " + predictNextValue(tempData)));
	}
	
	private int predictNextValue(int[] data){
		int n = data.length;

		double xAverage = 0;
		for (int i = 0; i < n; i++){
			xAverage += data[i];
		}
		xAverage = xAverage / n;

		double yAverage = 0;
		for (int i = 0; i < n; i++){
			yAverage += i;
		}
		yAverage = yAverage / n;

		double mDen = 0;
		double mNum = 0;

		for(int i = 0; i < n; i++)
		{
			mDen += (data[i] - xAverage)*(i - yAverage);
			mNum += (data[i] - xAverage)*(i - xAverage);
		}
		double m = mDen / mNum;

		double c = (yAverage - (m*xAverage)) / n;

		double nextPrediction = m * (n + 1) + c;
		return (int)Math.ceil(nextPrediction);
	}

}

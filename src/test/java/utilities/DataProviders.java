package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	//DataProvider 1
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException {
		String path=".\\testData\\Aura_LoginData.xlsx";//taking xl file from testData
		ExcelUtility xlUtil=new ExcelUtility(path);//creating an object for XLUtility
		int totalRows=xlUtil.getRowCount("Sheet1");
		int totalCols=xlUtil.getCellCount("Sheet1",1);
		String loginData[][]=new String[totalRows][totalCols];//created for two dimension array which can store the data user and password
		for(int i=1;i<=totalRows;i++)  //read the data from xl storing in 2-D array
		{		
			for(int j=0;j<totalCols;j++)
			{
				loginData[i-1][j]= xlUtil.getCellData("Sheet1",i, j);
			}
		}
	return loginData;
	}
	
	//DataProvider 2
}

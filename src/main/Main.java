

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.*;




public class Main {
	static String gameName = "spaghetti";
	public static Connection conn;

	//insertScenerio Method (validation Checks)
	public static boolean insertScenario(Scenario currentScenario){

		try{

			PreparedStatement psInsert = conn
					.prepareStatement("insert into Scenario values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			psInsert.setString(1,"" + (getLatestCycleNum() + 1));
			psInsert.setString(2, "" + currentScenario.getCashAssets());
			psInsert.setString(3, "" + currentScenario.getSalesPrice());
			psInsert.setString(4, "" + currentScenario.getPassword());
			//			psInsert.setString(5, "" + currentScenario.getProduction());
			//			psInsert.setString(6, "" + currentScenario.getDoubleShiftCost());
			//			psInsert.setString(7, "" + currentScenario.getDoubleShiftProdAdv());
			//			psInsert.setString(8, "" + currentScenario.getNormalShiftCost());
			psInsert.setString(9, "" + currentScenario.getNewEmpAdv());
			psInsert.setString(10, "" + currentScenario.getNewHireCost());
			psInsert.setString(11, "" + currentScenario.getSeveranceCost());
			psInsert.setString(12, "" + currentScenario.getDemand());
			psInsert.setString(13, "" + currentScenario.getBatchSizeProdSmall());
			psInsert.setString(14, "" + currentScenario.getBatchSizeProdLarge());
			psInsert.setString(15, "" + currentScenario.getInventory());
			psInsert.setString(16, "" + currentScenario.getStorageCost());
			psInsert.setString(17, "" + currentScenario.getShrinkage());
			psInsert.setString(18, "" + currentScenario.getDefectiveProdRate());
			psInsert.setString(19, "" + currentScenario.getWaterBottles());
			psInsert.setString(20, "" + currentScenario.getLabels());
			psInsert.setString(21, "" + currentScenario.getWaterBottles());
			psInsert.setString(22, "" + currentScenario.getPackingCases());
			//			psInsert.setString(23, "" + currentScenario.getFinalInspection());
			psInsert.setString(24, "" + currentScenario.getFinBottleInspection());
			//			psInsert.setString(25, "" + currentScenario.getEmployees());
			psInsert.setString(26, "" + currentScenario.getDemandPenalty());
			psInsert.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.print(e);
			return false;
		}
		return true;

	}
	//Prints current contents of Scenario table, for testing
	public static void printScenario(){
		ResultSet rs;

		try{
			Statement stmt2 = conn.createStatement();
			rs = stmt2.executeQuery("select * from Scenario");


			while (rs.next()) {
				for(int x = 1;x<=26;x++)
					System.out.print(rs.getString(x) + " ");
				System.out.println();
			}

		}
		catch(Exception e){

		}
	}

	//Deletes everything from the Scenario Table
	public static void clearScenarioTable(){

		try{
			Statement stmt2 = conn.createStatement();
			stmt2.executeUpdate("delete from Scenario");

		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	private static void generateDemandTemplate(String sFileName)
	{
		try
		{
			FileWriter writer = new FileWriter(sFileName);

			writer.append("Year =>");
			writer.append(',');
			writer.append("1");
			writer.append('\n');

			writer.append("January");
			writer.append('\n');
			writer.append("February");
			writer.append('\n');
			writer.append("March");
			writer.append('\n');
			writer.append("April");
			writer.append('\n');
			writer.append("May");
			writer.append('\n');
			writer.append("June");
			writer.append('\n');
			writer.append("July");
			writer.append('\n');
			writer.append("August");
			writer.append('\n');
			writer.append("September");
			writer.append('\n');
			writer.append("October");
			writer.append('\n');
			writer.append("November");
			writer.append('\n');
			writer.append("December");

			//generate whatever data you want

			writer.flush();
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		} 
	}

	public static int[] returnAllDemand(){
		//return array of demand numbers for team game
		ResultSet numRows;
		ResultSet demandRows;
		int totalRows = 0;
		try{
			Statement getNumRowsStmt = conn.createStatement();
			numRows = getNumRowsStmt.executeQuery("select count(Demand) from Demand");
			numRows.next();
			totalRows = numRows.getInt(1);
			System.out.println("Total rows= " + totalRows);
			int[] demandArray = new int[totalRows];

			Statement getDemandStmt = conn.createStatement();
			demandRows = getDemandStmt.executeQuery("select Demand from Demand");

			int currSpot = 0;
			while (demandRows.next()) {
				demandArray[currSpot] = demandRows.getInt(1);
				currSpot++;
			}
			return demandArray;
		}
		catch(Exception e){
			System.out.println(e);
		}
		int[] demandArray = new int[0];
		return demandArray;
	}
	//Reads in CSV file, checks if each line has data for all months given and years, then inserts into demand table
	public static boolean insertDemand(File demandCurve){
		try{
			Scanner fileIn = new Scanner(demandCurve);
			//Scanner lineIn = new Scanner(fileIn.nextLine());
			String[] yearLine = fileIn.nextLine().split(",");
			int totalYears = yearLine.length;
			String[] januaryArray = new String[totalYears];
			String[] februaryArray = new String[totalYears];
			String[] marchArray = new String[totalYears];
			String[] aprilArray = new String[totalYears];
			String[] mayArray = new String[totalYears];
			String[] juneArray = new String[totalYears];
			String[] julyArray = new String[totalYears];
			String[] augustArray = new String[totalYears];
			String[] septemberArray = new String[totalYears];
			String[] octoberArray = new String[totalYears];
			String[] novemberArray = new String[totalYears];
			String[] decemberArray = new String[totalYears];

			int currentMonth = 1;
			while(fileIn.hasNextLine()){
				String[] demandLine = fileIn.nextLine().split(",");
				if(demandLine.length != totalYears){
					System.out.println("INVALID DEMAND FILE");
					clearDemandTable();
					fileIn.close();
					return false;
				}
				else{
					for(int x = 0;x<demandLine.length;x++){
						if(demandLine[x].equals("")){
							System.out.println("INVALID DEMAND FILE");
							clearDemandTable();
							fileIn.close();
							return false;
						}
					}
				}
				for(int x = 0;x < totalYears;x++){
					if(currentMonth == 1){
						januaryArray[x] = demandLine[x];
					}
					else if(currentMonth == 2){
						februaryArray[x] = demandLine[x];
					}
					else if(currentMonth == 3){
						marchArray[x] = demandLine[x];
					}
					else if(currentMonth == 4){
						aprilArray[x] = demandLine[x];
					}
					else if(currentMonth == 5){
						mayArray[x] = demandLine[x];
					}
					else if(currentMonth == 6){
						juneArray[x] = demandLine[x];
					}
					else if(currentMonth == 7){
						julyArray[x] = demandLine[x];
					}
					else if(currentMonth == 8){
						augustArray[x] = demandLine[x];
					}
					else if(currentMonth == 9){
						septemberArray[x] = demandLine[x];
					}
					else if(currentMonth == 10){
						octoberArray[x] = demandLine[x];
					}
					else if(currentMonth == 11){
						novemberArray[x] = demandLine[x];
					}
					else if(currentMonth == 12){
						decemberArray[x] = demandLine[x];
					}


				}
				currentMonth++;

			}

			fileIn.close();

			int currentCycle = 1;
			for(int x=1; x< januaryArray.length; x++){

				PreparedStatement psInsert = conn
						.prepareStatement("insert into Demand values (?,?,?,?)");
				psInsert.setString(1, "" + currentCycle);
				psInsert.setString(2, "" + januaryArray[0]);
				psInsert.setString(3, "" + x);
				psInsert.setString(4, "" + januaryArray[x]);
				psInsert.executeUpdate();

				currentCycle++;

				psInsert = conn
						.prepareStatement("insert into Demand values (?,?,?,?)");
				psInsert.setString(1, "" + currentCycle);
				psInsert.setString(2, "" + februaryArray[0]);
				psInsert.setString(3, "" + x);
				psInsert.setString(4, "" + februaryArray[x]);
				psInsert.executeUpdate();

				currentCycle++;

				psInsert = conn
						.prepareStatement("insert into Demand values (?,?,?,?)");
				psInsert.setString(1, "" + currentCycle);
				psInsert.setString(2, "" + marchArray[0]);
				psInsert.setString(3, "" + x);
				psInsert.setString(4, "" + marchArray[x]);
				psInsert.executeUpdate();

				currentCycle++;

				psInsert = conn
						.prepareStatement("insert into Demand values (?,?,?,?)");
				psInsert.setString(1, "" + currentCycle);
				psInsert.setString(2, "" + aprilArray[0]);
				psInsert.setString(3, "" + x);
				psInsert.setString(4, "" + aprilArray[x]);
				psInsert.executeUpdate();

				currentCycle++;

				psInsert = conn
						.prepareStatement("insert into Demand values (?,?,?,?)");
				psInsert.setString(1, "" + currentCycle);
				psInsert.setString(2, "" + mayArray[0]);
				psInsert.setString(3, "" + x);
				psInsert.setString(4, "" + juneArray[x]);
				psInsert.executeUpdate();

				currentCycle++;

				psInsert = conn
						.prepareStatement("insert into Demand values (?,?,?,?)");
				psInsert.setString(1, "" + currentCycle);
				psInsert.setString(2, "" + juneArray[0]);
				psInsert.setString(3, "" + x);
				psInsert.setString(4, "" + juneArray[x]);
				psInsert.executeUpdate();

				currentCycle++;

				psInsert = conn
						.prepareStatement("insert into Demand values (?,?,?,?)");
				psInsert.setString(1, "" + currentCycle);
				psInsert.setString(2, "" + julyArray[0]);
				psInsert.setString(3, "" + x);
				psInsert.setString(4, "" + julyArray[x]);
				psInsert.executeUpdate();

				currentCycle++;

				psInsert = conn
						.prepareStatement("insert into Demand values (?,?,?,?)");
				psInsert.setString(1, "" + currentCycle);
				psInsert.setString(2, "" + augustArray[0]);
				psInsert.setString(3, "" + x);
				psInsert.setString(4, "" + augustArray[x]);
				psInsert.executeUpdate();

				currentCycle++;

				psInsert = conn
						.prepareStatement("insert into Demand values (?,?,?,?)");
				psInsert.setString(1, "" + currentCycle);
				psInsert.setString(2, "" + septemberArray[0]);
				psInsert.setString(3, "" + x);
				psInsert.setString(4, "" + septemberArray[x]);
				psInsert.executeUpdate();

				currentCycle++;

				psInsert = conn
						.prepareStatement("insert into Demand values (?,?,?,?)");
				psInsert.setString(1, "" + currentCycle);
				psInsert.setString(2, "" + octoberArray[0]);
				psInsert.setString(3, "" + x);
				psInsert.setString(4, "" + octoberArray[x]);
				psInsert.executeUpdate();

				currentCycle++;

				psInsert = conn
						.prepareStatement("insert into Demand values (?,?,?,?)");
				psInsert.setString(1, "" + currentCycle);
				psInsert.setString(2, "" + novemberArray[0]);
				psInsert.setString(3, "" + x);
				psInsert.setString(4, "" + novemberArray[x]);
				psInsert.executeUpdate();

				currentCycle++;

				psInsert = conn
						.prepareStatement("insert into Demand values (?,?,?,?)");
				psInsert.setString(1, "" + currentCycle);
				psInsert.setString(2, "" + decemberArray[0]);
				psInsert.setString(3, "" + x);
				psInsert.setString(4, "" + decemberArray[x]);
				psInsert.executeUpdate();

				currentCycle++;
			}

		}

		catch(Exception e)
		{
			System.out.println(e);
			return false;
		}

		return true;

	}
	//Prints current contents of the Demand table for testing
	public static void printDemand(){
		ResultSet rs;

		try{
			Statement stmt2 = conn.createStatement();
			rs = stmt2.executeQuery("select * from Demand");

			while (rs.next()) {
				for(int x = 1;x<=4;x++)
					System.out.print(rs.getString(x) + " ");
				System.out.println();
			}

		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public static void saveGame(){// Obfuscate the file by subtracting 1 from each byte. Reverse by adding when we read in
		ResultSet rs;
		// Need a file buffer/stream
		String out = "I like turtles:";
		try{
			int demandIter = 0;
			Statement stmt2 = conn.createStatement();
			rs = stmt2.executeQuery("select * from Demand");
			while (rs.next()) {//need to keep track of how many rs.next() we get
				demandIter ++;
				for(int x = 1;x<=4;x++){
					//				Byte bout = 0;
					InputStream in = rs.getBinaryStream(x);
					int input = in.read()-1;// .read returns an int 0-255
					while(input != -2){//because we're always subtracting 1, if it returned -1 for no more, it would be -2
						out += Integer.toBinaryString(input);
						input = in.read()-1;
					}
				}
			}
			out += "DemandEnd("+ demandIter +")";
			//out now has the demand as a binary string
			int scenarioIter = 0;
			Statement stmt1 = conn.createStatement();
			rs = stmt1.executeQuery("select * from Scenario");
			while (rs.next()) {
				scenarioIter++;
					for(int x = 1;x<=26;x++){
					//				Byte bout = 0;
					InputStream in = rs.getBinaryStream(x);
					int input = in.read()-1;// .read returns an int 0-255
					while(input != -2){//because we're always subtracting 1, if it returned -1 for no more, it would be -2
						out += Integer.toBinaryString(input);
						input = in.read()-1;
					}
				}
			}
			// out now is the demand as a bytestring, with the scenario as a byte string concatenated on it
			out += "ScenariosEnd("+ scenarioIter +")";
		}
		catch(Exception e){
			System.out.println(e);
		}
		try {
			FileWriter fOut = new FileWriter(gameName+".gme");//We'll have to change game name if they change it in their prompt.
			//We COULD make it so that we dictate the save names, but people tend to not like that.
			fOut.append(out);
			fOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
	

	//Clears everything from the demand table
	public static void clearDemandTable(){

		try{
			Statement stmt2 = conn.createStatement();
			stmt2.executeUpdate("delete from Demand");

		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public static int getNextDemand(int currentCycle){
		//currentCycle comes from scenario file
		//Should return linked list
		int nextCycle = currentCycle + 1;
		int nextMonthDemand = 0;
		ResultSet rs;
		try{
			String demandString = "Select Demand from Demand where cycle = '" + nextCycle + "'";
			Statement stmt2 = conn.createStatement();
			rs = stmt2.executeQuery(demandString);
			while (rs.next()) {
				System.out.println(rs.getInt(1));
				nextMonthDemand = rs.getInt(1);
			}
		}
		catch(Exception e){
			System.out.println(e);
			return nextMonthDemand;
		}
		return nextMonthDemand;

	}

	//Returns the max scenario number from the scenario table, which correlates to the the current cycle we are on
	public static int getLatestCycleNum(){
		ResultSet rs;
		try{
			String cycleString = "Select MAX(CycleNum) from Scenario";
			Statement stmt2 = conn.createStatement();
			rs = stmt2.executeQuery(cycleString);
			rs.next();
			System.out.println("Last Cycle Num is " + rs.getInt(1));
			return rs.getInt(1);
		}
		catch(Exception e){
			System.out.println(e);
			return 0;
		}

	}

	public void generateReport1(){

	}

	public void generateReport2(){

	}

	public void generateReport3(){

	}

	public static void updateScenarioData(Scenario currentScenario){
		//Called by simulator
		//Inserts the scenario after a run into the database, pulls out the next cycles demand and returns the scenario for the next run
		insertScenario(currentScenario);
		//Scenario newScenario = new Scenario(double cashAssets, double salesPrice, String password, int production, double doubleShiftCost, double doubleShiftProdAdv, double normalShiftCost, int newEmpAdv, double newHireCost, double severanceCost, int batchSizeProdSmall, int batchSizeProdLarge, int inventory, double storageCost, double shrinkage, double defectiveProdRate, double waterBottles, double labels, double packingCases, double finProdInspection, double finBottleInspection, int employees);

		//return newScenario;


	}

	public void outputGame(Scenario currentScenario){

	}

	private static void loadGame(){
		//load in byte by byte in string, unless
		
		//Game name should be kept track of to auto-fill the save game bar, and will be grabbed when it's selected to load from whatever list it is that we want.
		// we can also decide how we want to organize it's folders
		
		//Should return a Scenario
		ResultSet rs;
        try{
            String scenarioString = "Select * from Scenario where CycleNum = (Select MAX(CycleNum) from Scenario)";
        Statement stmt2 = conn.createStatement();
        rs = stmt2.executeQuery(scenarioString);
        rs.next();
        System.out.println(rs.getString(1));
        }catch(Exception e){
            System.out.println(e);
        }
        //return latestScenario
	}

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        String dbName = "POMSDB";
        String connectionURL = "jdbc:derby:" + dbName + ";";
        try{
        Class.forName(driver);
        conn = DriverManager.getConnection(connectionURL);
        }
        catch(Exception e){
        }
        
        Scenario test = new Scenario(.01, .01, "test", 10, .01, .01, .01, 10, .01, .01, 10, 10, 10, .01, .01, .01, .01, .01, .01, .01, .01, 20);
        clearScenarioTable();
        insertScenario(test);
        insertScenario(test);
        insertScenario(test);
        insertScenario(test);
        printScenario();
        
        File demand = new File("testDemand.csv");
        clearDemandTable();
        insertDemand(demand);
        printDemand();
        getNextDemand(getLatestCycleNum());    
        int[] demandArray = returnAllDemand();
        for(int x = 0; x<demandArray.length;x++){
            System.out.println(demandArray[x]);
        }
        generateDemandTemplate("DemandTemplate.csv"); 
        //loadGame();
        
        
    }

}

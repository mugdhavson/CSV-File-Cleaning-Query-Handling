/*
 * IMP NOTE: In order to use this, you will have to compile it AT THE
 *           SAME TIME as DataRecord.java. 
 *
 * Prog1A.java -- A program that converts a csv file to a binary file
 * 		  using java.io methods. The csv file needs to be specific; 
 * 		  changing the format of it will lead to execution problems. 
 * 		  The records will be added to the binary file in ascending 
 * 		  order of the EIA_ID values.
 *
 * Accompanying this file is DataRecord.java, which represents one record
 * in the csv file. One of it's methods, dumpObject(), is used to write the
 * record to the binary file.
 * 
 * Author: Mugdha Sonawane
 * NOTE: Some code was from BinaryIO.java file from Professor McCann
 * 
 * Version 1: 31st August, 2022
 * */        


import java.io.*;
import java.util.*;

/*+----------------------------------------------------------------------
 ||
 ||  Class DataRecord
 ||
 ||         Author(s):  Mugdha Sonawane and L. McCann
 ||
 ||        Purpose:  An object of this class holds the field values of one
 ||                  record of data.  There are 9 fields:  EIA_ID, Project
 ||                  Name, Solar COD, State, Latitude, Longitude, Average
 ||                  GHI, Solar Capacity MW-DC, Solar Capacity MW-AC.
 ||                  These are motivated by 2021 Utility Scale Solar
 ||                  Plants.
 ||
 ||  Inherits From:  None.
 ||
 ||     Interfaces:  Comparable<T>
 ||                  T is the type of objects that this object may be
 ||                  compared to
 ||
 |+-----------------------------------------------------------------------
 ||
 ||      Constants:  None.
 ||
 |+-----------------------------------------------------------------------
 ||
 ||   Constructors:  Just the default constructor; no arguments.
 ||
 ||  Class Methods:  None.
 ||
 ||  Inst. Methods:     int getStateCode()
 ||                     int getPlaceCode()
 ||                  String getCountyName()
 ||                    void setStateCode(int newCode)
 ||                    void setPlaceCode(int newCode)
 ||                    void setCountyName(String newName)
 ||
 ||                    void dumpObject(RandomAccessFile stream, int
 ||                                   ProjNameLen, int CODLen, int StateLen)
 ||                    int compareTo(DataRecord compObj)
 ||
 ++-----------------------------------------------------------------------*/
class DataRecord implements Comparable<DataRecord> {
    // The data fields that comprise a record of our file

        private    int id;
        private String projName = "";
        private String COD = "";        // Construction Date (I'm guessing)
        private String State = "";      // State that the plant is in
        private double lat = 0.0;       // latitude
        private double lon = 0.0;       // longitude
        private double ghi = 0.0;       // Average GHI
        private double SC_DC = 0.0;     // Solar Capacity MW-DC
        private double SC_AC = 0.0;     // Solar Capacity MW-AC

    // 'Getters' for the data field values

        public    int getEIA_ID() { return(id); }
        public String getProjName() { return(projName); }
        public String getCOD() { return(COD); }
        public String getState() { return(State); }
        public double getLat() { return(lat); }
        public double getLon() { return(lon); }
        public double getGHI() { return(ghi); }
        public double getSC_DC() { return(SC_DC); }
        public double getSC_AC() { return(SC_AC); }


    // 'Setters' for the data field values

        public    void setEIA_ID(int newEIA_ID) { id = newEIA_ID; }
        public    void setProjName(String newProjName) { projName = newProjName; }
        public    void setCOD(String newCOD) { COD = newCOD; }
        public    void setState(String newState) { State = newState; }
        public    void setLat(double newLat) { lat = newLat; }
        public    void setLon(double newLon) { lon = newLon; }
        public    void setGHI(double newGHI) { ghi = newGHI; }
        public    void setSC_DC(double newSC_DC) { SC_DC = newSC_DC; }
        public    void setSC_AC(double newSC_AC) { SC_AC = newSC_AC; }

         /*---------------------------------------------------------------------
        |  Method dumpObject(stream, ProgNameLen, CODLen, StateLen)
        |
        |  Purpose:  Writes the content of the object's fields
        |            to the file represented by the given RandomAccessFile
        |            object reference.  Primitive types (e.g., int)
        |            are written directly.  Non-fixed-size values
        |            (e.g., strings) are converted to the maximum allowed
        |            size before being written.  The maximum allowed size for
        |            each string field is stored in the parametes of the
        |            method. The result is a file of uniformly-sized records.
        |            Text (i.e., strings) is written with just one byte per
        |            character, meaning that general Unicode text is not
        |            supported.
        |
        |  Pre-condition:  Fields have been populated, stream is writeable,
        |                  file pointer is positioned to new data's location.
        |
        |  Post-condition: Stream contains field data in sequence, file pointer
        |                  is left at the end of the written data.
        |
        |  Parameters:
        |       stream --       This is the stream object representing the data file
        |                       to which the data is being written.
        |
        |       ProjNameLen --  This is an int, representing the maximum string
        |                       field length for Project Name.
        |
        |       CODLen --       This is an int, representing the maximum string
        |                       field length for COD.
        |
        |       StateLen --     This is an int, representing the maximum string
        |                       field length for State Name.
        |
        |  Returns:  None.
        *-------------------------------------------------------------------*/
public void dumpObject(RandomAccessFile stream, int ProjNameLen, int CODLen, int StateLen) {
                StringBuffer ProjectName = new StringBuffer(projName);  // paddable name str
                StringBuffer CODstr = new StringBuffer(COD);  // paddable COD str
                StringBuffer StateStr = new StringBuffer(State);  // paddable State str
                try {
                        stream.writeInt(id);

                        ProjectName.setLength(ProjNameLen);  // pads to right with nulls
                        stream.writeBytes(ProjectName.toString());  // only ASCII, not UNICODE

                        CODstr.setLength(CODLen);  // pads to right with nulls
                        stream.writeBytes(CODstr.toString());  // only ASCII, not UNICODE

                        StateStr.setLength(StateLen);  // pads to right with nulls
                        stream.writeBytes(StateStr.toString());  // only ASCII, not UNICODE

                        stream.writeDouble(lat);

                        stream.writeDouble(lon);
                        stream.writeDouble(ghi);
                        stream.writeDouble(SC_DC);
                        stream.writeDouble(SC_AC);

                } catch (IOException e) {
                        System.out.println("I/O ERROR: Couldn't write to the file;\n\t"
                            + "perhaps the file system is full?");
                        System.exit(-1);
                }
        }

	/*---------------------------------------------------------------------
        |  Method compareTo(DataRecord compObj)
        |
        |  Purpose:     Compares compObj with this object, returing an int
        |               according to the comparison
        |
        |  Pre-condition:  Fields have been populated. i.e. this object is ready
        |                  to be compared
        |  Post-condition: compareid is populated with the EIA_ID of the compObj
        |
        |  Parameters:  compObj -- This is the DataRecord object representing
        |               another record that needs to be compared to this object
        |
        |  Returns:  0, if the objects are the same
        |            negative number, if compObj is greater than this object
        |            positive number, if this object is greater than compObj
        *-------------------------------------------------------------------*/
        @Override public int compareTo(DataRecord compObj) {
                int compareid=((DataRecord)compObj).getEIA_ID();
                /* For Ascending order*/
                return this.id - compareid;
        }
}


/*+----------------------------------------------------------------------
 ||
 ||  Class Prog1A
 ||
 ||         Author(s):  Mugdha Sonawane and L. McCann
 ||
 ||        Purpose:	This class has several methods which are used to
 ||			convert a particular type of csv file to binary file.
 ||			Information on the csv file is in DataRecord.java 
 ||			documentation 
 ||  	
 ||	Inherits From:  None.
 ||
 ||     Interfaces:  None.
 ||
 |+-----------------------------------------------------------------------
 ||
 ||      Constants: None. 
 ||
 |+-----------------------------------------------------------------------
 ||	
 ||	Fields: int maxProjNameLen, int maxCODLen, int maxStateLen; all of
 ||		them store the maximum field length. More info in the 
 ||		DataRecord.java file.
 ||   	
 ||	Constructors:  None.
 ||
 ||	Class Methods:  static boolean isValidEIA_ID(String str)
 ||			static boolean isValidDouble(String str)
 ||			static ArrayList<DataRecord> readCSV (String csvName)
 ||			static void writeBinaryFile (String fileName,
 ||                                         ArrayList<DataRecord> csvContent)
 ||			
 ||  	Inst. Methods: None.   
 ++-----------------------------------------------------------------------*/

public class Prog1A {

	private static int maxProjNameLen = 0; // this is only for the projectName length

	private static int maxCODLen = 0; // this is only for the projectName length
	private static int maxStateLen = 0; // this is only for the projectName length


	/*---------------------------------------------------------------------
        |  Method isValidEIA_ID(String str)
        |
        |  Purpose: checks to see if a string is a valid integer            
	|
	|  Pre-condition: str is populated.         
	|
	|  Post-condition: since it's a try-catch block, there could be an 
	|		   exception occuring
        |
        |  Parameters: str, this is the one to check and see if its an integer
       	|	
        |  Returns:  true, if it is an integer
        |	     false, otherwise                  
	 *-------------------------------------------------------------------*/

	public static boolean isValidEIA_ID(String str) { 
 		try {  
    			Integer.parseInt(str);  
    			return true;
  		} catch(NumberFormatException e){  
    			return false;  
 		}  
	}

	/*---------------------------------------------------------------------
        |  Method isValidDouble(String str)
        |
        |  Purpose: checks to see if a string is a valid double            
        |
        |  Pre-condition: str is populated.         
        |
        |  Post-condition: since it's a try-catch block, there could be an 
        |                  exception occuring
        |
        |  Parameters: str, this is the one to check and see if its an double
        |       
        |  Returns:  true, if it is a double
        |            false, otherwise                  
         *-------------------------------------------------------------------*/
	public static boolean isValidDouble(String str) { 
 		try {  
    			Double.parseDouble(str);  
    			return true;
  		} catch(NumberFormatException e){  
    			return false;  
 		}  
	}

	/*---------------------------------------------------------------------
        |  Method readCSV (String csvName)
        |
        |  Purpose: 	reads the contents of the csv file, csvName, and stores each
	|		record in a DataRecord object, which are inturn stored in an
	|		ArrayList. When reading the csv file, if an EIA_ID is malformed 
	|		or just not present, the record is skipped. For all the other
       	|		fields, there's default values.	
        |
        |  Pre-condition: csvName is a valid file name.         
        |
        |  Post-condition: The ArrayList, csvContent, is populated and sorted in 
	|		   ascending order of EIA_ID.
        |
        |  Parameters: csvName, this is the file name of the csv file 
        |       
        |  Returns:  csvContent, an ArrayList of DataRecord objects, sorted in 
	|	     ascending order of the EIA_ID values
         *-------------------------------------------------------------------*/
	private static ArrayList<DataRecord> readCSV (String csvName) {
		File csvFile = null;
		BufferedReader csvReader = null;
		ArrayList<DataRecord> csvContent = null; // list of record contents
     		DataRecord currentRecord = null;    
		try {
        		csvFile = new File(csvName);     // don't need to add the extension because file name has the path name
          		if (!csvFile.exists()) {
              			System.out.println("PROBLEM:  The input file `binaryIO.csv' "
                              	 + "does not exist in the current directory.");
           		        System.exit(-1);
                        }
                } catch (Exception e) {
          		System.out.println("I/O ERROR: Something went wrong with the "
                             + "detection of the CSV input file.");
           		 System.exit(-1);
        	}

		try {
			csvContent = new ArrayList<DataRecord>();
	        	csvReader = new BufferedReader(new FileReader(csvFile));
           		String record = null;  // content of one line/record of the CSV file
      			csvReader.readLine();	// header		
           		while((record = csvReader.readLine()) != null) {
				currentRecord = new DataRecord();
				if (record.contains("\"")) {
					String[] fields = record.split("\"");
					// check if fields[0] length is > 0
					// for the case below
					// "57777, 57779, 57781",RE Dillard 1-3,12/30/2011,CA,38.46,-121.18,5.06,12.03,8.91
					if (fields[0].length() > 0) {
						String id = fields[0].substring(0, fields[0].length() - 1);
						if (id.length() > 0 && isValidEIA_ID(id)) {

							currentRecord.setEIA_ID(Integer.parseInt(id));
					//strip the comma from the EIA_ID, then check if EIA_ID is valid
						// if it is valid, then do the below
						//split the 2nd string on the comma
						//and then do the same as below	
					
							currentRecord.setProjName(fields[1]);
							if (maxProjNameLen < fields[1].length()) {
								maxProjNameLen = fields[1].length();
							}
						
							String[] latterFields = fields[2].split(",");	
							// SPLIT fields[2] here with commas as the delimiter	
							currentRecord.setCOD(latterFields[1]);
							if (maxCODLen < latterFields[1].length()) {
								maxCODLen = latterFields[1].length();
							}	
							currentRecord.setState(latterFields[2]);
							if (maxStateLen < latterFields[2].length()) {
								maxStateLen = latterFields[2].length();
							}	
						
							if (isValidDouble(latterFields[3])) {
								currentRecord.setLat(Double.parseDouble(latterFields[3]));
							}
							if (isValidDouble(latterFields[4])) {
								currentRecord.setLon(Double.parseDouble(latterFields[4]));
							}
							if (isValidDouble(latterFields[5])) {
								currentRecord.setGHI(Double.parseDouble(latterFields[5]));
							}
							if (isValidDouble(latterFields[6])) {
								currentRecord.setSC_DC(Double.parseDouble(latterFields[6]));
							}
							if (isValidDouble(latterFields[7])) {
								currentRecord.setSC_AC(Double.parseDouble(latterFields[7]));
							}
							csvContent.add(currentRecord);
						}	
					}
				} else if (record.contains(",")) { // what if there's only one field in the record??	
					String[] fields = record.split(",");	
					if (fields[0].length() > 0 && isValidEIA_ID(fields[0])) {
						currentRecord.setEIA_ID(Integer.parseInt(fields[0]));
						// you need to keep track of this one's max length
						currentRecord.setProjName(fields[1]);
						if (maxProjNameLen < fields[1].length()) {
							maxProjNameLen = fields[1].length();
						}	
						currentRecord.setCOD(fields[2]);
						if (maxCODLen < fields[2].length()) {
							maxCODLen = fields[2].length();
						}	
						currentRecord.setState(fields[3]);
						if (maxStateLen < fields[3].length()) {
							maxStateLen = fields[3].length();
						}	
						
						if (isValidDouble(fields[4])) {
							currentRecord.setLat(Double.parseDouble(fields[4]));
						}
						if (isValidDouble(fields[5])) {
							currentRecord.setLon(Double.parseDouble(fields[5]));
						}
						if (isValidDouble(fields[6])) {
							currentRecord.setGHI(Double.parseDouble(fields[6]));
						}
						if (isValidDouble(fields[7])) {
							currentRecord.setSC_DC(Double.parseDouble(fields[7]));
						}
						if (isValidDouble(fields[8])) {
							currentRecord.setSC_AC(Double.parseDouble(fields[8]));
						}
						csvContent.add(currentRecord);
					}
				} else if (isValidEIA_ID(record)) { // for a record where only the EIA_ID field is filled 
					currentRecord.setEIA_ID(Integer.parseInt(record));
					csvContent.add(currentRecord);
				}
			}
		} catch (IOException e) {
         		System.out.println("I/O ERROR: Couldn't open, or couldn't read "
                             + "from, the CSV file.");
           		 System.exit(-1);
       		}

		try {
         		csvReader.close();
      		} catch (IOException e) {
            		System.out.println("VERY STRANGE I/O ERROR: Couldn't close "
                             + "the CSV file!");
           		 System.exit(-1);
       		}

		// sorted on EIA_ID because of "Comparable" Interface
		Collections.sort(csvContent);
		// sort the ArrayList of objects here
		return csvContent;
	}


	/*---------------------------------------------------------------------
        |  Method writeBinaryFile (String fileName,
        |                               ArrayList<DataRecord> csvContent)
        |
        |  Purpose: 	writes the data from DataRecord objects in csvContent to 
	|		a binary file.	
	|
	|  Pre-condition: the DataRecord objects in csvContent are populated and 
	|		  sorted in ascending order.        
	|
	|  Post-condition: the binary file, of the same name as the csv file, is
 	|		   populated with the records from the csv file.	
        |  
        |  Parameters:  fileName -- the file name of the binary file
	|		csvContent -- An ArrayList of DataRecord objects, that 
	|		represent the valid records from a csv file.
        |
        |  Returns:  None.
        |             
        *-------------------------------------------------------------------*/
	private static void writeBinaryFile (String fileName,
                                         ArrayList<DataRecord> csvContent) {
        
		File fileObj = null;              // provides exists(), delete()
	        RandomAccessFile binFile = null;  // RAF specializes in binary file I/O

                    // If an old version of this binary file exists, delete it.
                    // We can overwrite an old file, but it's safer to delete
                    // and start fresh.

        	try {
            		fileObj = new File(fileName + ".bin");
	            	if (fileObj.exists()) {
                		fileObj.delete();
           	 	}
        	} catch (Exception e) {
            		System.out.println("I/O ERROR: Something went wrong with the "
                             + "deletion of the previous binary file.");
            		System.exit(-1);
        	}

                    // (Re)Create the binary file.  The mode cannot be just
                    // "w"; that's not an acceptable option to Java.

        	try {
            		binFile = new RandomAccessFile(fileObj,"rw");
        	} catch (IOException e) {
            		System.out.println("I/O ERROR: Something went wrong with the "
                             + "creation of the RandomAccessFile object.");
            		System.exit(-1);
        	}

                    // Ask the DataRecord objects to write themselves to the
                    // binary file, in the same order in which they were read.

        	for (int i = 0; i < csvContent.size(); i++) {
            		DataRecord r = csvContent.get(i);
            		r.dumpObject(binFile, maxProjNameLen, maxCODLen, maxStateLen);
        	}

                    // Wrtiting is complete; close the binary file.

		// add the string field lengths here
		// stream.writeInt() -- three statements
		try {
        
			binFile.writeInt(maxProjNameLen);
			binFile.writeInt(maxCODLen);
			binFile.writeInt(maxStateLen);
       
        	} catch (IOException e) {
           		System.out.println("I/O ERROR: Couldn't write to the file;\n\t"
                            + "perhaps the file system is full?");
           		System.exit(-1);
        	}
		
	

		try {
            		binFile.close();
        	} catch (IOException e) {
            		System.out.println("VERY STRANGE I/O ERROR: Couldn't close "
                             + "the binary file!");
            		System.exit(-1);
        	}

    	}  

	public static void main (String [] args) {
		// when we're reading the csv file, we need the path name
		// when we're writing the bin file, we don't need the path name
		// however, to change from csv to bin extension, we need the file name
		String csvFileName = "";
		// csv File Name -- has the full path name since you want to read the whole file
		ArrayList<DataRecord> csvContent = null;  // the objects to write/read

		if (args.length > 0) {
			csvFileName = args[0];    // THIS IS THE ONE YOU SEND TO readCSVFile
		} else {
			System.out.println("No CSV file provided!");
			System.exit(0);
		}

		csvContent = readCSV(csvFileName);
		
		// extracting file name to convert to bin extension
		String[] arrOfStr = args[0].split("/");
		String str = arrOfStr[arrOfStr.length - 1];
		String binFileName = str.substring(0, str.length() - 4);
		// PASS binFileName AND 
		// WRITE TO BINARY HERE		
		writeBinaryFile(binFileName, csvContent);
	}
	
}

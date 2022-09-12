/* IMPORTANT-- I tried padding the output to the right with whitespace
 	    -- many times. It didn't happen. Not even the professor's 
 	    -- program works to show the remaining white space in a 
	    -- string field.
 	    -- EXAMPLE -- [Solar Power Plant                  ]

 	    --give file name as an argument from the command line
 	    --filename should include the path 
*/ 
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
        |  Method fetchObject(stream, ProgNameLen, CODLen, StateLen)
        |
        |  Purpose: writes from binary file to this object's appropriate fields.
	|	    The binary file is just a binary representation of a cleaned
	|	    csv file.	
	|
	|  Pre-condition:  stream is readable,
        |                  file pointer is positioned to new data's location.
        |
        |  Post-condition: fields are populated, 
	|		   file pointer is left at the end of the written data.
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

	public void fetchObject(RandomAccessFile stream, int ProjNameLen, int CODLen, int StateLen)	{
        	byte[] projTitle = new byte[ProjNameLen];  // ASCII, not UNICODE
        	byte[] CODString = new byte[CODLen];  // ASCII, not UNICODE
        	byte[] StateString = new byte[StateLen];  // ASCII, not UNICODE

        	try {
            		id = stream.readInt();	
            		
			stream.readFully(projTitle);        // reads all the bytes we need...
            		projName = new String(projTitle); // ...& makes a String of them
       		

			stream.readFully(CODString);        // reads all the bytes we need...
            		COD = new String(CODString); // ...& makes a String of them
			
			stream.readFully(StateString);        // reads all the bytes we need...
            		State = new String(StateString); // ...& makes a String of them
		
			lat = stream.readDouble();
		 	lon = stream.readDouble();
		 	ghi = stream.readDouble();
        		SC_DC = stream.readDouble();
        		SC_AC = stream.readDouble();
			
		} catch (IOException e) {
           		System.out.println("I/O ERROR: Couldn't read from the file;\n\t"
                            + "perhaps it doesn't have the expected content?");
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
 ||  Class Prog1B
 ||
 ||         Author(s):  Mugdha Sonawane and L. McCann
 ||
 ||        Purpose:     This class has several methods which are used to
 ||                     convert a particular format of binary file (which
 || 			was originally created from a csv file) to
 ||			human readable output. It prints out the first
 ||			three records, middle three records and last 
 ||			three records. Finally, it asks the user for an
 ||			EIA_ID, and outputs the corresponding fields.
 ||     
 ||     Inherits From:  None.
 ||
 ||     Interfaces:  None.
 ||
 |+-----------------------------------------------------------------------
 ||
 ||      Constants: None. 
 ||
 |+-----------------------------------------------------------------------
 ||     
 ||     Fields: int ProjNameLen, int CODLen, int StateLen; all of
 ||             them store the maximum field length.
 ||		int totalRecords -- total number of records in the file
 ||		int totalBytesInOneRecord -- total number of bytes in one
 ||						record
 ||     
 ||     Constructors:  None.
 ||
 ||     Class Methods: 	static ArrayList<DataRecord> readBinaryFile 
 ||				(RandomAccessFile binFile)
 ||			static int fileRecord(RandomAccessFile stream, 
 ||				int recordNumber) 
 ||			static int binarySearch(RandomAccessFile stream, 
 ||				int min, int max, int EIA_ID)
 ||			static int exponentialSearch(RandomAccessFile 
 ||				stream, int EIA_ID)
 ||			static void query(RandomAccessFile binFile)
 || 
 ||     Inst. Methods: None.   
 ++-----------------------------------------------------------------------*/
public class Prog1B {

	private static long totalRecords = 0; // this is only for the projectName length
	private static long totalBytesInOneRecord = 0;

        private static int ProjNameLen = 0;
        private static int CODLen = 0;
        private static int StateLen = 0;
	
	 /*---------------------------------------------------------------------
        |  Method readBinaryFile (RandomAccessFile binFile) 
        |
        |  Purpose: 	reads from binary file object and writes to console the first
	|		three, the middle three and the last three records.
        |           	The binary file is just a binary representation of a cleaned
        |           	csv file.  
        |
        |  Pre-condition:  stream is readable,
        |                  file pointer is positioned to start of the file.
        |
        |  Post-condition: fields of this class are populated, 
        |                  file pointer is left at the end of the last record,
	|		   the above-mentioned records are displayed on screen.
        |
        |  Parameters:
        |       binFile --  This is the stream object representing the data file
        |                   from which the data is being read.
        |
        |
        |  Returns:  ArrayList<DataRecord> csvContent. returns the DataRecord
	|		objects of the three types of record mentioned above
        *-------------------------------------------------------------------*/
	private static ArrayList<DataRecord> readBinaryFile (RandomAccessFile binFile) {
        	ArrayList<DataRecord> binContent = new ArrayList<DataRecord>();
        	long totalBytes = 0;
		// Open the binary file of data for reading
                try {
                        totalBytes = binFile.length(); 
                } catch (IOException e) {
                        System.out.println("I/O ERROR: Couldn't get the file's length.");
                        System.exit(-1);
                }

		if (totalBytes == 0) {
			return binContent;
		}

                try {
                        binFile.seek(totalBytes - 12);
                        ProjNameLen = binFile.readInt();
                        CODLen = binFile.readInt();
                        StateLen = binFile.readInt();
                } catch (IOException e) {
                        System.out.println("I/O ERROR: Seems we can't reset the file "
                             + "pointer to the start of the file.");
                        System.exit(-1);
                }
		// EIA_ID = integer and there are 5 doubles with 1 double having 8 bytes
                totalBytesInOneRecord = (long) (4 + ProjNameLen + CODLen + StateLen + (8 * 5));
                totalRecords = (totalBytes - 12) / totalBytesInOneRecord; 
		// problem -- there's 3 lines here for string field lengths that don't come into a proper record
                // so I've subtracted 12 from totalBytes        
			
		try {
            		binFile.seek(0);
        	} catch (IOException e) {
            		System.out.println("I/O ERROR: Seems we can't reset the file "
                             + "pointer to the start of the file.");
            		System.exit(-1);
        	}

                    // Read the records from the binary file into an
                    // in-memory data structure, for return to the caller.
                    // to hold binary file records
		// Number of records in each group of first, middle and last
		int forLimit = 0;
		long middleRecord = totalRecords / 2;	
		long lastGroupStart = totalRecords - 2;
		if (totalRecords < 3 && totalRecords > 0) {
			forLimit = (int)totalRecords;
			middleRecord = totalRecords/totalRecords; 
			lastGroupStart =  totalRecords/totalRecords; 
		} else {
			forLimit = 3;
		}
		
		// FIRST THREE
        	for (int i = 1; i <= forLimit; i++) {
            		DataRecord rec = new DataRecord(); // create object to hold record
            		rec.fetchObject(binFile, ProjNameLen, CODLen, StateLen);
            		binContent.add(rec);
        	}

		// MIDDLE GROUP
       		try {
		        binFile.seek(totalBytesInOneRecord * (middleRecord - 1));    	
        	} catch (IOException e) {
            		System.out.println("I/O ERROR: Seems we can't reset the file "
                             + "pointer to the start of the file.");
            		System.exit(-1);
        	}

		
		if (totalRecords % 2 == 0) {
                        // i = middleRecord to i = middleRecord + 1
                        // for loop to read in the records
        		for (int i = 1; i <= 2; i++) {
            			DataRecord rec = new DataRecord(); // create object to hold record
            			rec.fetchObject(binFile, ProjNameLen, CODLen, StateLen);
            			binContent.add(rec);
        		}
		} else if (totalRecords % 2 == 1){
                        // i = middleRecord to i = middleRecord + 2
                        // for loop to read in the records

        		for (int i = 1; i <= forLimit; i++) {
            			DataRecord rec = new DataRecord(); // create object to hold record
            			rec.fetchObject(binFile, ProjNameLen, CODLen, StateLen);
            			binContent.add(rec);
        		}
                }

                // i = lastGroupStart to i = lastGroupStart + 2
                // + 1 because the pointer needs to be at the start of the next record
                // instead of at the end of the current record
       		try {  
			binFile.seek(totalBytesInOneRecord * (lastGroupStart - 1));
		} catch (IOException e) {
            		System.out.println("I/O ERROR: Seems we can't reset the file "
                             + "pointer to the start of the file.");
            		System.exit(-1);
        	}

		for (int i = 1; i <= forLimit; i++) {
            		DataRecord rec = new DataRecord(); // create object to hold record
            		rec.fetchObject(binFile, ProjNameLen, CODLen, StateLen);
            		binContent.add(rec);
        	}


		return binContent;
    	}

	 /*---------------------------------------------------------------------
        |  Method fileRecord(RandomAccessFile stream, int recordNumber) 
        |
        |  Purpose:	finds the record recordNumber in the binary file
	|		represented by stream and reads the EIA_ID field
	|		of this record. This is a helper function for 
	|		exponentialSearch(), binarySearch(), query();	
	|
	|  Pre-condition:  stream is readable,
        |                  file pointer is positioned to start of the file.
        |
        |  Post-condition: file pointer is left at the end of the recordNumber's
	|			EIA_ID
        |                  
        |
        |  Parameters:
        |       stream --  This is the stream object representing the data file
        |                   from which the data is being read.
        |	recordNumber -- this is the number of the record that we're 
	|			looking for.
        |
        |  Returns: an int value that represents the EIA_ID of the record we're
       	|	    looking for.
  	*-------------------------------------------------------------------*/

	public static int fileRecord(RandomAccessFile stream, int recordNumber) {
		int recordFromFile = 0;		
		try {
                        stream.seek(totalBytesInOneRecord * (recordNumber - 1));
                } catch (IOException e) {
                        System.out.println("I/O ERROR: Seems we can't reset the file "
                             + "pointer to the start of the file.");
                        System.exit(-1);
                }
		
		try {	
                        recordFromFile = stream.readInt(); 
		} catch (IOException e) {
                        System.out.println("I/O ERROR: Couldn't read from the file;\n\t"
                            + "perhaps it doesn't have the expected content?");
                        System.exit(-1);
                }


		return recordFromFile;
	
	}	

	 /*---------------------------------------------------------------------
        |  Method binarySearch(RandomAccessFile stream, int min, int max, int EIA_ID)
        |
        |  Purpose:     searches for EIA_ID in the range of min and max, inclusive.
	|		Function uses binary search and uses the fileRecord() as a
	|		helper function since this is a binary file.
	|		This is a helper function for
        |               exponentialSearch() and query();
        |
        |  Pre-condition:  stream is readable,
        |                  file pointer is positioned to start of the file.
        |
        |  Post-condition: file pointer is left at the end of the record that
	|			is passed as a parameter to fileRecord().
        |
        |
        |  Parameters:
        |       stream --  This is the stream object representing the data file
        |                   from which the data is being read.
        |       min -- first record in range
	|	max -- last record in range
	|	EIA_ID -- this is the record whose position we're looking for
        |
        |  Returns: an int value that represents the position of the EIA_ID that
	|		we're looking for
        *-------------------------------------------------------------------*/
	public static int binarySearch(RandomAccessFile stream, int min, int max, int EIA_ID) {
		if (min > max) {
            		return -1;
        	}	

		int middle = min + (max - min)/2;
		
		// edge case for First Record
		if (middle == 0) {
			middle = 1;
		}	

        	if (EIA_ID == fileRecord(stream, middle)) {
	        	return middle;
        	} else if (EIA_ID < fileRecord(stream, middle)) {
        		return binarySearch(stream, min, middle - 1, EIA_ID);
	        } else {
            		return binarySearch(stream, middle + 1, max, EIA_ID);
        	}	
	}



	 /*---------------------------------------------------------------------
        |  Method exponentialSearch(RandomAccessFile stream, int EIA_ID) 
	|
        |  Purpose:     searches for EIA_ID in the binary file exponentially.
       	|		binarySearch() and fileRecord() are helper functions.
	|		more information in the spec for exponential search	
	|
	|  Pre-condition:  stream is readable,
        |                  file pointer is positioned to start of the file.
        |
        |  Post-condition: file pointer is left at the end of the record that
        |                       is passed as a parameter to fileRecord().
        |
        |
        |  Parameters:
        |       stream --  This is the stream object representing the data file
        |                   from which the data is being read.
        |       EIA_ID -- this is the record whose position we're looking for
        |
        |  Returns: an int value that represents the position of the EIA_ID that
        |               we're looking for
        *-------------------------------------------------------------------*/
	public static int exponentialSearch(RandomAccessFile stream, int EIA_ID) {
        	// stream = A
		// base case
		
		if (totalRecords  == 0) {
			return -1;
        	}

        	int limit = 1;

        	while (limit <= totalRecords && fileRecord(stream, limit) < EIA_ID) {
			limit *= 2;        // calculate the next power of 2
        	}

        	return binarySearch(stream, limit/2, Integer.min(limit, (int)totalRecords), EIA_ID);
    	}
	
	 /*---------------------------------------------------------------------
        |  Method  query(RandomAccessFile binFile)       
	|
        |  Purpose: queries the user for a valid EIA_ID, looks it up in the
	|	    binary file through exponential binary search, and gives
	|	    the user some information on the corresponding record if
	|	    it is found.	
	|
	|  Pre-condition:  binFile is readable,
        |                  file pointer is positioned to start of the file.
        |
        |  Post-condition: file pointer is left at the end of the record that
        |                       is passed as a parameter to exponentialSearch().
        |
        |
        |  Parameters:
        |       binFile --  This is the RandomAccessFile object representing the data file
        |                   from which the data is being read.
        |
        |  Returns: None
        *-------------------------------------------------------------------*/
	// input -- RandomAccessFile object -- binFile
	// you will need totalRecords, totalBytesInOneRecord, ProjNameLen, CODLen, StateLen as global variables
    	private static void query(RandomAccessFile binFile) {	
		Scanner scan = new Scanner(System.in);
               	System.out.println("Enter an EIA_ID: ");
	       	String currentInp = scan.nextLine();
                int EIA_ID = 0;
		int recordLineNumber = 0;
                try {
                        EIA_ID = Integer.parseInt(currentInp);
                } catch (NumberFormatException e) {
                        System.out.println("That is not a valid EIA_ID!");
                }
                while (EIA_ID != -1) {
                        System.out.println("This is what I have on record: " + currentInp);
                       	// perform the query here
			// call the function here with binFile and EIA_ID as the parameter
		      	recordLineNumber = exponentialSearch(binFile, EIA_ID);
			if (recordLineNumber == -1) {
				System.out.println("A record with this EIA_ID does not exist.");
			} else {
				// try catch block to seek to the correct place 
				try {
        		                binFile.seek(totalBytesInOneRecord * (recordLineNumber - 1));
                		} catch (IOException e) {
                        		System.out.println("I/O ERROR: Seems we can't reset the file "
                             		+ "pointer to the start of the file.");
                        		System.exit(-1);
                		}
				DataRecord rec = new DataRecord(); // create object to hold record
                                rec.fetchObject(binFile, ProjNameLen, CODLen, StateLen);
				System.out.println("[" + rec.getEIA_ID() + "][" + rec.getProjName() + "][" + rec.getSC_AC() + "]\n");
			}
               		System.out.println("Enter an EIA_ID: ");
			currentInp = scan.nextLine();
                        try {
                                EIA_ID = Integer.parseInt(currentInp);
                        } catch (NumberFormatException e) {
                                System.out.println("That is not a valid EIA_ID!");
                        }
                }
                System.out.println("Goodbye!");
		System.exit(0);

       	}
	

	public static void main (String [] args) {
         	String filename = args[0]; 
		RandomAccessFile binFile = null;  // RAF specializes in binary file I/O
                try {
                        binFile = new RandomAccessFile(filename,"r");
                } catch (IOException e) {
                        System.out.println("I/O ERROR: Something went wrong with the "
                             + "opening of the RandomAccessFile object.");
                        System.exit(-1);
                }

  		ArrayList<DataRecord> binContent = readBinaryFile(binFile);
		
		if (binContent.size() > 0) {	
			for (int i = 0; i < binContent.size(); i++) {
         			DataRecord rec = binContent.get(i);
            			System.out.println("[" + rec.getEIA_ID() + "][" + rec.getProjName() + "][" + rec.getSC_AC() + "]");
        		}
		}
		System.out.println("\nThere are " + totalRecords
                        + " records in the file.\n");
		query(binFile);
	
			
		try {
            		binFile.close();
        	} catch (IOException e) {
            		System.out.println("VERY STRANGE I/O ERROR: Couldn't close "
                             + "the binary file after reading!");
            		System.exit(-1);
        	}
		
		// call a function here called "query"
	}


}


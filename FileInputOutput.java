package fileIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileInputOutput {
	public void createFile(String fileName) {
		try {
			File addressFile = new File(fileName);
			if(addressFile.createNewFile()) {
				System.out.println("the file does not exist, the system will create one");
			}
			else {
				System.out.println("File already exists");
			}
			System.out.println(fileName + " is located at " + addressFile.getAbsolutePath());
			if(addressFile.canRead()) {
				System.out.println(fileName + " is readable");
			}
			else {
				System.out.println(fileName + " is not readable");
			}
			if(addressFile.canWrite()) {
				System.out.println(fileName + " is writable");
			}
			else {
				System.out.println(fileName + " is not writable");
			}
			if(addressFile.canExecute()) {
				System.out.println(fileName + " is executable");
			}
			else {
				System.out.println(fileName + " is not executable");
			}
		}
		catch(IOException ie) {
			ie.printStackTrace();
		
		}
	}
	public void writeToFile(String fileName, String text) {
		try(FileWriter fw = new FileWriter(fileName)){
			fw.write(text);
			System.out.println("Wrote " + text + " to " + fileName);
		} 
		catch (IOException e) {
			//  Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void readFromFile(String fileName) {
		File file = new File(fileName);
		try(Scanner reader = new Scanner(file)){
			String fullText = "";
			while (reader.hasNextLine()) {
				String nl = reader.nextLine();
				System.out.println("Next line: " + nl);
		        fullText += nl;
		        
		        if(reader.hasNextLine()) {
		        	fullText += System.lineSeparator();
		        }
		    }
			System.out.println("Contents of " + fileName + ": ");
			System.out.println(fullText);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void appendToFile(String fileName, String string) {
		try(FileWriter fw = new FileWriter(fileName, true);){
			fw.write(System.lineSeparator());
			fw.write(string);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		String fileName = "address.txt";
		String country = "USA";
		String state = "NJ";
		String city = "Newark";
		String zipcode = "07102";
		String streetName ="lincoln ave";
		String houseNumber= "480";
		FileInputOutput fio = new FileInputOutput();
		fio.createFile(fileName);
		fio.writeToFile(fileName,"the country is : " + country);
		fio.appendToFile(fileName,"the state is : " + state);
		fio.appendToFile(fileName,"the city is : " + city);
		fio.appendToFile(fileName,"streetname : " + streetName);
		fio.appendToFile(fileName,"houseNumber : " + houseNumber);
		fio.appendToFile(fileName,"the zipcode is : " + zipcode);
		fio.readFromFile(fileName);
		fio.appendToFile(fileName,"the address is : " + houseNumber +" "+ streetName +" "+ city + " "+ state+" "+ zipcode  );
		fio.readFromFile(fileName);

		
	}

}
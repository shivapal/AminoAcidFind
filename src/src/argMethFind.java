package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

//Author: Shiva Pal
//Date: 12/25/17
//Takes in amino acid sequence to find location of a residue, and outputs a file of their positions
//Required input format: List protein name first without spaces in front. Then list position number of the first amino acid in the line at the beginning of the line, followed by the amino acids
public class argMethFind {
	static List <String> linePos;
	static List <String> protStore;
	static JFrame frame;
	static JPanel pane;
	static JTextField entry;
	static JComboBox<Character> residue;
	static String instructions;
	static String instructions1;
	
	/*
	@param filename: name of file to be parsed with program 
	@param residue: single capital letter code for the amino acid of interest
	reads input file and writes it to an output file named "output.txt" in the src folder
	*/
	private static void read(String filename, char residue){
		linePos = new ArrayList<String>();
		protStore = new ArrayList<String>();
		
		try{
            for(String line : Files.readAllLines(Paths.get(filename))){
                linePos.add(line);
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
		
		for(String line: linePos){
			if(line.length()>0){
				if(line.charAt(0)!='1'&&line.charAt(0)!='2'&&line.charAt(0)!='3'&&line.charAt(0)!='4'&&line.charAt(0)!='5'&&line.charAt(0)!='6'&&line.charAt(0)!='7'&&line.charAt(0)!='8'&&line.charAt(0)!='9'
						&&line.charAt(0)!=' '){
					protStore.add(line);
				} else{
					String noSpace=removeSpace(line);
					int lineResPos=lineResiduePosition(noSpace);
					parseArg(noSpace,lineResPos, residue);
				}
			}
			
		}
		try{
            String output = "src/output.txt";
            String title = "Amino Acid Positions";
            Files.write(Paths.get(output), title.getBytes());
        }catch(IOException ex){
            ex.printStackTrace();
        }
		for(int i=0; i<protStore.size();i++){
			write(protStore.get(i).toString());
			System.out.println(protStore.get(i));
		}
	}
	
	/*@param input: string to be parsed
	 *@param lineRes: position of amino acid at front of line
	 *@param residue: amino acid to find
	 *searches input file for position of amino acid of interest and records those positions
	 */
	private static void parseArg(String input, int lineRes, char residue){
		List<Character> charStore = new ArrayList<Character>();
		if(input.length()>0){
			for(int i = 0 ; i<input.length();i++){
				charStore.add(input.charAt(i));
			}
		}
		for(int i=0;i<charStore.size();i++){
			if(charStore.get(i)==residue){
				int position = lineRes+i-1;
				String posString = Integer.toString(position);
				protStore.add(posString);
			}
		}
	}
	
	/*@param input: String input to write to output file
	 * writes input string to output file
	 */
	private static void write(String input){
		try{
            String filename = "src/output.txt";
            String modInput= System.lineSeparator() + input; 
            Files.write(Paths.get(filename), modInput.getBytes(),StandardOpenOption.APPEND);
        }catch(IOException ex){
            ex.printStackTrace();
        }
	}
	
	/*@param input: String that will have spaces removed from
	 * removes spaces from string
	 */
	private static String removeSpace(String input){
		List<Character> charStore = new ArrayList<Character>();
		String retVal="";
		if(input.length()>0){
			for(int i = 0 ; i<input.length();i++){
				if(input.charAt(i)!=' ')
				charStore.add(input.charAt(i));
			}
		}
		for(Character c: charStore){
			retVal= retVal + c;
		}
		return retVal;
	}
	
	/*@param input: string to be analyzed
	 * returns the position of the residue at the front of the line
	 */
	private static int lineResiduePosition(String input){
		String numString="";
		if(input.length()>0){
			for(int i = 0 ; i<input.length();i++){
				if(input.charAt(i)=='1'||input.charAt(i)=='2'||input.charAt(i)=='3'||input.charAt(i)=='4'||input.charAt(i)=='5'||input.charAt(i)=='6'||input.charAt(i)=='7'||input.charAt(i)=='8'||
						input.charAt(i)=='9'||input.charAt(i)=='0'){
					numString = numString+ input.charAt(i);
				}
			}
		}
		int retVal=new Integer(numString);
		return retVal;
	}
	
	/*populates GUI components
	 * 
	 */
	private static void populateUI(){
        frame.getContentPane().add(pane);
        pane.add(entry);
        residue.addItem('A');
        residue.addItem('R');
        residue.addItem('N');
        residue.addItem('D');
        residue.addItem('C');
        residue.addItem('Q');
        residue.addItem('E');
        residue.addItem('G');
        residue.addItem('H');
        residue.addItem('I');
        residue.addItem('L');
        residue.addItem('K');
        residue.addItem('M');
        residue.addItem('F');
        residue.addItem('P');
        residue.addItem('S');
        residue.addItem('T');
        residue.addItem('W');
        residue.addItem('Y');
        residue.addItem('V');
        pane.add(residue);
        JButton enter = new JButton("Submit");
        enter.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				read(entry.getText(),residue.getItemAt(residue.getSelectedIndex()));
			}
        });
        pane.add(enter);
        JLabel instruct = new JLabel(instructions);
        JLabel instruct1 = new JLabel(instructions1);
        pane.add(instruct);
        pane.add(instruct1);
	}
	
	//sets up GUI
	private static void runGUI(){
		frame = new JFrame("Amino Acid Locator");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        pane = new JPanel();
        entry = new JTextField(40);
        residue = new JComboBox();
        instructions = "Enter in a path to a path to a valid text file, for example \"src/src/arginineSource.txt\"";
        instructions1 = "Then, select the amino acid you wish to locate and press Submit";
        populateUI();
	}
	
	public static void main(String [] args){
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                runGUI();
            }
        });
	}
}

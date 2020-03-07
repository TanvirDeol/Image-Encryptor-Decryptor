package imgProcess;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Scanner;
/**
 * This is the main class, that runs the Image Encryption Program
 * Below are all the public variable used across the program's files. 
 * @author Tanvir
 *
 */
public class imgProcess {

	  public static String seed ="";
	  public static String saveFirst = "resources/defaultImage.png";
	  public static String path = "resources/";
	  public static String fileName = "";
	  public static String rotSamplePath = "resources/RotImage.png";
	  public static BufferedImage newImg;
	  public static BufferedImage rotSample;
	  public static BufferedImage toDecrypt;
	  public static BufferedImage newImg2;
	  public static BufferedImage sample;
	  public static BufferedImage defaultImage;

	  /**
	   * This is the main method, where all other methods are 
	   * called from <br>
	   * 
	   * @param args
	   * 
	   */
	public static void main(String[] args)  {
		
		imageDecryptor imgD = new imageDecryptor();
		custom seedGenerator = new custom();
		
		Scanner input = new Scanner(System.in);
		System.out.print("Would you like to Encrypt or Decrypt an image? (E/D)");
		String ans = input.next();
		try {
		if (ans.toUpperCase().startsWith("E")) {
			System.out.println("Enter the name of the file to Encrypt (yourImage.png/jpg)");
			fileName = input.next();
			defaultImage = ImageIO.read(new File(path+fileName));
			System.out.println("Image Loaded");
			seed = custom.stringGen(10);
			newImg = new BufferedImage(defaultImage.getWidth(), defaultImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			newImg2 = new BufferedImage(defaultImage.getWidth(), defaultImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			initializeFiles(defaultImage);
			merger(defaultImage,newImg, 0);
			writeToFile(newImg,saveFirst);
			sample = ImageIO.read(new File(saveFirst));

			rotSample = ImageIO.read(new File(saveFirst));
			merger(rotSample, newImg2, 1);
			writeToFile(newImg2,rotSamplePath); 
			System.out.println("See the result in the resources folder.");
			input.close();
			System.exit(0);
			
		}}catch(Exception e){
			System.out.println("File not found, restarting program...");
			main(args);
		}

		if (ans.toUpperCase().startsWith("D")) {
			System.out.println("Enter the name of the file to Decrypt (encryptedImage.png/jpg)");
			fileName = input.next();
			try {
			toDecrypt = ImageIO.read(new File (path+fileName));
			}catch(Exception e){
				System.out.println("File not found, restarting program...");
				main(args);
			}
			System.out.println("Image Loaded");
			System.out.println("Enter the seed for Decryption");
			
			seed = input.next();
			if (seed.length()!= 10) {
				System.out.println("This is not a valid seed, your seed must be a 10-digit number");
				main(args);
			}try {
			double n = Double.parseDouble(seed);
			}catch (NumberFormatException e) {
				System.out.println("This is not a valid seed, restarting....");
				main(args);
			}
			imageDecryptor.Decrypt(toDecrypt, seed);
			System.out.println("See the result in the resources folder.");
			input.close();
			System.exit(0);
			
		}else {
			System.out.println("Please Enter either E or D.");
			main(args);
			
		}	
		
		input.close();
		System.exit(0);
		}
	
	/**
	 * This private method is the first step in the encryption process.<br>
	 * Two duplicate <b>BufferedImages</b> are written to the same directory to function as containers. <br>
	 * Soon they will hold the encrypted images.
	 * @param img This is the default image that the user inputs, the duplicates are created from this.
	 */
	private static void initializeFiles(BufferedImage img) {
		writeToFile(img, saveFirst);
		writeToFile(img, rotSamplePath);
	}
	
	
	/**
	 * Merger is another private method exclusive to the Encryption process. <br>
	 * This method splits the inputed image into 10 equal strips, and uses the seed to determine their new position.<br>
	 * These strips are pasted onto a new BufferedImage and returned. 
	 * @param defaultImage - This is the image from which the strips are extracted.
	 * @param mergedImg - The strips from the default image are pasted here in a random position, determined by the seed.
	 * @param x - When the user inputs 0, the strips are extracted vertically, when the input is 1, the strips are extracted horizontally. <br>
	 * calling these numbers one after the other allows the program to randomize image sections onto a 10x10 grid. 
	 * 
	 * @return - A BufferedImage object is returned, ready to be written to the directory. 
	 */
	private static BufferedImage merger(BufferedImage defaultImage, BufferedImage mergedImg, int x){
		if (x ==0) {
		for (int i = 0; i<10; i++) {
			int n = (int)seed.charAt(i)-48;
			BufferedImage processed = defaultImage.getSubimage(0, i*(defaultImage.getHeight()/10), defaultImage.getWidth(), defaultImage.getHeight()/10);
			Graphics merge = mergedImg.getGraphics();
			merge.drawImage(processed, 0, mergedImg.getHeight()/10 * n, null);
			
			}}
		if (x==1) {
			for (int i = 0; i<10; i++) {
				int n = (int)seed.charAt(i)-48;
				BufferedImage processed = defaultImage.getSubimage(i*(defaultImage.getWidth()/10),0, defaultImage.getWidth()/10, defaultImage.getHeight());
				Graphics merge = mergedImg.getGraphics();
				merge.drawImage(processed, mergedImg.getWidth()/10 * n,0 , null);
				
				}
		}
		
		return mergedImg;
	}
	/**
	 * This is a public method, that can be accessed from other files in this program.  <br>
	 * This method takes in an Image Object and a fileName, and writes the image to the resource folder. 
	 * 
	 * @param bfImg - The BufferedImage object to be written to the disk
	 * @param fileLoc - Holds the exact name and fileType of the Image. 
	 */
	
	public static void writeToFile(BufferedImage bfImg, String fileLoc) {
		try {
			ImageIO.write(bfImg, "png", new File (fileLoc));
		}catch (IOException e) {
			System.out.println("Didnt Work");
			System.exit(0);
				}
			}
	
}

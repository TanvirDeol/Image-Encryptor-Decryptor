package imgProcess;

import java.awt.image.BufferedImage;
import java.awt.*;
/**
 * This is the method that decrypts an encrypted image. The methods in this class are called to reverse the algorithm using the data from the seed. 
 * @author Tanvir
 *
 */
public class imageDecryptor {
	public static BufferedImage newImg3;
	public static BufferedImage newImg4;

	
/**
 * This is the primary method for the decryption. It is called from the main method when the user chooses to decrypt.
 * 
 * @param bfr - The program reads the encrypted image from the directory and sends it to this method.
 * @param seed - The user enters the secret seed. Only the correct seed will successfully decrypt the image. 
 * 
 */
	public static void Decrypt(BufferedImage bfr, String seed) {
		String saveDec = "resources/Decrypted.png";
		imgProcess mainP = new imgProcess();
		newImg3 = new BufferedImage(bfr.getWidth(), bfr.getHeight(), BufferedImage.TYPE_INT_RGB);
		newImg4 = new BufferedImage(bfr.getWidth(), bfr.getHeight(), BufferedImage.TYPE_INT_RGB);
		reverseMerge(bfr,newImg3, seed,0);
		reverseMerge(newImg3,newImg4, seed,1);
		imgProcess.writeToFile(newImg4, saveDec);
	}
	
/**
 * This method is the reverse algorithm for the encryption, it finds the location of image strip at position N, and moves it to 
 * position i. The integer parameters are also used to run this algorithm horizontally and vertically. 
 * @param img - The BufferedImage object to be deciphered. 
 * @param blank - The BufferedImage object (blank) to which the deciphered strips will be written to. 
 * @param seed - The generated seed used to reverse the encryption. 
 * @param x - Integer parameter for vertical and horizontal image deciphering. 
 * @return - Returns deciphered image back to parent method to be written. 
 */
	public static BufferedImage reverseMerge(BufferedImage img, BufferedImage blank, String seed, int x) {
		if (x ==0) {
			for (int i = 0; i<10; i++) {
				int n = (int)seed.charAt(i)-48;
				BufferedImage processed = img.getSubimage(0, n*(img.getHeight()/10), img.getWidth(), img.getHeight()/10);
				Graphics merge = blank.getGraphics();
				merge.drawImage(processed, 0, blank.getHeight()/10 * i, null);
				
				}
			
		}
		if (x ==1) {
			for (int i = 0; i<10; i++) {
				int n = (int)seed.charAt(i)-48;
				BufferedImage processed = img.getSubimage(n*(img.getWidth()/10),0, img.getWidth()/10, img.getHeight());
				Graphics merge = blank.getGraphics();
				merge.drawImage(processed, blank.getWidth()/10 * i,0 , null);
				
			}
		}
		return blank;
	}
}

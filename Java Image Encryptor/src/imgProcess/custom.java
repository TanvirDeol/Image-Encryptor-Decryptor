package imgProcess;
import java.util.Random;
/**
 * This class is responsible for creating a seed, with specific requirements. This seed is randomly generated 
 * and is used to determine the position of image strips in the final encrypted image. 
 * @author gamed
 *
 */
public class custom {
		/**
		 * This is the primary method for the seed generator class. 
		 * <br>
		 * This seed that is generated has some specific requirements to meet, otherwise it would cause data loss in the image. 
		 * Each number in this string is assigned to a image strip in the main program. 
		 * <br> - There can be no duplicate number in this string, otherwise it would show a image strip more than once. 
		 * <br> - There can also be no consecutive numbers in the string, because that show a large portion of the original image. 
		 * @param x - An integer is inputed from the main class. This integer represents the number of characters 
		 * in the seed.
		 * @return - This method returns a randomly generated string back to the main method. 
		 */
	public static String stringGen(int x) {
		Random seedGen = new Random();
		String str ="";
		
		for (int i =0; i<x; i++) {
			int n = seedGen.nextInt(9);

			while (str.contains(""+n)) {
				n++;
				if (n>9) {
					n = 0;
				}
			}
			str += n;
		}

		str = nonConsec(str);
		return str;
	}
	/**
	 * This is the method that removes consecutive pairs of numbers in the string because 
	 * there can also be no consecutive numbers in the string, since that would show a large portion of the original image. 
	 * @param str - The string from the stringGen method is inputed here to be analyzed.
	 * @return - The string is corrected for any consecutive errors and its returned back to its original method. 
	 */
	private static String nonConsec(String str) {
		str+= " ";
		for (int i = 0; i <str.length()-1; i++) {
			int n = Integer.parseInt(str.substring(i, i+1));
			int k = 0;
			if (i>0) {
			k = Integer.parseInt(str.substring(i-1, i));
				if (n == k+1) {
					char rep = str.charAt(i-1);
					char cur = str.charAt(i);
					char inc = (char)((int)str.charAt(i-1)+1);
					str = str.replace(cur, rep);
					str = str.replaceFirst(rep+"", inc+"");
			}	
		}
		
	}
		System.out.println("Final Seed: "+ str);
		return str;
}
	}

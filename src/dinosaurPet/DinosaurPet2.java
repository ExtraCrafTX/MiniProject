/**
 *  Gives your pet the name that you chose and tells you how thirsty it is.
 */

package dinosaurPet;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Kaamil Jasani
 */
public class DinosaurPet2 {
    
    public static void main(String[] args) {
        String name = getName();
        outputName(name);
        int thirst = getThirstLevel();
        outputThirst(thirst, name);
    }
    
    public static void outputName(String name){
        System.out.println("Happy birthday " + name + "!");
    }
    
    public static void outputThirst(int thirst, String name){
        System.out.println("The thirst level of " + name + " is " + thirst + "/10.");
    }
    
    public static String getName(){
        return input("What is your pet's name?");
    }
    
    public static int getThirstLevel(){
        Random random = new Random();
        return random.nextInt(11);
    }
    
    public static String input(String message){
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
}

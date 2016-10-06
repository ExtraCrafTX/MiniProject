package dinosaurPet;

import java.util.Scanner;

/**
 *
 * @author Kaamil Jasani
 */
public class DinosaurPet1 {
    
    public static void main(String[] args) {
        String name = getName();
        outputName(name);
    }
    
    public static void outputName(String name){
        System.out.println("Happy birthday " + name + "!");
    }
    
    public static String getName(){
        return input("What is your pet's name?");
    }
    
    public static String input(String message){
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
}

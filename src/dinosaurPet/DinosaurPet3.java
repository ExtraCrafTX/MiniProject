/**
 * Gives your pet the name that you chose and tells you how thirsty and hungry
 * it is, as well as its anger score.
 */

package dinosaurPet;

//Imports utilities for generating random numbers and taking input
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Kaamil Jasani
 */
public class DinosaurPet3 {
    
    //Launched on start of program
    public static void main(String[] args) {
        String name = getName();
        String species = getSpecies();
        outputName(name);
        outputSpecies(species, name);
        int thirst = getThirstLevel();
        outputThirst(thirst, name);
        int hunger = getHungerLevel();
        outputHunger(hunger, name);
        int anger = getAngerScore(thirst, hunger);
        outputAnger(anger, name);
    }
    
    //Outputs the name of the pet
    public static void outputName(String name){
        System.out.println("Happy birthday " + name + "!");
    }
    
    //Outputs the name of the pet
    public static void outputSpecies(String species, String name){
        System.out.println(name + " is a " + species + ".");
    }
    
    //Outputs the thirst level of the pet
    public static void outputThirst(int thirst, String name){
        System.out.println("The thirst level of " + name + " is " + thirst + "/10.");
    }
    
    //Outputs the hunger level of the pet
    public static void outputHunger(int hunger, String name){
        System.out.println("The hunger level of " + name + " is " + hunger + "/10.");
    }
    
    //Outputs the anger score of the pet
    public static void outputAnger(int anger, String name){
        if(anger == 0){
            System.out.println(name + " is relatively calm.");
        }else if(anger == 1){
            System.out.println(name + " is slightly irritable.");
        }else if(anger == 2){
            System.out.println(name + " is getting pretty angry now.");
        }else if(anger == 3){
            System.out.println(name + " is getting quite dangerous now!");
        }else if(anger == 4){
            System.out.println(name + " is about ready to explode!");
        }else{
            System.out.println("Your pet is being put down for everyone's safety.");
        }
    }
    
    //Gets the name of the pet from the user
    public static String getName(){
        return input("What is your pet's name?");
    }
    
    //Gets the species of the pet from the user
    public static String getSpecies(){
        return input("What is your pet's species?");
    }
    
    //Gets the thirst level of the pet (currently at random)
    public static int getThirstLevel(){
        Random random = new Random();
        return random.nextInt(11);
    }
    
    //Gets the thirst level of the pet (currently at random)
    public static int getHungerLevel(){
        Random random = new Random();
        return random.nextInt(11);
    }
    
    //Gets the anger score of the pet
    public static int getAngerScore(int thirst, int hunger){
        return (thirst + hunger) / 4;
    }
    
    //Gets input from the user with the specified message
    public static String input(String message){
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
}
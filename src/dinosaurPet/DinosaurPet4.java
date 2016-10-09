/**
 * Gives your pet the name and species that you chose and tells you how thirsty 
 * and hungry it is, as well as its anger score.
 */

package dinosaurPet;

//Imports utilities for generating random numbers and taking input
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Kaamil Jasani
 */
public class DinosaurPet4 {
    
    //Launched on start of program
    public static void main(String[] args) {
        explainProgram();
        Pet4 pet = new Pet4();
        setName(pet, inputName());
        setSpecies(pet, inputSpecies());
        outputName(pet);
        outputSpecies(pet);
        setThirst(pet, calculateThirstLevel());
        outputThirst(pet);
        setHunger(pet, calculateHungerLevel());
        outputHunger(pet);
        calculateAnger(pet);
        outputAnger(pet);
    }
    
    //Explains what the program is
    public static void explainProgram(){
        System.out.println("This program will allow you to name a pet and take care of it!");
    }
    
    //Outputs the name of the pet
    public static void outputName(Pet4 pet){
        System.out.println("Happy birthday " + getName(pet) + "!");
    }
    
    //Outputs the name of the pet
    public static void outputSpecies(Pet4 pet){
        System.out.println(getName(pet) + " is a " + getSpecies(pet) + ".");
    }
    
    //Outputs the thirst level of the pet
    public static void outputThirst(Pet4 pet){
        System.out.println("The thirst level of " + getName(pet) + " is " + getThirst(pet) + "/10.");
    }
    
    //Outputs the hunger level of the pet
    public static void outputHunger(Pet4 pet){
        System.out.println("The hunger level of " + getName(pet) + " is " + getHunger(pet) + "/10.");
    }
    
    //Outputs the anger score of the pet
    public static void outputAnger(Pet4 pet){
        int anger = getAnger(pet);
        String name = getName(pet);
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
    public static String inputName(){
        return input("What is your pet's name?");
    }
    
    //Gets the species of the pet from the user
    public static String inputSpecies(){
        return input("What is your pet's species?");
    }
    
    //Gets the thirst level of the pet (currently at random)
    public static int calculateThirstLevel(){
        Random random = new Random();
        return random.nextInt(11);
    }
    
    //Gets the thirst level of the pet (currently at random)
    public static int calculateHungerLevel(){
        Random random = new Random();
        return random.nextInt(11);
    }
    
    //Gets input from the user with the specified message
    public static String input(String message){
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
    //Sets the name of the specified pet
    public static void setName(Pet4 pet, String name){
        pet.name = name;
    }
    
    //Sets the species of the specified pet
    public static void setSpecies(Pet4 pet, String species){
        pet.species = species;
    }
    
    //Sets the thirst of the specified pet
    public static void setThirst(Pet4 pet, int thirst){
        pet.thirst = thirst;
    }
    
    //Sets the hunger of the specified pet
    public static void setHunger(Pet4 pet, int hunger){
        pet.hunger = hunger;
    }
    
    //Calculates the anger of the specified pet
    public static void calculateAnger(Pet4 pet){
        pet.anger = (pet.thirst + pet.hunger)/4;
    }
    
    //Gets the name of the specified pet
    public static String getName(Pet4 pet){
        return pet.name;
    }
    
    //Gets the species of the specified pet
    public static String getSpecies(Pet4 pet){
        return pet.species;
    }
    
    //Gets the thirst of the specified pet
    public static int getThirst(Pet4 pet){
        return pet.thirst;
    }
    
    //Gets the hunger of the specified pet
    public static int getHunger(Pet4 pet){
        return pet.hunger;
    }
    
    //Gets the anger of the specified pet
    public static int getAnger(Pet4 pet){
        return pet.anger;
    }
    
}

//Record class to store information about pet
class Pet4{
    
    String name;
    String species;
    int thirst;
    int hunger;
    int anger;
    
}
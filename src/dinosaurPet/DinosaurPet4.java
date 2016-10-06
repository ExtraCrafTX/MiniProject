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
        Pet pet = new Pet();
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
    
    //Outputs the name of the pet
    public static void outputName(Pet pet){
        System.out.println("Happy birthday " + getName(pet) + "!");
    }
    
    //Outputs the name of the pet
    public static void outputSpecies(Pet pet){
        System.out.println(getName(pet) + " is a " + getSpecies(pet) + ".");
    }
    
    //Outputs the thirst level of the pet
    public static void outputThirst(Pet pet){
        System.out.println("The thirst level of " + getName(pet) + " is " + getThirst(pet) + "/10.");
    }
    
    //Outputs the hunger level of the pet
    public static void outputHunger(Pet pet){
        System.out.println("The hunger level of " + getName(pet) + " is " + getHunger(pet) + "/10.");
    }
    
    //Outputs the anger score of the pet
    public static void outputAnger(Pet pet){
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
            System.out.println(name + " is about ready to explode! "
                    + "Your pet is being put down for everyone's safety.");
        }else{
            System.out.println("How is " + name + " not dead yet?!");
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
    public static void setName(Pet pet, String name){
        pet.name = name;
    }
    
    //Sets the species of the specified pet
    public static void setSpecies(Pet pet, String species){
        pet.species = species;
    }
    
    //Sets the thirst of the specified pet
    public static void setThirst(Pet pet, int thirst){
        pet.thirst = thirst;
    }
    
    //Sets the hunger of the specified pet
    public static void setHunger(Pet pet, int hunger){
        pet.hunger = hunger;
    }
    
    //Calculates the anger of the specified pet
    public static void calculateAnger(Pet pet){
        pet.anger = (pet.thirst + pet.hunger)/4;
    }
    
    //Gets the name of the specified pet
    public static String getName(Pet pet){
        return pet.name;
    }
    
    //Gets the species of the specified pet
    public static String getSpecies(Pet pet){
        return pet.species;
    }
    
    //Gets the thirst of the specified pet
    public static int getThirst(Pet pet){
        return pet.thirst;
    }
    
    //Gets the hunger of the specified pet
    public static int getHunger(Pet pet){
        return pet.hunger;
    }
    
    //Gets the anger of the specified pet
    public static int getAnger(Pet pet){
        return pet.anger;
    }
    
}

//Record class to store information about pet
class Pet{
    
    String name;
    String species;
    int thirst;
    int hunger;
    int anger;
    
}
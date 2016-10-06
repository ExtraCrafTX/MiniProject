/**
 * Gives your pet the name and species that you chose, tells you how thirsty 
 * and hungry it is, as well as its anger score and lets you take care of it.
 */

package dinosaurPet;

//Imports utilities for generating random numbers and taking input
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Kaamil Jasani
 */
public class DinosaurPet5 {
    
    //Launched on start of program
    public static void main(String[] args) {
        Pet5 pet = new Pet5();
        setName(pet, inputName());
        setSpecies(pet, inputSpecies());
        outputName(pet);
        outputSpecies(pet);
        setThirst(pet, calculateThirstLevel());
        outputThirst(pet);
        setHunger(pet, calculateHungerLevel());
        outputHunger(pet);
        setIrritation(pet, calculateIrritation());
        outputIrritation(pet);
        calculateAnger(pet);
        outputAnger(pet);
        while(getAnger(pet) != 5){
            String input = inputAction().toLowerCase();
            if(input.contains("feed")){
                feed(pet);
            }else if(input.contains("water")){
                water(pet);
            }else if(input.contains("sing")){
                sing(pet);
            }else{
                System.out.println("Please input a valid action.");
                continue;
            }
            System.out.println();
            outputThirst(pet);
            outputHunger(pet);
            outputIrritation(pet);
            outputAnger(pet);
        }
    }
    
    public static String inputAction(){
        return input("What would you like to do? (feed, water, sing, end)");
    }
    
    //Outputs the name of the pet
    public static void outputName(Pet5 pet){
        System.out.println("Happy birthday " + getName(pet) + "!");
    }
    
    //Outputs the name of the pet
    public static void outputSpecies(Pet5 pet){
        System.out.println(getName(pet) + " is a " + getSpecies(pet) + ".");
    }
    
    //Outputs the thirst level of the pet
    public static void outputThirst(Pet5 pet){
        System.out.println("The thirst level of " + getName(pet) + " is " + getThirst(pet) + "/10.");
    }
    
    //Outputs the hunger level of the pet
    public static void outputHunger(Pet5 pet){
        System.out.println("The hunger level of " + getName(pet) + " is " + getHunger(pet) + "/10.");
    }
    
    //Outputs the irritation of the pet
    public static void outputIrritation(Pet5 pet){
        System.out.println("The irritation of " + getName(pet) + " is " + getIrritation(pet) + "/10.");
    }
    
    //Outputs the anger score of the pet
    public static void outputAnger(Pet5 pet){
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
    
    //Gets the irritation of the pet (currently at random)
    public static int calculateIrritation(){
        Random random = new Random();
        return random.nextInt(11);
    }
    
    //Gets input from the user with the specified message
    public static String input(String message){
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
    //Feeds the pet
    public static void feed(Pet5 pet){
        Random random = new Random();
        int feedAmount = random.nextInt(5) + 1;
        if(getHunger(pet) < feedAmount){
            setHunger(pet, 0);
        }else{
            setHunger(pet, getHunger(pet) - feedAmount);
        }
    }
    
    //Gives the pet water
    public static void water(Pet5 pet){
        Random random = new Random();
        int waterAmount = random.nextInt(5) + 1;
        if(getThirst(pet) < waterAmount){
            setThirst(pet, 0);
        }else{
            setThirst(pet, getThirst(pet) - waterAmount);
        }
    }
    
    //Sings to the pet
    public static void sing(Pet5 pet){
        Random random = new Random();
        int singAmount = random.nextInt(5) + 1;
        if(getIrritation(pet) < singAmount){
            setIrritation(pet, 0);
        }else{
            setIrritation(pet, getIrritation(pet) - singAmount);
        }
    }
    
    //Sets the name of the specified pet
    public static void setName(Pet5 pet, String name){
        pet.name = name;
    }
    
    //Sets the species of the specified pet
    public static void setSpecies(Pet5 pet, String species){
        pet.species = species;
    }
    
    //Sets the thirst of the specified pet
    public static void setThirst(Pet5 pet, int thirst){
        pet.thirst = thirst;
    }
    
    //Sets the hunger of the specified pet
    public static void setHunger(Pet5 pet, int hunger){
        pet.hunger = hunger;
    }
    
    //Sets the irritation of the specified pet
    public static void setIrritation(Pet5 pet, int irritation){
        pet.irritation = irritation;
    }
    
    //Calculates the anger of the specified pet
    public static void calculateAnger(Pet5 pet){
        pet.anger = (pet.thirst + pet.hunger + pet.irritation)/6;
    }
    
    //Gets the name of the specified pet
    public static String getName(Pet5 pet){
        return pet.name;
    }
    
    //Gets the species of the specified pet
    public static String getSpecies(Pet5 pet){
        return pet.species;
    }
    
    //Gets the thirst of the specified pet
    public static int getThirst(Pet5 pet){
        return pet.thirst;
    }
    
    //Gets the hunger of the specified pet
    public static int getHunger(Pet5 pet){
        return pet.hunger;
    }
    
    //Gets the irritation of the specified pet
    public static int getIrritation(Pet5 pet){
        return pet.irritation;
    }
    
    //Gets the anger of the specified pet
    public static int getAnger(Pet5 pet){
        return pet.anger;
    }
    
}

//Record class to store information about pet
class Pet5{
    
    String name;
    String species;
    int thirst;
    int hunger;
    int irritation;
    int anger;
    
}
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
public class DinosaurPet6 {
    
    //Launched on start of program
    public static void main(String[] args) {
        Pet6[] save = new Pet6[5];
        
        Pet6 pet = new Pet6();
        
        //Get name and species
        setName(pet, inputName());
        setSpecies(pet, inputSpecies());
        outputName(pet);
        outputSpecies(pet);
        
        //Randomly give it thirst
        setThirst(pet, calculateThirstLevel());
        outputThirst(pet);
        
        //Randomly give it hunger
        setHunger(pet, calculateHungerLevel());
        outputHunger(pet);
        
        //Randomly give it irritation
        setIrritation(pet, calculateIrritation());
        outputIrritation(pet);
        
        //Calculate anger score
        calculateAnger(pet);
        outputAnger(pet);
        
        //Game loop
        while(true){
            while(getAnger(pet) < 4){
                //Save the state
                save = saveState(save, pet);
                
                //Get input
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

                //Update state of mind
                updatePet(pet);

                //Output state of mind
                System.out.println();
                outputThirst(pet);
                outputHunger(pet);
                outputIrritation(pet);

                //Calculate anger score
                calculateAnger(pet);
                outputAnger(pet);
            }
            
            //Ask user for how many steps they want to go back
            int numStepsBack = numStepsBack();
            if(numStepsBack == 0){
                break;
            }else{
                //Load state
                pet = save[save.length - numStepsBack];
                
                System.out.println();
                outputThirst(pet);
                outputHunger(pet);
                outputIrritation(pet);

                calculateAnger(pet);
                outputAnger(pet);
            }
        }
    }
    
    //Gets the action that the user wants to do
    public static String inputAction(){
        return input("What would you like to do? (feed, water, sing, end)");
    }
    
    //Gets input from the user as to how many steps back they would like to go
    public static int numStepsBack(){
        int input = Integer.parseInt(input("How many steps back would you like to go?"));
        while(input < 0 || input > 5){
            System.out.println("Please input a valid number of steps (0-5).");
            input = Integer.parseInt(input("How many steps back would you like to go?"));
        }
        return input;
    }
    
    //Saves the state of the pet
    public static Pet6[] saveState(Pet6[] save, Pet6 pet){
        Pet6[] newSave = new Pet6[save.length];
        for(int i = 1; i < save.length; i++){
            newSave[i-1] = save[i];
        }
        newSave[save.length - 1] = copyPet(pet);
        return newSave;
    }
    
    //Creates a copy of the pet to avoid errors due to referencing instead of value
    public static Pet6 copyPet(Pet6 pet){
        Pet6 savedPet = new Pet6();
        setName(savedPet, getName(pet));
        setSpecies(savedPet, getSpecies(pet));
        setThirst(savedPet, getThirst(pet));
        setHunger(savedPet, getHunger(pet));
        setIrritation(savedPet, getIrritation(pet));
        return savedPet;
    }
    
    //Outputs the name of the pet
    public static void outputName(Pet6 pet){
        System.out.println("Happy birthday " + getName(pet) + "!");
    }
    
    //Outputs the name of the pet
    public static void outputSpecies(Pet6 pet){
        System.out.println(getName(pet) + " is a " + getSpecies(pet) + ".");
    }
    
    //Outputs the thirst level of the pet
    public static void outputThirst(Pet6 pet){
        System.out.println("The thirst level of " + getName(pet) + " is " + getThirst(pet) + "/10.");
    }
    
    //Outputs the hunger level of the pet
    public static void outputHunger(Pet6 pet){
        System.out.println("The hunger level of " + getName(pet) + " is " + getHunger(pet) + "/10.");
    }
    
    //Outputs the irritation of the pet
    public static void outputIrritation(Pet6 pet){
        System.out.println("The irritation of " + getName(pet) + " is " + getIrritation(pet) + "/10.");
    }
    
    //Outputs the anger score of the pet
    public static void outputAnger(Pet6 pet){
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
        return random.nextInt(6);
    }
    
    //Gets the thirst level of the pet (currently at random)
    public static int calculateHungerLevel(){
        Random random = new Random();
        return random.nextInt(6);
    }
    
    //Gets the irritation of the pet (currently at random)
    public static int calculateIrritation(){
        Random random = new Random();
        return random.nextInt(6);
    }
    
    //Updates the state of mind of the pet
    public static void updatePet(Pet6 pet){
        Random random = new Random();
        int action = random.nextInt(3);
        switch(action){
            case 0:
                addThirst(pet);
                break;
            case 1:
                addHunger(pet);
                break;
            case 2:
                addIrritation(pet);
                break;
        }
    }
    
    //Gets input from the user with the specified message
    public static String input(String message){
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
    //Feeds the pet
    public static void feed(Pet6 pet){
        Random random = new Random();
        int feedAmount = random.nextInt(6) + 1;
        if(getHunger(pet) < feedAmount){
            setHunger(pet, 0);
        }else{
            setHunger(pet, getHunger(pet) - feedAmount);
        }
    }
    
    //Gives the pet water
    public static void water(Pet6 pet){
        Random random = new Random();
        int waterAmount = random.nextInt(6) + 1;
        if(getThirst(pet) < waterAmount){
            setThirst(pet, 0);
        }else{
            setThirst(pet, getThirst(pet) - waterAmount);
        }
    }
    
    //Sings to the pet
    public static void sing(Pet6 pet){
        Random random = new Random();
        int singAmount = random.nextInt(6) + 1;
        if(getIrritation(pet) < singAmount){
            setIrritation(pet, 0);
        }else{
            setIrritation(pet, getIrritation(pet) - singAmount);
        }
    }
    
    //Sets the name of the specified pet
    public static void setName(Pet6 pet, String name){
        pet.name = name;
    }
    
    //Sets the species of the specified pet
    public static void setSpecies(Pet6 pet, String species){
        pet.species = species;
    }
    
    //Sets the thirst of the specified pet
    public static void setThirst(Pet6 pet, int thirst){
        pet.thirst = thirst;
    }
    
    //Sets the hunger of the specified pet
    public static void setHunger(Pet6 pet, int hunger){
        pet.hunger = hunger;
    }
    
    //Sets the irritation of the specified pet
    public static void setIrritation(Pet6 pet, int irritation){
        pet.irritation = irritation;
    }
    
    //Adds to the thirst of the specified pet
    public static void addThirst(Pet6 pet){
        Random random = new Random();
        int amount = random.nextInt(6);
        if(getThirst(pet) > 10 - amount){
            setThirst(pet, 10);
        }else{
            setThirst(pet, getThirst(pet) + amount);
        }
    }
    
    //Adds to the hunger of the specified pet
    public static void addHunger(Pet6 pet){
        Random random = new Random();
        int amount = random.nextInt(6);
        if(getHunger(pet) > 10 - amount){
            setHunger(pet, 10);
        }else{
            setHunger(pet, getHunger(pet) + amount);
        }
    }
    
    //Adds to the irritation of the specified pet
    public static void addIrritation(Pet6 pet){
        Random random = new Random();
        int amount = random.nextInt(6);
        if(getIrritation(pet) > 10 - amount){
            setIrritation(pet, 10);
        }else{
            setIrritation(pet, getIrritation(pet) + amount);
        }
    }
    
    //Calculates the anger of the specified pet
    public static void calculateAnger(Pet6 pet){
        pet.anger = (pet.thirst + pet.hunger + pet.irritation)/6;
    }
    
    //Gets the name of the specified pet
    public static String getName(Pet6 pet){
        return pet.name;
    }
    
    //Gets the species of the specified pet
    public static String getSpecies(Pet6 pet){
        return pet.species;
    }
    
    //Gets the thirst of the specified pet
    public static int getThirst(Pet6 pet){
        return pet.thirst;
    }
    
    //Gets the hunger of the specified pet
    public static int getHunger(Pet6 pet){
        return pet.hunger;
    }
    
    //Gets the irritation of the specified pet
    public static int getIrritation(Pet6 pet){
        return pet.irritation;
    }
    
    //Gets the anger of the specified pet
    public static int getAnger(Pet6 pet){
        return pet.anger;
    }
    
}

//Record class to store information about pet
class Pet6{
    
    String name;
    String species;
    int thirst;
    int hunger;
    int irritation;
    int anger;
    
}
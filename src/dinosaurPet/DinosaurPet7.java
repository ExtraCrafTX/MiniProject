/**
 * This program allows you to keep multiple pets and allows you to take
 * care of each of them in order to keep them all calm.
 */

package dinosaurPet;

//Imports utilities for generating random numbers and taking input
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Kaamil Jasani
 */
public class DinosaurPet7 {
    
    //Launched on start of program
    public static void main(String[] args) {
        explainProgram();
        
        Save7 save = new Save7();
        
        Menagerie7 menagerie = new Menagerie7();
        
        int numPets = inputNumPets();
        createMenagerie(menagerie, numPets);
        
        gameLoop(menagerie, save);
    }
    
    //Loops to keep the game running
    public static void gameLoop(Menagerie7 menagerie, Save7 save){
        while(true){
            //Declare and initialise variables to keep track of winning or losing
            boolean nirvana = false;
            boolean lost = false;
            
            while(!nirvana && !lost){
                performAction(menagerie);
                
                //Updates all the pets and checks for winning or losing
                nirvana = true;
                for(int i = 0; i < getSize(menagerie); i++){
                    Pet7 pet = getPet(menagerie, i);

                    //Update state of mind
                    updatePet(pet);

                    //Output state of mind
                    calculateAnger(pet);
                    outputPet(pet, i);
                    
                    if(getAnger(pet) != 0){
                        nirvana = false;
                    }
                    if(getAnger(pet) >= 4){
                        lost = true;
                    }
                }
                
                //Save the state
                save = saveState(save, menagerie);
            }
            
            if(lost){
                //Ask user for how many steps they want to go back
                int numStepsBack = numStepsBack();
                if(numStepsBack == 0){
                    break;
                }else{
                    //Load state
                    menagerie = loadState(save, numStepsBack);

                    for(int i = 0; i < getSize(menagerie); i++){
                        Pet7 pet = getPet(menagerie, i);

                        outputPet(pet, i);
                    }
                }
            }else{
                //If they won tell them so and end the game
                System.out.println("You won! All your pets were calm at once! You have achieved Dinosaur Nirvana!");
                break;
            }
        }
    }
    
    //Lets the user perform an action
    public static void performAction(Menagerie7 menagerie){
        //Ask user to select pet to take care of
        int petToTakeCareOf = selectPet(getSize(menagerie)-1);
        
        while(true){
            //Get input
            String input = inputAction().toLowerCase();
            if(input.contains("feed")){
                feed(getPet(menagerie, petToTakeCareOf));
                return;
            }else if(input.contains("water")){
                water(getPet(menagerie, petToTakeCareOf));
                return;
            }else if(input.contains("sing")){
                sing(getPet(menagerie, petToTakeCareOf));
                return;
            }else if(input.contains("end")){
                System.exit(0);
            }else{
                System.out.println("Please input a valid action.");
            }
        }
    }
    
    //Output all details about specified pet
    public static void outputPet(Pet7 pet, int id){
        System.out.println();
        outputDetails(pet, id);
        outputThirst(pet);
        outputHunger(pet);
        outputIrritation(pet);
        outputAnger(pet);
        System.out.println();
    }
    
    //Asks user to input the number of pets
    public static int inputNumPets(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many pets would you like to have?");
        return scanner.nextInt();
    }
    
    //Asks user to input the ID of the pet they would like to take care of
    public static int selectPet(int max){
        Scanner scanner = new Scanner(System.in);
        int input = -1;
        while(input < 0 || input > max){
            System.out.println("Enter the pet ID of the pet you would like to take care of: ");
            input = scanner.nextInt();
            if(input < 0 || input > max){
                System.out.println("Invalid ID!");
            }
        }
        return input;
    }
    
    //Explains what the program is
    public static void explainProgram(){
        System.out.println("This program will allow you to name pets and take care of them!");
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
    public static Save7 saveState(Save7 save, Menagerie7 menagerie){
        Save7 newSave = new Save7();
        for(int i = 1; i < save.save.length; i++){
            newSave.save[i-1] = save.save[i];
        }
        newSave.save[save.save.length - 1] = copyState(menagerie);
        return newSave;
    }
    
    //Creates a copy of the pet to avoid errors due to referencing instead of value
    public static Menagerie7 copyState(Menagerie7 menagerie){
        Menagerie7 savedState = new Menagerie7();
        savedState.pets = menagerie.pets;
        return savedState;
    }
    
    //Outputs the name of the pet
    public static void outputName(Pet7 pet){
        System.out.println("Happy birthday " + getName(pet) + "!");
    }
    
    //Outputs the name of the pet
    public static void outputSpecies(Pet7 pet){
        System.out.println(getName(pet) + " is a " + getSpecies(pet) + ".");
    }
    
    //Outputs details of the pet
    public static void outputDetails(Pet7 pet, int id){
        System.out.println("Pet name: " + getName(pet) + ". Pet ID: " + id + ".");
    }
    
    //Outputs the thirst level of the pet
    public static void outputThirst(Pet7 pet){
        System.out.println("The thirst level of " + getName(pet) + " is " + getThirst(pet) + "/10.");
    }
    
    //Outputs the hunger level of the pet
    public static void outputHunger(Pet7 pet){
        System.out.println("The hunger level of " + getName(pet) + " is " + getHunger(pet) + "/10.");
    }
    
    //Outputs the irritation of the pet
    public static void outputIrritation(Pet7 pet){
        System.out.println("The irritation of " + getName(pet) + " is " + getIrritation(pet) + "/10.");
    }
    
    //Outputs the anger score of the pet
    public static void outputAnger(Pet7 pet){
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
    public static String inputName(int id){
        return input("What is your pet's name? (Pet ID: " + id + ")");
    }
    
    //Gets the species of the pet from the user
    public static String inputSpecies(int id){
        return input("What is your pet's species? (Pet ID: " + id + ")");
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
    public static void updatePet(Pet7 pet){
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
    public static void feed(Pet7 pet){
        Random random = new Random();
        int feedAmount = random.nextInt(6) + 1;
        if(getHunger(pet) < feedAmount){
            setHunger(pet, 0);
        }else{
            setHunger(pet, getHunger(pet) - feedAmount);
        }
    }
    
    //Gives the pet water
    public static void water(Pet7 pet){
        Random random = new Random();
        int waterAmount = random.nextInt(6) + 1;
        if(getThirst(pet) < waterAmount){
            setThirst(pet, 0);
        }else{
            setThirst(pet, getThirst(pet) - waterAmount);
        }
    }
    
    //Sings to the pet
    public static void sing(Pet7 pet){
        Random random = new Random();
        int singAmount = random.nextInt(6) + 1;
        if(getIrritation(pet) < singAmount){
            setIrritation(pet, 0);
        }else{
            setIrritation(pet, getIrritation(pet) - singAmount);
        }
    }
    
    //Sets the name of the specified pet
    public static void setName(Pet7 pet, String name){
        pet.name = name;
    }
    
    //Sets the species of the specified pet
    public static void setSpecies(Pet7 pet, String species){
        pet.species = species;
    }
    
    //Sets the thirst of the specified pet
    public static void setThirst(Pet7 pet, int thirst){
        pet.thirst = thirst;
    }
    
    //Sets the hunger of the specified pet
    public static void setHunger(Pet7 pet, int hunger){
        pet.hunger = hunger;
    }
    
    //Sets the irritation of the specified pet
    public static void setIrritation(Pet7 pet, int irritation){
        pet.irritation = irritation;
    }
    
    //Adds to the thirst of the specified pet
    public static void addThirst(Pet7 pet){
        Random random = new Random();
        if(random.nextBoolean()){
            int amount = random.nextInt(3) + 1;
            if(getThirst(pet) > 10 - amount){
                setThirst(pet, 10);
            }else{
                setThirst(pet, getThirst(pet) + amount);
            }
        }
    }
    
    //Adds to the hunger of the specified pet
    public static void addHunger(Pet7 pet){
        Random random = new Random();
        if(random.nextBoolean()){
            int amount = random.nextInt(3) + 1;
            if(getHunger(pet) > 10 - amount){
                setHunger(pet, 10);
            }else{
                setHunger(pet, getHunger(pet) + amount);
            }
        }
    }
    
    //Adds to the irritation of the specified pet
    public static void addIrritation(Pet7 pet){
        Random random = new Random();
        if(random.nextBoolean()){
            int amount = random.nextInt(3) + 1;
            if(getIrritation(pet) > 10 - amount){
                setIrritation(pet, 10);
            }else{
                setIrritation(pet, getIrritation(pet) + amount);
            }
        }
    }
    
    //Calculates the anger of the specified pet
    public static void calculateAnger(Pet7 pet){
        pet.anger = (pet.thirst + pet.hunger + pet.irritation)/6;
    }
    
    //Gets the name of the specified pet
    public static String getName(Pet7 pet){
        return pet.name;
    }
    
    //Gets the species of the specified pet
    public static String getSpecies(Pet7 pet){
        return pet.species;
    }
    
    //Gets the thirst of the specified pet
    public static int getThirst(Pet7 pet){
        return pet.thirst;
    }
    
    //Gets the hunger of the specified pet
    public static int getHunger(Pet7 pet){
        return pet.hunger;
    }
    
    //Gets the irritation of the specified pet
    public static int getIrritation(Pet7 pet){
        return pet.irritation;
    }
    
    //Gets the anger of the specified pet
    public static int getAnger(Pet7 pet){
        return pet.anger;
    }
    
    //Gets the pet at the specified index in the menagerie
    public static Pet7 getPet(Menagerie7 menagerie, int index){
        return menagerie.pets[index];
    }
    
    //Initialises the menagerie passed to it with the right number of slots
    public static void createMenagerie(Menagerie7 menagerie, int numPets){
        menagerie.pets = new Pet7[numPets];
        for(int i = 0; i < numPets; i++){
            menagerie.pets[i] = new Pet7();
        }
        
        for(int i = 0; i < numPets; i++){
            Pet7 pet = getPet(menagerie, i);
            
            //Get name and species
            setName(pet, inputName(i));
            setSpecies(pet, inputSpecies(i));
            outputName(pet);
            outputSpecies(pet);

            //Randomly give it thirst
            setThirst(pet, calculateThirstLevel());

            //Randomly give it hunger
            setHunger(pet, calculateHungerLevel());

            //Randomly give it irritation
            setIrritation(pet, calculateIrritation());

            //Calculate anger score
            calculateAnger(pet);
            
            outputPet(pet, i);
        }
    }
    
    //Gets the size of the menagerie
    public static int getSize(Menagerie7 menagerie){
        return menagerie.pets.length;
    }
    
    //Loads the state from the specified save
    public static Menagerie7 loadState(Save7 save, int numStepsBack){
        return save.save[save.save.length - numStepsBack];
    }
    
}

//Record class to store information about pet
class Pet7{
    
    String name;
    String species;
    int thirst;
    int hunger;
    int irritation;
    int anger;
    
}

//Abstract data type to hold multiple pets
class Menagerie7{
    
    Pet7[] pets;
    
}

//Abstract data type to hold the saved states
class Save7{
    
    Menagerie7[] save = new Menagerie7[5];
    
}
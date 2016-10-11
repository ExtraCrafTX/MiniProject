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
public class DinosaurPet8 {
    
    public static final String FEED_ACTION = "feed";
    public static final String WATER_ACTION = "water";
    public static final String SING_ACTION = "sing";
    public static final String END_ACTION = "end";
    public static final String INVALID_ACTION = "Please input a valid action.";
    public static final String WIN_MESSAGE = "You won! All your pets were calm at once! You have achieved Dinosaur Nirvana!";
    public static final String HOW_MANY_PETS = "How many pets would you like to have?";
    public static final String SORT_PETS = "Would you like to sort pets by anger? (yes/no)";
    public static final String YES = "yes";
    public static final String NO = "no";
    public static final String YES_OR_NO = "Please input either yes or no:";
    public static final String SELECT_ID = "Please input the id of the pet you would like to take care of:";
    public static final String INVALID_ID = "Invalid id!";
    public static final String EXPLAIN_PROGRAM = "This program will allow you to name pets and take care of them!";
    public static final String INPUT_ACTION = "What would you like to do? (water, feed, sing, end)";
    public static final String INPUT_STEPS_BACK = "How many steps back would you like to go?";
    public static final String INVALID_STEPS = "Please input a valid number of steps (0-5).";
    public static final String CALM = " is relatively calm.";
    public static final String IRRITABLE = " is slightly irritable.";
    public static final String ANGRY = " is getting pretty angry now.";
    public static final String DANGEROUS = " is getting quite dangerous now!";
    public static final String EXPLODE = " is about ready to explode!";
    public static final String PUT_DOWN = " is being put down for everyone's safety.";
    
    //Launched on start of program
    public static void main(String[] args) {
        explainProgram();
        
        boolean toSort = askSort();
        
        Save8 save = new Save8();
        
        Menagerie8 menagerie = new Menagerie8();
        
        int numPets = inputNumPets();
        createMenagerie(menagerie, numPets);
        
        for(int i = 0; i < numPets; i++){
            Pet8 pet = getPet(menagerie, i);
            
            setId(pet, i);
            
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
        }
        
        //Ask user if pets should be sorted
        if(toSort)
            sortByAnger(menagerie);
        else
            sortById(menagerie);
        
        for(int i = 0; i < numPets; i++){
            Pet8 pet = getPet(menagerie, i);
            outputPet(pet);
        }
        
        //Game loop
        while(true){
            //Declare and initialise variables to keep track of winning or losing
            boolean nirvana = false;
            boolean lost = false;
            
            while(!nirvana && !lost){
                //Ask user to select pet to take care of
                int petToTakeCareOf = selectPet(numPets-1);
                
                for(int i = 0; i < numPets; i++){
                    if(getId(getPet(menagerie, i)) == petToTakeCareOf){
                        //Get input
                        String input = inputAction().toLowerCase();
                        if(input.contains(FEED_ACTION)){
                            feed(getPet(menagerie, petToTakeCareOf));
                        }else if(input.contains(WATER_ACTION)){
                            water(getPet(menagerie, petToTakeCareOf));
                        }else if(input.contains(SING_ACTION)){
                            sing(getPet(menagerie, petToTakeCareOf));
                        }else if(input.contains(END_ACTION)){
                            System.exit(0);
                        }else{
                            System.out.println(INVALID_ACTION);
                            continue;
                        }
                    }
                }
                
                toSort = askSort();
                if(toSort)
                    sortByAnger(menagerie);
                else
                    sortById(menagerie);
                
                //Updates all the pets and checks for winning or losing
                nirvana = true;
                for(int i = 0; i < numPets; i++){
                    Pet8 pet = getPet(menagerie, i);

                    //Update state of mind
                    updatePet(pet);

                    //Output state of mind
                    calculateAnger(pet);
                    outputPet(pet);
                    
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

                    for(int i = 0; i < numPets; i++){
                        Pet8 pet = getPet(menagerie, i);

                        outputPet(pet);
                    }
                }
            }else{
                //If they won tell them so and end the game
                System.out.println(WIN_MESSAGE);
                break;
            }
        }
    }
    
    //Output all details about specified pet
    public static void outputPet(Pet8 pet){
        System.out.println();
        outputDetails(pet);
        outputThirst(pet);
        outputHunger(pet);
        outputIrritation(pet);
        outputAnger(pet);
        System.out.println();
    }
    
    //Asks user to input the number of pets
    public static int inputNumPets(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(HOW_MANY_PETS);
        return scanner.nextInt();
    }
    
    //Asks the user whether pets should be sorted and returns boolean
    public static boolean askSort(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(SORT_PETS);
        String input = "";
        while(!(input.contains(YES) || input.contains(NO))){
            input = scanner.nextLine();
            if(input.contains(YES)){
                return true;
            }else if(input.contains(NO)){
                return false;
            }else{
                System.out.println(YES_OR_NO);
            }
        }
        return false;
    }
    
    //Asks user to input the ID of the pet they would like to take care of
    public static int selectPet(int max){
        Scanner scanner = new Scanner(System.in);
        int input = -1;
        while(input < 0 || input > max){
            System.out.println(SELECT_ID);
            input = scanner.nextInt();
            if(input < 0 || input > max){
                System.out.println(INVALID_ID);
            }
        }
        return input;
    }
    
    //Explains what the program is
    public static void explainProgram(){
        System.out.println(EXPLAIN_PROGRAM);
    }
    
    //Gets the action that the user wants to do
    public static String inputAction(){
        return input(INPUT_ACTION);
    }
    
    //Gets input from the user as to how many steps back they would like to go
    public static int numStepsBack(){
        int input = Integer.parseInt(input(INPUT_STEPS_BACK));
        while(input < 0 || input > 5){
            System.out.println(INVALID_STEPS);
            input = Integer.parseInt(input(INPUT_STEPS_BACK));
        }
        return input;
    }
    
    //Saves the state of the pet
    public static Save8 saveState(Save8 save, Menagerie8 menagerie){
        Save8 newSave = new Save8();
        for(int i = 1; i < save.save.length; i++){
            newSave.save[i-1] = save.save[i];
        }
        newSave.save[save.save.length - 1] = copyState(menagerie);
        return newSave;
    }
    
    //Creates a copy of the pet to avoid errors due to referencing instead of value
    public static Menagerie8 copyState(Menagerie8 menagerie){
        Menagerie8 savedState = new Menagerie8();
        savedState.pets = menagerie.pets;
        return savedState;
    }
    
    //Outputs the name of the pet
    public static void outputName(Pet8 pet){
        System.out.println("Happy birthday " + getName(pet) + "!");
    }
    
    //Outputs the name of the pet
    public static void outputSpecies(Pet8 pet){
        System.out.println(getName(pet) + " is a " + getSpecies(pet) + ".");
    }
    
    //Outputs the details of the pet
    public static void outputDetails(Pet8 pet){
        System.out.println("Pet name: " + getName(pet) + ". Pet ID: " + getId(pet) + ".");
    }
    
    //Outputs the thirst level of the pet
    public static void outputThirst(Pet8 pet){
        System.out.println("The thirst level of " + getName(pet) + " is " + getThirst(pet) + "/10.");
    }
    
    //Outputs the hunger level of the pet
    public static void outputHunger(Pet8 pet){
        System.out.println("The hunger level of " + getName(pet) + " is " + getHunger(pet) + "/10.");
    }
    
    //Outputs the irritation of the pet
    public static void outputIrritation(Pet8 pet){
        System.out.println("The irritation of " + getName(pet) + " is " + getIrritation(pet) + "/10.");
    }
    
    //Outputs the anger score of the pet
    public static void outputAnger(Pet8 pet){
        int anger = getAnger(pet);
        String name = getName(pet);
        if(anger == 0){
            System.out.println(name + CALM);
        }else if(anger == 1){
            System.out.println(name + IRRITABLE);
        }else if(anger == 2){
            System.out.println(name + ANGRY);
        }else if(anger == 3){
            System.out.println(name + DANGEROUS);
        }else if(anger == 4){
            System.out.println(name + EXPLODE);
        }else{
            System.out.println(name + PUT_DOWN);
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
    public static void updatePet(Pet8 pet){
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
    public static void feed(Pet8 pet){
        Random random = new Random();
        int feedAmount = random.nextInt(6) + 1;
        if(getHunger(pet) < feedAmount){
            setHunger(pet, 0);
        }else{
            setHunger(pet, getHunger(pet) - feedAmount);
        }
    }
    
    //Gives the pet water
    public static void water(Pet8 pet){
        Random random = new Random();
        int waterAmount = random.nextInt(6) + 1;
        if(getThirst(pet) < waterAmount){
            setThirst(pet, 0);
        }else{
            setThirst(pet, getThirst(pet) - waterAmount);
        }
    }
    
    //Sings to the pet
    public static void sing(Pet8 pet){
        Random random = new Random();
        int singAmount = random.nextInt(6) + 1;
        if(getIrritation(pet) < singAmount){
            setIrritation(pet, 0);
        }else{
            setIrritation(pet, getIrritation(pet) - singAmount);
        }
    }
    
    //Sets the name of the specified pet
    public static void setName(Pet8 pet, String name){
        pet.name = name;
    }
    
    //Sets the species of the specified pet
    public static void setSpecies(Pet8 pet, String species){
        pet.species = species;
    }
    
    //Sets the thirst of the specified pet
    public static void setThirst(Pet8 pet, int thirst){
        pet.thirst = thirst;
    }
    
    //Sets the hunger of the specified pet
    public static void setHunger(Pet8 pet, int hunger){
        pet.hunger = hunger;
    }
    
    //Sets the irritation of the specified pet
    public static void setIrritation(Pet8 pet, int irritation){
        pet.irritation = irritation;
    }
    
    //Sets the id of the pet
    public static void setId(Pet8 pet, int id){
        pet.id = id;
    }
    
    //Adds to the thirst of the specified pet
    public static void addThirst(Pet8 pet){
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
    public static void addHunger(Pet8 pet){
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
    public static void addIrritation(Pet8 pet){
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
    public static void calculateAnger(Pet8 pet){
        pet.anger = (pet.thirst + pet.hunger + pet.irritation)/6;
    }
    
    //Gets the name of the specified pet
    public static String getName(Pet8 pet){
        return pet.name;
    }
    
    //Gets the species of the specified pet
    public static String getSpecies(Pet8 pet){
        return pet.species;
    }
    
    //Gets the thirst of the specified pet
    public static int getThirst(Pet8 pet){
        return pet.thirst;
    }
    
    //Gets the hunger of the specified pet
    public static int getHunger(Pet8 pet){
        return pet.hunger;
    }
    
    //Gets the irritation of the specified pet
    public static int getIrritation(Pet8 pet){
        return pet.irritation;
    }
    
    //Gets the anger of the specified pet
    public static int getAnger(Pet8 pet){
        return pet.anger;
    }
    
    //Gets the id of the pet
    public static int getId(Pet8 pet){
        return pet.id;
    }
    
    //Gets the pet at the specified index in the menagerie
    public static Pet8 getPet(Menagerie8 menagerie, int index){
        return menagerie.pets[index];
    }
    
    //Initialises the menagerie passed to it with the right number of slots
    public static void createMenagerie(Menagerie8 menagerie, int numPets){
        menagerie.pets = new Pet8[numPets];
        for(int i = 0; i < numPets; i++){
            menagerie.pets[i] = new Pet8();
        }
    }
    
    //Gets the size of the menagerie
    public static int getSize(Menagerie8 menagerie){
        return menagerie.pets.length;
    }
    
    //Loads the state from the specified save
    public static Menagerie8 loadState(Save8 save, int numStepsBack){
        return save.save[save.save.length - numStepsBack];
    }
    
    //Sorts the pets by Anger
    public static void sortByAnger(Menagerie8 menagerie){
        for(int p = getSize(menagerie); p >= 0; p--){
            for(int i = 0; i < p - 1; i++){
                int j = i + 1;
                if(getAnger(getPet(menagerie, i)) > getAnger(getPet(menagerie, j))){
                    swap(i, j, menagerie.pets);
                }
            }
        }
    }

    //Sorts the pets by ID
    public static void sortById(Menagerie8 menagerie) {
        for(int p = getSize(menagerie); p >= 0; p--){
            for(int i = 0; i < p - 1; i++){
                int j = i + 1;
                if(getId(getPet(menagerie, i)) > getId(getPet(menagerie, j))){
                    swap(i, j, menagerie.pets);
                }
            }
        }
    }
    
    //Swaps two entries of an array
    public static void swap(int i, int j, Pet8[] array){
        Pet8 temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
}

//Record class to store information about pet
class Pet8{
    
    String name;
    String species;
    int thirst;
    int hunger;
    int irritation;
    int anger;
    int id;
    
}

//Abstract data type to hold multiple pets
class Menagerie8{
    
    Pet8[] pets;
    
}

//Abstract data type to hold the saved states
class Save8{
    
    Menagerie8[] save = new Menagerie8[5];
    
}
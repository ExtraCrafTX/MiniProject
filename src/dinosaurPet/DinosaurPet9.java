/**
 * This program allows you to keep multiple pets and allows you to take
 * care of each of them in order to keep them all calm.
 */

package dinosaurPet;

//Imports utilities for generating random numbers, taking input and reading/writing files
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Kaamil Jasani
 */
public class DinosaurPet9 {
    
    public static final String FEED_ACTION = "feed";
    public static final String WATER_ACTION = "water";
    public static final String SING_ACTION = "sing";
    public static final String END_ACTION = "end";
    public static final String INVALID_ACTION = "Please input a valid action.";
    public static final String WIN_MESSAGE = "You won! All your pets were calm at once!";
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
    public static final String ASK_LOAD = "Would you like to load the game or start a new one? (load/new)";
    public static final String LOAD = "load";
    public static final String NEW = "new";
    public static final String LOAD_NEW = "Please input either load or new:";
    public static final String FILE_NAME = "save.txt";
    
    //Launched on start of program
    public static void main(String[] args) {
        explainProgram();
        
        File file = new File(FILE_NAME);
        boolean load = file.exists();
        if(load){
            load = askLoad();
        }
        
        Save9 save = new Save9();
        Menagerie9 menagerie = new Menagerie9();
        int numPets = 0;
        
        if(!load){
            numPets = inputNumPets();
            createMenagerie(menagerie, numPets);

            for(int i = 0; i < numPets; i++){
                Pet9 pet = getPet(menagerie, i);

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
        }else{
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);
                
                numPets = Integer.parseInt(reader.readLine());
                menagerie = readMenagerie(reader, numPets);
                save = readSave(reader, numPets);
                
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        //Ask user if pets should be sorted
        boolean toSort = askSort();
        
        if(toSort)
            sortByAnger(menagerie);
        else
            sortById(menagerie);
        
        outputPets(menagerie);
        
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
                            feed(getPet(menagerie, i));
                        }else if(input.contains(WATER_ACTION)){
                            water(getPet(menagerie, i));
                        }else if(input.contains(SING_ACTION)){
                            sing(getPet(menagerie, i));
                        }else if(input.contains(END_ACTION)){
                            System.exit(0);
                        }else{
                            System.out.println(INVALID_ACTION);
                            continue;
                        }
                    }
                }
                
                //Updates all the pets and checks for winning or losing
                nirvana = true;
                for(int i = 0; i < numPets; i++){
                    Pet9 pet = getPet(menagerie, i);

                    //Update state of mind
                    updatePet(pet);
                    calculateAnger(pet);
                    
                    if(getAnger(pet) != 0){
                        nirvana = false;
                    }
                    if(getAnger(pet) >= 4){
                        lost = true;
                    }
                }
                
                toSort = askSort();
                if(toSort)
                    sortByAnger(menagerie);
                else
                    sortById(menagerie);
                
                outputPets(menagerie);
                
                //Save the state
                save = saveState(save, menagerie);
                
                writeState(menagerie, save, file);
            }
            
            if(lost){
                //Ask user for how many steps they want to go back
                int numStepsBack = numStepsBack();
                if(numStepsBack == 0){
                    file.delete();
                    break;
                }else{
                    //Load state
                    menagerie = loadState(save, numStepsBack);

                    for(int i = 0; i < numPets; i++){
                        Pet9 pet = getPet(menagerie, i);

                        outputPet(pet);
                    }
                }
            }else{
                //If they won tell them so and end the game
                System.out.println(WIN_MESSAGE);
                file.delete();
                break;
            }
        }
    }
    
    //Asks the user whether they would like to load or start a new game
    public static boolean askLoad(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(ASK_LOAD);
        String input = "";
        while(!(input.contains(LOAD) || input.contains(NEW))){
            input = scanner.nextLine();
            if(input.contains(LOAD)){
                return true;
            }else if(input.contains(NEW)){
                return false;
            }else{
                System.out.println(LOAD_NEW);
            }
        }
        return false;
    }
    
    //Outputs details of all pets in specified menagerie
    public static void outputPets(Menagerie9 menagerie){
        for(int i = 0; i < getSize(menagerie); i++){
            Pet9 pet = getPet(menagerie, i);
            outputPet(pet);
        }
    }
    
    //Output all details about specified pet
    public static void outputPet(Pet9 pet){
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
    public static Save9 saveState(Save9 save, Menagerie9 menagerie){
        Save9 newSave = new Save9();
        for(int i = 1; i < save.save.length; i++){
            newSave.save[i-1] = save.save[i];
        }
        newSave.save[save.save.length - 1] = copyState(menagerie);
        return newSave;
    }
    
    //Creates a copy of the pet to avoid errors due to referencing instead of value
    public static Menagerie9 copyState(Menagerie9 menagerie){
        Menagerie9 savedState = new Menagerie9();
        savedState.pets = menagerie.pets;
        return savedState;
    }
    
    //Outputs the name of the pet
    public static void outputName(Pet9 pet){
        System.out.println("Happy birthday " + getName(pet) + "!");
    }
    
    //Outputs the name of the pet
    public static void outputSpecies(Pet9 pet){
        System.out.println(getName(pet) + " is a " + getSpecies(pet) + ".");
    }
    
    //Outputs the details of the pet
    public static void outputDetails(Pet9 pet){
        System.out.println("Pet name: " + getName(pet) + ". Pet ID: " + getId(pet) + ".");
    }
    
    //Outputs the thirst level of the pet
    public static void outputThirst(Pet9 pet){
        System.out.println("The thirst level of " + getName(pet) + " is " + getThirst(pet) + "/10.");
    }
    
    //Outputs the hunger level of the pet
    public static void outputHunger(Pet9 pet){
        System.out.println("The hunger level of " + getName(pet) + " is " + getHunger(pet) + "/10.");
    }
    
    //Outputs the irritation of the pet
    public static void outputIrritation(Pet9 pet){
        System.out.println("The irritation of " + getName(pet) + " is " + getIrritation(pet) + "/10.");
    }
    
    //Outputs the anger score of the pet
    public static void outputAnger(Pet9 pet){
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
    public static void updatePet(Pet9 pet){
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
    public static void feed(Pet9 pet){
        Random random = new Random();
        int feedAmount = random.nextInt(6) + 1;
        if(getHunger(pet) < feedAmount){
            setHunger(pet, 0);
        }else{
            setHunger(pet, getHunger(pet) - feedAmount);
        }
    }
    
    //Gives the pet water
    public static void water(Pet9 pet){
        Random random = new Random();
        int waterAmount = random.nextInt(6) + 1;
        if(getThirst(pet) < waterAmount){
            setThirst(pet, 0);
        }else{
            setThirst(pet, getThirst(pet) - waterAmount);
        }
    }
    
    //Sings to the pet
    public static void sing(Pet9 pet){
        Random random = new Random();
        int singAmount = random.nextInt(6) + 1;
        if(getIrritation(pet) < singAmount){
            setIrritation(pet, 0);
        }else{
            setIrritation(pet, getIrritation(pet) - singAmount);
        }
    }
    
    //Sets the name of the specified pet
    public static void setName(Pet9 pet, String name){
        pet.name = name;
    }
    
    //Sets the species of the specified pet
    public static void setSpecies(Pet9 pet, String species){
        pet.species = species;
    }
    
    //Sets the thirst of the specified pet
    public static void setThirst(Pet9 pet, int thirst){
        pet.thirst = thirst;
    }
    
    //Sets the hunger of the specified pet
    public static void setHunger(Pet9 pet, int hunger){
        pet.hunger = hunger;
    }
    
    //Sets the irritation of the specified pet
    public static void setIrritation(Pet9 pet, int irritation){
        pet.irritation = irritation;
    }
    
    //Sets the id of the pet
    public static void setId(Pet9 pet, int id){
        pet.id = id;
    }
    
    //Adds to the thirst of the specified pet
    public static void addThirst(Pet9 pet){
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
    public static void addHunger(Pet9 pet){
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
    public static void addIrritation(Pet9 pet){
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
    public static void calculateAnger(Pet9 pet){
        pet.anger = (pet.thirst + pet.hunger + pet.irritation)/6;
    }
    
    //Gets the name of the specified pet
    public static String getName(Pet9 pet){
        return pet.name;
    }
    
    //Gets the species of the specified pet
    public static String getSpecies(Pet9 pet){
        return pet.species;
    }
    
    //Gets the thirst of the specified pet
    public static int getThirst(Pet9 pet){
        return pet.thirst;
    }
    
    //Gets the hunger of the specified pet
    public static int getHunger(Pet9 pet){
        return pet.hunger;
    }
    
    //Gets the irritation of the specified pet
    public static int getIrritation(Pet9 pet){
        return pet.irritation;
    }
    
    //Gets the anger of the specified pet
    public static int getAnger(Pet9 pet){
        return pet.anger;
    }
    
    //Gets the id of the pet
    public static int getId(Pet9 pet){
        return pet.id;
    }
    
    //Gets the pet at the specified index in the menagerie
    public static Pet9 getPet(Menagerie9 menagerie, int index){
        return menagerie.pets[index];
    }
    
    //Initialises the menagerie passed to it with the right number of slots
    public static void createMenagerie(Menagerie9 menagerie, int numPets){
        menagerie.pets = new Pet9[numPets];
        for(int i = 0; i < numPets; i++){
            menagerie.pets[i] = new Pet9();
        }
    }
    
    //Gets the size of the menagerie
    public static int getSize(Menagerie9 menagerie){
        return menagerie.pets.length;
    }
    
    //Loads the state from the specified save
    public static Menagerie9 loadState(Save9 save, int numStepsBack){
        return save.save[save.save.length - numStepsBack];
    }
    
    //Sorts the pets by Anger
    public static void sortByAnger(Menagerie9 menagerie){
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
    public static void sortById(Menagerie9 menagerie) {
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
    public static void swap(int i, int j, Pet9[] array){
        Pet9 temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    //Writes the current state to the file so it can be loaded next time
    public static void writeState(Menagerie9 menagerie, Save9 save, File file){
        try{
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            
            writeMenagerie(menagerie, writer);
            
            for(int i = 0; i < save.save.length; i++){
                writeMenagerie(save.save[i], writer);
            }
            
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //Writes the specified menagerie to the file
    public static void writeMenagerie(Menagerie9 menagerie, FileWriter writer){
        try{
            writer.write(getSize(menagerie)+"\n");
            for(int i = 0; i < getSize(menagerie); i++){
                Pet9 pet = getPet(menagerie, i);
                
                writer.write(getName(pet)+"\n");
                writer.write(getSpecies(pet)+"\n");
                writer.write(getThirst(pet)+"\n");
                writer.write(getHunger(pet)+"\n");
                writer.write(getIrritation(pet)+"\n");
                writer.write(getId(pet)+"\n");
            }
        }catch(Exception e){
            
        }
    }
    
    //Reads a menagerie from the specified file
    public static Menagerie9 readMenagerie(BufferedReader reader, int numPets){
        Menagerie9 menagerie = new Menagerie9();
        createMenagerie(menagerie, numPets);
        for(int i = 0; i < numPets; i++){
            menagerie.pets[i] = readPet(reader);
        }
        return menagerie;
    }
    
    //Reads the save data from the file
    public static Save9 readSave(BufferedReader reader, int numPets){
        try {
            Save9 save = new Save9();
            String line;
            int i = 0;
            while((line = reader.readLine()) != null){
                save.save[i] = readMenagerie(reader, numPets);
                i++;
            }
            return save;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    //Reads a pet from the file
    public static Pet9 readPet(BufferedReader reader){
        try{
            Pet9 pet = new Pet9();
            setName(pet, reader.readLine());
            setSpecies(pet, reader.readLine());
            setThirst(pet, Integer.parseInt(reader.readLine()));
            setHunger(pet, Integer.parseInt(reader.readLine()));
            setIrritation(pet, Integer.parseInt(reader.readLine()));
            calculateAnger(pet);
            setId(pet, Integer.parseInt(reader.readLine()));
            return pet;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
}

//Record class to store information about pet
class Pet9{
    
    String name;
    String species;
    int thirst;
    int hunger;
    int irritation;
    int anger;
    int id;
    
}

//Abstract data type to hold multiple pets
class Menagerie9{
    
    Pet9[] pets;
    
}

//Abstract data type to hold the saved states
class Save9{
    
    Menagerie9[] save = new Menagerie9[5];
    
}
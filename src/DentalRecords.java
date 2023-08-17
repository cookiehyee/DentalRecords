import java.util.Scanner;

public class DentalRecords { //CSC 120 PROJECT 1 - HYUN KIM

    final static char INCISORS = 'I'; //Incisors tooth
    final static char BICUSPIDS = 'B'; //Bicuspids tooth
    final static char MISSING = 'M'; //Missing tooth
    final static int MAX_TEETH = 8; //Max teeth for each layer
    final static int MAX_ROW = 2; //Uppers and lowers
    final static int UPPER_ROW = 0; //Index for upper layer
    final static int LOWER_ROW = 1; //Index for lower layer

    public static void main(String[] args) {

        int familySize;
        char[][][] familyTeethData;
        String[] familyMembers;

        welcomeMessage(); //Displays welcome message

        familySize = getFamilySize(); //Gets family size as input
        familyMembers = new String[familySize]; //Declares a string array with family size
        familyTeethData = new char[familySize][MAX_ROW][];
        //Declares a new 3D char array to record teeth data of the family
        //Size for the 3rd dimension not yet declared since everyone has different teeth length per layer

        recordFamilyData(familyTeethData,familyMembers); //Records family teeth data
        displayMenu(familyTeethData, familyMembers); //Displays menu (Print, Extract, Root, Exit) for user

    }

    public static void welcomeMessage() { //Method to display welcome message

        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");

    }

    public static int getFamilySize() { //Method to return family size

        Scanner keyboard = new Scanner(System.in);
        int size; //Family size

        System.out.print("Please enter number of people in the family\t: ");
        size = keyboard.nextInt();

        //Prompts user to input family size again if family size is out of range
        while (size > 6 || size < 0) {
            System.out.print("Invalid number of people, try again\t\t\t: ");
            size = keyboard.nextInt();
        }

        return size; //Returns family size

    }
    public static void recordFamilyData(char[][][] teethData, String[] family) { //Method to record family teeth data

        Scanner keyboard = new Scanner(System.in);
        String upperTeeth, lowerTeeth;
        int toothLocation;


        //Loops and records teeth information of each family member based on family size
        for (int i = 0; i < family.length; i++) {

            System.out.print("Please enter the name for family member " + (i + 1) + "\t: ");
            family[i] = keyboard.nextLine();

            upperTeeth = getTeethString(family[i], "uppers"); //Receives uppers teeth string from user
            lowerTeeth = getTeethString(family[i], "lowers"); //Receives lowers teeth string from user

            teethData[i][UPPER_ROW] = new char[upperTeeth.length()]; //Declares length of upper layer
            teethData[i][LOWER_ROW] = new char[lowerTeeth.length()]; //Declares length of lower layer
            toothLocation = i; //Tooth location of each family member

            //Fills in teeth data of family in the 3D array
            fillTeeth(teethData, toothLocation, upperTeeth, lowerTeeth);

        }

    }

    //Method to return a family member's (upper or lower) teeth string
    public static String getTeethString(String member, String layer) {

        Scanner keyboard = new Scanner(System.in);
        String teethString;
        boolean invalidType; //Invalid teeth type
        boolean invalidLength; //Invalid teeth length

        //Formats string width with left justification since family member's name are of different length
        System.out.printf("%-44s", "Please enter the " + layer + " for " + member);
        System.out.print(": ");
        teethString = keyboard.nextLine().toUpperCase(); //Stores teeth string as uppercase

        invalidType = invalidTeethType(teethString); //Method to check if teeth input is invalid
        invalidLength = teethString.length() > MAX_TEETH; //Assigns true if teeth length is invalid

        while(invalidType || invalidLength) { //Loops to check if teeth string input or length are invalid

            //Prompts user to input teeth again if invalid teeth input was detected above
            if(invalidType) {
                System.out.print("Invalid teeth type, try again\t\t\t\t: ");
                teethString = keyboard.nextLine().toUpperCase();
                invalidType = invalidTeethType(teethString);
                invalidLength = teethString.length() > MAX_TEETH;
            }

            else { //Checks teeth length again if all tooth are valid input

                /* Prompts user to input teeth string again if too many tooth
                - Overall teeth length for each layer cannot exceed 8       */
                while(invalidLength) {
                    System.out.print("Too many teeth, try again\t\t\t\t\t: ");
                    teethString = keyboard.nextLine().toUpperCase();
                    invalidType = invalidTeethType(teethString);
                    invalidLength = teethString.length() > MAX_TEETH;

                    if(invalidType) {
                        break; //Breaks out of inner while loop if teeth input is invalid
                    }

                }

            }

        }

        return teethString; //Returns teeth string

    }

    //Method to return true or false if teeth input is invalid or not
    public static boolean invalidTeethType(String teeth) {

        for (int i = 0; i < teeth.length(); i++) {
            if (teeth.charAt(i) != INCISORS && teeth.charAt(i) != BICUSPIDS
                    && teeth.charAt(i) != MISSING) { //Verifies each tooth
                return true; //Return true if invalid tooth found in teeth string
            }
        }

        return false; //Return false if all tooth in teeth string are valid input

    }

    //Method to fill teeth data
    public static void fillTeeth(char[][][] data, int location, String uppers, String lowers) {

        //Fills upper teeth
        for(int i = 0; i < uppers.length(); i++) {
            data[location][UPPER_ROW][i] = Character.toUpperCase(uppers.charAt(i));
        }

        //Fills lower teeth
        for(int i = 0; i < lowers.length(); i++) {
            data[location][LOWER_ROW][i] = Character.toUpperCase(lowers.charAt(i));
        }

    }

    public static void displayMenu(char[][][] teethData, String[] family) { //Method to display menu

        char userOption;

        do {

            userOption = getUserOption(); //Receives user option for menu

            if (userOption == 'P') {
                printTeeth(teethData, family); //Prints teeth information of the family
            }

            else if (userOption == 'E') {
                extractTooth(teethData, family); //Extracts a tooth of a family member
            }

            else if (userOption == 'R') {
                reportFamilyRoot(teethData); //Reports the root canal of the family
            }

        } while (userOption != 'X'); //Terminates if user option is X

        //Displays exit message
        System.out.println("\nExiting the Floridian Tooth Records :-)");

    }

    public static char getUserOption() { //Method to return user option

        Scanner keyboard = new Scanner(System.in);
        char option; //User option

        System.out.print("\n(P)rint, (E)xtract, (R)oot, e(X)it\t\t\t: ");
        option = Character.toUpperCase(keyboard.next().charAt(0));

        //Prompts user to input menu option if an invalid option was input
        while(option != 'P' && option != 'E' && option != 'R' && option != 'X') {
            System.out.print("Invalid menu option, try again\t\t\t\t: ");
            option = Character.toUpperCase(keyboard.next().charAt(0));
        }

        return option; //Returns user option

    }

    public static void printTeeth(char[][][] data, String[] family) { //Method to print family teeth

        for(int i = 0; i < data.length; i++) { //Loops through tooth location of each family member
            System.out.println("\n" + family[i]); //Prints family member of the corresponding location

            for(int j = 0; j < data[i].length; j++) { //Loops through and print layers
                if (j == UPPER_ROW) { //If row index is 0 = upper layer
                    System.out.print("  Uppers: ");
                }

                else if (j == LOWER_ROW) { //If row index is 1 = lower layer
                    System.out.print("  Lowers: ");
                }

                for(int k = 0; k < data[i][j].length; k++) { //Loops through and prints teeth of each layer
                    System.out.print((k + 1) + ":" + data[i][j][k] + " ");
                }

                System.out.println();

            }
        }

    }

    public static void extractTooth(char[][][] data, String[] family) { //Method to extract tooth

        int toothLocation;
        int toothRow;
        int toothNumber;

        toothLocation = getToothLocation(family); //Gets tooth location (family member) to be extracted
        toothRow = getToothRow(); //Gets the tooth row (layer) to be extracted
        toothNumber = getToothNumber(data, toothLocation, toothRow); //Gets the tooth number
        data[toothLocation][toothRow][toothNumber - 1] = MISSING; //Extracts tooth
        //Replaces the location of the extracted tooth by a missing tooth
    }

    //Method to return the tooth location (family member) to be extracted
    public static int  getToothLocation(String[] family) {

        Scanner keyboard = new Scanner(System.in);
        String member; //Family member
        boolean invalidMember = true;
        int location = 0;

        System.out.print("Which family member\t\t\t\t\t\t\t: ");
        member = keyboard.nextLine().toUpperCase(); //Since family member was stored as uppercase

        while(invalidMember) { //Checks if family member exists in the teeth data
            for(int i = 0; i < family.length; i++) {
                if (family[i].toUpperCase().equals(member)) {
                    invalidMember = false;
                    location = i;
                    break; //Breaks out from for loop if family member exists
                }
            }

            //Prompts user to input family member again if family member does not exist
            if(invalidMember) {
                System.out.print("Invalid family member, try again\t\t\t: ");
                member = keyboard.nextLine().toUpperCase();
            }

        }

        return location; //Returns teeth location

    }

    public static int getToothRow() { //Method to return the tooth row (layer) to be extracted

        Scanner keyboard = new Scanner(System.in);
        char layer;

        System.out.print("Which tooth layer\t\t\t\t\t\t\t: ");
        layer = Character.toUpperCase(keyboard.next().charAt(0));
        // Lowercase input converted to uppercase for valid boolean evaluation below:

        //Prompts user to input layer again if invalid layer was input:
        while(layer != 'U' && layer != 'L') {
            System.out.print("Invalid layer, try again\t\t\t\t\t: ");
            layer = Character.toUpperCase(keyboard.next().charAt(0));
        }

        if (layer == 'U') { //Returns 0 if upper layer
            return UPPER_ROW;
        }

        else { //Returns 1 if lower layer
            return LOWER_ROW;
        }

    }

    //Method to return the tooth number to be extracted
    public static int getToothNumber(char[][][] data, int location, int row) {

        Scanner keyboard = new Scanner(System.in);
        int number;
        boolean invalidNumber = true;

        System.out.print("Which tooth number\t\t\t\t\t\t\t: ");
        number = keyboard.nextInt();

        while(invalidNumber) { //Check is tooth number is out of range or missing

            // Prompts user to input tooth number again if number is out of range
            if(number > data[location][row].length || number < 0) {
                System.out.print("Invalid tooth number, try again\t\t\t\t: ");
                number = keyboard.nextInt();
            }

            //Prompts user to input teeth location again if teeth is missing
            else if(data[location][row][number - 1] == MISSING) {
                System.out.print("Missing tooth, try again\t\t\t\t\t: ");
                number = keyboard.nextInt();
            }

            else {
                invalidNumber = false;
            }

        }

        return number; //Returns tooth number

    }

    public static void reportFamilyRoot(char[][][] data) { //Method to report family root canal indices

        //Root canal indices are the roots of the quadratic equation: Ix^2 + Bx - M
        int a, b, c; //Coefficients of the quadratic equation
        double firstCanal, secondCanal;

        a = countTeeth(data, INCISORS); //Counts and returns the coefficient of incisors
        b = countTeeth(data, BICUSPIDS); //Counts and returns the coefficient of bicuspids
        c = countTeeth(data, MISSING); //Counts and returns the coefficient of missing teeth

        firstCanal = calculateCanal(a,b,c,1); //Calculates first root canal index
        secondCanal = calculateCanal(a,b,c,2); //Calculates second root canal index

        System.out.printf("%-22s", "One root canal at"); //Formats string width with left justification
        System.out.printf("%4.2f %n", firstCanal); //Formats decimal

        //Prints the second root canal index if the root canals are not equivalent
        if (firstCanal != secondCanal) {
            System.out.printf("%-22s", "Another root canal at");
            System.out.printf("%4.2f %n", secondCanal);
        }

    }

    //Method to count and return number of teeth of a specific teeth type
    public static int countTeeth(char[][][] data, char type) {

        int teethCount = 0;

        for(int i = 0; i < data.length; i++) { //Loops through tooth location
            for (int j = 0; j < data[i].length; j++) { //Loops through tooth row
                for (int k = 0; k < data[i][j].length; k++) { //Loops through each tooth
                    //Counts tooth if it matches the tooth type passed as a parameter
                    if(data[i][j][k] == type) {
                        teethCount++;
                    }
                }
            }
        }

        return teethCount; //Returns number of teeth count

    }

    //Method to calculate and return root canal index
    public static double calculateCanal(int a, int b, int c, int canal) {

        double canalIndex = 0;
        double squareRoot;

        /* Quadratic formula:
            First canal: (-b + square root of (b^2 - 4ac)) / 2a
            Second canal: (-b + square root of (b^2 - 4ac)) / 2a */

        //c is negative below because M is negative in the equation: Ix^2 + Bx - M
        squareRoot = Math.sqrt((Math.pow(b, 2) - (4 * a * (-c)))); //Square root portion of the formula

        if(canal == 1) { //First canal index adds the square root
            canalIndex = (-b + squareRoot) / (2 * a);
        }
        else if(canal == 2){ //Second index subtracts the square root
            canalIndex = (-b - squareRoot) / (2 * a);
        }

        return canalIndex; //Returns calculated canal index

    }

}

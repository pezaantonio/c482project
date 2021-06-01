package project.model;

/**
 *
 * @author Antonio Peza
 *
 * This class is for InHouse parts and extends the Part component
 *
 * */

public class InHouse extends Part{

    /**
    *  Integer variable that holds the partMachineID for InHouse parts
    */
    private int partMachineID;

    /**
    *
    * This is the constructor for the InHouse objects it takes the following parameters:
    * @param id - id number
    * @param name - name of part
    * @param price - the price of the part as a double to hold the decimal
    * @param stock - amount in inventory
    * @param min - minimum amount in inventory
    * @param max - max amount in inventory
    * @param partMachineID - the id of the part machine id
    * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int partMachineID) {
        super(id, name, price, stock, min, max);
        this.partMachineID = partMachineID;
    }

    /**
    * Method to return the partID
    * @return partMachineID
    * */
    public int getPartMachineID() {
        return partMachineID;
    }

    /**
    * Method to set the machine partid
    * @param partMachineID
    * */
    public void setPartMachineID(int partMachineID) {
        this.partMachineID = partMachineID;
    }
}

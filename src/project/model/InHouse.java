package project.model;

public class InHouse extends Part{

    private int partMachineID;

    // This is the constructor, added a parMachineID since that is for this subclass
    public InHouse(int id, String name, double price, int stock, int min, int max, int partMachineID) {
        super(id, name, price, stock, min, max);
        this.partMachineID = partMachineID;
    }

    public int getPartMachineID() {
        return partMachineID;
    }

    public void setPartMachineID(int partMachineID) {
        this.partMachineID = partMachineID;
    }
}

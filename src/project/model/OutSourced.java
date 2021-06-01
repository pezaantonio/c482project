package project.model;
/**
 *
 * @author Antonio Peza
 *
 * This class is used to hold the Outsourced parts. It is an extension of the Part class
 *
 * */
public class OutSourced extends Part{

    /**
     * Variable used to hold the part company name
     * */
    private String partCompName;

    /**
     *
     * Constructor for the Outsourced objects with the following parameters:
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param partCompName
     * */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String partCompName) {
        super(id, name, price, stock, min, max);
        this.partCompName = partCompName;
    }

    /**
     * Method to return part company name
     * @return partCompName
     * */
    public String getPartCompName() {
        return partCompName;
    }

    /**
     * Method to set the partcompname
     * @param partCompName
     * */
    public void setPartCompName(String partCompName) {
        this.partCompName = partCompName;
    }
}

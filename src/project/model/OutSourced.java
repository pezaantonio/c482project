package project.model;

public class OutSourced extends Part{

    private String partCompName;

    public OutSourced(int id, String name, double price, int stock, int min, int max, String partCompName) {
        super(id, name, price, stock, min, max);
        this.partCompName = partCompName;
    }

    public String getPartCompName() {
        return partCompName;
    }

    public void setPartCompName(String partCompName) {
        this.partCompName = partCompName;
    }
}

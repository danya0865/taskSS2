package dto;

public class AdditionResponse {
    private String additional_info;
    private int additional_number;
    private int id;

    // Constructors, getters, setters
    public AdditionResponse() {}

    // Getters and setters
    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public int getAdditional_number() {
        return additional_number;
    }

    public void setAdditional_number(int additional_number) {
        this.additional_number = additional_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
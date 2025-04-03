package dto;

import java.util.List;

public class EntityRequest {
    private AdditionRequest addition;
    private List<Integer> important_numbers;
    private String title;
    private boolean verified;

    // Constructors, getters, setters
    public EntityRequest() {}

    public EntityRequest(AdditionRequest addition, List<Integer> important_numbers, String title, boolean verified) {
        this.addition = addition;
        this.important_numbers = important_numbers;
        this.title = title;
        this.verified = verified;
    }

    // Getters and setters
    public AdditionRequest getAddition() {
        return addition;
    }

    public void setAddition(AdditionRequest addition) {
        this.addition = addition;
    }

    public List<Integer> getImportant_numbers() {
        return important_numbers;
    }

    public void setImportant_numbers(List<Integer> important_numbers) {
        this.important_numbers = important_numbers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
package dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EntityResponse {
    @JsonProperty("addition")
    private AdditionResponse addition;

    @JsonProperty("id")
    private int id;

    @JsonProperty("important_numbers")
    private List<Integer> important_numbers;

    @JsonProperty("title")
    private String title;

    @JsonProperty("verified")
    private boolean verified;

    public EntityResponse() {}

    public AdditionResponse getAddition() {
        return addition;
    }

    public void setAddition(AdditionResponse addition) {
        this.addition = addition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
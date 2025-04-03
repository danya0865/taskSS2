package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
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
}
package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entity {
    @JsonProperty("addition")
    private Addition addition;

    @JsonProperty("important_numbers")
    private List<Integer> important_numbers;

    @JsonProperty("title")
    private String title;

    @JsonProperty("verified")
    private boolean verified;

    @JsonProperty("id")
    private Integer id;
}
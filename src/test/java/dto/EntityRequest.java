package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityRequest {
    private AdditionRequest addition;
    private List<Integer> important_numbers;
    private String title;
    private boolean verified;
}
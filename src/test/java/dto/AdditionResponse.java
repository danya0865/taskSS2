package dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdditionResponse {
    private String additional_info;
    private int additional_number;
    private int id;
}
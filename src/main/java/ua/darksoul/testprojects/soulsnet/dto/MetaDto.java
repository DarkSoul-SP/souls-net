package ua.darksoul.testprojects.soulsnet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetaDto {
    private String title;
    private String description;
    private String cover;
}

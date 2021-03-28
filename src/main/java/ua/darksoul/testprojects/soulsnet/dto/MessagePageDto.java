package ua.darksoul.testprojects.soulsnet.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Setter;
import lombok.Value;
import ua.darksoul.testprojects.soulsnet.domain.Message;
import ua.darksoul.testprojects.soulsnet.domain.Views;

import java.util.List;

/**
 * Created by DarkSoul on 28.03.2021
 **/

@Value
@JsonView(Views.FullMessage.class)
public class MessagePageDto {
    List<Message> messages;
    int currentPage;
    int totalPages;
}

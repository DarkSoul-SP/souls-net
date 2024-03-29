package ua.darksoul.testprojects.soulsnet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.darksoul.testprojects.soulsnet.domain.User;
import ua.darksoul.testprojects.soulsnet.domain.Views;
import ua.darksoul.testprojects.soulsnet.dto.MessagePageDto;
import ua.darksoul.testprojects.soulsnet.repo.UserDetailsRepo;
import ua.darksoul.testprojects.soulsnet.service.MessageService;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageService messageService;
    private final UserDetailsRepo userDetailsRepo;

    @Value("${spring.profiles.active:prod}")
    private String mode;
    private final ObjectWriter messageWriter;
    private final ObjectWriter profileWriter;
    private final ObjectWriter usersWriter;

    @Autowired
    public MainController(MessageService messageService, UserDetailsRepo userDetailsRepo, ObjectMapper mapper) {
        this.messageService = messageService;
        this.userDetailsRepo = userDetailsRepo;

        ObjectMapper objectMapper = mapper.setConfig(mapper.getSerializationConfig());
        this.messageWriter = objectMapper.writerWithView(Views.FullMessage.class);
        this.profileWriter = objectMapper.writerWithView(Views.FullProfile.class);
        this.usersWriter = objectMapper.writerWithView(Views.IdName.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();

        if(user != null) {
            User userFromDB = userDetailsRepo.findById(user.getId()).get();
            String serializedProfile = profileWriter.writeValueAsString(userFromDB);
            model.addAttribute("profile", serializedProfile);

            List<User> users = userDetailsRepo.findAll();
            String serializedUsers = usersWriter.writeValueAsString(users);
            model.addAttribute("users", serializedUsers);

            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(0, MessageController.MESSAGES_PER_PAGE, sort);
            MessagePageDto messagePageDto = messageService.findForUser(pageRequest, user);

            String serializedMessages = messageWriter.writeValueAsString(messagePageDto.getMessages());

            model.addAttribute("messages", serializedMessages);
            data.put("currentPage", messagePageDto.getCurrentPage());
            data.put("totalPages", messagePageDto.getTotalPages());
        } else {
            model.addAttribute("profile", "null");
            model.addAttribute("users", "[]");
            model.addAttribute("messages", "[]");
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(mode));

        return "index";
    }
}

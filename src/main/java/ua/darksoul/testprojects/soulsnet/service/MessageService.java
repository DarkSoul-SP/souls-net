package ua.darksoul.testprojects.soulsnet.service;

import lombok.NonNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.darksoul.testprojects.soulsnet.domain.Message;
import ua.darksoul.testprojects.soulsnet.domain.User;
import ua.darksoul.testprojects.soulsnet.domain.UserSubscription;
import ua.darksoul.testprojects.soulsnet.domain.Views;
import ua.darksoul.testprojects.soulsnet.dto.EventType;
import ua.darksoul.testprojects.soulsnet.dto.MessagePageDto;
import ua.darksoul.testprojects.soulsnet.dto.MetaDto;
import ua.darksoul.testprojects.soulsnet.dto.ObjectType;
import ua.darksoul.testprojects.soulsnet.repo.MessageRepo;
import ua.darksoul.testprojects.soulsnet.repo.UserSubscriptionRepo;
import ua.darksoul.testprojects.soulsnet.util.WsSender;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by DarkSoul on 28.03.2021
 **/

@Service
public class MessageService {
    private static String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMG_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

    private final MessageRepo messageRepo;
    private final UserSubscriptionRepo userSubscriptionRepo;
    private final BiConsumer<EventType, Message> wsSender;

    @Autowired
    public MessageService(MessageRepo messageRepo, UserSubscriptionRepo userSubscriptionRepo, WsSender wsSender) {
        this.messageRepo = messageRepo;
        this.userSubscriptionRepo = userSubscriptionRepo;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.FullMessage.class);
    }

    private void fillMeta(Message message) throws IOException {
        @NonNull
        String text = message.getText();

        Matcher matcher = URL_REGEX.matcher(text);

        if(matcher.find()) {
            String url = text.substring(matcher.start(), matcher.end());

            message.setLink(url);

            matcher = IMG_REGEX.matcher(url);

            if(matcher.find()) {
                message.setLinkCover(url);
            } else if(!url.contains("youtu")) {
                MetaDto meta = getMeta(url);

                message.setLinkTitle(meta.getTitle());
                message.setLinkDescription(meta.getDescription());
                message.setLinkCover(meta.getCover());
            }
        }
    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements title = doc.select("meta[name$=title], meta[property$=title]");
        Elements description = doc.select("meta[name$=description], meta[property$=description]");
        Elements cover = doc.select("meta[name$=image], meta[property$=image]");

        return new MetaDto(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first())
        );
    }

    private String getContent(Element element) {
        return element == null ? "" : element.attr("content");
    }

    public void delete(Message message) {
        messageRepo.delete(message);
        wsSender.accept(EventType.REMOVE, message);
    }

    public Message update(Message messageFromDB, Message message) throws IOException {
        if(messageFromDB == null) {
            return null;
        }

        messageFromDB.setText(message.getText());
        fillMeta(messageFromDB);

        Message updatedMessage = messageRepo.save(messageFromDB);

        wsSender.accept(EventType.UPDATE, updatedMessage);

        return updatedMessage;
    }

    public Message create(User author, Message message) throws IOException {
        message.setCreationDate(LocalDateTime.now());
        fillMeta(message);
        message.setAuthor(author);
        Message updatedMessage = messageRepo.save(message);

        wsSender.accept(EventType.CREATE, updatedMessage);

        return updatedMessage;
    }

    public MessagePageDto findForUser(Pageable pageable, User user) {
        List<User> channels = userSubscriptionRepo.findBySubscriber(user)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getChannel)
                .collect(Collectors.toList());

        channels.add(user);

        Page<Message> page = messageRepo.findByAuthorIn(channels, pageable);

        return new MessagePageDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }
}

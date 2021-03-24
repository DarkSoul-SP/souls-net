package ua.darksoul.testprojects.soulsnet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.darksoul.testprojects.soulsnet.domain.Comment;
import ua.darksoul.testprojects.soulsnet.domain.User;
import ua.darksoul.testprojects.soulsnet.domain.Views;
import ua.darksoul.testprojects.soulsnet.dto.EventType;
import ua.darksoul.testprojects.soulsnet.dto.ObjectType;
import ua.darksoul.testprojects.soulsnet.repo.CommentRepo;
import ua.darksoul.testprojects.soulsnet.util.WsSender;

import java.util.function.BiConsumer;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentService(CommentRepo commentRepo, WsSender wsSender) {
        this.commentRepo = commentRepo;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User author) {
        comment.setAuthor(author);
        Comment commentFromDB = commentRepo.save(comment);

        wsSender.accept(EventType.CREATE, commentFromDB);

        return commentFromDB;
    }
}

package ua.darksoul.testprojects.soulsnet.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr")
@Data
@EqualsAndHashCode(of = { "id"})
@ToString(of = { "id", "name"})
public class User implements Serializable {
    @Id
    @JsonView(Views.IdName.class)
    private String id;
    @JsonView(Views.IdName.class)
    private String name;
    @JsonView(Views.IdName.class)
    private String userpic;
    @JsonView(Views.FullProfile.class)
    private String email;
    private String gender;
    private String locale;
    @JsonView(Views.FullProfile.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisit;
    @Column(columnDefinition = "boolean default false")
    @JsonView(Views.FullProfile.class)
    private boolean autoApprove = false;

    @JsonView(Views.FullProfile.class)
    @OneToMany(mappedBy = "subscriber", orphanRemoval = true)
    private Set<UserSubscription> subscriptions = new HashSet<>();

    @JsonView(Views.FullProfile.class)
    @OneToMany(mappedBy = "channel", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<UserSubscription> subscribers = new HashSet<>();
}

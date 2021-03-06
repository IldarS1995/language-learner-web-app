package ru.ildar.languagelearner.database.domain;

import ru.ildar.languagelearner.controller.dto.PopularCluster;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/** A list of lessons of a single user, bound by the sentences languages.
 * One cluster can have many lessons, defined for this language pair. */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"language1_name", "language2_name", "user_id"})},
        indexes = { @Index(name = "cluster_user_id_idx", columnList = "user_id") }
)
public class Cluster implements Serializable
{
    /** Entity's primary key */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clusterId;

    /** First language of a cluster */
    @ManyToOne
    @JoinColumn(name = "language1_name", nullable = false)
    private Language language1;

    /** Second language of a cluster */
    @ManyToOne
    @JoinColumn(name = "language2_name", nullable = false)
    private Language language2;

    /** User owning this cluster */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appUser;

    /** Creation date of last lesson in this cluster */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLessonAddDate;

    /** Count of lessons that belong to this cluster */
    @Column(name = "lessons_count")
    private long lessonsCount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cluster")
    private List<Lesson> lessons;

    public Cluster() { }
    public Cluster(AppUser appUser)
    {
        this.appUser = appUser;
    }

    @PrePersist
    public void prePersist()
    {
        lessonsCount = 0;
    }

    public Long getClusterId()
    {
        return clusterId;
    }

    public void setClusterId(Long clusterId)
    {
        this.clusterId = clusterId;
    }

    public Language getLanguage1()
    {
        return language1;
    }

    public void setLanguage1(Language language1)
    {
        this.language1 = language1;
    }

    public Language getLanguage2()
    {
        return language2;
    }

    public void setLanguage2(Language language2)
    {
        this.language2 = language2;
    }

    public AppUser getAppUser()
    {
        return appUser;
    }

    public void setAppUser(AppUser appUser)
    {
        this.appUser = appUser;
    }

    public Date getLastLessonAddDate()
    {
        return lastLessonAddDate;
    }

    public long getLessonsCount()
    {
        return lessonsCount;
    }
}

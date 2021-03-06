package ru.ildar.languagelearner.database.domain;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/** Lesson in a cluster - contains pairs "sentence-translation" in cluster defined languages. */
@Entity
@Table(
        indexes = { @Index(name = "lesson_cluster_id_idx", columnList = "cluster_id") }
)
public class Lesson implements Serializable
{
    /** Lesson's primary key */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;

    /** Header of the lesson */
    @Column(length = 100, nullable = false)
    private String lessonName;

    /** Short description of the lesson */
    @Column(length = 300)
    private String description;

    /** The cluster this lesson pertains to */
    @ManyToOne
    @JoinColumn(name = "cluster_id", nullable = false)
    private Cluster cluster;

    /** Date of this lesson creation */
    @Temporal(TemporalType.TIMESTAMP)
    private Date addDate;

    /** How many times this lesson was taken as exercise */
    @Column(nullable = false)
    private long timesLessonTaken;

    /** The sum of all grades received by this lesson exercises */
    @Column(nullable = false)
    private Double sumGrade;

    /** Translations list of this lesson */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lesson")
    private List<Translation> translations;

    /** Date when this lesson was last taken by user as an exercise */
    @Column(name = "last_taken", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTaken;

    /** Count of translations that belong to this lesson */
    @Formula("(select count(*) from translation t where t.lesson_id = lesson_id)")
    private int translationsCount;

    public Lesson() { }
    public Lesson(Cluster cluster)
    {
        this.cluster = cluster;
    }

    @PrePersist
    public void prePersist() throws ParseException
    {
        addDate = new Date();
        timesLessonTaken = 0;
        sumGrade = 0.0;
        lastTaken = new SimpleDateFormat("yyyy").parse("0000");
    }

    public double averageGrade()
    {
        return (double)((int)(sumGrade / timesLessonTaken * 10000)) / 100;
    }

    public Long getLessonId()
    {
        return lessonId;
    }

    public void setLessonId(Long lessonId)
    {
        this.lessonId = lessonId;
    }

    public String getLessonName()
    {
        return lessonName;
    }

    public void setLessonName(String lessonName)
    {
        this.lessonName = lessonName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Cluster getCluster()
    {
        return cluster;
    }

    public void setCluster(Cluster cluster)
    {
        this.cluster = cluster;
    }

    public Date getAddDate()
    {
        return addDate;
    }

    public void setAddDate(Date addDate)
    {
        this.addDate = addDate;
    }

    public List<Translation> getTranslations()
    {
        return translations;
    }

    public void setTranslations(List<Translation> translations)
    {
        this.translations = translations;
    }

    public int getTranslationsCount()
    {
        return translationsCount;
    }

    public long getTimesLessonTaken()
    {
        return timesLessonTaken;
    }

    public void setTimesLessonTaken(long timesLessonTaken)
    {
        this.timesLessonTaken = timesLessonTaken;
    }

    public Double getSumGrade()
    {
        return sumGrade;
    }

    public void setSumGrade(Double sumGrade)
    {
        this.sumGrade = sumGrade;
    }

    public Date getLastTaken()
    {
        return lastTaken;
    }

    public void setLastTaken(Date lastTaken)
    {
        this.lastTaken = lastTaken;
    }
}

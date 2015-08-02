package ru.ildar.languagelearner.database.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import ru.ildar.languagelearner.database.domain.Cluster;
import ru.ildar.languagelearner.database.domain.Lesson;

import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson, Long>
{
    List<Lesson> findByCluster(Cluster cluster);

    List<Lesson> findByCluster(Cluster cluster, Pageable pageable);
}

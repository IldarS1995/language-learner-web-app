package ru.ildar.languagelearner.service;

import ru.ildar.languagelearner.controller.dto.ClusterDTO;
import ru.ildar.languagelearner.controller.dto.LanguagePairDTO;
import ru.ildar.languagelearner.controller.dto.PopularCluster;
import ru.ildar.languagelearner.database.domain.Cluster;
import ru.ildar.languagelearner.exception.*;
import ru.ildar.languagelearner.exercise.Exerciser;

import java.util.List;

public interface ClusterService
{
    /**
     * Add new cluster to the database
     * @param clusterDTO DTO object of the claster
     * @param nickname Nickname of user who owns this cluster
     * @return Assigned ID to the cluster row
     * @throws LanguagesAreEqualException If languages in the cluster are equal
     * @throws LanguageNotFoundException If one of the languages of the cluster
     * is not found in the database
     * @throws ClusterAlreadyExistsException If there's already a cluster with such language pair
     */
    Long addCluster(ClusterDTO clusterDTO, String nickname) throws LanguagesAreEqualException,
            LanguageNotFoundException, ClusterAlreadyExistsException;

    /** Returns all clusters of the user specified by this nickname */
    List<Cluster> getClustersOfUser(String nickname);

    /** Returns the cluster specified by this ID */
    Cluster getClusterById(Long id);

    /**
     * Checks if there's a cluster in the database with such language pair for this user
     * @param lang1 Language 1
     * @param lang2 Language 2
     * @param userNickname Nickname of the user whose cluster list needs to be checked
     * @return true, if it exists; otherwise false
     * @throws LanguageNotFoundException If one of the languages was not found in the DB
     */
    boolean checkClusterExistence(String lang1, String lang2, String userNickname)
                        throws LanguageNotFoundException;

    /**
     * Returns a pair of two arbitrary languages for which the cluster doesn't exist for this user.
     * @param userNickname Nickname of the user whose cluster list is searched in
     * @return Such pair, if it exists; otherwise <code>null</code>
     */
    LanguagePairDTO getNonExistentLanguagePair(String userNickname);

    /**
     * Checks if the cluster specified by this ID is owner by the user specified by this nickname
     * @param clusterId ID of the cluster
     * @param nickname Nickname of the user
     * @return Cluster specified by this name if the exception has not been thrown
     * @throws ClusterNotOfThisUserException If the cluster is non-existent or not of this user
     */
    Cluster checkClusterOwner(long clusterId, String nickname);

    /**
     * Set the lesson specified by this ID to the exerciser object.
     * The method checks that the lesson belongs to the user with this nickname.
     * @param exerciser Exerciser object
     * @param lessonId ID of the lesson to set
     * @param userNickname Nickname of the user
     * @throws LessonNotOfThisUserException If the lesson does not belong to this user
     */
    void setLesson(Exerciser exerciser, Long lessonId, String userNickname);

    /**
     * Deletes this cluster, checking if this cluster belongs to the user
     * authorizing the Delete operation.
     * @param cluster Cluster to delete
     * @param username Username of the user
     * @throws ClusterNotOfThisUserException If the cluster doesn't belong to this user
     */
    void deleteCluster(Cluster cluster, String username);

    /**
     * Returns the most popular clusters. The most popular cluster is the one that is defined
     * most often among users.
     * @param clustersToTake How many results to return
     */
    List<PopularCluster<Long>> getMostPopularClusters(int clustersToTake);

    /**
     * Returns average count of lessons per cluster(that is, per language pair)
     * and sorts them by count in descending order, returning only some of the top ones.
     * @param clustersToTake How many results to return
     */
    List<PopularCluster<Double>> getAvgLessonsCountOfClusters(int clustersToTake);
}

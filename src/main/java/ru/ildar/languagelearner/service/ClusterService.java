package ru.ildar.languagelearner.service;

import ru.ildar.languagelearner.controller.dto.ClusterDTO;
import ru.ildar.languagelearner.controller.dto.LanguagePairDTO;
import ru.ildar.languagelearner.database.domain.Cluster;
import ru.ildar.languagelearner.exception.ClusterAlreadyExistsException;
import ru.ildar.languagelearner.exception.LanguageNotFoundException;
import ru.ildar.languagelearner.exception.LanguagesAreEqualException;

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
    long addCluster(ClusterDTO clusterDTO, String nickname) throws LanguagesAreEqualException,
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
     */
    boolean checkClusterExistence(String lang1, String lang2, String userNickname);

    /**
     * Returns a pair of two arbitrary languages for which
     * the cluster doesn't exist for this user.
     * @param userNickname Nickname of the user whose cluster list is searched in
     * @return Such pair, if it exists; otherwise <code>null</code>
     */
    LanguagePairDTO getNonExistentLanguagePair(String userNickname);
}

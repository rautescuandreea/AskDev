package utcn.ps.assignment1demo.persistance.api;

import utcn.ps.assignment1demo.entity.Question;
import utcn.ps.assignment1demo.entity.vote.VoteAnswer;

import java.util.List;
import java.util.Optional;

public interface VoteAnswerRepository {
    VoteAnswer save(VoteAnswer voteAnswer);

    /*
         Optional is a generic introduced in Java 8
         Null is considered to be a horrible mistake, there are even languages which don't have Null
         Optional is basically a simple wrapper which has two states: it is either empty or it has one element, it can never be null
         see https://www.geeksforgeeks.org/java-8-optional-class/
     */
    Optional<VoteAnswer> findById(int id);

    void remove(VoteAnswer voteAnswer);

    List<VoteAnswer> findAll();
}

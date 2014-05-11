package com.clowndata.model.valueobject;

import com.clowndata.model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2014.
 */
public class GamesStatistics {

    List<Game> games = new ArrayList<>();

    public GamesStatistics(List<Game> games) {
        this.games.clear();
        this.games.addAll(games);

        for (Game game : games) {
            GameSummary gameSummary = new GameSummary(game);
            if (gameSummary.isGameEnded()) {
                totalGameTimeInSeconds += gameSummary.getDurationInSeconds();
                totalNumberOfGoals += gameSummary.getTotalNumberOfGoals();
            }
            totalNumberOfOwnGoals+= game.getNumberOfOwnGoals();
        }
    }
//List of URIs to GameSummary
    //List of URIs to PlayerStatistics

    long totalGameTimeInSeconds;
    int totalNumberOfGoals;
    int totalNumberOfOwnGoals;

    //List<PlayerStatItem> PlayerStatItem frequentPlayer = new ArrayList(3);
    //List<PlayerStatItem> PlayerStatItem frequentScorer = new ArrayList(3);
    //List<PlayerStatItem> PlayerStatItem frequentAssist = new ArrayList(3);
    //List<PlayerStatItem> PlayerStatItem frequentOwnGoal = new ArrayList(3);
    //List<PlayerStatItem> PlayerStatItem bestWinLoseRatio = new ArrayList(3);
    //List<PlayerStatItem> PlayerStatItem worstWinLoseRatio = new ArrayList(3);

    //List<PlayerStatItem> PlayerStatItem highestGoalPerMinute = new ArrayList(3);
    //List<PlayerStatItem> PlayerStatItem highestGoalPerGame = new ArrayList(3);

    //List<PlayerStatItem> PlayerStatItem highestPointsPerMinute = new ArrayList(3);
    //List<PlayerStatItem> PlayerStatItem highestPointsPerGame = new ArrayList(3);

    //List<PlayerStatItem> PlayerStatItem firstGoals = new ArrayList(3);
    //List<PlayerStatItem> PlayerStatItem lastGoals = new ArrayList(3);

    //List<PlayerStatItem> PlayerStatItem goalsInARow = new ArrayList(3);

    String mostAssister;
    String mostAssistee;
    int numberOfAssist;

    public long getTotalGameTimeInSeconds() {
        return totalGameTimeInSeconds;
    }


    public int getTotalNumberOfGoals() {
        return totalNumberOfGoals;
    }

    public int getTotalNumberOfOwnGoals() {
        return totalNumberOfOwnGoals;
    }
}

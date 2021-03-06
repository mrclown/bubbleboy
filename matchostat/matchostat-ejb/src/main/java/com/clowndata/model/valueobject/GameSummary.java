package com.clowndata.model.valueobject;

import com.clowndata.model.Game;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;
import java.util.Date;

/**
 * Created by 2014.
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class GameSummary {

    @XmlAttribute
    private long durationInSeconds;

    @XmlAttribute
    private TeamSummary team1Summary;

    @XmlAttribute
    private TeamSummary team2Summary;

    @XmlAttribute
    private boolean gameEnded;

    public GameSummary(Game game) {
        team1Summary = new TeamSummary(game.getTeam1(), game);
        team2Summary = new TeamSummary(game.getTeam2(), game);

        gameEnded = game.isGameEnded();

        long endTime;
        if (!gameEnded) {
            Date now = new Date();
            endTime = now.getTime();
        } else {
            endTime = game.getGameEnd().getTime();
        }
        durationInSeconds = (endTime - game.getGameStart().getTime()) / 1000;

    }

    public long getDurationInSeconds() {
        return durationInSeconds;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public TeamSummary getTeam1Summary() {
        return this.team1Summary;
    }

    public TeamSummary getTeam2Summary() {
        return this.team2Summary;
    }

    @JsonIgnore
    public int getTotalNumberOfGoals(){
        return this.team1Summary.getTeamGoals() + this.team2Summary.getTeamGoals();
    }
}

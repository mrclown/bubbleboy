package com.clowndata.model.valueobject;

import com.clowndata.model.Game;

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

        long endTime = 0;
        if (game.getGameEnd() == null) {
            Date now = new Date();
            endTime = now.getTime();
            gameEnded = false;
        } else {
            endTime = game.getGameEnd().getTime();
            gameEnded = true;
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
}

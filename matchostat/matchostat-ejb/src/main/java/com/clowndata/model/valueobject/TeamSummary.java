package com.clowndata.model.valueobject;

import com.clowndata.model.Game;
import com.clowndata.model.Player;
import com.clowndata.model.Team;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2014.
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class TeamSummary {

    @XmlAttribute
    private int teamGoals;

    @XmlAttribute
    private List<PlayerSummary> playerSummaries = new ArrayList<>();

    public TeamSummary(Team team, Game game) {
        this.teamGoals = team.getScore();
        for (Player player : team.getPlayers()) {
            PlayerSummary playerSummary = new PlayerSummary(player.getName(), player.getGoals(game), player.getPoints(game));
            playerSummaries.add(playerSummary);
        }
    }

    public int getTeamGoals() {
        return this.teamGoals;
    }

    public List<PlayerSummary> getPlayerSummaries() {
        return playerSummaries;
    }
}

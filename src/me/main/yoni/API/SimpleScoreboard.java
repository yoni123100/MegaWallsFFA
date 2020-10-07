package me.main.yoni.API;
 
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
 
 
public class SimpleScoreboard
{
 
    private Scoreboard scoreboard;
 
    private String title;
    private Map<String, Integer> scores;
    private List<Team> teams;
 
    public SimpleScoreboard(String title)
    {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.title = title;
        this.scores = Maps.newLinkedHashMap();
        this.teams = Lists.newArrayList();
    }
 
    public void blankLine()
    {
        add("§4");
    }
 
    public void add(String text)
    {
        add(text, null);
    }
 
    public void add(String text, Integer score)
    {
        Preconditions.checkArgument(text.length() < 48, "text cannot be over 48 characters in length");
        text = fixDuplicates(text);
        scores.put(text, score);
    }
 
    private String fixDuplicates(String text)
    {
        while (scores.containsKey(text))
            text += "§r";
        if (text.length() > 48)
            text = text.substring(0, 47);
        return text;
    }
 
    private Map.Entry<Team, String> createTeam(String text)
    {
        String result = "";
        if (text.length() <= 16)
            return new AbstractMap.SimpleEntry<>(null, text);
        Team team = scoreboard.registerNewTeam("text-" + scoreboard.getTeams().size());
        Iterator<String> iterator = Splitter.fixedLength(16).split(text).iterator();
        team.setPrefix(iterator.next());
        result = iterator.next();
        if (text.length() > 32)
            team.setSuffix(iterator.next());
        teams.add(team);
        return new AbstractMap.SimpleEntry<>(team, result);
    }
 
    @SuppressWarnings("deprecation")
    public void build()
    {
        Objective obj = scoreboard.registerNewObjective((title.length() > 16 ? title.substring(0, 15) : title), "dummy");
        obj.setDisplayName(title);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        int index = scores.size();
 
        for (Map.Entry<String, Integer> text : scores.entrySet()){
            Map.Entry<Team, String> team = createTeam(text.getKey());
            Integer score = text.getValue() != null ? text.getValue() : index;
            OfflinePlayer player = new Message(team.getValue());
            if (team.getKey() != null)
                team.getKey().addPlayer(player);
            obj.getScore(player).setScore(score);
            index -= 1;
        }
    }
 
    public void reset(){
        title = null;
        scores.clear();
        for (Team t : teams) t.unregister();
        teams.clear();
    }
 
    public Scoreboard getScoreboard(){return scoreboard;}
 
    public void send(Player... players){
        for (Player p : players)
            p.setScoreboard(scoreboard);
    }
 
    public static void resetScoreboard(Player p)
    {
        SimpleScoreboard scoreboard = new SimpleScoreboard("");
        scoreboard.build();
        scoreboard.send(p);
        scoreboard.reset();
    }
}
 
class Message implements OfflinePlayer
{
    String name;
 
    public Message(String name)
    {
        this.name = name;
    }
 
    /**
     * Returns the name of this player
     *
     * @return Player name
     */
    @Override
    public String getName()
    {
        return name;
    }
 
    @Override
    public Map<String, Object> serialize()
    {
        return null;
    }
 
    @Override
    public UUID getUniqueId(){return UUID.randomUUID();}
 
    @Override
    public boolean isOp(){return false;}
 
    @Override
    public void setOp(boolean value){}
 
    @Override
    public Location getBedSpawnLocation(){
        return null;
    }
 
    @Override
    public long getFirstPlayed(){
        return 0;
    }
 
    @Override
    public long getLastPlayed(){
        return 0;
    }
 
    @Override
    public Player getPlayer(){
        return null;
    }
 
    @Override
    public boolean hasPlayedBefore(){
        return false;
    }
 
    @Override
    public boolean isBanned(){
        return false;
    }
 
    @Override
    public boolean isOnline(){
        return false;
    }
 
    @Override
    public boolean isWhitelisted(){
        return false;
    }
 
    @Override
    public void setBanned(boolean banned){}
    @Override
    public void setWhitelisted(boolean value){}
}
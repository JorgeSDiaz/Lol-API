package rg.lol.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rg.lol.model.Invoker;
import rg.lol.service.external.DragonService;
import rg.lol.service.external.OldRiotService;
import rg.lol.service.external.RiotService;

@Service
public class InvokersService {
    @Autowired
    private OldRiotService oldRiotService;
    @Autowired
    private RiotService riotService;
    @Autowired
    private DragonService dragonService;

    /**
     * Request the player icon from the Dragon API
     * 
     * @param nickname The nickname of the player
     * @return A byte array with the player icon
     */
    public byte[] requestLolPlayerIcon(String nickname) {
        Invoker lolPlayer = getLolPlayerDataByNickname(nickname);
        return dragonService.requestLolPlayerIcon(lolPlayer.getProfileIconId());
    }

    /**
     * Get the matches data from the Riot API
     * 
     * @param puuid The puuid of the player
     * @return A list with the matches data
     */
    public List<Map<String, Object>> getMatchesDataByPlayerPUUID(String puuid) {
        List<String> matchesId = riotService.requestMatchesIdFromPlayerPUUID(puuid);
        List<Map<String, Object>> matchesData = matchesId.stream()
                .map(matchId -> riotService.requestMatchDataByMatchId(matchId))
                .toList();

        return matchesData;
    }

    /**
     * Get the player data from the Riot API
     * 
     * @param nickname The nickname of the player
     * @return A Invoker object with the player data
     */
    public Invoker getLolPlayerDataByNickname(String nickname) {
        Map<String, Object> lolPlayerData = oldRiotService.requestLolPlayerDataByNickname(nickname);
        List<Map<String, Object>> matches = getMatchesDataByPlayerPUUID((String) lolPlayerData.get("puuid"));

        Invoker lolPlayer = Invoker.builder()
                .id((String) lolPlayerData.get("id"))
                .accountId((String) lolPlayerData.get("accountId"))
                .puuid((String) lolPlayerData.get("puuid"))
                .name((String) lolPlayerData.get("name"))
                .profileIconId((int) lolPlayerData.get("profileIconId"))
                .summonerLevel((int) lolPlayerData.get("summonerLevel"))
                .matches(matches)
                .build();

        return lolPlayer;
    }
}

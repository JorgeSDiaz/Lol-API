package rg.lol.service.external;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * RiotService
 */
@Service
public class RiotService {
    @Value("${riot.token}")
    private String key;

    WebClient riotClient;

    public RiotService() {
        this.riotClient = WebClient.builder().baseUrl("https://americas.api.riotgames.com/lol").build();
    }

    /**
     * Request the match data from the Riot API
     * 
     * @param matchId The id of the match
     * @return A map with the match data
     */
    public Map<String, Object> requestMatchDataByMatchId(String matchId) {
        Map<String, Object> matchData = riotClient.get()
                .uri("/match/v5/matches/{matchId}", matchId)
                .header("X-Riot-Token", key)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                }).block();

        return matchData;
    }

    /**
     * Request the matches id from the Riot API
     * 
     * @param puuid The puuid of the player
     * @return A list with the matches id
     */
    public List<String> requestMatchesIdFromPlayerPUUID(String puuid) {
        List<String> matchesId = riotClient.get()
                .uri("/match/v5/matches/by-puuid/{puuid}/ids?start=0&count=1", puuid)
                .header("X-Riot-Token", key)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                }).block();

        return matchesId;
    }
}

package rg.lol.service.external;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * OldRiotService
 */
@Service
public class OldRiotService {
    @Value("${riot.token}")
    private String key;

    WebClient oldRiotClient;

    public OldRiotService() {
        this.oldRiotClient = WebClient.builder().baseUrl("https://la1.api.riotgames.com/lol").build();
    }

        /**
     * Request the player data from the Riot API
     * @param nickname The nickname of the player
     * @return A map with the player data
     */
    public Map<String, Object> requestLolPlayerDataByNickname(String nickname) {
        Map<String, Object> lolPlayerDataRequest = oldRiotClient.get()
                .uri("/summoner/v4/summoners/by-name/{nickname}", nickname)
                .header("X-Riot-Token", key)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                }).block();

        return lolPlayerDataRequest;
    }
}
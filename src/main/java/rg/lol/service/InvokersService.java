package rg.lol.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import rg.lol.model.Invoker;

@Service
public class InvokersService {
    @Value("${riot.token}")
    private String key;

    WebClient oldRiotClient;
    WebClient riotClient;
    WebClient dragonClient;

    public InvokersService() {
        this.oldRiotClient = WebClient.builder().baseUrl("https://la1.api.riotgames.com/lol").build();
        this.riotClient = WebClient.builder().baseUrl("https://americas.api.riotgames.com/lol").build();
        this.dragonClient = WebClient.builder().baseUrl("http://ddragon.leagueoflegends.com/cdn").build();
    }

    public Map<String, Object> requestLolPlayerDataByNickname(String nickname) {
        Map<String, Object> lolPlayerDataRequest = oldRiotClient.get()
                .uri("/summoner/v4/summoners/by-name/{nickname}", nickname)
                .header("X-Riot-Token", key)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                }).block();

        return lolPlayerDataRequest;
    }

    public byte[] requestLolPlayerIcon(String nickname) {
        Invoker lolPlayer = getLolPlayerDataByNickname(nickname);

        byte[] lolPlayerIcon = dragonClient.get()
                .uri("/13.24.1/img/profileicon/{iconId}.png", String.valueOf(lolPlayer.getProfileIconId()))
                .retrieve()
                .bodyToMono(byte[].class)
                .block();

        return lolPlayerIcon;
    }

    public List<String> requestMatchesIdFromPlayerPUUID(String puuid) {
        List<String> matchesId = riotClient.get()
                .uri("/match/v5/matches/by-puuid/{puuid}/ids", puuid)
                .header("X-Riot-Token", key)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                }).block();

        return matchesId;
    }

    public Invoker getLolPlayerDataByNickname(String nickname) {
        Map<String, Object> lolPlayerData = requestLolPlayerDataByNickname(nickname);
        List<String> matchesId = requestMatchesIdFromPlayerPUUID((String) lolPlayerData.get("puuid"));

        Invoker lolPlayer = Invoker.builder()
                .id((String) lolPlayerData.get("id"))
                .accountId((String) lolPlayerData.get("accountId"))
                .puuid((String) lolPlayerData.get("puuid"))
                .name((String) lolPlayerData.get("name"))
                .profileIconId((int) lolPlayerData.get("profileIconId"))
                .summonerLevel((int) lolPlayerData.get("summonerLevel"))
                .matchesId(matchesId)
                .build();

        return lolPlayer;
    }
}

package rg.lol.service.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * DragonService
 */
@Service
public class DragonService {
    @Value("${riot.token}")
    private String key;

    WebClient dragonClient;

    public DragonService() {
        this.dragonClient = WebClient.builder().baseUrl("http://ddragon.leagueoflegends.com/cdn").build();
    }

    public byte[] requestLolPlayerIcon(int profileIconId) {
        byte[] lolPlayerIcon = dragonClient.get()
                .uri("/13.24.1/img/profileicon/{iconId}.png", String.valueOf(profileIconId))
                .retrieve()
                .bodyToMono(byte[].class)
                .block();

        return lolPlayerIcon;
    }
}

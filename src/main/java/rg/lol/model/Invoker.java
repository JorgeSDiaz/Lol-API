package rg.lol.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoker {
    String id, accountId, puuid, name;
    int profileIconId, summonerLevel;  
}

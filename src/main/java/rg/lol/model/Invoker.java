package rg.lol.model;

import java.util.List;
import java.util.Map;

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
    List<Map<String, Object>> matches; 
}

package rg.lol.service.invokersService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import rg.lol.model.Invoker;
import rg.lol.service.InvokersService;

class GetLolPlayerDataByNickname {
    @InjectMocks
    InvokersService invokersService;
    @Test
    public void givenLolPlayerNickname_whenRequestRiotData_thenResponseData() {
        Invoker RGCoraPlayer = invokersService.getLolPlayerDataByNickname("RG Cora");
        System.out.println(RGCoraPlayer);
        Assertions.assertNotEquals("", RGCoraPlayer);
    }
}
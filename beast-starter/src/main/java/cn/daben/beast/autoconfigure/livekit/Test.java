package cn.daben.beast.autoconfigure.livekit;

import com.google.protobuf.util.JsonFormat;
import io.livekit.server.AccessToken;
import io.livekit.server.RoomJoin;
import io.livekit.server.RoomName;
import io.livekit.server.RoomServiceClient;
import livekit.LivekitModels;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * @author Justubborn
 * @since 2024/7/1
 */
public class Test {

    public static void main(String[] args) throws IOException {
        RoomServiceClient client = RoomServiceClient.createClient(
                "http://127.0.0.1:7880",
                "devkey",
                "secret");

        Call<LivekitModels.Room> call = client.createRoom("123");
        Response<LivekitModels.Room> response = call.execute(); // Use call.enqueue for async
        LivekitModels.Room room = response.body();

        System.out.println(JsonFormat.printer().print(room));

        AccessToken token = new AccessToken("devkey", "secret");

// Fill in token information.
        token.setName("测试");
        token.setIdentity("identity");
        token.setMetadata("metadata");
        token.addGrants(new RoomJoin(true), new RoomName("123"));

// Sign and create token string.
        System.out.println("New access token: " + token.toJwt());
    }
}

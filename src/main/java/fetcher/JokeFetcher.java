package fetcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.ChuckDTO;
import dto.DadDTO;
import dto.JokesDTO;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import utils.HttpUtils;

public class JokeFetcher {

    private static final String chuckNorrisAPI = "https://api.chucknorris.io/jokes/random";
    private static final String dadJokeAPI = "https://icanhazdadjoke.com";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static String fetchJokes(ExecutorService es) throws TimeoutException, InterruptedException, ExecutionException {

        Callable<ChuckDTO> chuckCall = new Callable<ChuckDTO>() {
            @Override
            public ChuckDTO call() throws Exception {
                String chuck = HttpUtils.fetchData(chuckNorrisAPI);
                ChuckDTO chuckDTO = GSON.fromJson(chuck, ChuckDTO.class);
                return chuckDTO;
            }
        };

        Callable<DadDTO> dadCall = new Callable<DadDTO>() {
            @Override
            public DadDTO call() throws Exception {
                String dad = HttpUtils.fetchData(dadJokeAPI);
                DadDTO dadDTO = GSON.fromJson(dad, DadDTO.class);
                return dadDTO;
            }
        };

        Future<ChuckDTO> chuckFuture = es.submit(chuckCall);
        Future<DadDTO> dadFuture = es.submit(dadCall);

        ChuckDTO chuck = chuckFuture.get(2, TimeUnit.SECONDS);
        DadDTO dad = dadFuture.get(2, TimeUnit.SECONDS);

        JokesDTO combinedDTO = new JokesDTO(chuck, dad);
        String json = GSON.toJson(combinedDTO);

        return json;
    }
    
}

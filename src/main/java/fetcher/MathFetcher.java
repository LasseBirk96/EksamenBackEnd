
package fetcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.MathDTO;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import utils.HttpUtils;

/**
 *
 * @author Dane
 */
public class MathFetcher {
    
    private static final String MathAPI = "https://aboutnumber.p.rapidapi.com/getNumberInfo";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    public static String fetchMath (ExecutorService es) throws InterruptedException, ExecutionException, TimeoutException{
        Callable<MathDTO> mathTask = new Callable<MathDTO>() {
            @Override
            public MathDTO call() throws Exception {
                String math = HttpUtils.fetchData(MathAPI);
                MathDTO mathDTO = GSON.fromJson(math, MathDTO.class);
                
                return mathDTO;
            }          
        };
        
        Future<MathDTO> futureMath = es.submit(mathTask);     
        MathDTO math = futureMath.get(2, TimeUnit.SECONDS);
                
        String json = GSON.toJson(math);
        return json;
    }
}

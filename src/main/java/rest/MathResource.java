
package rest;

import fetcher.MathFetcher;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Dane
 */
@Path("math")
public class MathResource {

    @Context
    private UriInfo context;
    private static final ExecutorService es = Executors.newCachedThreadPool();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMath() throws InterruptedException, ExecutionException, TimeoutException{
        return MathFetcher.fetchMath(es);
    }
}

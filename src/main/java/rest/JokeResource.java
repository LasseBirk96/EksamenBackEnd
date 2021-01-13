package rest;

import fetcher.JokeFetcher;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;


@Path("jokes")
public class JokeResource {
    
    @Context
    private UriInfo context;
    private static final ExecutorService es = Executors.newCachedThreadPool();
  
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJokes() throws TimeoutException, InterruptedException, ExecutionException{
    return JokeFetcher.fetchJokes(es);
    }
    
  
    
    

}

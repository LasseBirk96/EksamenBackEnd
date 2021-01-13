package dto;

/**
 *
 * @author Dane
 */
public class DadDTO {

    private String url;
    private String joke;

    public DadDTO(String url, String joke) {
        this.url = url;
        this.joke = joke;
    }

    public DadDTO() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

}

URL url = new URL("http://example.com");
HttpURLConnection con = (HttpURLConnection) url.openConnection();
con.setRequestMethod("GET");

Map<String, String> parameters = new HashMap<>();
parameters.put("param1", "val");

con.setDoOutput(true);
DataOutputStream out = new DataOutputStream(con.getOutputStream());
out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
out.flush();
out.close();

con.setRequestProperty("Content-Type", "application/json");


@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getPostsPlainJSON() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        return this.restTemplate.getForObject(url, String.class);
    }
}

public Post getPostWithUrlParameters() {
    String url = "https://jsonplaceholder.typicode.com/posts/{id}";
    return this.restTemplate.getForObject(url, Post.class, 1);
}

public Post getPostWithResponseHandling() {
    String url = "https://jsonplaceholder.typicode.com/posts/{id}";
    ResponseEntity<Post> response = this.restTemplate.getForEntity(url, Post.class, 1);
    if(response.getStatusCode() == HttpStatus.OK) {
        return response.getBody();
    } else {
        return null;
    }
}

public Post getPostWithCustomHeaders() {
    String url = "https://jsonplaceholder.typicode.com/posts/{id}";

    // create headers
    HttpHeaders headers = new HttpHeaders();
    // set `accept` header
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    // set custom header
    headers.set("x-request-source", "desktop");

    // build the request
    HttpEntity request = new HttpEntity(headers);

    // use `exchange` method for HTTP call
    ResponseEntity<Post> response = this.restTemplate.exchange(url, HttpMethod.GET, request, Post.class, 1);
    if(response.getStatusCode() == HttpStatus.OK) {
        return response.getBody();
    } else {
        return null;
    }
}

WebClient client = WebClient.create();

WebClient.ResponseSpec responseSpec = client.get()
    .uri("http://example.com")
    .retrieve();
    
 private static void getEmployees()
{
    final String uri = "http://localhost:8080/springrestexample/employees.xml";

    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.getForObject(uri, String.class);

    System.out.println(result);
}

@Bean
public WebClient localApiClient() {
    return WebClient.create("http://localhost:8080/api/v3");
}

@Service
public class UserService {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    private final WebClient localApiClient;

    @Autowired
    public UserService(WebClient localApiClient) {
        this.localApiClient = localApiClient;
    }

    public User getUser(long id) {
        return localApiClient
                .get()
                .uri("/users/" + id)
                .retrieve()
                .bodyToMono(User.class)
                .block(REQUEST_TIMEOUT);
    }

}

@Service
public class ClientService {

    private final WebClient webClient;

    public ClientService(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder
                        .baseUrl("https://your.api.com")
    }

    //Add all the API call methods you need leveraging webClient instance

}


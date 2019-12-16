# EmbeddedMicroservicePoc
Embedded Service and Micro-Service Poc






动态Feign：
https://stackoverflow.com/questions/43733569/how-can-i-change-the-feign-url-during-the-runtime

eign has a way to provide the dynamic URLs and endpoints at runtime.

The following steps have to be followed:

In the FeignClient interface we have to remove the URL parameter. We have to use @RequestLine annotation to mention the REST method (GET, PUT, POST, etc.):
@FeignClient(name="customerProfileAdapter")
public interface CustomerProfileAdaptor {

    // @RequestMapping(method=RequestMethod.GET, value="/get_all")
    @RequestLine("GET")
    public List<Customer> getAllCustomers(URI baseUri); 

    // @RequestMapping(method=RequestMethod.POST, value="/add")
    @RequestLine("POST")
    public ResponseEntity<CustomerProfileResponse> addCustomer(URI baseUri, Customer customer);

    @RequestLine("DELETE")
    public ResponseEntity<CustomerProfileResponse> deleteCustomer(URI baseUri, String mobile);
}
In RestController you have to import FeignClientConfiguration
You have to write one RestController constructor with encoder and decoder as parameters.
You need to build the FeignClient with the encoder, decoder.
While calling the FeignClient methods, provide the URI (BaserUrl + endpoint) along with rest call parameters if any.
@RestController
@Import(FeignClientsConfiguration.class)
public class FeignDemoController {

    CustomerProfileAdaptor customerProfileAdaptor;

    @Autowired
    public FeignDemoController(Decoder decoder, Encoder encoder) {
        customerProfileAdaptor = Feign.builder().encoder(encoder).decoder(decoder) 
           .target(Target.EmptyTarget.create(CustomerProfileAdaptor.class));
    }

    @RequestMapping(value = "/get_all", method = RequestMethod.GET)
    public List<Customer> getAllCustomers() throws URISyntaxException {
        return customerProfileAdaptor
            .getAllCustomers(new URI("http://localhost:8090/customer-profile/get_all"));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<CustomerProfileResponse> addCustomer(@RequestBody Customer customer) 
            throws URISyntaxException {
        return customerProfileAdaptor
            .addCustomer(new URI("http://localhost:8090/customer-profile/add"), customer);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<CustomerProfileResponse> deleteCustomer(@RequestBody String mobile)
            throws URISyntaxException {
        return customerProfileAdaptor
            .deleteCustomer(new URI("http://localhost:8090/customer-profile/delete"), mobile);
    }
}





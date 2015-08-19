package rest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import rest.model.Customer;
import rest.util.Utils;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... strings) throws Exception {

        Utils.importCerts();

        final String customersUrl = "https://api.start.payfort.com/customers";
        final String chargesUrl = "https://api.start.payfort.com/charges";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        byte[] authEncBytes = Base64.encodeBase64("test_sec_k_2a1c61eee3295562cb663:".getBytes());
        String authStringEnc = new String(authEncBytes);
        requestHeaders.set("Authorization", "Basic " + authStringEnc);

        /**
         * Customers request
         */
        MultiValueMap<String, String> customersData = new LinkedMultiValueMap<>();
        customersData.add("name", "Abdullah Ahmed");
        customersData.add("email", "abdullah@msn.com");
        customersData.add("card[number]", "4242424242424242");
        customersData.add("card[exp_month]", "12");
        customersData.add("card[exp_year]", "2016");
        customersData.add("card[cvc]", "123");
        customersData.add("description", "Signed up at the Trade Show in Dec 2014");


        HttpEntity<MultiValueMap<String, String>> customersRequestEntity = new HttpEntity<>(customersData, requestHeaders);
        ResponseEntity<Customer> customersResult = restTemplate.exchange(customersUrl, HttpMethod.POST, customersRequestEntity, Customer.class);
        log.info(customersResult.getBody().getId());

        /**
         * Charges request
         */
        MultiValueMap<String, String> chargesData = new LinkedMultiValueMap<>();
        chargesData.add("amount", "1000");
        chargesData.add("currency", "aed");
        chargesData.add("customer_id", customersResult.getBody().getId());
        chargesData.add("description", "Two widgets (test@example.com)");

        HttpEntity<MultiValueMap<String, String>> chargesRequestEntity = new HttpEntity<>(chargesData, requestHeaders);
        ResponseEntity<String> chargesResponse = restTemplate.exchange(chargesUrl, HttpMethod.POST, chargesRequestEntity, String.class);
        String chargesResult = chargesResponse.getBody();
        log.info(chargesResult);


    }
}
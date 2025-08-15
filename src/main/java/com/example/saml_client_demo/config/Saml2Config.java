package com.example.saml_client_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml2.core.Saml2X509Credential;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.Saml2MessageBinding;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

@Configuration
public class Saml2Config {

    @Bean
    public RelyingPartyRegistrationRepository relyingPartyRegistrationRepository() {
        RelyingPartyRegistration registration = RelyingPartyRegistration
                .withRegistrationId("cas")
                .entityId("https://localhost:8081/fake-sp")
                .assertionConsumerServiceLocation("http://localhost:8081/login/saml2/sso/cas")
                .assertionConsumerServiceBinding(Saml2MessageBinding.POST)
                .assertingPartyDetails(party -> party
                        .entityId("https://localhost:8080/idp")
                        .singleSignOnServiceLocation("https://localhost:8080/cas/idp/profile/SAML2/Redirect/SSO")
                        .singleSignOnServiceBinding(Saml2MessageBinding.REDIRECT)
                        .singleLogoutServiceLocation("https://localhost:8080/idp/profile/SAML2/Redirect/SLO")
                        .singleLogoutServiceResponseLocation("https://localhost:8080/idp/profile/SAML2/Redirect/SLO")
                        .singleLogoutServiceBinding(Saml2MessageBinding.REDIRECT)
                        .wantAuthnRequestsSigned(false)
                        .verificationX509Credentials(c -> {
                            try {
                                // Extract the public key certificate from CAS metadata for signature verification
                                String certString = "MIIFBTCCAu2gAwIBAgIUddqVx+eFo1ODF0nH0rTNBv9GhVowDQYJKoZIhvcNAQEN" +
                                        "BQAwFDESMBAGA1UEAwwJbG9jYWxob3N0MB4XDTI1MDgxNDAyNDcxNVoXDTQ1MDgx" +
                                        "NDAyNDcxNVowFDESMBAGA1UEAwwJbG9jYWxob3N0MIICIjANBgkqhkiG9w0BAQEF" +
                                        "AAOCAg8AMIICCgKCAgEApcD5aUx092cPLnJdJPgW/mGn5J0JSbkp3xZQT98KyyEd" +
                                        "YQwbBiNEcUwVIPHt6IAdxQpU8QZl6IG8vIMtPr0L19OwAHhNjdVBfMnmsOzRdxWf" +
                                        "+LWb1s6akkkJ05CW/bpmJIgAszNzi8SQ+/1TcJSVslUlKmbZU+d78ILWYHTpBRjg" +
                                        "NbU5UPNLe0OgezRHSG1O+3pq5XyCw4fti15HuVq1Tv71lPSdUiGgwTSPGJr6gSv9" +
                                        "slGoeRH3jHKPs08uhBXLIGkgWIG/Y4bVPcte4Berk7H0jReETV8YaCfBlHnW7Oz0" +
                                        "AErauvqX/qXBaP5BZLGymxvKx5/7sbMZS6xwbo2XYckgIdBrXtfBjIvdZcaTRQYt" +
                                        "hNm4MF6lIiWZ7QqERm6UgRcPyOar4T4m1YYSgr1BVG7li//sIeHF5KfJJ4GmhIeb" +
                                        "bBAA6etAm87gLA5PtONb0+JqJhscxw63C84uSKdd4VJ219E/KIhfQZGbYXZulYtX" +
                                        "2CY6mKrgaw9pUsU0ESso6ZtZHGYA/exoHtRAjs79N4IwCGvROcx82CG3J8raUrHE" +
                                        "MbkbWL3zhNn9GQt0MfQdMv943kDIUiGTSl951Syj8Ryp6mEsCdG8WcunL0xfnJMk" +
                                        "0nz/wDN3oLg0X7EStQzyWzyFhxM47jkkYgQwTOCB7/b63PpCkcVkdOtSyQnryGUC" +
                                        "AwEAAaNPME0wHQYDVR0OBBYEFB83c1Oeu8mviM1oIfPPA7blat7PMCwGA1UdEQQl" +
                                        "MCOCCWxvY2FsaG9zdIYWbG9jYWxob3N0L2lkcC9tZXRhZGF0YTANBgkqhkiG9w0B" +
                                        "AQ0FAAOCAgEABfHZNja94bfF7npOrfs55fpxZDvGmUyus+gplcdHFLLi09hHW2+l" +
                                        "etifm6IxqQTLeKKCnDvOWsFUc8Aw19JxYSy3jDSErsDJsK0Zopp/NGb55v0IEfUx" +
                                        "3iJXuVz9tQC1UKuNpPbHqeWNz1M/nNzIjXxIkke5IX/rPBGFq8D47NZuWaO31f1m" +
                                        "Y9jnMCPziBSccvr0NpG10iyX1HQaT1KUZp7tmcbOY/IwJ++YC+uMXMcFfKyHm9gf" +
                                        "EBDdxsom7jAiPAr7h+TPNeNH8QrmMyD1CjiLyDThtSDxI8S5rLs+y6GX1I3unxt/" +
                                        "+BcErbdq9uOnb+oRl6NGoFx7/Sw/usB9W8L7Ge/ppyGKn4dGXsDH4xhCOXuX/lQb" +
                                        "RPtd80RkKSyqKlJwhnt40n0Yvh+Nf+T5q+ViDOMiUhLz/AAFIppnshXIKNekft8t" +
                                        "ivJ3eqWG6JUL1x16cV7p6df1nSyZy2VkXgRAZLPH+kpQx+glCoJ3yisaUb0vZEpx" +
                                        "dTLpwz/QmCM0S2lUGkbVlvIJfprRpuycDP6v1xpIsRauoJkkaKnwEPmoANdVEqhO" +
                                        "3w2ARtxn6Ve7pN2eZea7KFAv5Of8jAY/PKs67LyyLLQvK05vxqdC3KXJUfJ+g3FT" +
                                        "J+87vZjO7ISg1bRzMkSx9SuvIXAUgkYp5QWC4fIiqyqlV87GqyDdve4=";
                                
                                // Decode the certificate and create X509Certificate object
                                byte[] certBytes = Base64.getDecoder().decode(certString);
                                CertificateFactory factory = CertificateFactory.getInstance("X.509");
                                X509Certificate cert = (X509Certificate) factory.generateCertificate(
                                        new ByteArrayInputStream(certBytes));
                                
                                // Create verification credential using the public key from the certificate
                                c.add(Saml2X509Credential.verification(cert));
                            } catch (Exception e) {
                                throw new RuntimeException("Failed to load verification certificate", e);
                            }
                        })
                )
                .build();

        return new InMemoryRelyingPartyRegistrationRepository(registration);
    }
}
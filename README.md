# SAML2 Client Demo

This is a Spring Boot application that demonstrates SAML2 authentication with a CAS (Central Authentication Service) server.

## Features

- SAML2 Authentication with CAS server
- User dashboard with SAML attributes display
- Profile page showing detailed user information
- Logout functionality

## Configuration

The application is configured to work with a CAS server running on `https://localhost:8080`.

### Application Properties

- **Server Port**: 8081 (to avoid conflicts with CAS server on 8080)
- **SAML Registration ID**: `cas`
- **Identity Provider Entity ID**: `https://localhost:8080/idp`
- **Service Provider Entity ID**: `http://localhost:8081/saml2/service-provider-metadata/cas`

### SAML Endpoints

- **SP Metadata**: `http://localhost:8081/saml2/service-provider-metadata/cas`
- **ACS (Assertion Consumer Service)**: `http://localhost:8081/login/saml2/sso/cas`
- **Login**: `http://localhost:8081/saml2/authenticate/cas`
- **Logout**: `http://localhost:8081/logout`

## Running the Application

1. Make sure your CAS server is running on `https://localhost:8080`
2. Build the application:
   ```bash
   ./gradlew build -x test
   ```
3. Run the application:
   ```bash
   ./gradlew bootRun
   ```
4. Access the application at `http://localhost:8081`

## Testing SAML2 Flow

1. Go to `http://localhost:8081`
2. Click "Login with SAML2" or go directly to `/saml2/authenticate/cas`
3. You will be redirected to the CAS server for authentication
4. After successful authentication, you'll be redirected back to the dashboard
5. View user profile at `/profile` to see detailed SAML attributes
6. Logout using the logout button

## CAS Server Configuration

You need to register this SAML2 client in your CAS server. Add the following to your CAS configuration:

### Service Registry Entry

Create a JSON file (e.g., `saml-client-demo.json`) in your CAS services directory:

```json
{
  "@class": "org.apereo.cas.support.saml.services.SamlRegisteredService",
  "serviceId": "http://localhost:8081/saml2/service-provider-metadata/cas",
  "name": "SAML Client Demo",
  "id": 1,
  "description": "SAML2 Client Demo Application",
  "evaluationOrder": 1,
  "metadataLocation": "http://localhost:8081/saml2/service-provider-metadata/cas",
  "attributeReleasePolicy": {
    "@class": "org.apereo.cas.services.ReturnAllAttributeReleasePolicy"
  }
}
```

## Troubleshooting

### SSL/TLS Issues
If you encounter SSL certificate issues with the CAS server:

1. For development, you can add the CAS server certificate to your Java truststore
2. Or configure your CAS server to use proper SSL certificates

### Metadata Issues
- Ensure the CAS server metadata is accessible
- Check that the entityID matches between client and server
- Verify that the SSO and SLO URLs are correct

### Debug Logging
The application includes debug logging for SAML2 operations. Check the console output for detailed information about SAML requests and responses.

## Application Structure

- `SecurityConfig.java`: SAML2 security configuration
- `HomeController.java`: Web controllers for pages
- `templates/`: Thymeleaf templates for UI
- `cas-idp-metadata.xml`: CAS server metadata file
- `application.properties`: Application configuration

## Dependencies

- Spring Boot 3.5.4
- Spring Security SAML2 Service Provider
- Spring Boot Starter Web
- Spring Boot Starter Thymeleaf
- Spring Boot Starter Security

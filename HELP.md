https://chromedriver.storage.googleapis.com/index.html?path=101.0.4951.15/

http://tmi.twitch.tv/group/user/fextralife/chatters
https://api.twitch.tv/helix/streams?limit=100
./mvnw spring-boot:run

POST https://id.twitch.tv/oauth2/token
--data-urlencode
?grant_type=refresh_token
&refresh_token=<your refresh token>
&client_id=<your client ID>
&client_secret=<your client secret>

{
"access_token": "1ssjqsqfy6badst1ws7m03gras79zfr",
"refresh_token": "eyJfMzUtNDU0OC04MWYwLTQ5MDY5ODY4NGNlMSJ9%asdfasdf=",
"scope": [
"channel:read:subscriptions",
"channel:manage:polls"
],
"token_type": "bearer"
}

https://id.twitch.tv/oauth2/authorize ?client_id=<your client ID> &redirect_uri=<your registered redirect URI> &response_type=code &scope=<space-separated list of scopes>




# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.6/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.6/maven-plugin/reference/html/#build-image)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#web.reactive)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#using-boot-devtools)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)


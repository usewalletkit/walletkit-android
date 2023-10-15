# walletkit-android

## OpenApi Generator
Api classes and models are auto-generated with openapi-generator. To update the schema, run `
./gradlew openApiGenerate`. This will update the files under 
`src/main/kotlin/com/usewalletkit/sdk/openapi/`. The openapi spec url is defined in 
`sdk/gradle.properties` and can be changed there. To run the generator with a temporary url without 
changing the property, it is possible to run 
`./gradlew openApiGenerate -PopenApiSpecUrl=<your_url>`.
 
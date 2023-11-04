# walletkit-android

## Getting Started
### Initialize the login client and login
WalletKit supports different auth providers:
- WalletKit (native)
- Firebase
- Supabase

The first step is to initialize the login client and use it to login with the preferred method the 
client provides. E.g.: with WalletKit you can login by email + code or anonymously, with Firebase 
you can login via third-parties, with Supabase via email and password. All the clients provide 
session persistency cross-sessions out of the box.

- WalletKit
```kotlin
val loginClient = WalletKitLoginClient(
    projectId = "my_project_id",
    baseUrl = "https://testnet.usewalletkit.com", // or mainnet
    context = context,
)
val response = loginClient.loginAnonymously()
if (response.isSuccessful) {
    // do something
} else {
    // handle error
}
```

- Firebase
```kotlin
val auth: FirebaseAuth = Firebase.auth
auth.signInWithEmailAndPassword(email, password)
    .addOnCompleteListener(Dispatchers.IO.asExecutor()) { task ->
        if (task.isSuccessful) {
            // do something
        } else {
            // handle error
        }
    }
```

Refer to the official [Firebase documentation](https://firebase.google.com/docs/auth/android/start).

- Supabase
```kotlin
val loginClient = createSupabaseClient(
    supabaseUrl = projectUrl,
    supabaseKey = apiKey
) {
    install(GoTrue)
}.gotrue

loginClient.loginWith(Email) {
    this.email = email
    this.password = password
}

if (loginClient.currentSessionOrNull() != null) {
    // do something
} else {
    // handle error
}
```
Refer to the official [Supabase documentation](https://supabase.com/docs/reference/kotlin/auth-api).

### Initialize WalletKitClient
Once logged in you can create a `WalletKitClient`. Technically, it can be created before a 
successful login, as far as you make sure no api call are performed before a successful login. The 
client will take the login client created in the previous step.

- WalletKit auth
```kotlin
val walletKitClient = WalletKitClient(
    loginClient = loginClient,
)
```
NOTE: we are not passing projectId or baseUrl wich is derived from the `loginClient`.

- Firebase auth
```kotlin
private val walletKitClient = WalletKitClient(
    firebaseAuth = auth,
    projectId = "my_project_id",
    baseUrl = "https://testnet.usewalletkit.com", // or mainnet
)
```

- Supabase auth
```kotlin
private val walletKitClient = WalletKitClient(
    loginClient = loginClient,
    projectId = "my_project_id",
    baseUrl = "https://testnet.usewalletkit.com", // or mainnet
)
```

### Use WalletKitClient for API requests
At this point `WalletKitClient` can be used for API requests independently from the login client 
used. Given that the session persistency is provided under the hood, the login client should not be 
used anymore, unless the long living session is expired.

```kotlin
val response = walletKitClient.getService(WalletsApi::class.java)
    .walletsList()
if (response.isSuccessful && response.body() != null) {
    val wallets = response.body()
    // show wallets or store them in the DB

} else {
    // handle error

}
```

All the services and endpoints can be found in the SDK package 
`com.usewalletkit.sdk.generated.apis`.

## Contributing
### OpenApi Generator
Api classes and models are auto-generated with openapi-generator. To update the schema, run `
./gradlew openApiGenerate`. This will update the files under 
`src/main/kotlin/com/usewalletkit/sdk/openapi/`. The openapi spec url is defined in 
`sdk/gradle.properties` and can be changed there. To run the generator with a temporary url without 
changing the property, it is possible to run 
`./gradlew openApiGenerate -PopenApiSpecUrl=<your_url>`.
 
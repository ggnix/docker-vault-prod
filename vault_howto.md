### Dev mode

Since vault in dev mode stores data in-memory every restart would clear all your secrets
 

Install Vault server * [link](https://www.vaultproject.io/intro/getting-started/install.html)

#### Start server in dev mode:
```
   vault server -dev
```
#### Server will start in foreground, export VAULT_ADDR variable:

```
   export VAULT_ADDR='http://127.0.0.1:8200'
```

By default, Vault mounts a backend called generic to secret/. The generic backend reads and writes raw data to the backend storage.

You can inspect mounts using vault mounts:

```
   vault mounts
   Path      Type     Description
   generic/  generic
   secret/   generic  generic secret storage
   sys/      system   system endpoints used for control, policy and debugging
```

#### Write your secret:

``` 
   vault write secret/hello value=world
   Success! Data written to: secret/hello
```

This writes the pair value=world to the path secret/hello. The secret/ prefix is where arbitrary secrets can be read and written

#### Read your secret:

```
   vault read secret/hello

   Key                 Value
   ---                 -----
   refresh_interval    768h0m0s
   excited             yes
   value               world
```

#### To delete your secret use the following command:

```
   vault delete secret/hello
   Success! Deleted 'secret/hello' if it existed.
```

#### AWS credentials generation

The AWS secret backend for Vault generates AWS access credentials dynamically based on IAM policies. This makes IAM much easier to use: credentials could be generated on the fly.
The first step to using the aws backend is to mount it. Unlike the generic backend, the aws backend is not mounted by default.

```
   vault mount aws
   Successfully mounted 'aws' at 'aws'!
```

Next, we must configure the root credentials that are used to manage IAM credentials:

```
    vault write aws/config/root \
    access_key=AKIAJWVN5Z4FOFT7NLNA \
    secret_key=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 \
    region=us-east-1
```

Let's create ec2 readonly role using existing aws policy:

```
    vault write aws/roles/readonly arn=arn:aws:iam::aws:policy/AmazonEC2ReadOnlyAccess
```

To generate a new set of IAM credentials, we simply read from that role

```
    vault read aws/creds/readonly
    Key             Value
    lease_id        aws/creds/readonly/7cb8df71-782f-3de1-79dd-251778e49f58
    lease_duration  3600
    access_key      AKIAIOMYUTSLGJOGLHTQ
    secret_key      BK9++oBABaBvRKcT5KEF69xQGcH7ZpPRF3oqVEv7
    security_token  <nil>
``` 

### Prod mode

#### Vault is configured using HCL files. Configuration file (config.hcl) example:

```
   backend "file" {
   path = "/opt/vault/filetest"
   }

   listener "tcp" {
   address = "127.0.0.1:8200"
   tls_disable = 1
   }
   
   storage "s3" {
   access_key = "abcd1234"
   secret_key = "defg5678"
   bucket     = "my-bucket"
   }
   disable_mlock = true
```

#### Start vault server:

```
   vault server -config=config.hcl
```

#### Initialization is the first step of Vault configuration. During initialization, the encryption keys are generated, unseal keys are created, and the initial root token is setup. To initialize Vault:

```
   vault init -key-shares=1 -key-threshold=1

   Key 1: 427cd2c310be3b84fe69372e683a790e01
   Initial Root Token: eaf5cc32-b48f-7785-5c94-90b5ce300e9
```

#### Save the unseal key and root token. Unseal Vault with the key provided earlier:

```
   vault unseal 427cd2c310be3b84fe69372e683a790e01
```

#### Last step of configuration is to authenticate with root token:

```
   vault auth eaf5cc32-b48f-7785-5c94-90b5ce300e9
```

Now you can write and read your secrets just as you did for dev mode.
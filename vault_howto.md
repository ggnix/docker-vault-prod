<h3>Dev mode</h3>

Since vault in dev mode stores data in-memory every restart would clear all your secrets
 

Install Vault server https://www.vaultproject.io/intro/getting-started/install.html

<h4>Start server in dev mode:</h4>

   vault server -dev

<h4>Server will start in foreground, export VAULT_ADDR variable:</h4>

   export VAULT_ADDR='http://127.0.0.1:8200'

<h4>Write your secret:</h4>
 
   vault write secret/hello value=world
   Success! Data written to: secret/hello

This writes the pair value=world to the path secret/hello. The secret/ prefix is where arbitrary secrets can be read and written

<h4>Read your secret:</h4>

   vault read secret/hello

   Key                 Value
   ---                 -----
   refresh_interval    768h0m0s
   excited             yes
   value               world

<h4>To delete your secret use the following command:</h4>

   vault delete secret/hello
   Success! Deleted 'secret/hello' if it existed.

<h3>Prod mode</h3>

<h4>Vault is configured using HCL files. Configuration file (config.hcl) example:</h4>

   backend "consul" {
   address = "127.0.0.1:8500"
   path = "vault"
   }

   listener "tcp" {
   address = "127.0.0.1:8200"
   tls_disable = 1
   }
   disable_mlock = true

<h4>Start vault server:</h4>

   vault server -config=config.hcl

<h4>Initialization is the first step of Vault configuration. During initialization, the encryption keys are generated, unseal keys are created, and the initial root token is setup. To initialize Vault:</h4>

   vault init -key-shares=1 -key-threshold=1

   Key 1: 427cd2c310be3b84fe69372e683a790e01
   Initial Root Token: eaf5cc32-b48f-7785-5c94-90b5ce300e9

<h4>Save the unseal key and root token. Unseal Vault with the key provided earlier:</h4>

   vault unseal 427cd2c310be3b84fe69372e683a790e01

<h4>Last step of configuration is to authenticate with root token:</h4>

   vault auth eaf5cc32-b48f-7785-5c94-90b5ce300e9

<h4>Now you can write and read your secrets just as you did for dev mode.</h4>
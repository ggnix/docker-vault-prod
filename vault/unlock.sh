#!/bin/sh
echo "----------------------Initialize Vault save the token and keys----------------------"
vault init 2>&1 | egrep '^Unseal Key|Initial Root Token' >/vault/keys.txt
echo "----------------------Unseal Vault---------------------"
egrep -m3 '^Unseal Key' /vault/keys.txt | cut -f2- -d: | tr -d ' ' |

while read key 
do
vault unseal ${key}
done
echo "----------------------Authenticate to Vault----------------------"
export VAULT_TOKEN=$(egrep '^Initial Root Token:' /vault/keys.txt | cut -f2- -d: | tr -d ' ') && vault auth ${VAULT_TOKEN} 

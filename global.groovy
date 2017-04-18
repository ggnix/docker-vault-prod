import jenkins.model.*

jenkins = jenkins.model.Jenkins.getInstance()
vault = jenkins.getDescriptorByType(com.datapipe.jenkins.vault.VaultBuildWrapper.DescriptorImpl)
vault.vaultUrl = "http://172.17.0.3:8200"
vault.authToken = ""
vault.save()

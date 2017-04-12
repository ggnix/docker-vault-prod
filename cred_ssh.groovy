import jenkins.model.*
import hudson.model.FreeStyleProject;
import com.cloudbees.plugins.credentials.domains.Domain;
import com.cloudbees.plugins.credentials.CredentialsScope;
import com.cloudbees.plugins.credentials.SystemCredentialsProvider;
import com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey;
import com.cloudbees.jenkins.plugins.awscredentials.*;
import javaposse.jobdsl.plugin.*;
import hudson.tools.InstallSourceProperty

privateKey = new BasicSSHUserPrivateKey(CredentialsScope.GLOBAL,"jenkins-key","jenkins",new BasicSSHUserPrivateKey.UsersPrivateKeySource(),"","jenkins ssh key")
credentialsStore = Jenkins.instance.getExtensionList(com.cloudbees.plugins.credentials.SystemCredentialsProvider.class)[0];
credentialsStore.store.addCredentials(Domain.global(), privateKey);

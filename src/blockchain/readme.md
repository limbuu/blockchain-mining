# codehub/kubernetes
  
[Kubernetes](https://kubernetes.io/) is a portable, extensible open-source platform for managing 
containerized workloads and services,that facilitates both declarative configuration and automation.

`Codehub` is deployed in well configured kubernetes environment in `Google Kubernetes Engine(GKE)`. 

# Storage 
[Persistent Volume(PV)](https://kubernetes.io/docs/concepts/storage/persistent-volumes/) 
and [Persistent Volume Claim(PVC)](https://kubernetes.io/docs/concepts/storage/persistent-volumes/) 
are used for storage in kubernetes. Using `Nfs server` codehub users data volumes are stored in GCE/GKE's
persistent disks.  

    kubectl apply -f nfs-pvc.yaml
    kubectl apply -f nfs-deployment-service.yaml
          
 Add the NFS server's service cluster IP in Persistent Volume server path and create PV and PVC for the codehub.  
          
    kubectl get svc     
          
  ```Deployemntfile
  nfs:
    server: 10.23.248.169
    path: "/"
  ```
  
    kubectl apply -f jupyterhub-nfs-pv-pvc.yaml
          
# Codehub Deployement 
Deploy codehub with rbac roles enabled. 
     
    kubectl apply -f jupyterhub-deployment.yaml

# Nginx-Ingress Controller

In Kubernetes, Ingress allows external users and client applications access to HTTP services. Ingress consists of two components. Ingress Resource is a collection of rules for the inbound traffic to reach Services. These are Layer 7 (L7) rules that allow hostnames (and optionally paths) to be directed to specific Services in Kubernetes. The second component is the Ingress Controller which acts upon the rules set by the Ingress Resource, typically via an HTTP or L7 load balancer. It is vital that both pieces are properly configured to route traffic from an outside client to a Kubernetes Service.

## Deploy NGINX Ingress Controller via Helm
Helm has two parts: a client (helm) and a server (tiller).Tiller runs inside of your Kubernetes cluster, and manages releases (installations) of your helm charts. Helm runs on your laptop, CI/CD, or in our case, the Cloud Shell.

    curl -o get_helm.sh https://raw.githubusercontent.com/kubernetes/helm/master/scripts/get
    chmod +x get_helm.sh
    ./get_helm.sh

Install Tiller with RBAC enabled, 

   kubectl create serviceaccount --namespace kube-system tiller
   kubectl create clusterrolebinding tiller-cluster-rule --clusterrole=cluster-admin --serviceaccount=kube-system:tiller
   kubectl patch deploy --namespace kube-system tiller-deploy -p '{"spec":{"template":{"spec":{"serviceAccount":"tiller"}}}}'      
   helm init --service-account tiller --upgrade
   
Install stable nginx-ingress with RBAC enabled,

   helm install --name nginx-ingress stable/nginx-ingress --set rbac.create=true

Now, apply ingress-resource that contains configuration nginx-ingress controller 
    
    kubectl apply -f ingress-resource.yaml 

# SSL Cerficate
To enable inbound traffic through HTTP and HTTPs, tls secret is used along with annotations.

Create secret from the ssl-certificates 
  
    kubectl create scecret tls tls-certificate --key <server>.key --cert <certificate>.crt 
 
Add the secret with the host domain name to ingress-resource.yaml file. Also, add the annotation ingress.kubernetes.io/ssl-redirect: “true” to the ingress.yaml.

    kubectl apply -f ingress-resource.yaml
 

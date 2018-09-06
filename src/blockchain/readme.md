# Codehub/Jupyterhub

Codehub is modified version of JupyterHub for simplified and scalable Machine Learning Development. 
With codehub you can create a multi-user Hub which spawns, manages, and proxies multiple instances of
the single-user Jupyter notebook server. As, Codehub is build on top of the jupyterhub, numereous advanced 
modifications are made on it.  

## Jupyterhub using Docker

Stable version of Jupyterhub(0.9.1) is installed from [docker hub](https://hub.docker.com/r/jupyterhub/jupyterhub/~/dockerfile/)
where all necessary prerequisites are already installed.
* Linux (Ubuntu)
* Python 3.5 or greater
* nodejs/npm
* container port exposed on 8000

On top of this jupyterhub, further oauthenticator, kubespawner, cull_idle are added with the modified static files and templates.  

## Oauthenticator 
OAuth + JupyterHub Authenticator = OAuthenticator
Jupyterhub supports numerous types of authentication services of which we use [Auth0 OAuthenticator]
(https://github.com/jupyterhub/oauthenticator/blob/master/oauthenticator/auth0.py)

Install with pip3, 

      python3 -m pip install oauthenticator
      
Or can clone the repo and do a dev install:

      git clone https://github.com/jupyterhub/oauthenticator.git
      cd oauthenticator
      pip3 install -e .

For advanced changes, you need to modify the Auth0OAuthenticator class. For normal setup, in jupyterhub_config.py add: 
      
      c.JupyterHub.authenticator_class = 'oauthenticator.auth0.Auth0OAuthenticator'

To Set callback URL, client ID, and client secret,one can also simply set in the jupyterhub_config.py as:
      
      c.MyOAuthenticator.oauth_callback_url = 'http[s]://[your-host]/hub/oauth_callback'
      c.MyOAuthenticator.client_id = 'your-client-id'
      c.MyOAuthenticator.client_secret = 'your-client-secret'
 
Meanwhile, we have passed these configuration as envs through codehub-deployemnt yaml file.

``` Deployment
    
    spec:
      containers:
      - env:
        - name: AUTH0_SUBDOMAIN
          value: <Auth0-Subdomain>
        - name: OAUTH_CALLBACK_URL
          value: "http[s]://[your-host]/hub/oauth_callback"
        - name: OAUTH_CLIENT_ID
          value: <Auth0-Client-Id>
        - name: OAUTH_CLIENT_SECRET
          value: <Auth0-Client-Secret>

``` 
## Kubespawner 
The kubespawner (also known as JupyterHub Kubernetes Spawner) enables JupyterHub to spawn single-user notebook
servers on a Kubernetes cluster. To know more about the kubespawner click [here.]
(https://jupyterhub-kubespawner.readthedocs.io/en/latest/spawner.html#module-kubespawner.spawner)

Install from the git : 

      git clone https://github.com/jupyterhub/kubespawner.git
      cd kubespawner && python setup.py install

## Cull_idle server
The `cull_idle_servers.py` file provides a script to cull and shut down idle
single-user notebook servers. This script is used when `cull-idle` is run as
a Service or when it is run manually as a standalone script.


### Configure `cull-idle` to run as a Hub-Managed Service

In `jupyterhub_config.py`, add the following dictionary for the `cull-idle`
Service to the `c.JupyterHub.services` list:

```python
c.JupyterHub.services = [
    {
        'name': 'cull-idle',
        'admin': True,
        'command': 'python3 /optimization/cull_idle_servers.py --timeout=3600'.split(),
    }
]
```

where:

- `'admin': True` indicates that the Service has 'admin' permissions, and
- `'command'` indicates that the Service will be managed by the Hub.
  

## Templates and Static files 
Custom design are added to codehub through the static files and templates.

      ADD ./templates /opt/conda/share/jupyterhub/templates
      ADD ./static /opt/conda/share/jupyterhub/static


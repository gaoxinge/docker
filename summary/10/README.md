# k8s

- dim1
  - node
  - pod
  - app
- dim2
  - service

## Create a Kubernetes cluster

```
> minikube version
> minikube start

> hostname
> kubectl version
> kubectl get nodes
> kubectl get nodes/minikube
> kubectl describe nodes
> kubectl describe nodes/minikube
> kubectl cluster-info
```

## Deploy an app

```
> kubectl proxy
> curl http://localhost:8001/version

> kubectl run kubernetes-bootcamp --image=gcr.io/google-samples/kubernetes-bootcamp:v1
> kubectl get deployments
> kubectl get deployments/kubernetes-bootcamp
> kubectl describe deployments
> kubectl describe deployments/kubernetes-bootcamp

> export POD_NAME=$(kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}}{{"\n"}}{{end}}')
> echo $POD_NAME
```

## Explore your app

```
> kubectl get pods
> kubectl get pods/$POD_NAME
> kubectl describe pods
> kubectl describe pods/$POD_NAME
> curl http://localhost:8001/api/v1/namespaces/default/pods/$POD_NAME/proxy/

> kubectl logs $POD_NAME
> kubectl exec $POD_NAME env
> kubectl exec -it $POD_NAME bash
$ cat server.js
$ curl localhost:8080
$ exit
```

## Expose your app publicly

```
> kubectl expose deployment/kubernetes-bootcamp --type="NodePort" --port 8080
> kubectl get services
> kubectl get services/kubernetes-bootcamp
> kubectl describe services
> kubectl describe services/kubernetes-bootcamp

> export NODE_PORT=$(kubectl get services/kubernetes-bootcamp -ogo-template='{{(index .spec.ports 0).nodePort}}')
> echo $(minikube ip)
> echo $NODE_PORT
> curl $(minikube ip):$NODE_PORT

> kubectl describe deployment kubernetes-bootcamp  # Labels
> kubectl describe pods $POD_NAME                  # Labels
> kubectl describe services kubernetes-bootcamp    # Labels
> kubectl get deployments -l run=kubernetes-bootcamp
> kubectl get pods -l run=kubernetes-bootcamp
> kubectl get services -l run=kubernetes-bootcamp

> kubectl label pod $POD_NAME app=v1

> kubectl delete service -l run=kubernetes-bootcamp
```
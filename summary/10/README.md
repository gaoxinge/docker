# k8s

- dim1
  - node
  - pod
  - app
- dim2
  - service

## Creating a Cluster

```
> minikube version
> minikube start

> hostname
> kubectl get nodes
> kubectl cluster-info
```

## Deploy an app

```
> kubectl run kubernetes-bootcamp --image=gcr.io/google-samples/kubernetes-bootcamp:v1 --port=8080
> kubectl get deployments
```

## Explore your app

```
> kubectl get pods
> kubectl describe pods
> kubectl logs kubernetes-bootcamp-5b48cfdcbd-vnqhn
> kubectl exec kubernetes-bootcamp-5b48cfdcbd-vnqhn env
> kubectl exec -it kubernetes-bootcamp-5b48cfdcbd-vnqhn bash
$ cat server.js
$ curl localhost:8080
$ exit
```
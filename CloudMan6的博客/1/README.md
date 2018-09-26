## hypervisor

- [Hypervisor](https://en.wikipedia.org/wiki/Hypervisor)
- type1
  - microsoft Hyper-v
  - VMware ESX/ESXi.
- type2
  - VMware Workstation
  - VMware Player
  - VirtualBox
  
## VM type1

```
-----------------------
| App                 |
-----------------------
| Bins/Libs           |
-----------------------
| Host OS             |
-----------------------
| Hypervisor (Type 1) |
-----------------------
| Server              |
-----------------------
```

## VM type2

```
-----------------------
| App                 |
-----------------------
| Bins/Libs           |
-----------------------
| Guest OS            |
-----------------------
| Hypervisor (Type 2) |
-----------------------
| Host OS             |
-----------------------
| Server              |
-----------------------
```

## docker windows (hyper-v)

```
--------------------------------
| App      | App      | Docker |
--------------------------------
| Bins/Libs| Bin/Libs | Docker |
--------------------------------
| Windows  | Linux    |
-----------------------
| Hyper-v             |
-----------------------
| Server              |
-----------------------
```


## docker windows (virtual box)

```
--------------------------------
| App                 | Docker |
--------------------------------
| Bins/Libs           | Docker |
--------------------------------
| Linux               |
-----------------------
| Virtual Box         |
-----------------------
| Windows             |
-----------------------
| Server              |
-----------------------
```

## docker linux

```
--------------------------------
| App                 | Docker |
--------------------------------
| Bins/Libs           | Docker |
--------------------------------
| Linux               |
-----------------------
| Server              |
-----------------------
```
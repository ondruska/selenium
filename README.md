# Deploy remote driver from container

```shell
podman run -d -p 4444:4444 -p 7900:7900 --name selenium docker.io/selenium/standalone-firefox:latest
```

Point remote driver to 4444.

Open http://localhost:7900 and use "secret" as vnc password.

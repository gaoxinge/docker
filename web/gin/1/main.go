package main

import (
    "net/http"
    "github.com/gin-gonic/gin"
)

func main() {

    router := gin.Default()

    router.GET("/hello/:name", func(c *gin.Context) {
        name := c.Param("name")
        c.String(http.StatusOK, "hello %s", name)
    })
    router.Run(":7070")
}
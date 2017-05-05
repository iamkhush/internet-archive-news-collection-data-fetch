// run the program with conn. string like "user=iamkhush password=iamkhush dbname=dbname"
package main

import (
        s "strings"
        "os"
        "log"
        "fmt"
        "net/http"
        "database/sql"
        _ "github.com/lib/pq"
        "html/template"
)


func handler(w http.ResponseWriter, r *http.Request) {
    if r.Method == "GET" {
        t, _ := template.ParseFiles("index.gtpl")
        t.Execute(w, nil)
    }
}

func Fetchhandler(w http.ResponseWriter, r *http.Request){
    if r.Method == "GET" {
        key := r.FormValue("key")
        fmt.Fprintf(w, "%s", key)
    }
}

func main() {
    argsWithoutProg := os.Args[1:]
    fmt.Println(argsWithoutProg)
    db, err := sql.Open("postgres", s.Join(argsWithoutProg,""))
    if  err!= nil {
       log.Fatal(err)
    }
    defer db.Close()

    http.HandleFunc("/", handler)
    http.HandleFunc("/fetch", Fetchhandler)
    http.ListenAndServe(":8081", nil)
}

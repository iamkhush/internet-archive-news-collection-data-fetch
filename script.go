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
        "encoding/json"
)

var db *sql.DB

type HostData struct {
    Host string
    Url int
    Mindate string
    Maxdate string
}

func handler(w http.ResponseWriter, r *http.Request) {
    if r.Method == "GET" {
        t, _ := template.ParseFiles("index.gtpl")
        t.Execute(w, nil)
    }
}

func Fetchhandler(w http.ResponseWriter, r *http.Request){
    var (
            urls string
            mindate string
            maxdate string
    )

    if r.Method == "GET" {
        key := r.FormValue("key")
        fmt.Println(key)
        err := db.QueryRow("select urls, mindate, maxdate from hosts where host=$1", key).Scan(&urls, &mindate, &maxdate)
        switch {
            case err == sql.ErrNoRows:
                http.Error(w, err.Error(), http.StatusInternalServerError)
                return
            case err != nil:
                log.Fatal(err)
        }

        fmt.Println(urls, mindate, maxdate, len(s.Split(urls, ";")))

        thisHost := &HostData{
            Host: key, 
            Url:  len(s.Split(urls, ";")),
            Mindate: mindate,
            Maxdate: maxdate,
        }

        js, err := json.Marshal(thisHost)
        if err != nil {
            http.Error(w, err.Error(), http.StatusInternalServerError)
            return
        }
        fmt.Println(string(js))
        w.Header().Set("Content-Type", "application/json")
        w.Write(js)
    }
}

func main() {
    argsWithoutProg := os.Args[1:]
    fmt.Println(argsWithoutProg)
    var err error
    db, err = sql.Open("postgres", s.Join(argsWithoutProg," "))
    if  err!= nil {
       log.Fatal(err)
    }
    defer db.Close()

    http.HandleFunc("/", handler)
    http.HandleFunc("/fetch", Fetchhandler)
    http.ListenAndServe(":8081", nil)
}

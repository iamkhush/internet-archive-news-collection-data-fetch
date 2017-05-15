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
        "strconv"
        humanize "github.com/dustin/go-humanize"
)

var db *sql.DB

type HostData struct {
    Host string
    Url int
    Mindate string
    Maxdate string
    UrlCount []map[string]string
}

func handler(w http.ResponseWriter, r *http.Request) {
    var rowcount uint64
    err := db.QueryRow("SELECT reltuples::bigint AS estimate FROM pg_class where relname='hosts'").Scan(&rowcount)
    if err != nil {
        log.Fatal(err)
    }
    t, _ := template.ParseFiles("index.gtpl")
    t.Execute(w, humanize.Comma(rowcount))
}

func checkOrAppend(item string, list []map[string]string) []map[string]string {
    isPresent := false
    for _, b := range list {
        if b["key"] == item {
            count, _ := strconv.Atoi(b["count"])
            b["count"] = strconv.Itoa(count + 1)
            isPresent = true
        }
    }
    if (isPresent == false) {
        host := map[string]string{}
        host["key"] = item
        host["count"] = "1"
        list = append(list, host)
    }

    return list
}


func fetchHandler(w http.ResponseWriter, r *http.Request){
    var (
            urls string
            mindate string
            maxdate string
    )

    if r.Method == "GET" {
        key := r.FormValue("key")
        err := db.QueryRow("select urls, mindate, maxdate from hosts where host=$1", key).Scan(&urls, &mindate, &maxdate)
        switch {
            case err == sql.ErrNoRows:
                http.Error(w, err.Error(), http.StatusInternalServerError)
                return
            case err != nil:
                log.Fatal(err)
        }

        extns := []map[string]string{}

        // Aggregating counts according to extension
        for _, url := range s.Split(urls, ";") {
            extn := s.Split(url, ".")[len(s.Split(url, "."))-1]
            // check extn length
            if len(extn) < 4 {
                extns = checkOrAppend(extn, extns)
            } else {
                // probably not extention
                extns = checkOrAppend("html", extns)
                fmt.Println(extn)
            }
        }

        fmt.Println(extns)

        thisHost := &HostData{
            Host: key, 
            Url:  len(s.Split(urls, ";")),
            Mindate: mindate,
            Maxdate: maxdate,
            UrlCount: extns,
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
       fmt.Println(err)
       log.Fatal(err)
    }
    defer db.Close()

    http.HandleFunc("/", handler)
    http.HandleFunc("/fetch", fetchHandler)
    fs := http.FileServer(http.Dir("dist"))
    http.Handle("/dist/", http.StripPrefix("/dist/", fs))
    http.ListenAndServe(":8081", nil)
}

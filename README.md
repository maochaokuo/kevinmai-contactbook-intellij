# kevinmai-contactbook-intellij

## [1] Reitit Ring Set Up
step 1
```clojure
clj -A:run
```
will show hello world

step 2
- make default rest api, port 4000
- again, clj -A:run, see Server Started
  to confirm
- use Insomia or postman, to test get
  http://localhost:4000/api
- will get "ok"

step3
- add atom server
- add stop-server
- in vscode, Ctrl-Alt-c Ctrl-Alt-j, choose deps.edn,
  then no option, will enter REPL, wait until Jack-in
  done
  
step4
- change everything to IntelliJ Leiningen project

step5
- 可以在(comment)做http client test, ex: 
```clojure
(app {:request-method :get :uri "/api"})
```
=> {:status 200, :body {:hello "world"}}

step6
add more routing, test:
```clojure
(app {:request-method :get :uri "/api/"})
```
=> {:status 301, :headers {"Location" "/api"}, :body ""}

test
```clojure
(app {:request-method :get :uri "/api2"})
```
=> {:status 404, :body "Route not found"}

## [2] HugSQL Configuration (change to HoneySql)
step1
- update project.clj
- add docker-compose.yml
- add db.clj
- add resources/contacts.sql

### make up docker for postgresql
when docker-compose.yml added, run in terminal:
```shell
docker-compose up -d
```

then after db.clj and resources/contacts.sql created, run, in this tutorial
```shell
docker exec -it clj_contacts_db psql -U postgres
```

if something wrong then make up the followings
```shell
docker pull postgres
docker run postgres
#or
docker run -e POSTGRES_PASSWORD=postgres postgres
```

step2
- cannot continue with HugSQL, replaced by HoneySql
- 初步建table與select query ok
- insert ok
- insert version2

step3
- query by id ok
- update ok

step4
- api update
- delete ok

## [3] API Endpoints
step1
- type in all the source code

## open issues
1. may need to go through HugSQL again
2. insert parameters, make consistent with the original, and figure it
   out
3. 浪費這麼久的結論，得放棄HugSQL, 放棄ring/router, 改用HoneySQL與compojure   
   
### post request to string 
```clojure
(POST "/agents" request
  (let [body (json/read-str (slurp (:body request)))]
    (println body)))
```

## change log

### 2021/9/8
- 這裡的parameters同post中的body, 所以先解決怎麼拆分內容，或直接丟給insert
- 所以跟clojure-rest專案不同，這裡body會變成
  org.httpkit.BytesInputStream，無法解析

### 2021/9/2
- 終於可以呼叫到資料庫異動了，取欄位怪怪的，再研究。有進步了。ya!
  可以繼續kevin mai的冗長的範例了

### 2021/8/26
- 浪費這麼久的結論，得放棄HugSQL, 放棄ring/router, 改用HoneySQL與compojure

### 2021/8/9
以下不錯試試，看來露出一線曙光

[https://tutswiki.com/rest-api-in-clojure/](https://tutswiki.com/rest-api-in-clojure/)
[https://github.com/metosin/compojure-api](https://github.com/metosin/compojure-api)
[https://www.codementor.io/@tamizhvendan/developing-restful-apis-in-clojure-using-compojure-api-and-toucan-part-1-oc6yzsigc](https://www.codementor.io/@tamizhvendan/developing-restful-apis-in-clojure-using-compojure-api-and-toucan-part-1-oc6yzsigc)
[https://stackoverflow.com/questions/49471912/ring-and-compojure-post-requests-with-content-type-application-json-does-not-w](https://stackoverflow.com/questions/49471912/ring-and-compojure-post-requests-with-content-type-application-json-does-not-w)
[https://www.demystifyfp.com/clojure/blog/restful-crud-apis-using-compojure-api-and-toucan-part-1/](https://www.demystifyfp.com/clojure/blog/restful-crud-apis-using-compojure-api-and-toucan-part-1/)
[https://www.demystifyfp.com/clojure/blog/restful-crud-apis-using-compojure-api-and-toucan-part-2/](https://www.demystifyfp.com/clojure/blog/restful-crud-apis-using-compojure-api-and-toucan-part-2/)

### 2021/8/1
- api update
- delete ok

[3] API Endpoints

- type in all the source code  
### 2021/7/31
- insert version 2
- query by id ok

### 2021/7/30
- HoneySql insert ok

### 2021/7/28

[2] HugSQL replaced by HoneySql

初步建table與select query ok

### 2021/7/14
- done with tutorial [1] Reitit Ring Set Up

start [2] HugSQL Configuration

- error happened
```clojure
  (create-contacts-table config)
```

### 2021/7/13
- add more routing, but cannot compile, try find the source code
- type a lot of code, to test

### 2021/7/12
- reset! works now, may activate web by (-main)
- update to Reitit Ring Set up 06:08

### 2021/7/11
- make default rest api

### 2021/7/10
- initial commit
- show Hello World

#
#
#
#
#
#
#
#
# kevinmai-contactbook-intellij

A Clojure library designed to ... well, that part is up to you.

## Usage

FIXME

## License

Copyright © 2021 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
"# kevinmai-contactbook-intellij" 

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

step2
- cannot continue with HugSQL, replaced by HoneySql
- 初步建table與select query ok
- insert ok

## change log

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

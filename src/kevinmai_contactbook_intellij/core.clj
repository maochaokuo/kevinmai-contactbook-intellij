;(ns contacts.core)

(ns kevinmai-contactbook-intellij.core
  (:require [org.httpkit.server :refer [run-server]]
            [reitit.ring :as ring]))

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)
    ;(println "Server stopped")
    ))

(def app
  (ring/ring-handler
    (ring/router
      [["/api" {:get (fn [req]
                       {:status 200
                        :body   "ok"})}]])))

(defn -main []
  (println "Server started")
  (reset! server (run-server app {:port 4004})))

;(defn restart-server []
;  (stop-server)
;  (-main))

(comment
  (-main)
  )
;
;(defn foo
;  "I don't do a whole lot."
;  [x]
;  (println x "Hello, World!"))

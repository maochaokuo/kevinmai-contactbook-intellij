;(ns contacts.core)

(ns kevinmai-contactbook-intellij.core
  (:require [org.httpkit.server :refer [run-server]]
            [reitit.ring :as ring]))

(defonce server (atom nil))

(def app
  (ring/ring-handler
    (ring/router
      [["/api" {:get (fn [req]
                       {:status 200
                        :body   {:hello "world"}})}]])
    (ring/routes
      (ring/redirect-trailing-slash-handler)
      (ring/create-default-handler
        {:not-found (constantly {:status 404
                                 :body   "Route not found"})})
      )))

(defn -main []
  (println "Server started")
  (reset! server (run-server app {:port 4004})))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)
    ;(println "Server stopped")
    ))

(defn restart-server []
  (stop-server)
  (-main))

(comment
  (restart-server)
  (stop-server)
  @server
  (-main)
  (app {:request-method :get
        :uri            "/api/"})
  )

;
;(defn foo
;  "I don't do a whole lot."
;  [x]
;  (println x "Hello, World!"))

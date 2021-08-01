(ns contacts.core
  (:require [org.httpkit.server :refer [run-server]]
            [reitit.ring :as ring]
            [reitit.ring.middleware.exception :refer [exception-middleware]]
            [reitit.ring.middleware.parameters :refer [parameters-middleware]]
            [reitit.ring.middleware.muuntaja :refer [format-negotiate-middleware
                                                     format-request-middleware
                                                     format-response-middleware]]
            [reitit.ring.coercion :refer [coerce-exceptions-middleware
                                          coerce-request-middleware
                                          coerce-response-middleware]]
            [reitit.coercion.schema]
            [muuntaja.core :as m]
            [kevinmai-contactbook-intellij.db :as db]
            [kevinmai-contactbook-intellij.routes :refer [ping-routes contact-routes]]))

(defonce server (atom nil))

(def app
  (ring/ring-handler
    (ring/router
      [["/api"
        ping-routes
        contact-routes]]
      {:data {:coercion reitit.coercion.schema/coercion
              :muutaja    m/instance
              :middleware [parameters-middleware
                           format-negotiate-middleware
                           format-response-middleware
                           exception-middleware
                           format-request-middleware
                           coerce-exceptions-middleware
                           coerce-request-middleware
                           coerce-response-middleware]}})
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
        :uri "/api/ping"})
  (app {:request-method :get
        :uri "/api/contacts"})
  )

;
;(defn foo
;  "I don't do a whole lot."
;  [x]
;  (println x "Hello, World!"))

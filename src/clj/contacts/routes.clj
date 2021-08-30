(ns contacts.routes
  (:require [compojure.core :refer [defroutes GET POST PUT DELETE context]]
            [compojure.route :as route]
            [schema.core :as s]
            [contacts.contacts :refer [get-contacts
                                       create-contact
                                       get-contact-by-id
                                       update-contact
                                       delete-contact]]
            [cheshire.core :as json]
            ))

(defn request-body->map
  [request]
  (-> request
      ;:body
      slurp
      (json/parse-string true)))
;;(json/parse-string (slurp (:body request)) true)

(def ping-routes
  (GET "/ping" []
       {:status 200
        ;:headers {"Content-Type" "application/json"}
        :body {:ping "pong"}}))

;(def ping-routes
;  ["/ping" {:get (fn [req]
;                   {:status 200
;                    :body   {:ping "pong"}})}])

(defn dummy [req]
  {:status 200
   :body {:ping "pong"}})

(def contact-routes
  (context "/contacts" [] (defroutes contacts-routes
   (GET "/" [] get-contacts)
   (POST "/" request
         ;[{:keys [request]}]
         (let [body (:body request)]
           ;[bodyS (request-body->map body)]
           (println (request-body->map body))
           ;(create-contact body)
           {:status 200
            :headers {"Content-Type" "application/json"}
            :body {:post "received"}
            ;(json/encode {:json true?
            ;                    :response
            ;                        ;(request-body->map body)
            ;                        body
            ;                    })
            }))
   (context "/:id" [id] (defroutes contact-routes
     (GET "/" [] get-contact-by-id id)
     (PUT "/" {body :body} update-contact id body)
     (DELETE "/" [] (delete-contact id))
     ))
   ;(GET "/:id" [id] dummy)
 )))

;(def contact-routes
;  ["/contacts"
;   ["/" {:get get-contacts
;         :post {:parameters {:body {:first-name s/Str
;                                     :last-name s/Str
;                                     :email s/Str}}
;                :handler create-contact}}]
;   ["/:id" {:parameters {:path {:id s/Int}}
;            :get dummy
;            :put {:parameters {:body {:first-name s/Str
;                                      :last-name s/Str
;                                      :email s/Str}}
;                  :handler update-contact}
;            :delete delete-contact}]
;  ])

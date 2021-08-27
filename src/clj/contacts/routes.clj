(ns contacts.routes
  (:require [schema.core :as s]
    [contacts.contacts :refer [get-contacts
                               create-contact
                               get-contact-by-id
                               update-contact
                               delete-contact]]))

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
  (context "/contacts" []
           (GET "/" [] get-contacts)
           (POST "/" request
                 (let [response (:request (request-body->map request))]
                   (println response)
                   (create-contact request)
                   {:status 200
                    :headers {"Content-Type" "application/json"}
                    :body (json/encode {:json true?
                                        :response response})}))))

(def contact-routes
  ["/contacts"
   ["/" {:get get-contacts
         :post {:parameters {:body {:first-name s/Str
                                     :last-name s/Str
                                     :email s/Str}}
                :handler create-contact}}]
   ["/:id" {:parameters {:path {:id s/Int}}
            :get dummy
            :put {:parameters {:body {:first-name s/Str
                                      :last-name s/Str
                                      :email s/Str}}
                  :handler update-contact}
            :delete delete-contact}]
  ])

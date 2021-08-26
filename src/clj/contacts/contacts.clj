(ns contacts.contacts
  (:require [contacts.db :as db]
            [cheshire.core :as json]))

(defn get-contacts
  [_] ;req]
  {:status 200
   :body (db/get-contacts db/config)})

(defn request-body->map
  [request]
  (-> request
      :body
      slurp
      (json/parse-string true)))
;;(json/parse-string (slurp (:body request)) true)

(defn create-contact
  [{:keys [parameters]}]
  (let [;data (:body parameters)
        response (:parameters (request-body->map parameters))
        ;first-name (:first-name parameters);data)
        ;created-id (db/insert-contact db/config data)
        ]
    ;(println :keys)
    (println response)
    ;(println :body)
    {:status 201
     :body (json/encode {:json true
                         :response response})
        ;"parameters" ;first-name ;"" ; (db/get-contact-by-id db/config created-id)
     })
  )

(defn get-contact-by-id
  [{:keys [parameters]}]
  (let [id (:path parameters)]
    {:status 201
     :body (db/get-contact-by-id db/config id)}))

(defn update-contact
  [{:keys [parameters]}]
  (let [id (get-in parameters [:path :id])
        body (:body parameters)
        data (assoc body :id id)]
    ;(db/update-contact-by-id db/config data)
    {:status 200
     :body (db/get-contact-by-id db/config {:id id})}))

(defn delete-contact
  [{:keys [parameters]}]
  (let [id (:path parameters)
        before-deleted (db/get-contact-by-id db/config id)]
    (db/delete-contact-by-id db/config id)
    {:status 200
     :body {:deleted true?
            :contact before-deleted}}))

(comment
  (db/insert-contact db/config {:first-name "John"
                                :last-name "Smith"
                                :email "john.smith@email.com"}))
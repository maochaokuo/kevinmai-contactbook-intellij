(ns kevinmai-contactbook-intellij.db
  (:require ;[hugsql :as hugsql]
            [honey.sql :as sql]
            [honey.sql.helpers :refer :all :as h]
            [next.jdbc :as jdbc]))

;; connectTimeout / socketTimeout via db-spec:
(def db {:dbtype "postgres" :dbname "postgres" :user "postgres"
         :password "root"
          ;; milliseconds:
          :connectTimeout 60000 :socketTimeout 30000})
(def ds (jdbc/get-datasource db))
;; queryTimeout via options:
;(jdbc/execute! ds ["select * from drugs"] {:timeout 5}) ; seconds
(defn create-contacts-table []
  (jdbc/execute! ds [ "
 create table contacts (
     id serial primary key,
        first_name text,
        last_name text,
        email text,
        created_at timestamp not null default current_timestamp
        )"]))


(defn get-contacts []
  (jdbc/execute! ds (-> '{select (*) from contacts}
                        (sql/format))))

(defn get-contact-by-id [{:keys [parameters]}]
  (let [id (:path parameters)]
    (jdbc/execute! ds (-> '{select (*) from (contacts) where
                              (= f.id id)}
                          (sql/format))))

(defn create-contact [{:keys [parameters]}]
  (let [first_name (:first_name parameters)
        last_name (:last_name parameters)
        email (:email parameters)]
    (jdbc/execute! ds (-> (insert-into :contacts)
                          (values [{:first_name first_name
                                    :last_name last_name
                                    :email email}])))
    (print "return key ")
    (print :return-keys)
  )

(defn update-contact [{:keys [parameters]}]
  (let [id (:id parameters)
        first_name (:first_name parameters)
        last_name (:last_name parameters)
        email (:email parameters)]
    (jdbc/execute! ds (-> (h/update :contacts)
                          (set {:first_name first_name
                                :last_name last_name
                                :email email})
                          (where [:= :id id])
                          (sql/format {:pretty true})))
    )

(defn delete-contact [[:key [parameters]]]
  (let [id (:id parameters)]
    (jdbc/execute! ds (-> (delete-from :contacts)
                          (where [:= :id id])
                          (sql/format)))))

(comment
  (create-contacts-table )
  (get-contacts)
  (get-contact-by-id "baz")
  (jdbc/execute! ds ["select * from drugs"] {:timeout 5}) ; seconds




  ;(def config
  ;  {:classname "org.postgresql.Driver"
  ;   :subprotocal "postgresql"
  ;   :subname "//localhost:5432/clj_contacts"
  ;   :user "postgres"
  ;   :password "tm3456"
  ;   })
  ;(hugsql/def-db-fns "contacts.sql")
  ;(create-contacts-table config)
  )

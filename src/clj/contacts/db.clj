(ns contacts.db
  (:require ;[hugsql :as hugsql]
            [honey.sql :as sql]
            [honey.sql.helpers :refer :all :as h]
            [next.jdbc :as jdbc]))

;; connectTimeout / socketTimeout via db-spec:
(def config {:dbtype      "postgres" :dbname "postgres" :user "postgres"
         :password        "postgres"          ;; milliseconds:
          :connectTimeout 60000 :socketTimeout 30000})

(def ds (jdbc/get-datasource config))
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

(defn get-contacts0 []
  (jdbc/execute! ds (-> '{select (*) from contacts}
                        (sql/format))))

(defn get-contacts [config]
  (jdbc/execute! (jdbc/get-datasource config)
                 (-> '{select (*) from contacts}
                        (sql/format))))

(defn get-contact-by-id0 [id]
  (let [sql "null"]
    (jdbc/execute! ds (-> (select :*) (from :contacts)
                          (where [:= :id id])
                          (sql/format)))))

(defn get-contact-by-id [config id]
  (let [sql "null"]
    (jdbc/execute! (jdbc/get-datasource config)
                   (-> (select :*) (from :contacts)
                          (where [:= :id id])
                          (sql/format)))))

(defn create-contact0a [first_name last_name email]
  (let [sql "null"]
   ;(print first_name  last_name  email)
   (jdbc/execute! ds ["insert into contacts
        (first_name, last_name, email)
        values ( ?, ?, ?)
   "   first_name last_name email ;{:return-keys true}
                      ])
    ;(print "return key ")
    (print :return-keys))
  )

(defn create-contact0b [fields] ; [{:keys [parameters]}]
  (let [sql (str "insert into contacts
        (first_name, last_name, email)
        values (" fields ")")]
    ;(println fields)
    ;(println sql)
    (jdbc/execute! ds [sql
                       ;{:return-keys true}
                       ])
    ;(print "return key ")
    (print :return-keys))
  )

(defn create-contact [config fields]
  (let [sql (str "insert into contacts
        (first_name, last_name, email)
        values (" fields ")")]
    ;(println fields)
    ;(println sql)
    (jdbc/execute! (jdbc/get-datasource config) [sql])
    ;(print "return key ")
    (print :return-keys))
  )

(defn update-contact0 [id first_name last_name email]
  (let [sql (-> (h/update :contacts)
                (set {:first_name first_name
                       :last_name last_name
                       :email email})
                (where [:= :id id])
                (sql/format {:pretty true}))]
    (println sql)
    (jdbc/execute! ds sql)
    ))

(defn update-contact-by-id [config id first_name last_name email]
  (let [sql (-> (h/update :contacts)
                (set {:first_name first_name
                      :last_name last_name
                      :email email})
                (where [:= :id id])
                (sql/format {:pretty true}))]
    ;(println sql)
    (jdbc/execute! (jdbc/get-datasource config) sql)
    ))

(defn delete-contact-by-id [config id]
  (let [sql (-> (delete-from :contacts)
                (where [:= :id id])
                (sql/format))]
    (jdbc/execute! (jdbc/get-datasource config)
                   sql)))

(comment
  (create-contacts-table )
  (get-contacts0)
  (get-contacts config)
  (create-contact0a "Kevin" "Mai" "kevin.mai@email.com"
    ;[{:first_name "Kevin" :last_name "Mai" :email "kevin.mai@email.com"}]
    )
  (create-contact config "'Kevin2', 'Mai2', 'kevin2.mai@email.com'" )
  (get-contact-by-id config 4)
  (update-contact config 4 "Kevin4b" "Mai4b" "kevin4b.mai@email.com")
  (delete-contact config 2)
  ;(-> (:update :contacts)
  ;    (:set {:first_name "first_name"
  ;           :last_name "last_name"
  ;           :email "email"})
  ;    (:where [:= :id 1])
  ;    (sql/format ;{:pretty true}
  ;                ))
  (-> (h/update :contacts)
      (set {:first_name "first_name"
             :last_name "last_name"
             :email "email"})
      (where [:= :id 1])
      (sql/format {:pretty true}
        ))

  ;(defn aaa [p1 p2]
  ;  (print p1)
  ;  (print p2))
  ;
  ;(aaa "p1" "p2")

  ;(jdbc/execute! ds ["select * from drugs"] {:timeout 5}) ; seconds
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

(defn insertest [{:keys [parameters]}]
  (let [data (:body parameters)
        first-name (:first-name data)
        last-name (:last-name data)
        email (:email data)
        ]
    (println first-name "," last-name "," email)
    (println data)
    (println parameters)
    ))
(defn itest1 [& {:keys [parameters] :as opts}]
  (println parameters)
  (println opts)
  )
(defn destr [& {:keys [a b] :as opts}]
  [a b opts])
(comment
  (destr :a 1)
  (destr {:a 1 :b 2})
  (itest1 {:parameters 123})
  (itest1 [{:first-name "Kevin"
           :last-name "Mai"
           :email "kevin.mai@email.com"}])
  (insertest  {:first-name "Kevin"
              :last-name "Mai"
              :email "kevin.mai@email.com"})
  (-> (h/update :contacts)
      (set {:first_name "first_name"
            :last_name "last_name"
            :email "email"})
      (where [:= :id 1])
      (sql/format {:pretty true}
                  ))
  )
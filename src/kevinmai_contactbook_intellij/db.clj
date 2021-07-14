(ns kevinmai-contactbook-intellij.db
  (:require [hugsql.core :as hugsql]))

(def config
  {:classname "org.postgresql.Driver"
   :subprotocal "postgresql"
   :subname "//localhost:5432/clj_contacts"
   :user "postgres"
   :password "postgres"
   })

(hugsql/def-db-fns "contacts.sql")

(comment
  (create-contacts-table config)
  )
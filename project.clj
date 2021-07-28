(defproject kevinmai-contactbook-intellij "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.10.1"]
                 [metosin/reitit "0.5.2"]
                 [http-kit "2.3.0"]
                 [org.postgresql/postgresql "42.2.2"]
                 [com.layerware/hugsql "0.5.1"]
                 [com.github.seancorfield/next.jdbc "1.2.674"]
                 [com.github.seancorfield/honeysql "2.0.0-rc5"]

                 ]
  :repl-options {:init-ns kevinmai-contactbook-intellij.core})

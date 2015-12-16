(defproject printer "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [environ "0.5.0"]
                 [com.novemberain/langohr "3.0.0-rc2"]
                 [clj-json "0.3.2"]
                 [yesql "0.5.1"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.1"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]]
  :plugins [[lein-environ "1.0.1"]]
  :main ^:skip-aot printer.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})


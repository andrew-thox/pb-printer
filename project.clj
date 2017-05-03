(defproject printer "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                  ;Elastic
                 [cc.qbits/spandex "0.3.9"]
                 ; Environment
                 [environ "0.5.0"]
                 [com.novemberain/langohr "3.0.0-rc2"]
                 ;Database dependencies
                 [yesql "0.5.1"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.1"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 ;Hashing functions
                 [pandect "0.5.4"]
                 ;avro
                 [com.damballa/abracad "0.4.13"]
                 ;statsd
                 [clj-statsd "0.3.11"]
                 [org.clojure/core.async "0.3.442"]]
  :plugins [[lein-environ "1.0.1"]
            [lein-gorilla "0.4.0"]]
  :main ^:skip-aot printer.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :migratus{:store :database
            :migration-dir "migrations"
            :db ~(get (System/getenv) "DATABASE_URI")})



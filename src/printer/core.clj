(ns printer.core
  (:gen-class)
  (:require [printer.consumers :as consumers]
            [environ.core :refer [env]]
            [langohr.channel :as lch]
            [langohr.core :as rmq]
            [langohr.queue :as lq]
            [langohr.consumers :as lc]
            [clj-statsd :as statsd]))

(defn -main [& args]
  (let [conn  (rmq/connect {:uri (env :amqp-url)})
        ch    (lch/open conn)]
    (println (format "[main] Connected. Channel id: %d" (.getChannelNumber ch)))
    (lq/declare ch (env :queue-name) {:exclusive false :auto-delete false})
    (lc/subscribe ch (env :queue-name) consumers/article-consumer {:auto-ack true})
    (.addShutdownHook (Runtime/getRuntime) (Thread. #(do (rmq/close ch) (rmq/close conn))))))
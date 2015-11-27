(ns printer.core
  (:gen-class)
  (:require [environ.core :refer [env]]
            [langohr.channel :as lch]
            [langohr.core :as rmq]
            [langohr.queue :as lq]))

(defn -main [& args]
  (let [conn  (rmq/connect {:uri (env :amqp-url)})
        ch    (lch/open conn)]
    (println (format "[main] Connected. Channel id: %d" (.getChannelNumber ch)))
    (lq/declare ch (env :queue-name) {:exclusive false :auto-delete false})
    (.addShutdownHook (Runtime/getRuntime) (Thread. #(do (rmq/close ch) (rmq/close conn))))))
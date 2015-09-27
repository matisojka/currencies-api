(ns currencies-api.main
  (:use currencies-api.handler
        [org.httpkit.server :only [run-server]])
  (:gen-class))

(defn -main [& [port]]
  (let [port (if port (Integer/parseInt port) 4000)]
    (run-server app {:port port})))


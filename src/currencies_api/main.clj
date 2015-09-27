(ns currencies-api.main
  (:use currencies-api.handler
        [org.httpkit.server :only [run-server]])
  (:gen-class))

(defn -main [&]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "4000"))]
    (run-server app {:port port})
    (println (str "Server started on port " port))))


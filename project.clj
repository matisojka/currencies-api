(defproject currencies-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [ring-server "0.3.1"]
                 [http-kit "2.1.18"]
                 [liberator "0.13"]
                 [cheshire "5.3.1"]]
  :min-lein-version "2.0.0"
  :main currencies-api.main
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler currencies-api.handler/app
         :init currencies-api.handler/init
         :destroy currencies-api.handler/destroy}
  :uberjar-name "currencies-api.jar"
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? true, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.3.1"]]}})

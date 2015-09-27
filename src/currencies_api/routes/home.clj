(ns currencies-api.routes.home
  (:require [compojure.core :refer :all]
            [liberator.core
             :refer [defresource request-method-in]]
            [cheshire.core :refer [generate-string]]
            [currencies-api.models.exchange
             :refer [calculate-currency-values]]))

(defresource currency
  :allowed-methods [:get]
  :available-media-types ["application/json"]
  :handle-ok (fn [context]
               (let [params (get-in context [:request :params])
                     currency-params
                     (update-in
                       (select-keys params [:value :base-currency]) [:value] #(Integer/parseInt %))]
                 (generate-string
                   (calculate-currency-values currency-params)))))

(defroutes home-routes
  (ANY "/" request currency))


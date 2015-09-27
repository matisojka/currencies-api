(ns currencies-api.routes.home
  (:require [compojure.core :refer :all]
            [liberator.core
             :refer [defresource request-method-in]]
            [cheshire.core :refer [generate-string]]))

(def exchange-rates
  {:CHF {:EUR 0.9 :USD 1.1}})

(defn get-exchange-rates
  [base-currency]
  (get exchange-rates base-currency))

(defn calculate-currency-value
  [{:keys [value base-currency]}]
  (let [base-currency (keyword base-currency)
        exchange-rates (get-exchange-rates base-currency)]
    exchange-rates))

(defresource currency
  :allowed-methods [:get]
  :available-media-types ["application/json"]
  :handle-ok (fn [context]
               (let [params (get-in context [:request :params])
                     currency-params (select-keys params [:value :base-currency])]
                 (generate-string
                   (calculate-currency-value currency-params)))))

(defroutes home-routes
  (ANY "/" request currency))


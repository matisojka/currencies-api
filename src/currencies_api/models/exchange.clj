(ns currencies-api.models.exchange
  (:require [cheshire.core :as json]))

(defn fetch-exchange-rates
  [base-currency target-currencies]
  (let [exchange-rate-endpoint
        (str "http://api.fixer.io/latest?base=" base-currency "&symbols=" target-currencies)]
  (slurp exchange-rate-endpoint)))

(defn exchange-rates
  [base-currency target-currencies]
  ((json/parse-string (fetch-exchange-rates base-currency target-currencies) true) :rates))

(defn calculate-currency-values
  [{:keys [value base-currency target-currencies]}]
  (let [exchange-rates (exchange-rates base-currency target-currencies)
        map-vals (fn [f m]
                   (into {} (for [[k v] m]
                              [k (f v)])))]
    (map-vals #(* value %) exchange-rates)))


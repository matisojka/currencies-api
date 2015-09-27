(ns currencies-api.models.exchange
  (:require [cheshire.core :as json]))

(defn fetch-exchange-rates
  [base-currency]
  (let [exchange-rate-endpoint
        (str "http://api.fixer.io/latest?base=" base-currency "&symbols=EUR,USD")]
  (slurp exchange-rate-endpoint)))

(defn exchange-rates
  [base-currency]
  ((json/parse-string (fetch-exchange-rates base-currency) true) :rates))

(defn calculate-currency-values
  [{:keys [value base-currency]}]
  (let [exchange-rates (exchange-rates base-currency)
        map-vals (fn [f m]
                   (into {} (for [[k v] m]
                              [k (f v)])))]
    (map-vals #(* value %) exchange-rates)))


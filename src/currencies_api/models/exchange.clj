(ns currencies-api.models.exchange
  (:require [cheshire.core :as json]
            [clojure.core.cache :as cache]))

(def Cache (atom (cache/fifo-cache-factory {} :ttl 60)))

(defn is-in-cache?
  [cache-key]
  (cache/has? @Cache cache-key))

(defn read-from-cache
  [cache-key]
  (cache/lookup @Cache cache-key))

(defn write-to-cache
  [cache-key value]
  (if (is-in-cache? cache-key)
    (swap! Cache #(cache/hit % cache-key))
    (swap! Cache #(cache/miss % cache-key value))))

(defn fetch-exchange-rates
  [base-currency target-currencies]
  (let [exchange-rate-endpoint
        (str "http://api.fixer.io/latest?base=" base-currency "&symbols=" target-currencies)
        cache-key (str "rates/" base-currency "/" target-currencies)]
    (if (is-in-cache? cache-key)
      (read-from-cache cache-key)
      (let [rates (slurp exchange-rate-endpoint)]
        (write-to-cache cache-key rates)
        rates))))

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


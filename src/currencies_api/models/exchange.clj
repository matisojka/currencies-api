(ns currencies-api.models.exchange)

(def exchange-rates
  {:CHF {:EUR 0.9 :USD 1.1}})

(defn get-exchange-rates
  [base-currency]
  (get exchange-rates base-currency))

(defn calculate-currency-values
  [{:keys [value base-currency]}]
  (let [value (Integer/parseInt value)
        base-currency (keyword base-currency)
        exchange-rates (get-exchange-rates base-currency)
        map-vals (fn [f m]
                   (into {} (for [[k v] m]
                              [k (f v)])))]
    (map-vals #(* value %) exchange-rates)))


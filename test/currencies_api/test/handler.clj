(ns currencies-api.test.handler
  (:use clojure.test
        ring.mock.request
        currencies-api.handler)
  (:require [cheshire.core :as json]))

(def mock-exchange-rates
  {#'currencies-api.models.exchange/exchange-rates (fn [_] {:USD 1.1 :EUR 0.9})})

(deftest test-app
  (testing "main route"
    (let [response
          (with-redefs-fn
            mock-exchange-rates
            #(app (request :get "/?value=5&base-currency=CHF")))
          body
          (json/parse-string (:body response) true)]
      (is (= (:status response) 200))
      (is (= body {:USD 5.5 :EUR 4.5}))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))

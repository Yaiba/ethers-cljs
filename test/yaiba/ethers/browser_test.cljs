(ns yaiba.ethers.browser-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [yaiba.ethers.test-helper.web3 :as web3]))

(enable-console-print!)

(deftest test-running-env
  (is (= (web3/running-in-browser?) true)))

(deftest test-ethers
  (let [provider (web3/get-provider)]
    (js/console.log provider)
    ))

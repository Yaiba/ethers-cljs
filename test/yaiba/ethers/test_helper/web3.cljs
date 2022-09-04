(ns yaiba.ethers.test-helper.web3
  (:require [yaiba.ethers.provider :as yep]))

(defn running-in-browser? []
  (and (exists? js/window) (exists? js/document)))

(defn get-provider []
  (if (running-in-browser?)
    (yep/web3-provider (.-ethereum js/window))
    (yep/rpc-provider "http://localhost:8545")))

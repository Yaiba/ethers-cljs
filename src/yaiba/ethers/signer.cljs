(ns yaiba.ethers.signer
  (:require
   ["ethers" :as es]
   [oops.core :refer [ocall ocall+ oapply+]]))


(def Signer (oget es "providers" "JsonRpcSigner"))

(defn get-address
  [signer]
  (ocall signer "getAddress"))

;; blockchain methods
(defn get-balance
  [signer & [_block-tag :as args]]
  (oapply+ signer "getBalance" args))

(defn get-chain-id
  [signer]
  (ocall signer "getChainId"))

(defn get-gas-price
  [signer]
  (ocall signer "getGasPrice"))

(defn get-transaction-count
  [signer & [_block-tag :as args]]
  (oapply+ signer "getTransactionCount" args))

(defn call
  [signer tx-request]
  (ocall+ signer "call" tx-request))

(defn estimate-gas
  "Return BigNumber"
  [signer tx-request]
  (ocall+ signer "estimateGas" tx-request))

(defn resolve-name
  [signer ens-name]
  (ocall+ signer "resolveName" ens-name))

;; signing
(defn sign-message
  [signer msg]
  (ocall+ signer "signMessage" msg))

(defn sign-transaction
  [signer tx-request]
  (ocall+ signer "signTransaction" tx-request))

(defn send-transaction
  [signer tx-request]
  (ocall+ signer "sendTransaction" tx-request))

(defn sign-typed-data
  [signer domain types value]
  (ocall+ signer "_signTypedData" domain types value))

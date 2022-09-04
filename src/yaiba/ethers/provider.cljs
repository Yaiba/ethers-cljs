(ns yaiba.ethers.provider
  (:require
   ["ethers" :as es]
   [oops.core :refer [oget oset! ocall oget+ ocall+ oapply oapply+]]
   [yaiba.ethers.utils :as utils]))

(def Web3Provider (oget es "providers" "Web3Provider"))
(def JsonRpcProvider (oget es "providers" "JsonRpcProvider"))

(defn web3-provider
  "Return a Web3Provider from eth-provider. eth-provider should follow EIP 1193"
  [eth-provider]
  (Web3Provider eth-provider))

(defn rpc-provider
  ([]
   (JsonRpcProvider.))
  ([url]
   (JsonRpcProvider. url)))

(defn default-provider
  "Return a FallbackProvider."
  [[network & [options :as args]]]
  (ocall es "getDefaultProvider" args))

(defn rpc-request
  "EIP 1193 standard, send rpc request"
  [provider & [method-name _prarms :as args]]
  (oapply+ provider "send" args))

(defn rpc-handler
  [provider method-name on-success-fn on-error-fn args]
  (-> (oapply+ (oget provider "send") method-name args)
      (.then on-success-fn)
      (.catch on-error-fn)
      (.finally true)))

(defn get-signer
  [provider]
  (ocall provider "getSigner"))

;; account methods
(defn get-balance
  [provider & [address _block-tag :as args]]
  (oapply+ provider "getBalance" address args))

(defn get-code
  [provider & [address _block-tag :as args]]
  (oapply+ provider "getCode" args))

(defn get-transaction-count
  [provider & [address _block-tag :as args]]
  (oapply+ provider "getTransactionCount" args))

(defn get-storage-at
  [provider & [address pos _block-tag :as args]]
  (oapply+ provider "getStorageAt" args))


;; blocks methods
(defn get-block
  [provider block]
  (ocall+ provider "getBlock" block))

(defn get-block-with-transactions
  [provider block]
  (ocall+ provider "getBlockWithTransactions" block))

;; ens
(defn get-avatar
  "Return the url for the avatar associated to the ens name"
  [provider name]
  (ocall+ provider "getAvatar" name))

(defn get-resolver
  "Return an EnsResolver instance"
  [provider name]
  (ocall+ provider "getResolver" name))

(defn lookup-address
  "Return name of address"
  [provider address]
  (ocall+ provider "lookupAddress" address))

(defn resolve-name
  "Return the address of name"
  [provider name]
  (ocall+ provider "resolveName" name))

;; logs methods
(defn get-logs
  "Return Array<Log>"
  [provider filter]
  (ocall+ provider "getLogs" filter))

;; network status methods
(defn get-network
  "Return Network map"
  [provider]
  (ocall provider "getNetwork"))

(defn get-block-number
  [provider]
  (ocall provider "getBlockNumber"))

(defn get-gas-price
  "Return BigNumber"
  [provider]
  (ocall provider "getGasPrice"))

(defn get-fee-data
  "return FeeData"
  [provider]
  (ocall provider "getFeeData"))

(defn ready
  "Return Network"
  [provider]
  (ocall provider "ready"))

;; Transactions Methods
(defn call
  "Return string<DataHexString>"
  [provider & [transaction _block-tag :as args]]
  (oapply+ provider "call" args))

(defn estimateg-gas
  "Return BigNumber"
  [provider transaction]
  (ocall+ provider "estimateGas" transaction))

(defn get-transaction
  "Return TransactionResponse"
  [provider hash]
  (ocall+ provider "getTransaction" hash))

(defn get-transaction-receipt
  "Return TransactionReceipt"
  [provider hash]
  (ocall+ provider "getTransactionReceipt" hash))

(defn send-transaction
  "Return TransactionResponse"
  [provider transaction]
  (ocall+ provider "sendTransaction" transaction))

(defn wait-for-transaction
  "Return TxReceipt"
  [provider & [hash _confirms _timeout :as args]]
  (oapply+ provider "waitForTransaction" args))

;; Event emitter methods

(defn event-call
    "Call provider event method.
   event are:
   - named event
   - transaction event
   - filter events
  "
    [provider method-name event listener]
    (ocall+ provider method-name event listener))
(defn on
  [provider event-name listener]
  (ocall+ provider "on" event-name listener))

(defn once
  [provider event-name listener]
  (ocall+ provider "once" event-name listener))

(defn emit
  "Return boolean"
  [provider & [event-name _args :as  args]]
  (oapply+ provider "emit" args))

(defn off
  [provider & [event-name _listener :as args]]
  (oapply+ provider "off" args))

(defn remove-all-listeners
  [provider & [event-name :as args]]
  (oapply+ provider "remoteAllListeners" args))

(defn listener-count
  "Return number"
  [provider & [event-name :as args]]
  (oapply+ provider "listenerCount" args))

(defn listeners
  "Return Array<Listener>"
  [provider event-name]
  (ocall+ provider "listeners" event-name))


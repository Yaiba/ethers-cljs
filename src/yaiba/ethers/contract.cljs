(ns yaiba.ethers.contract
  (:require
   ["ethers" :as es]
   [oops.core :refer [oget+ oapply+]]
   [yaiba.ethers.utils :as utils]))


(defn contract
  "Return a contract instance from abi(#js) "
  [provider-or-signer addr abi]
  (es/Contract. addr abi provider-or-signer))

(defn call
  [contract method & args]
  (if (oget+ contract method)
    (oapply+ contract method args)
    (throw (js/Error. (str "Method: " method " was not found in contract")))))

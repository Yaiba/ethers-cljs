(ns yaiba.ethers.test-helper.smart-contract
  ;;(:require-macros [yaiba.ethers.test-helper.macros :refer [slurp]])
  (:require-macros [yaiba.ethers.test-helper.macros :as m])
  (:require [oops.core :refer [oget]]))
;; :refer-macros in cljs, :refer in clj

(def build-info (js/JSON.parse (m/slurp ".build/__local__.json")))
(def abi (oget build-info "contractTypes" "Token" "abi"))

(def smart-contracts
  {:erc20
   {:name "YToken"
    :address "0x274b028b03A250cA03644E6c578D81f019eE1323"
    :abi abi
    :build build-info
    }})

(def account
  {:owner "0x1e59ce931B4CFea3fe4B875411e280e173cB7A9C"
   :receiver "0xc89D42189f0450C2b2c3c61f58Ec5d628176A1E7"})

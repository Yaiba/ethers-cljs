(ns yaiba.ethers.core
  (:require ["ethers" :as es]
            [oops.core :refer [ocall]]))

(defn to-big-number
  [big-numberish]
  (ocall es/BigNumber "from" big-numberish))
